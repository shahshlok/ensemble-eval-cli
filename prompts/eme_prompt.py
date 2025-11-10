"""Editable Ensembling Method Evaluation (EME) prompt templates."""
from __future__ import annotations

from typing import Dict
import json


def build_eme_prompt(question: str, rubric_json: Dict, student_code: str) -> str:
    """Return the base EME evaluation prompt ready for large language models."""
    # Researchers can quickly tweak this template (e.g., CRE/PRE variants) without
    # touching the rest of the evaluation stack.
    rubric_block = json.dumps(rubric_json, indent=2)
    prompt = f"""
You are evaluating a student's code submission for a programming problem.

Provide your evaluation in VALID JSON format only.

Problem:
{question}

Rubric:
{rubric_block}

Student Code:
{student_code}

IMPORTANT INSTRUCTIONS:
1. Evaluate strictly according to rubric criteria.
2. For each criterion, assign awarded points and short feedback.
3. Return only JSON of this form:
   {{
     "criteria_scores":[{{"criterion":"...","score":1,"max_score":2,"feedback":"..."}}],
     "total_score": <number>,
     "max_possible_score": <number>,
     "overall_feedback":"..."
   }}
""".strip()
    return prompt
