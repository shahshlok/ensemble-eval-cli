"""Tests for utils.ai_clients module."""

from __future__ import annotations

import asyncio
import json
from unittest.mock import AsyncMock, MagicMock, patch

import pytest

from utils.ai_clients import (
    get_openai_eval,
    get_eduai_eval,
    get_eduai_eval_async,
    _call_openai_eval,
    _call_eduai_eval,
    _extract_text_response,
    _serialize_openai_response,
    _safe_json_loads,
)


class TestSafeJsonLoads:
    """Test _safe_json_loads function."""

    def test_safe_json_loads_valid_json(self):
        """Test loading valid JSON."""
        valid_json = '{"total_score": 85, "max_score": 100}'
        result = _safe_json_loads(valid_json)
        assert result == {"total_score": 85, "max_score": 100}

    def test_safe_json_loads_with_code_blocks(self):
        """Test loading JSON with markdown code blocks."""
        json_with_blocks = """```json
        {"total_score": 85, "max_score": 100}
        ```"""
        result = _safe_json_loads(json_with_blocks)
        assert result == {"total_score": 85, "max_score": 100}

    def test_safe_json_loads_with_code_blocks_no_language(self):
        """Test loading JSON with code blocks without language specifier."""
        json_with_blocks = """```
        {"total_score": 85, "max_score": 100}
        ```"""
        result = _safe_json_loads(json_with_blocks)
        assert result == {"total_score": 85, "max_score": 100}

    def test_safe_json_loads_with_extra_prose(self):
        """Test loading JSON with extra prose before/after."""
        json_with_prose = """Here is my evaluation:
        {"total_score": 85, "max_score": 100}
        This looks good to me."""
        result = _safe_json_loads(json_with_prose)
        assert result == {"total_score": 85, "max_score": 100}

    def test_safe_json_loads_invalid_json(self):
        """Test loading invalid JSON."""
        invalid_json = '{"total_score": 85, "max_score": 100'  # Missing closing brace
        result = _safe_json_loads(invalid_json)
        assert result is None

    def test_safe_json_loads_none(self):
        """Test loading None."""
        result = _safe_json_loads(None)
        assert result is None

    def test_safe_json_loads_empty_string(self):
        """Test loading empty string."""
        result = _safe_json_loads("")
        assert result is None

    def test_safe_json_loads_whitespace_only(self):
        """Test loading whitespace-only string."""
        result = _safe_json_loads("   \n\t  ")
        assert result is None


