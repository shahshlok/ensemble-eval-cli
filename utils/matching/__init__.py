"""Matching module for comparing LLM detections against ground truth."""

from .fuzzy import fuzzy_match_misconception
from .semantic import semantic_match_misconception
from .classifier import classify_detection, MatchResult

__all__ = [
    "fuzzy_match_misconception",
    "semantic_match_misconception", 
    "classify_detection",
    "MatchResult",
]
