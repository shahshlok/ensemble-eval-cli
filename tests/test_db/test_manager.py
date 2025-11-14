"""Tests for db.manager module."""

from __future__ import annotations

import json
import sqlite3
from pathlib import Path
from unittest.mock import patch

import pytest

from db.manager import (
    DatabaseManager,
    init_db,
    ingest_results_file,
    restore_json_files,
    DEFAULT_DB_PATH,
    SCHEMA_FILE,
)


class TestDatabaseManager:
    """Test DatabaseManager class."""

    def test_init_default_path(self):
        """Test DatabaseManager initialization with default path."""
        manager = DatabaseManager()
        assert manager.db_path == DEFAULT_DB_PATH

    def test_init_custom_path(self, temp_db_file):
        """Test DatabaseManager initialization with custom path."""
        manager = DatabaseManager(str(temp_db_file))
        assert manager.db_path == str(temp_db_file)

    def test_init_db_success(self, temp_db_file):
        """Test successful database initialization."""
        manager = DatabaseManager(str(temp_db_file))

        # Should not raise an exception
        manager.init_db()

        # Verify tables were created
        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
            tables = [row[0] for row in cursor.fetchall()]
            assert "runs" in tables
            assert "evaluations" in tables

    def test_init_db_missing_schema(self, temp_db_file):
        """Test database initialization with missing schema file."""
        with patch("db.manager.SCHEMA_FILE", Path("/nonexistent/schema.sql")):
            manager = DatabaseManager(str(temp_db_file))

            with pytest.raises(FileNotFoundError, match="Schema file not found"):
                manager.init_db()

    def test_init_db_idempotent(self, temp_db_file):
        """Test that init_db can be called multiple times safely."""
        manager = DatabaseManager(str(temp_db_file))

        # Call init_db twice - should not fail
        manager.init_db()
        manager.init_db()

        # Database should still be functional
        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT COUNT(*) FROM runs")
            assert cursor.fetchone()[0] == 0

    def test_parse_filename_valid(self):
        """Test parsing valid filenames."""
        manager = DatabaseManager()

        strategy, timestamp = manager._parse_filename("results_direct_2025-11-13T17-52-45.json")
        assert strategy == "direct"
        assert timestamp == "2025-11-13T17-52-45"

        strategy, timestamp = manager._parse_filename("results_eme_2025-01-01T00-00-00.json")
        assert strategy == "eme"
        assert timestamp == "2025-01-01T00-00-00"

    def test_parse_filename_invalid_strategy(self):
        """Test parsing filename with invalid strategy."""
        manager = DatabaseManager()

        with pytest.raises(ValueError, match="Invalid strategy"):
            manager._parse_filename("results_invalid_2025-11-13.json")

    def test_parse_filename_invalid_format(self):
        """Test parsing filename with invalid format."""
        manager = DatabaseManager()

        with pytest.raises(ValueError, match="doesn't match expected format"):
            manager._parse_filename("invalid_filename.json")

        with pytest.raises(ValueError, match="doesn't match expected format"):
            manager._parse_filename("results_direct.json")

    def test_ingest_results_file_success(
        self, temp_db_file, temp_json_file, sample_evaluation_list
    ):
        """Test successful ingestion of results file."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        # Should not raise an exception
        manager.ingest_results_file(temp_json_file)

        # Verify data was inserted
        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()

            # Check runs table
            cursor.execute("SELECT COUNT(*) FROM runs")
            assert cursor.fetchone()[0] == 1

            cursor.execute("SELECT strategy, source_file FROM runs")
            run = cursor.fetchone()
            assert run[0] == "direct"  # strategy from filename
            assert "results_direct_2025-11-13T17-52-45.json" in run[1]

            # Check evaluations table
            cursor.execute("SELECT COUNT(*) FROM evaluations")
            assert cursor.fetchone()[0] == len(sample_evaluation_list)

    def test_ingest_results_file_nonexistent(self, temp_db_file):
        """Test ingestion of nonexistent file."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        with pytest.raises(FileNotFoundError, match="Results file not found"):
            manager.ingest_results_file(Path("/nonexistent/file.json"))

    def test_restore_json_files_success(self, temp_db_file, temp_dir, sample_evaluation_list):
        """Test successful restoration of JSON files."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        # First ingest some data
        results_file = temp_dir / "results_direct_2025-11-13T17-52-45.json"
        results_file.write_text(json.dumps(sample_evaluation_list, indent=2))
        manager.ingest_results_file(results_file)

        # Now restore
        output_dir = temp_dir / "restored"
        files_restored = manager.restore_json_files(output_dir)

        assert files_restored == 1
        restored_file = output_dir / "results_direct_2025-11-13T17-52-45.json"
        assert restored_file.exists()

        # Verify content
        restored_data = json.loads(restored_file.read_text())
        assert len(restored_data) == len(sample_evaluation_list)

    def test_restore_json_files_empty_db(self, temp_db_file, temp_dir):
        """Test restoration from empty database."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        output_dir = temp_dir / "restored"
        files_restored = manager.restore_json_files(output_dir)

        assert files_restored == 0
        assert output_dir.exists()

    def test_restore_json_files_multiple_runs(self, temp_db_file, temp_dir, sample_evaluation_list):
        """Test restoration with multiple runs."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        # Ingest multiple files
        for strategy in ["direct", "reverse", "eme"]:
            results_file = temp_dir / f"results_{strategy}_2025-11-13T17-52-45.json"
            results_file.write_text(json.dumps(sample_evaluation_list, indent=2))
            manager.ingest_results_file(results_file)

        # Restore
        output_dir = temp_dir / "restored"
        files_restored = manager.restore_json_files(output_dir)

        assert files_restored == 3

        # Verify all files exist
        for strategy in ["direct", "reverse", "eme"]:
            restored_file = output_dir / f"results_{strategy}_2025-11-13T17-52-45.json"
            assert restored_file.exists()

    def test_restore_json_files_with_validation(
        self, temp_db_file, temp_dir, sample_evaluation_list
    ):
        """Test restoration with validation enabled."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        # Ingest data
        results_file = temp_dir / "results_direct_2025-11-13T17-52-45.json"
        results_file.write_text(json.dumps(sample_evaluation_list, indent=2))
        manager.ingest_results_file(results_file)

        # Restore with validation (currently just passes through)
        output_dir = temp_dir / "restored"
        files_restored = manager.restore_json_files(output_dir, validate=True)

        assert files_restored == 1