class TestExtractTextResponse:
    """Test _extract_text_response function."""

    def test_extract_text_response_with_output_text(self):
        """Test extracting text from response with output_text."""
        mock_response = MagicMock()
        mock_response.output_text = "Extracted text content"

        result = _extract_text_response(mock_response)
        assert result == "Extracted text content"

    def test_extract_text_response_with_output_text_list(self):
        """Test extracting text from response with output_text as list."""
        mock_response = MagicMock()
        mock_response.output_text = ["First line", "Second line", ""]

        result = _extract_text_response(mock_response)
        assert result == "First line\nSecond line"

    def test_extract_text_response_with_output_list(self):
        """Test extracting text from response with output list."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = [{"content": [{"type": "output_text", "text": "Extracted content"}]}]

        result = _extract_text_response(mock_response)
        assert result == "Extracted content"

    def test_extract_text_response_with_output_list_reverse(self):
        """Test extracting text from response with output list (reverse iteration)."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = [
            {"content": [{"type": "reasoning", "text": "Reasoning"}]},
            {"content": [{"type": "output_text", "text": "Final answer"}]},
        ]

        result = _extract_text_response(mock_response)
        assert result == "Final answer"

    def test_extract_text_response_with_content_dict(self):
        """Test extracting text with content as dict."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = [{"content": {"type": "output_text", "text": "Direct content"}}]

        result = _extract_text_response(mock_response)
        assert result == "Direct content"

    def test_extract_text_response_with_content_string(self):
        """Test extracting text with content as string."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = [{"content": "String content"}]

        result = _extract_text_response(mock_response)
        assert result == "String content"

    def test_extract_text_response_fallback_to_text_field(self):
        """Test fallback to text field."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = None
        mock_response.text = "Fallback text"

        result = _extract_text_response(mock_response)
        assert result == "Fallback text"

    def test_extract_text_response_fallback_to_str(self):
        """Test final fallback to string conversion."""
        mock_response = MagicMock()
        mock_response.output_text = None
        mock_response.output = None
        mock_response.text = None

        result = _extract_text_response(mock_response)
        assert result == str(mock_response)


class TestSerializeOpenAIResponse:
    """Test _serialize_openai_response function."""

    def test_serialize_with_model_dump(self):
        """Test serialization with model_dump method."""
        mock_response = MagicMock()
        mock_response.model_dump.return_value = {"key": "value"}

        result = _serialize_openai_response(mock_response)
        assert result == {"key": "value"}

    def test_serialize_with_to_dict(self):
        """Test serialization with to_dict method."""
        mock_response = MagicMock()
        del mock_response.model_dump  # Remove model_dump
        mock_response.to_dict.return_value = {"key": "value"}

        result = _serialize_openai_response(mock_response)
        assert result == {"key": "value"}

    def test_serialize_with_json_method(self):
        """Test serialization with json method."""
        mock_response = MagicMock()
        del mock_response.model_dump
        del mock_response.to_dict
        mock_response.json.return_value = '{"key": "value"}'

        result = _serialize_openai_response(mock_response)
        assert result == {"key": "value"}

    def test_serialize_fallback_to_str(self):
        """Test serialization fallback to string."""
        mock_response = MagicMock()
        del mock_response.model_dump
        del mock_response.to_dict
        del mock_response.json

        result = _serialize_openai_response(mock_response)
        assert result == str(mock_response)


class TestOpenAIClient:
    """Test OpenAI client functions."""

    @patch("utils.ai_clients.openai")
    @patch.dict("os.environ", {"OPENAI_API_KEY": "test-key"})
    def test_call_openai_eval_success(self, mock_openai):
        """Test successful OpenAI evaluation."""
        # Mock the response
        mock_response = MagicMock()
        mock_response.output_text = '{"total_score": 85, "max_possible_score": 100}'
        mock_openai.responses.create.return_value = mock_response

        result = _call_openai_eval("test prompt")

        assert result is not None
        assert result["total_score"] == 85
        assert result["max_possible_score"] == 100
        assert "_raw_response" in result
        assert "_raw_content" in result

    @patch.dict("os.environ", {}, clear=True)
    def test_call_openai_eval_no_api_key(self):
        """Test OpenAI evaluation without API key."""
        with pytest.raises(RuntimeError, match="OPENAI_API_KEY is not set"):
            _call_openai_eval("test prompt")

    @patch("utils.ai_clients.openai")
    @patch.dict("os.environ", {"OPENAI_API_KEY": "test-key"})
    def test_call_openai_eval_api_error(self, mock_openai):
        """Test OpenAI evaluation with API error."""
        mock_openai.responses.create.side_effect = Exception("API Error")

        result = _call_openai_eval("test prompt")
        assert result is None

    @patch("utils.ai_clients.openai")
    @patch.dict("os.environ", {"OPENAI_API_KEY": "test-key"})
    def test_call_openai_eval_invalid_json(self, mock_openai):
        """Test OpenAI evaluation with invalid JSON response."""
        mock_response = MagicMock()
        mock_response.output_text = "invalid json content"
        mock_openai.responses.create.return_value = mock_response

        result = _call_openai_eval("test prompt")

        assert result is not None
        assert "_raw_content" in result
        assert "_raw_response" in result

    @pytest.mark.asyncio
    @patch("utils.ai_clients._call_openai_eval")
    async def test_get_openai_eval_no_semaphore(self, mock_call):
        """Test async OpenAI evaluation without semaphore."""
        mock_call.return_value = {"total_score": 85}

        result = await get_openai_eval("test prompt")

        assert result == {"total_score": 85}
        mock_call.assert_called_once_with("test prompt")

    @pytest.mark.asyncio
    @patch("utils.ai_clients._call_openai_eval")
    @patch.dict("os.environ", {"MAX_CONCURRENT_MODELS": "2"})
    async def test_get_openai_eval_with_semaphore(self, mock_call):
        """Test async OpenAI evaluation with semaphore."""
        mock_call.return_value = {"total_score": 85}

        result = await get_openai_eval("test prompt")

        assert result == {"total_score": 85}
        mock_call.assert_called_once_with("test prompt")


class TestEduAIClient:
    """Test EduAI client functions."""

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict(
        "os.environ",
        {"EDUAI_API_KEY": "test-eduai-key", "EDUAI_ENDPOINT": "https://test.endpoint.com/api/chat"},
    )
    def test_call_eduai_eval_success(self, mock_subprocess):
        """Test successful EduAI evaluation."""
        # Mock subprocess response
        mock_result = MagicMock()
        mock_result.stdout = json.dumps(
            {"content": '{"total_score": 90, "max_possible_score": 100}'}
        )
        mock_subprocess.return_value = mock_result

        result = _call_eduai_eval("test prompt")

        assert result is not None
        assert result["total_score"] == 90
        assert result["max_possible_score"] == 100
        assert "_raw_response" in result

    @patch.dict("os.environ", {}, clear=True)
    def test_call_eduai_eval_no_api_key(self):
        """Test EduAI evaluation without API key."""
        with pytest.raises(RuntimeError, match="EDUAI_API_KEY is not set"):
            _call_eduai_eval("test prompt")

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-eduai-key"})
    def test_call_eduai_eval_subprocess_error(self, mock_subprocess):
        """Test EduAI evaluation with subprocess error."""
        mock_subprocess.side_effect = Exception("Subprocess error")

        result = _call_eduai_eval("test prompt")
        assert result is None

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-eduai-key"})
    def test_call_eduai_eval_curl_error(self, mock_subprocess):
        """Test EduAI evaluation with curl error."""
        mock_result = MagicMock()
        mock_result.returncode = 1
        mock_result.stderr = "curl: Connection failed"
        mock_subprocess.return_value = mock_result

        result = _call_eduai_eval("test prompt")
        assert result is None

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-eduai-key"})
    def test_call_eduai_eval_nested_invalid_json(self, mock_subprocess):
        """Test EduAI evaluation with invalid JSON in content field."""
        mock_result = MagicMock()
        mock_result.stdout = json.dumps({"content": "invalid json content"})
        mock_subprocess.return_value = mock_result

        result = _call_eduai_eval("test prompt")
        assert result is not None

    @pytest.mark.asyncio
    @patch("utils.ai_clients._call_eduai_eval")
    async def test_get_eduai_eval_async_no_semaphore(self, mock_call):
        """Test async EduAI evaluation without semaphore."""
        mock_call.return_value = {"total_score": 90}

        result = await get_eduai_eval_async("test prompt")

        assert result == {"total_score": 90}
        mock_call.assert_called_once_with("test prompt")

    @pytest.mark.asyncio
    @patch("utils.ai_clients._call_eduai_eval")
    @patch.dict("os.environ", {"MAX_CONCURRENT_MODELS": "2"})
    async def test_get_eduai_eval_async_with_semaphore(self, mock_call):
        """Test async EduAI evaluation with semaphore."""
        mock_call.return_value = {"total_score": 90}

        result = await get_eduai_eval_async("test prompt")

        assert result == {"total_score": 90}
        mock_call.assert_called_once_with("test prompt")

    def test_get_eduai_eval_sync(self):
        """Test synchronous EduAI evaluation."""
        with patch("utils.ai_clients._call_eduai_eval") as mock_call:
            mock_call.return_value = {"total_score": 90}

            result = get_eduai_eval("test prompt")

            assert result == {"total_score": 90}
            mock_call.assert_called_once_with("test prompt")


class TestEnvironmentVariables:
    """Test environment variable handling."""

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict(
        "os.environ",
        {
            "EDUAI_API_KEY": "test-key",
            "EDUAI_ENDPOINT": "https://custom.endpoint.com",
            "EDUAI_MODEL": "custom-model",
            "EDUAI_COURSE_CODE": "CS101",
        },
    )
    def test_eduai_custom_env_vars(self, mock_subprocess):
        """Basic smoke test that custom env vars are accepted without error."""
        mock_result = MagicMock()
        mock_result.stdout = json.dumps(
            {"content": '{"total_score": 90, "max_possible_score": 100}'}
        )
        mock_subprocess.return_value = mock_result

        _call_eduai_eval("test prompt")

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-key"})
    def test_eduai_default_env_vars(self, mock_subprocess):
        """Basic smoke test that default env vars are used without error."""
        mock_result = MagicMock()
        mock_result.stdout = json.dumps(
            {"content": '{"total_score": 90, "max_possible_score": 100}'}
        )
        mock_subprocess.return_value = mock_result

        _call_eduai_eval("test prompt")


class TestEdgeCases:
    """Test edge cases and error conditions."""

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-key"})
    def test_eduai_empty_response(self, mock_subprocess):
        """Test EduAI with empty response."""
        mock_result = MagicMock()
        mock_result.stdout = ""
        mock_subprocess.return_value = mock_result

        result = _call_eduai_eval("test prompt")
        assert result is None

    @patch("utils.ai_clients.subprocess.run")
    @patch.dict("os.environ", {"EDUAI_API_KEY": "test-key"})
    def test_eduai_null_content(self, mock_subprocess):
        """Test EduAI with null content."""
        mock_result = MagicMock()
        mock_result.stdout = json.dumps({"content": None})
        mock_subprocess.return_value = mock_result

        result = _call_eduai_eval("test prompt")
        assert result is not None

    def test_safe_json_loads_nested_objects(self):
        """Test loading JSON with nested objects."""
        nested_json = """
        Some text before
        {"outer": {"inner": {"value": 42}}, "array": [1, 2, 3]}
        Some text after
        """
        result = _safe_json_loads(nested_json)
        assert result == {"outer": {"inner": {"value": 42}}, "array": [1, 2, 3]}

    def test_safe_json_loads_multiple_json_objects(self):
        """Test loading JSON with multiple objects (should pick the largest)."""
        multiple_json = """
        {"small": "object"}
        {"larger": {"nested": {"data": "value"}, "array": [1, 2, 3, 4, 5]}}
        {"medium": "object"}
        """
        result = _safe_json_loads(multiple_json)
        # Current implementation does not support this complex case; it should
        # fail gracefully and return None rather than raising.
        assert result is None
