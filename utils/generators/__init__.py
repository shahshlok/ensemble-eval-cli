"""Generators for datasets, submissions, and comparisons."""

from utils.generators.authentic_generator import (
    AuthenticGenerator,
    AuthenticSubmission,
)
from utils.generators.comparison_generator import (
    generate_category_agreement,
    generate_category_insights,
    generate_pairwise_differences,
    generate_score_summary,
)
from utils.generators.dataset_generator import (
    app as dataset_app,
    interactive_main,
)

__all__ = [
    # authentic_generator
    "AuthenticGenerator",
    "AuthenticSubmission",
    # comparison_generator
    "generate_category_agreement",
    "generate_category_insights",
    "generate_pairwise_differences",
    "generate_score_summary",
    # dataset_generator
    "dataset_app",
    "interactive_main",
]
