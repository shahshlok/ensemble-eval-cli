"""
Pydantic models for notional machine misconception detection.

Main Components:
- NotionalMisconception: A misconception rooted in a flawed mental model
- Evidence: Code snippet demonstrating a misconception
- LLMDetectionResponse: What the LLM returns
- ModelDetection: Full detection with metadata
"""

__version__ = "2.0.0"

# New detection models
from .models import (
    Evidence,
    LLMDetectionResponse,
    ModelDetection,
    NotionalMisconception,
)

# Legacy imports for backwards compatibility
from .models import (
    Config,
    LLMEvaluationResponse,
    Misconception,
    ModelEvaluation,
)

__all__ = [
    # New models
    "NotionalMisconception",
    "Evidence",
    "LLMDetectionResponse",
    "ModelDetection",
    # Legacy (deprecated)
    "Misconception",
    "LLMEvaluationResponse",
    "ModelEvaluation",
    "Config",
]
