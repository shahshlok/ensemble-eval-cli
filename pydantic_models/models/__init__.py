"""Models for notional machine misconception detection."""

from .evaluation import (
    Evidence,
    LLMDetectionResponse,
    ModelDetection,
    NotionalMisconception,
)

# Legacy aliases for backwards compatibility
Misconception = NotionalMisconception
LLMEvaluationResponse = LLMDetectionResponse
ModelEvaluation = ModelDetection
Config = None  # Deprecated, no longer used

__all__ = [
    # New models
    "Evidence",
    "NotionalMisconception",
    "LLMDetectionResponse",
    "ModelDetection",
    # Legacy aliases (deprecated)
    "Misconception",
    "LLMEvaluationResponse",
    "ModelEvaluation",
    "Config",
]
