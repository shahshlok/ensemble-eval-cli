import os
from typing import TypeVar

import instructor
from dotenv import load_dotenv
from openai import AsyncOpenAI
from pydantic import BaseModel
from tenacity import retry, stop_after_attempt, wait_exponential, wait_random

load_dotenv()

T = TypeVar("T", bound=BaseModel)

DEFAULT_MODEL = os.getenv("OPENAI_DEFAULT_MODEL", "gpt-5.1")

client = instructor.from_openai(
    AsyncOpenAI(api_key=os.getenv("OPENAI_API_KEY")),
    mode=instructor.Mode.RESPONSES_TOOLS,
)


@retry(
    stop=stop_after_attempt(3),
    wait=wait_exponential(multiplier=1, min=4, max=10) + wait_random(0, 0.4),
)
async def get_structured_response(
    messages: list[dict[str, str]],
    response_model: type[T],
    model: str = DEFAULT_MODEL,
) -> T:
    if not messages:
        raise ValueError("messages must contain at least one item")

    input_text = messages[-1].get("content", "")

    return await client.responses.create(
        model=model,
        input=input_text,
        response_model=response_model,
    )


@retry(
    stop=stop_after_attempt(3),
    wait=wait_exponential(multiplier=1, min=4, max=10) + wait_random(0, 0.4),
)
async def get_reasoning_response(
    messages: list[dict[str, str]],
    response_model: type[T],
    model: str = DEFAULT_MODEL,
    reasoning_tokens: int = 1024,
) -> T:
    if not messages:
        raise ValueError("messages must contain at least one item")

    input_text = messages[-1].get("content", "")

    # For reasoning models, we attempt to pass reasoning configuration.
    try:
        return await client.responses.create(
            model=model,
            input=input_text,
            response_model=response_model,
            reasoning={"effort": "medium"},
        )
    except (TypeError, Exception):
        # Fallback if reasoning parameter is not supported
        return await client.responses.create(
            model=model,
            input=input_text,
            response_model=response_model,
        )
