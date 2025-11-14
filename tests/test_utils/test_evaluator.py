"""Tests for utils.evaluator module."""

from __future__ import annotations

import asyncio
from pathlib import Path
from unittest.mock import AsyncMock, MagicMock, patch

import pytest

from utils.evaluator import (
    evaluate_submission,
    compute_metrics,
    _to_number,
    _get_student_semaphore,
    _evaluate_submission_impl,
)


class TestMetricsComputation:
    """Test metrics computation functionality."""

    def test_compute_metrics_both_valid(self):
        """Test metrics computation with both valid results."""
        gpt5_result = {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good work",
        }
        eduai_result = {
            "total_score": 90,
            "max_possible_score": 100,
            "overall_feedback": "Excellent work",
        }

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["total"] == 85.0
        assert metrics["gpt5_nano"]["max"] == 100.0
        assert metrics["gpt5_nano"]["pct"] == 85.0

        assert metrics["gpt_oss_120b"]["total"] == 90.0
        assert metrics["gpt_oss_120b"]["max"] == 100.0
        assert metrics["gpt_oss_120b"]["pct"] == 90.0

        assert metrics["avg_pct"] == 87.5
        assert metrics["diff_pct"] == 5.0
        assert metrics["flag"] == "✅"
        assert "within tolerance" in metrics["comment"]

    def test_compute_models_disagree(self):
        """Test metrics computation when models disagree significantly."""
        gpt5_result = {
            "total_score": 60,
            "max_possible_score": 100,
            "overall_feedback": "Needs work",
        }
        eduai_result = {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good work",
        }

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["diff_pct"] == 25.0
        assert metrics["flag"] == "🚩"
        assert "GPT-5 Nano stricter" in metrics["comment"]

    def test_compute_models_disagree_reverse(self):
        """Test metrics computation when EduAI is stricter."""
        gpt5_result = {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good work",
        }
        eduai_result = {
            "total_score": 60,
            "max_possible_score": 100,
            "overall_feedback": "Needs work",
        }

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["diff_pct"] == 25.0
        assert metrics["flag"] == "🚩"
        assert "GPT-OSS 120B stricter" in metrics["comment"]

    def test_compute_metrics_one_null(self):
        """Test metrics computation with one null result."""
        gpt5_result = {
            "total_score": 85,
            "max_possible_score": 100,
            "overall_feedback": "Good work",
        }
        eduai_result = None

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["total"] == 85.0
        assert metrics["gpt5_nano"]["pct"] == 85.0
        assert metrics["gpt_oss_120b"]["total"] is None
        assert metrics["gpt_oss_120b"]["pct"] is None
        assert metrics["avg_pct"] == 85.0
        assert metrics["diff_pct"] is None
        assert metrics["flag"] == "🚩"
        assert "Missing comparison data" in metrics["comment"]

    def test_compute_metrics_both_null(self):
        """Test metrics computation with both null results."""
        metrics = compute_metrics(None, None)

        assert metrics["gpt5_nano"]["total"] is None
        assert metrics["gpt_oss_120b"]["total"] is None
        assert metrics["avg_pct"] is None
        assert metrics["diff_pct"] is None
        assert metrics["flag"] == "🚩"

    def test_compute_metrics_different_max_scores(self):
        """Test metrics computation with different max scores."""
        gpt5_result = {"total_score": 80, "max_possible_score": 100, "overall_feedback": "Good"}
        eduai_result = {"total_score": 45, "max_possible_score": 50, "overall_feedback": "Good"}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["pct"] == 80.0
        assert metrics["gpt_oss_120b"]["pct"] == 90.0
        assert metrics["avg_pct"] == 85.0
        assert metrics["diff_pct"] == 10.0

    def test_compute_metrics_missing_fields(self):
        """Test metrics computation with missing fields."""
        gpt5_result = {
            "total_score": 85,
            # Missing max_possible_score
            "overall_feedback": "Good",
        }
        eduai_result = {"total_score": 90, "max_possible_score": 100, "overall_feedback": "Good"}

        metrics = compute_metrics(gpt5_result, eduai_result)

        # Should handle missing max_score gracefully
        assert metrics["gpt5_nano"]["total"] == 85.0
        assert metrics["gpt5_nano"]["max"] is None
        assert metrics["gpt5_nano"]["pct"] is None

    def test_compute_metrics_alternative_field_names(self):
        """Test metrics computation with alternative field names."""
        gpt5_result = {
            "total_score": 85,
            "max_score": 100,  # Alternative to max_possible_score
            "overall_feedback": "Good",
        }
        eduai_result = {"total_score": 90, "max_possible_score": 100, "overall_feedback": "Good"}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["max"] == 100.0
        assert metrics["gpt5_nano"]["pct"] == 85.0


