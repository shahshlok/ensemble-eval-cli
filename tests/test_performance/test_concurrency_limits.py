"""Lightweight performance/concurrency-style tests.

These do not measure real throughput but ensure that semaphore-based
rate limiting in ai_clients and evaluator is wired correctly and does
not explode under simple concurrent use.
"""

from __future__ import annotations

import asyncio
from unittest.mock import patch

import pytest

from utils.ai_clients import _get_semaphore as _get_model_semaphore
from utils.evaluator import _get_student_semaphore


@pytest.mark.asyncio
async def test_model_semaphore_respects_env_limit(monkeypatch: pytest.MonkeyPatch):
    """MAX_CONCURRENT_MODELS should create a semaphore with that limit."""
    monkeypatch.setenv("MAX_CONCURRENT_MODELS", "2")

    # Reset cached semaphore
    import utils.ai_clients as ai_clients

    ai_clients._rate_limit_semaphore = None

    sem = _get_model_semaphore()
    assert sem is not None
    assert sem._value == 2


@pytest.mark.asyncio
async def test_student_semaphore_respects_env_limit(monkeypatch: pytest.MonkeyPatch):
    """MAX_CONCURRENT_STUDENTS should create a semaphore with that limit."""
    monkeypatch.setenv("MAX_CONCURRENT_STUDENTS", "3")

    import utils.evaluator as evaluator

    evaluator._student_semaphore = None

    sem = _get_student_semaphore()
    assert sem is not None
    assert sem._value == 3


@pytest.mark.asyncio
async def test_student_semaphore_limits_parallel_tasks(monkeypatch: pytest.MonkeyPatch, tmp_path):
    """Basic smoke test: semaphore wraps evaluate_submission without deadlock."""
    monkeypatch.setenv("MAX_CONCURRENT_STUDENTS", "1")

    import utils.evaluator as evaluator

    evaluator._student_semaphore = None

    from utils.evaluator import evaluate_submission

    java = tmp_path / "S" / "SumCalculator.java"
    java.parent.mkdir(parents=True, exist_ok=True)
    java.write_text("public class SumCalculator {}", encoding="utf-8")

    async def fake_impl(path, question, rubric, prompt_builder=None):
        await asyncio.sleep(0.01)
        return {"student": path.parent.name, "metrics": {"flag": "✅"}}

    with patch("utils.evaluator._evaluate_submission_impl", side_effect=fake_impl):
        tasks = [
            evaluate_submission(java, "Q?", {"correctness": {"weight": 100}}),
            evaluate_submission(java, "Q?", {"correctness": {"weight": 100}}),
        ]
        results = await asyncio.gather(*tasks)

    assert len(results) == 2
    assert all(r["metrics"]["flag"] == "✅" for r in results)

