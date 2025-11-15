"""EduAI GPT-OSS 120B client helpers.

This module wraps the EduAI HTTP endpoint used for GPT-OSS 120B grading.
It mirrors the behavior in utils.ai_clients while allowing a more
granular import surface for callers that want a dedicated EduAI client.
"""
from __future__ import annotations

import asyncio
import json
import logging
import os
import subprocess
from typing import Any, Dict, Optional

from .openai_client import _safe_json_loads, _get_semaphore

logger = logging.getLogger(__name__)


def get_eduai_eval(prompt: str) -> Optional[Dict[str, Any]]:
    """Call the EduAI GPT-OSS 120B endpoint via curl (synchronous version)."""
    return _call_eduai_eval(prompt)


async def get_eduai_eval_async(prompt: str) -> Optional[Dict[str, Any]]:
    """Call the EduAI GPT-OSS 120B endpoint via curl (async version)."""
    semaphore = _get_semaphore()
    if semaphore:
        async with semaphore:
            return await asyncio.to_thread(_call_eduai_eval, prompt)
    return await asyncio.to_thread(_call_eduai_eval, prompt)


def _call_eduai_eval(prompt: str) -> Optional[Dict[str, Any]]:
    """Internal helper to call EduAI endpoint via curl."""
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
        parsed = _safe_json_loads(result.stdout)
        if parsed and isinstance(parsed.get("content"), str):
            # EduAI nests the rubric JSON in the 'content' string.
            content_json = _safe_json_loads(parsed["content"])
            if content_json:
                content_json["_raw_response"] = parsed
                return content_json
        return parsed
    except subprocess.CalledProcessError as exc:  # pragma: no cover - network call
        logger.warning("EduAI curl failed (code %s): %s", exc.returncode, exc.stderr)
    except Exception as exc:  # pragma: no cover - network call
        logger.warning("EduAI evaluation failed: %s", exc)
    return None


__all__ = ["get_eduai_eval", "get_eduai_eval_async", "_call_eduai_eval"]