class TestConvenienceFunctions:
    """Test convenience functions."""

    def test_init_db_function(self, temp_db_file):
        """Test init_db convenience function with explicit path."""
        init_db(str(temp_db_file))

        # Verify database was created
        assert temp_db_file.exists()
        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
            tables = [row[0] for row in cursor.fetchall()]
            assert "runs" in tables

    def test_ingest_results_file_function(self, temp_db_file, temp_json_file):
        """Test ingest_results_file convenience function."""
        # Initialize database first
        init_db(str(temp_db_file))

        # Use convenience function
        ingest_results_file(str(temp_db_file), temp_json_file)

        # Verify data was inserted
        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT COUNT(*) FROM evaluations")
            assert cursor.fetchone()[0] > 0

    def test_restore_json_files_function(self, temp_db_file, temp_dir, sample_evaluation_list):
        """Test restore_json_files convenience function."""
        # Initialize and populate database
        init_db(str(temp_db_file))

        results_file = temp_dir / "results_direct_2025-11-13T17-52-45.json"
        results_file.write_text(json.dumps(sample_evaluation_list, indent=2))
        ingest_results_file(str(temp_db_file), results_file)

        # Use convenience function
        output_dir = temp_dir / "restored"
        files_restored = restore_json_files(str(temp_db_file), output_dir)

        assert files_restored == 1
        assert (output_dir / "results_direct_2025-11-13T17-52-45.json").exists()


class TestDatabaseIntegrity:
    """Minimal integrity tests (kept lean for research use)."""

    def test_unique_constraint_evaluations(self, temp_db_file, temp_dir, sample_evaluation):
        """Basic smoke test for uniqueness: two runs with same student should both insert."""
        manager = DatabaseManager(str(temp_db_file))
        manager.init_db()

        duplicate_data = [sample_evaluation]
        results_file = temp_dir / "results_direct_2025-11-13T17-52-45.json"
        results_file.write_text(json.dumps(duplicate_data, indent=2))

        # Ingest the same file twice; this should not crash in typical research usage.
        manager.ingest_results_file(results_file)
        manager.ingest_results_file(results_file)

        with sqlite3.connect(temp_db_file) as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT COUNT(*) FROM evaluations")
            count = cursor.fetchone()[0]
            assert count == 2
