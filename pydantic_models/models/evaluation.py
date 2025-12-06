"""Models for notional machine misconception detection.

Simplified schema for thesis research on LLM-based misconception discovery.
Focuses on blind discovery - LLMs infer category names rather than matching to a predefined taxonomy.

Top-down structure:

    LLMDetectionResponse
    └─ misconceptions: list[NotionalMisconception]
        └─ NotionalMisconception
            ├─ inferred_category_name: str  (open-ended, LLM names it)
            ├─ student_thought_process: str
            ├─ conceptual_gap: str
            ├─ error_manifestation: str
            ├─ confidence: float
            └─ evidence: list[Evidence]
                └─ Evidence
                    ├─ line_number: int
                    └─ code_snippet: str

    ModelDetection
    └─ (extends LLMDetectionResponse with metadata)
        ├─ model_name: str
        ├─ provider: str
        ├─ run_id: str
        ├─ strategy: str
        └─ misconceptions: list[NotionalMisconception]
"""

from __future__ import annotations

from pydantic import BaseModel, ConfigDict, Field


class Evidence(BaseModel):
    """A specific code snippet that demonstrates a misconception."""

    model_config = ConfigDict(extra="forbid")

    line_number: int = Field(
        ..., description="Line number where the evidence appears (1-indexed)"
    )
    code_snippet: str = Field(
        ..., description="The actual code that demonstrates the misconception"
    )


class NotionalMisconception(BaseModel):
    """
    A misconception rooted in a flawed mental model of the computer.

    Designed for blind discovery: the LLM names the category rather than
    choosing from a predefined taxonomy. This allows measuring how well
    LLMs can independently discover notional machine categories.
    """

    model_config = ConfigDict(extra="forbid")

    # 1. The Label (Open-ended for Blind Discovery)
    inferred_category_name: str = Field(
        ...,
        description=(
            "A short, descriptive name for the type of mental model failure "
            "(e.g., 'Automatic Variable Updates', 'Input Order Confusion', "
            "'Integer Division Truncation'). Be specific and descriptive."
        ),
    )

    # 2. Mental Model Analysis
    student_thought_process: str = Field(
        ...,
        description=(
            "Reconstruct the student's flawed belief. "
            "Start with 'The student believes...'"
        ),
    )
    conceptual_gap: str = Field(
        ...,
        description=(
            "Explain the gap between the student's mental model "
            "and the actual Java execution model."
        ),
    )

    # 3. Error Manifestation
    error_manifestation: str = Field(
        default="",
        description=(
            "How does this misconception manifest? "
            "(e.g., 'wrong output', 'runtime exception', 'compile error', 'no visible error')"
        ),
    )

    # 4. Confidence & Evidence
    confidence: float = Field(
        ...,
        ge=0.0,
        le=1.0,
        description="Model's confidence (0-1) that this misconception is present",
    )
    evidence: list[Evidence] = Field(
        ..., description="Code snippets demonstrating this belief"
    )


class LLMDetectionResponse(BaseModel):
    """
    Response from LLM containing detected misconceptions.

    This is what the LLM fills out. Metadata (model_name, strategy, etc.)
    is added programmatically to create the full ModelDetection.
    """

    model_config = ConfigDict(extra="forbid")

    misconceptions: list[NotionalMisconception] = Field(
        default_factory=list,
        description="List of detected notional machine misconceptions",
    )


class ModelDetection(LLMDetectionResponse):
    """
    Complete detection result from a single LLM.

    Extends LLMDetectionResponse with execution metadata.
    """

    model_config = ConfigDict(extra="forbid")

    model_name: str = Field(
        ...,
        description="Model name with version (e.g., gpt-4o-2024-08-06, gemini-2.0-flash)",
    )
    provider: str = Field(
        ..., description="Provider name (openai, google, anthropic, openrouter)"
    )
    run_id: str = Field(
        ..., description="Unique ID for this detection run"
    )
    strategy: str = Field(
        ..., description="Prompt strategy used (baseline, taxonomy, cot, socratic)"
    )


# Legacy aliases for backwards compatibility (deprecated)
# These will be removed in a future version
Config = None  # No longer used
Misconception = NotionalMisconception  # Alias for migration
