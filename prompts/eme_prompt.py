"""Editable Ensembling Method Evaluation (EME) prompt templates.

This prompt is the single place to shape how both OpenAI and EduAI grade.
By default, it enforces a 100-point rubric and a strict JSON-only reply.
"""
from __future__ import annotations

from typing import Dict, Any, List
import json


def build_eme_prompt(question: str, rubric_json: Dict[str, Any], student_code: str) -> str:
    """Build the grading prompt enforcing a 100-point rubric and JSON-only output.

    Assumptions:
    - `rubric_json` can include a 100-point structure like:
        {"totalPoints": 100, "categories": [{"name": str, "points": int, "description": str}, ...]}
    - Models MUST return max_possible_score = 100 and total_score in [0, 100].
    - Each category becomes a criterion with `max_score = points` and an integer `score`.
    """

    total_points = rubric_json.get("totalPoints")
    categories: List[Dict[str, Any]] = rubric_json.get("categories", [])

    # Fall back to printing whatever rubric was provided, but emphasize 100-point requirement
    rubric_block = json.dumps(rubric_json, indent=2)

    prompt = f"""
You are evaluating a student's code submission for a programming problem.

Provide your evaluation in VALID JSON format only (no markdown fences, no prose outside JSON).

Problem:
{question}

Rubric (target total = 100 points):
{rubric_block}

Student Code:
{student_code}

STRICT INSTRUCTIONS:
1) Score ONLY according to the rubric categories above. Treat each category as a criterion.
2) For each category, output an object with fields: criterion (category name), score (integer), max_score (category points), feedback (<= 2 short sentences).
3) total_score MUST equal the sum of all category scores and MUST be in [0, 100].
4) max_possible_score MUST be 100.
5) Return ONLY JSON with EXACTLY these top-level keys:
   {{
     "criteria_scores": [
       {{"criterion": "<category>", "score": <int>, "max_score": <int>, "feedback": "<short>"}},
       ...
     ],
     "total_score": <int>,
     "max_possible_score": 100,
     "overall_feedback": "<2-3 sentences max>"
   }}
6) Do NOT include code fences, explanations, or any text outside the JSON.
""".strip()
    return prompt
