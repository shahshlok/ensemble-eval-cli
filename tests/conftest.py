"""Test configuration and fixtures for EME testing suite."""

from __future__ import annotations

import json
import tempfile
from pathlib import Path
from typing import Any, Dict
import pytest

# Test fixtures directory
FIXTURES_DIR = Path(__file__).parent / "fixtures"


@pytest.fixture
def temp_dir():
    """Create a temporary directory for test files."""
    with tempfile.TemporaryDirectory() as tmp_dir:
        yield Path(tmp_dir)


@pytest.fixture
def sample_evaluation():
    """Valid sample evaluation data."""
    return {
        "student": "Test_Student_123",
        "gpt5_nano_result": {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good implementation with minor issues",
        },
        "gpt_oss_120b_result": {
            "total_score": 90,
            "max_possible_score": 100,
            "overall_feedback": "Solid work, well-structured code",
        },
        "metrics": {
            "gpt5_nano": {"total": 85.0, "max": 100.0, "pct": 85.0},
            "gpt_oss_120b": {"total": 90.0, "max": 100.0, "pct": 90.0},
            "avg_pct": 87.5,
            "diff_pct": 5.0,
            "flag": "✅",
            "comment": "Models agree within tolerance",
        },
    }


@pytest.fixture
def sample_evaluation_list(sample_evaluation):
    """List of sample evaluations."""
    return [
        sample_evaluation,
        {
            "student": "Another_Student_456",
            "gpt5_nano_result": {
                "total_score": 75,
                "max_possible_score": 100,
                "overall_feedback": "Needs improvement in logic",
            },
            "gpt_oss_120b_result": {
                "total_score": 80,
                "max_possible_score": 100,
                "overall_feedback": "Basic implementation present",
            },
            "metrics": {
                "gpt5_nano": {"total": 75.0, "max": 100.0, "pct": 75.0},
                "gpt_oss_120b": {"total": 80.0, "max": 100.0, "pct": 80.0},
                "avg_pct": 77.5,
                "diff_pct": 5.0,
                "flag": "✅",
                "comment": "Models agree within tolerance",
            },
        },
    ]


@pytest.fixture
def invalid_evaluation():
    """Invalid evaluation data for negative testing."""
    return {
        "student": "",  # Empty student name
        "gpt5_nano_result": {
            "total_score": -10,  # Negative score
            "max_possible_score": 100,
            "overall_feedback": "Good",
        },
        "gpt_oss_120b_result": {
            "total_score": 105,  # Score > max
            "max_possible_score": 100,
            "overall_feedback": "Good",
        },
        "metrics": {
            "gpt5_nano": {"total": 75.0, "max": 100.0, "pct": 75.0},
            "gpt_oss_120b": {"total": 80.0, "max": 100.0, "pct": 80.0},
            "avg_pct": 77.5,
            "diff_pct": 5.0,
            "flag": "✅",
            "comment": "Models agree within tolerance",
        },
    }


@pytest.fixture
def sample_java_code():
    """Sample Java submission code."""
    return """
// Test Student 123
public class SumCalculator {
    public static void main(String[] args) {
        int totalSum = 0;
        for (int i = 1; i <= 100; i++) {
            totalSum = totalSum + i;
        }
        System.out.println("The sum of numbers from 1 to 100 is: " + totalSum);
    }
}
""".strip()


@pytest.fixture
def sample_rubric():
    """Sample grading rubric."""
    return {
        "correctness": {"weight": 40, "description": "Program produces correct output"},
        "compilation": {"weight": 20, "description": "Code compiles without errors"},
        "style": {"weight": 20, "description": "Code follows conventions"},
        "documentation": {"weight": 20, "description": "Adequate comments"},
    }


@pytest.fixture
def sample_question():
    """Sample programming question."""
    return "Write a Java program that computes the sum of integers from 1 to 100."


@pytest.fixture
def mock_openai_response():
    """Mock OpenAI API response."""
    return {
        "total_score": 85,
        "max_possible_score": 100,
        "overall_feedback": "Good implementation with minor issues",
    }


@pytest.fixture
def mock_eduai_response():
    """Mock EduAI API response."""
    return {
        "total_score": 90,
        "max_possible_score": 100,
        "overall_feedback": "Solid work, well-structured code",
    }


@pytest.fixture
def temp_json_file(temp_dir, sample_evaluation_list):
    """Create a temporary JSON file with sample evaluation data."""
    json_file = temp_dir / "results_direct_2025-11-13T17-52-45.json"
    json_file.write_text(json.dumps(sample_evaluation_list, indent=2))
    return json_file


@pytest.fixture
def temp_db_file(temp_dir):
    """Create a temporary database file."""
    return temp_dir / "test_evaluations.db"


@pytest.fixture
def mock_api_responses():
    """Mock API responses for testing."""
    return {
        "valid_gpt5": {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good implementation",
        },
        "valid_eduai": {
            "total_score": 90,
            "max_possible_score": 100,
            "overall_feedback": "Solid work",
        },
        "invalid_json": "This is not valid JSON",
        "missing_fields": {"total_score": 85},
        "null_response": None,
    }
