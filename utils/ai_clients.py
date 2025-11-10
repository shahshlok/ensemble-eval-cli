"""Model client helpers for GPT-5 (OpenAI) and EduAI."""
from __future__ import annotations

import asyncio
import json
import logging
import os
import subprocess
from typing import Any, Dict, Optional

import openai

logger = logging.getLogger(__name__)


async def get_openai_eval(prompt: str) -> Optional[Dict[str, Any]]:
    """Call OpenAI GPT-5 asynchronously and return parsed JSON."""
    return await asyncio.to_thread(_call_openai_eval, prompt)


def _call_openai_eval(prompt: str) -> Optional[Dict[str, Any]]:
    api_key = os.getenv("OPENAI_API_KEY")
    if not api_key:
        raise RuntimeError("OPENAI_API_KEY is not set")

    openai.api_key = api_key
    try:
        response = openai.responses.create(model="gpt-5-nano", input=prompt)
        content = _extract_text_response(response)
        return _safe_json_loads(content)
    except Exception as exc:  # pragma: no cover - network call
        logger.warning("OpenAI evaluation failed: %s", exc)
        return None


def _extract_text_response(response: Any) -> str:
    """Extract the first text block from the OpenAI responses API."""
    try:
        output = response.output or []
        first_item = output[0]
        content = first_item.get("content") or first_item.content
        text = content[0].get("text") if isinstance(content, list) else content
        if isinstance(text, dict):
            return text.get("text", "")
        return str(text)
    except Exception:  # pragma: no cover - defensive fallback
        return str(response)


def get_eduai_eval(prompt: str) -> Optional[Dict[str, Any]]:
    """Call the EduAI GPT-OSS 120B endpoint via curl."""
    endpoint = os.getenv("EDUAI_ENDPOINT", "https://eduai.ok.ubc.ca/api/chat")
    api_key = os.getenv("EDUAI_API_KEY")
    if not api_key:
        raise RuntimeError("EDUAI_API_KEY is not set")

    payload = json.dumps(
        {
            "messages": [{"role": "user", "content": prompt}],
            "model": os.getenv("EDUAI_MODEL", "ollama:gpt-oss:120b"),
            "apiKeys": {"ollama": {"isEnabled": True}},
            "courseCode": os.getenv("EDUAI_COURSE_CODE", ""),
            "streaming": False,
        }
    )

    cmd = [
        "curl",
        "-s",
        "-X",
        "POST",
        endpoint,
        "-H",
        "Content-Type: application/json",
        "-H",
        f"x-api-key: {api_key}",
        "-d",
        payload,
    ]

    try:
        result = subprocess.run(cmd, capture_output=True, text=True, check=True)
        return _safe_json_loads(result.stdout)
    except subprocess.CalledProcessError as exc:  # pragma: no cover - network call
        logger.warning("EduAI curl failed (code %s): %s", exc.returncode, exc.stderr)
    except Exception as exc:  # pragma: no cover - network call
        logger.warning("EduAI evaluation failed: %s", exc)
    return None


def _safe_json_loads(raw_text: str) -> Optional[Dict[str, Any]]:
    try:
        return json.loads(raw_text)
    except json.JSONDecodeError:
        logger.warning("Received invalid JSON: %s", raw_text[:200])
        return None
