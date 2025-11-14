"""Integration-style tests for cli.benchmark and helpers.

These tests stay lightweight by mocking out heavy dependencies:
- AI model calls
- Database ingestion
- Rich console rendering
"""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict
from unittest.mock import AsyncMock, patch

import pytest

import cli


def _set_env(monkeypatch: pytest.MonkeyPatch, question: str = "Q?", rubric: Dict[str, Any] | None = None):
    monkeypatch.setenv("QUESTION", question)
    if rubric is None:
        rubric = {"correctness": {"weight": 100, "description": "All or nothing"}}
    monkeypatch.setenv("RUBRIC", json.dumps(rubric))


def _make_submissions(tmp_path: Path, count: int = 2) -> Path:
    root = tmp_path / "student_submissions"
    for i in range(count):
        student_dir = root / f"Student_{i}"
        student_dir.mkdir(parents=True, exist_ok=True)
        (student_dir / "SumCalculator.java").write_text(
            "public class SumCalculator { public static void main(String[] args) {} }",
            encoding="utf-8",
        )
    return root


@pytest.mark.asyncio
async def test_run_benchmark_async_single_mode(tmp_path: Path, monkeypatch: pytest.MonkeyPatch):
    """_run_benchmark_async should discover submissions and save results for a single mode."""
    _set_env(monkeypatch)
    submissions_root = _make_submissions(tmp_path, count=2)

    monkeypatch.chdir(tmp_path)

    fake_results = [
        {
            "student": "Student_0",
            "gpt5_nano_result": {"total_score": 80, "max_possible_score": 100},
            "gpt_oss_120b_result": {"total_score": 82, "max_possible_score": 100},
            "metrics": {
                "gpt5_nano": {"total": 80.0, "max": 100.0, "pct": 80.0},
                "gpt_oss_120b": {"total": 82.0, "max": 100.0, "pct": 82.0},
                "avg_pct": 81.0,
                "diff_pct": 2.0,
                "flag": "✅",
                "comment": "Models agree within tolerance",
            },
        }
    ]

    # Patch direct grading mode to avoid real model calls
    with (
        patch("cli._discover_submissions", return_value=list(submissions_root.rglob("*.java"))),
        patch("cli.run_direct_grading", new_callable=AsyncMock, return_value=fake_results) as mock_run_direct,
        patch("cli._display_mode_results") as mock_display,
        patch("cli._save_results") as mock_save,
    ):
        await cli._run_benchmark_async("direct", advanced=False)

    mock_run_direct.assert_awaited_once()
    mock_display.assert_called_once()
    mock_save.assert_called_once()


def test_save_results_creates_json_and_calls_db(tmp_path: Path, monkeypatch: pytest.MonkeyPatch):
    """_save_results should write a JSON file and attempt DB ingestion."""
    monkeypatch.chdir(tmp_path)
    results = [
        {
            "student": "Student_0",
            "gpt5_nano_result": {"total_score": 80, "max_possible_score": 100, "_raw": "x"},
            "gpt_oss_120b_result": {"total_score": 82, "max_possible_score": 100},
            "metrics": {"avg_pct": 81.0, "diff_pct": 2.0, "flag": "✅"},
        }
    ]

    with patch("cli.db_manager.init_db") as mock_init_db, patch(
        "cli.db_manager.ingest_results_file"
    ) as mock_ingest:
        cli._save_results("direct", results)

    data_dir = tmp_path / "data"
    files = list(data_dir.glob("results_direct_*.json"))
    assert len(files) == 1

    saved = json.loads(files[0].read_text(encoding="utf-8"))
    assert saved[0]["student"] == "Student_0"
    # raw metadata should be stripped
    assert "_raw" not in saved[0]["gpt5_nano_result"]

    mock_init_db.assert_called_once()
    mock_ingest.assert_called_once()


def test_load_question_and_rubric_happy_path(monkeypatch: pytest.MonkeyPatch):
    """_load_question_and_rubric should parse env vars into Python objects."""
    _set_env(monkeypatch, question="Explain sum from 1..100")

    question, rubric = cli._load_question_and_rubric()

    assert "sum" in question
    assert "correctness" in rubric


def test_load_question_and_rubric_missing_env(monkeypatch: pytest.MonkeyPatch):
    """_load_question_and_rubric should fail fast when env is incomplete."""
    monkeypatch.delenv("QUESTION", raising=False)
    monkeypatch.delenv("RUBRIC", raising=False)

    with pytest.raises(RuntimeError, match="QUESTION is not set"):
        cli._load_question_and_rubric()

    monkeypatch.setenv("QUESTION", "Q?")
    with pytest.raises(RuntimeError, match="RUBRIC is not set"):
        cli._load_question_and_rubric()


def test_load_question_and_rubric_invalid_json(monkeypatch: pytest.MonkeyPatch):
    """_load_question_and_rubric should reject invalid RUBRIC JSON."""
    monkeypatch.setenv("QUESTION", "Q?")
    monkeypatch.setenv("RUBRIC", "{ invalid json")

    with pytest.raises(RuntimeError, match="RUBRIC must be valid JSON"):
        cli._load_question_and_rubric()


def test_discover_submissions_recurses(tmp_path: Path):
    """_discover_submissions should find nested .java files."""
    root = tmp_path / "student_submissions"
    (root / "A" / "B").mkdir(parents=True, exist_ok=True)
    (root / "A" / "SumCalculator.java").write_text("class A {}", encoding="utf-8")
    (root / "A" / "B" / "Other.java").write_text("class B {}", encoding="utf-8")

    paths = cli._discover_submissions(root)

    assert len(paths) == 2
    assert all(p.suffix == ".java" for p in paths)