class TestToNumber:
    """Test _to_number helper function."""

    def test_to_number_with_float(self):
        """Test converting float to number."""
        assert _to_number(85.5) == 85.5

    def test_to_number_with_int(self):
        """Test converting int to number."""
        assert _to_number(85) == 85.0

    def test_to_number_with_string_number(self):
        """Test converting string number to float."""
        assert _to_number("85.5") == 85.5
        assert _to_number("85") == 85.0

    def test_to_number_with_none(self):
        """Test converting None to None."""
        assert _to_number(None) is None

    def test_to_number_with_invalid_string(self):
        """Test converting invalid string to None."""
        assert _to_number("invalid") is None
        assert _to_number("") is None

    def test_to_number_with_complex_object(self):
        """Test converting complex object to None."""
        assert _to_number({"key": "value"}) is None
        assert _to_number([1, 2, 3]) is None


class TestEvaluateSubmission:
    """Test evaluate_submission function."""

    @pytest.mark.asyncio
    async def test_evaluate_submission_basic(
        self, temp_dir, sample_java_code, sample_question, sample_rubric
    ):
        """Test basic evaluation submission."""
        # Create a temporary Java file
        java_file = temp_dir / "Test_Student_123" / "SumCalculator.java"
        java_file.parent.mkdir(parents=True, exist_ok=True)
        java_file.write_text(sample_java_code)

        # Mock the AI client calls
        with (
            patch("utils.evaluator.get_openai_eval", new_callable=AsyncMock) as mock_openai,
            patch("utils.evaluator.get_eduai_eval_async", new_callable=AsyncMock) as mock_eduai,
        ):
            mock_openai.return_value = {
                "total_score": 85,
                "max_possible_score": 100,
                "overall_feedback": "Good work",
            }
            mock_eduai.return_value = {
                "total_score": 90,
                "max_possible_score": 100,
                "overall_feedback": "Excellent work",
            }

            result = await evaluate_submission(java_file, sample_question, sample_rubric)

            assert result["student"] == "Test_Student_123"
            assert "gpt5_nano_result" in result
            assert "gpt_oss_120b_result" in result
            assert "metrics" in result
            assert result["metrics"]["avg_pct"] == 87.5

    @pytest.mark.asyncio
    async def test_evaluate_submission_with_semaphore(
        self, temp_dir, sample_java_code, sample_question, sample_rubric
    ):
        """Test evaluation submission with rate limiting."""
        java_file = temp_dir / "Test_Student_123" / "SumCalculator.java"
        java_file.parent.mkdir(parents=True, exist_ok=True)
        java_file.write_text(sample_java_code)

        with (
            patch.dict("os.environ", {"MAX_CONCURRENT_STUDENTS": "1"}),
            patch("utils.evaluator.get_openai_eval", new_callable=AsyncMock) as mock_openai,
            patch("utils.evaluator.get_eduai_eval_async", new_callable=AsyncMock) as mock_eduai,
        ):
            mock_openai.return_value = {"total_score": 85, "max_possible_score": 100}
            mock_eduai.return_value = {"total_score": 90, "max_possible_score": 100}

            result = await evaluate_submission(java_file, sample_question, sample_rubric)

            assert result["student"] == "Test_Student_123"
            assert "metrics" in result

    @pytest.mark.asyncio
    async def test_evaluate_submission_custom_prompt_builder(
        self, temp_dir, sample_java_code, sample_question, sample_rubric
    ):
        """Test evaluation submission with custom prompt builder."""
        java_file = temp_dir / "Test_Student_123" / "SumCalculator.java"
        java_file.parent.mkdir(parents=True, exist_ok=True)
        java_file.write_text(sample_java_code)

        def custom_prompt_builder(question, rubric, code):
            return f"CUSTOM: {question[:20]}..."

        with (
            patch("utils.evaluator.get_openai_eval", new_callable=AsyncMock) as mock_openai,
            patch("utils.evaluator.get_eduai_eval_async", new_callable=AsyncMock) as mock_eduai,
        ):
            mock_openai.return_value = {"total_score": 85, "max_possible_score": 100}
            mock_eduai.return_value = {"total_score": 90, "max_possible_score": 100}

            result = await evaluate_submission(
                java_file, sample_question, sample_rubric, custom_prompt_builder
            )

            # Verify custom prompt was used
            mock_openai.assert_called_once()
            call_args = mock_openai.call_args[0][0]
            assert call_args.startswith("CUSTOM:")

    @pytest.mark.asyncio
    async def test_evaluate_submission_api_failure(
        self, temp_dir, sample_java_code, sample_question, sample_rubric
    ):
        """Test evaluation submission when API calls fail."""
        java_file = temp_dir / "Test_Student_123" / "SumCalculator.java"
        java_file.parent.mkdir(parents=True, exist_ok=True)
        java_file.write_text(sample_java_code)

        with (
            patch("utils.evaluator.get_openai_eval", new_callable=AsyncMock) as mock_openai,
            patch("utils.evaluator.get_eduai_eval_async", new_callable=AsyncMock) as mock_eduai,
        ):
            mock_openai.return_value = None  # API failure
            mock_eduai.return_value = {"total_score": 90, "max_possible_score": 100}

            result = await evaluate_submission(java_file, sample_question, sample_rubric)

            assert result["gpt5_nano_result"] is None
            assert result["gpt_oss_120b_result"] is not None
            assert result["metrics"]["flag"] == "🚩"

    @pytest.mark.asyncio
    async def test_evaluate_submission_file_not_found(self, sample_question, sample_rubric):
        """Test evaluation submission with nonexistent file."""
        nonexistent_file = Path("/nonexistent/file.java")

        with pytest.raises(FileNotFoundError):
            await evaluate_submission(nonexistent_file, sample_question, sample_rubric)


