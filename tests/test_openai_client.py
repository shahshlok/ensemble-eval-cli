import pytest
from pydantic import BaseModel

from utils.llm import openai as openai_module


class DemoModel(BaseModel):
    value: str


class DummyContent:
    def __init__(self, parsed):
        self.parsed = parsed


class DummyOutput:
    def __init__(self, content):
        self.content = content


class DummyParsed:
    def __init__(self, output):
        self.output = output


class DummyResponses:
    async def parse(self, *args, **kwargs):
        return DummyParsed([DummyOutput([DummyContent(DemoModel(value="ok"))])])


class DummyClient:
    def __init__(self):
        self.responses = DummyResponses()


@pytest.mark.asyncio
async def test_get_structured_response_returns_pydantic(monkeypatch):
    monkeypatch.setattr(openai_module, "client", DummyClient())

    result = await openai_module.get_structured_response(
        messages=[{"role": "user", "content": "test prompt"}],
        response_model=DemoModel,
        model="gpt-5.1",
    )

    assert isinstance(result, DemoModel)
    assert result.value == "ok"


@pytest.mark.asyncio
async def test_get_reasoning_response_returns_pydantic(monkeypatch):
    monkeypatch.setattr(openai_module, "client", DummyClient())

    result = await openai_module.get_reasoning_response(
        messages=[{"role": "user", "content": "test prompt"}],
        response_model=DemoModel,
        model="gpt-5.1",
    )

    assert isinstance(result, DemoModel)
    assert result.value == "ok"


@pytest.mark.asyncio
async def test_get_structured_response_empty_messages_raises(monkeypatch):
    monkeypatch.setattr(openai_module, "client", DummyClient())

    with pytest.raises(ValueError):
        await openai_module.get_structured_response(
            messages=[],
            response_model=DemoModel,
        )
