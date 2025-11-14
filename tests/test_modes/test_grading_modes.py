"""Tests for grading mode runners in modes package."""

from __future__ import annotations

from pathlib import Path
from typing import Any, Dict, List
from unittest.mock import patch

import pytest

from modes.direct_grading import run_direct_grading
from modes.eme_grading import run_eme_grading
from modes.reverse_grading import run_reverse_grading


def _fake_submissions(tmp_path: Path, count: int = 3) -> List[Path]:
    """Create a small set of fake Java submissions."""
    submissions: List[Path] = []
    for i in range(count):
        student_dir = tmp_path / f"Student_{i}"
        student_dir.mkdir(parents=True, exist_ok=True)
        java_file = student_dir / "SumCalculator.java"
        java_file.write_text(
            "public class SumCalculator { public static void main(String[] args) {} }",
            encoding="utf-8",
        )
        submissions.append(java_file)
    return submissions


def _dummy_result(student: str) -> Dict[str, Any]:
    return {
        "student": student,
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


@pytest.mark.asyncio
async def test_run_direct_grading_uses_evaluate_submission(tmp_path: Path):
    """run_direct_grading should call evaluate_submission for each submission."""
    submissions = _fake_submissions(tmp_path, count=2)
    question = "Dummy question"
    rubric: Dict[str, Any] = {"correctness": {"weight": 100, "description": "All or nothing"}}

    async def fake_eval(path: Path, q: str, r: Dict[str, Any], prompt_builder=None):
        return _dummy_result(path.parent.name)

    progress_calls: List[Any] = []

    def progress_cb(ev):
        progress_calls.append(ev)

    with patch("modes.direct_grading.evaluate_submission", side_effect=fake_eval) as mock_eval:
        results = await run_direct_grading(submissions, question, rubric, progress_callback=progress_cb)

    assert len(results) == 2
    assert {r["student"] for r in results} == {p.parent.name for p in submissions}
    assert mock_eval.call_count == 2
    # progress callback should be invoked once per result
    assert len([c for c in progress_calls if c is not None]) == 2


@pytest.mark.asyncio
async def test_run_eme_grading_uses_eme_prompt(tmp_path: Path):
    """run_eme_grading should pass a prompt builder into evaluate_submission."""
    submissions = _fake_submissions(tmp_path, count=1)
    question = "Dummy question"
    rubric: Dict[str, Any] = {"correctness": {"weight": 100, "description": "All or nothing"}}

    async def fake_eval(path: Path, q: str, r: Dict[str, Any], prompt_builder=None):
        # Prompt builder must not be None and should be a callable
        assert prompt_builder is not None
        assert callable(prompt_builder)
        prompt = prompt_builder(q, r, "// code")
        assert isinstance(prompt, str)
        return _dummy_result(path.parent.name)

    with patch("modes.eme_grading.evaluate_submission", side_effect=fake_eval) as mock_eval:
        results = await run_eme_grading(submissions, question, rubric)

    assert len(results) == 1
    mock_eval.assert_called_once()


@pytest.mark.asyncio
async def test_run_reverse_grading_uses_reverse_prompt(tmp_path: Path):
    """run_reverse_grading should pass a prompt builder into evaluate_submission."""
    submissions = _fake_submissions(tmp_path, count=1)
    question = "Dummy question"
    rubric: Dict[str, Any] = {"correctness": {"weight": 100, "description": "All or nothing"}}

    async def fake_eval(path: Path, q: str, r: Dict[str, Any], prompt_builder=None):
        assert prompt_builder is not None
        assert callable(prompt_builder)
        prompt = prompt_builder(q, r, "// code")
        assert isinstance(prompt, str)
        return _dummy_result(path.parent.name)

    with patch("modes.reverse_grading.evaluate_submission", side_effect=fake_eval) as mock_eval:
        results = await run_reverse_grading(submissions, question, rubric)

    assert len(results) == 1
    mock_eval.assert_called_once()