class TestEvaluateSubmissionImpl:
    """Test _evaluate_submission_impl function."""

    @pytest.mark.asyncio
    async def test_evaluate_submission_impl(
        self, temp_dir, sample_java_code, sample_question, sample_rubric
    ):
        """Test internal implementation of evaluate_submission."""
        java_file = temp_dir / "Test_Student_123" / "SumCalculator.java"
        java_file.parent.mkdir(parents=True, exist_ok=True)
        java_file.write_text(sample_java_code)

        with (
            patch("utils.evaluator.get_openai_eval", new_callable=AsyncMock) as mock_openai,
            patch("utils.evaluator.get_eduai_eval_async", new_callable=AsyncMock) as mock_eduai,
        ):
            mock_openai.return_value = {"total_score": 85, "max_possible_score": 100}
            mock_eduai.return_value = {"total_score": 90, "max_possible_score": 100}

            result = await _evaluate_submission_impl(java_file, sample_question, sample_rubric)

            assert result["student"] == "Test_Student_123"
            assert "prompt" in result
            assert "code_path" in result
            assert result["code_path"] == str(java_file)


class TestEdgeCases:
    """Test edge cases and boundary conditions."""

    def test_compute_metrics_zero_scores(self):
        """Test metrics computation with zero scores."""
        gpt5_result = {"total_score": 0, "max_possible_score": 100}
        eduai_result = {"total_score": 0, "max_possible_score": 100}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["pct"] == 0.0
        assert metrics["gpt_oss_120b"]["pct"] == 0.0
        assert metrics["avg_pct"] == 0.0
        assert metrics["diff_pct"] == 0.0
        assert metrics["flag"] == "✅"

    def test_compute_metrics_perfect_scores(self):
        """Test metrics computation with perfect scores."""
        gpt5_result = {"total_score": 100, "max_possible_score": 100}
        eduai_result = {"total_score": 100, "max_possible_score": 100}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["pct"] == 100.0
        assert metrics["gpt_oss_120b"]["pct"] == 100.0
        assert metrics["avg_pct"] == 100.0
        assert metrics["diff_pct"] == 0.0
        assert metrics["flag"] == "✅"

    def test_compute_metrics_zero_max_score(self):
        """Test metrics computation with zero max score (division by zero)."""
        gpt5_result = {"total_score": 50, "max_possible_score": 0}
        eduai_result = {"total_score": 50, "max_possible_score": 0}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["gpt5_nano"]["total"] == 50.0
        # Due to the `or` logic in extract_scores, 0 is falsy so it tries max_score field
        # Since max_score doesn't exist, it returns None
        assert metrics["gpt5_nano"]["max"] is None  # 0 is falsy, tries max_score field
        assert metrics["gpt5_nano"]["pct"] is None

    def test_compute_metrics_very_small_difference(self):
        """Test metrics computation with very small difference."""
        gpt5_result = {"total_score": 85, "max_possible_score": 100}
        eduai_result = {"total_score": 85.1, "max_possible_score": 100}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert abs(metrics["diff_pct"] - 0.1) < 0.0001  # Handle floating point precision
        assert metrics["flag"] == "✅"

    def test_compute_metrics_exactly_at_threshold(self):
        """Test metrics computation exactly at tolerance threshold."""
        gpt5_result = {"total_score": 85, "max_possible_score": 100}
        eduai_result = {"total_score": 95, "max_possible_score": 100}

        metrics = compute_metrics(gpt5_result, eduai_result)

        assert metrics["diff_pct"] == 10.0
        assert metrics["flag"] == "✅"  # Exactly at threshold should pass
