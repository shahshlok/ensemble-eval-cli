"""
Pydantic models for ensemble evaluation system.

This package provides a complete set of models for representing student evaluations
graded by multiple LLMs, along with comprehensive comparison and ensemble analysis.

Main Components:
- EvaluationDocument: Root model representing a complete evaluation
- Context: Course, assignment, and question metadata
- Submission: Student work and files
- Rubric: Grading criteria and categories
- ModelEvaluation: Individual model grading results
- Comparison: Multi-model comparison and ensemble analysis
"""

__version__ = "1.0.0"

# Root evaluation document
# Context models
from .context import Context
from .evaluation import EvaluationDocument

# Model evaluation models
from .models import (
    Config,
    Evidence,
    LLMEvaluationResponse,
    Misconception,
    ModelEvaluation,
)

# Submission models
from .submission import StudentFile, Submission

# Submission models

__all__ = [
    # Root model
    "EvaluationDocument",
    # Context
    "Context",
    # Submission
    "Submission",
    "StudentFile",
    # Model evaluation
    "ModelEvaluation",
    "LLMEvaluationResponse",
    "Config",
    "Evidence",
    "Misconception",
]
