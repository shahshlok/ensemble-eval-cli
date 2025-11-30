"""Direct grading prompt builder.

In this paradigm, the model grades the student code directly against the rubric
without any intermediate steps.
"""

from __future__ import annotations

import json
from typing import Any


def build_prompt(question: str, rubric_json: dict[str, Any], student_code: str) -> str:
    """Build a direct grading prompt.

    The model is instructed to evaluate the student's code strictly according to
    the rubric and return structured JSON feedback matching the LLMEvaluationResponse schema.
    """
    rubric_block = json.dumps(rubric_json, indent=2)

    prompt = f"""
You are an expert code evaluator. Evaluate the following student submission against the assignment requirements and rubric.

## Assignment Requirements:
{question}

## Grading Rubric:
{rubric_block}

## Student Submission:
```java
{student_code}
```

Please evaluate this submission and provide a structured response containing:

1. **Scores**:
   - Total points awarded
   - Max possible points (must match rubric total)
   - Percentage score

2. **Category Scores** (for each rubric category):
   - Points awarded and max points
   - Reasoning for the score
   - Confidence score (0-1)
   - Reasoning tokens (estimate)

3. **Feedback**:
   - Overall comment
   - List of strengths
   - List of areas for improvement

4. **Misconceptions** (IMPORTANT - Read carefully):
   
   A **misconception** is a fundamental misunderstanding of a concept that reveals a gap in knowledge.
   
   **REPORT these as misconceptions:**
   - Using `int` instead of `double` for decimal calculations (misunderstands data types)
   - Using `^` instead of `Math.pow()` for exponentiation (misunderstands Java operators)
   - Wrong formula like `(v1 + v0) / t` instead of `(v1 - v0) / t` (misunderstands the math)
   - Not understanding integer division (`5/2` gives `2`, not `2.5`)
   - Incorrect operator precedence in expressions
   - Misinterpreting what the problem is asking for
   
   **DO NOT report these (they are NOT misconceptions):**
   - Missing semicolons (syntax typo, not conceptual)
   - Misspelled variable names (typo)
   - Missing import statements (mechanical, not conceptual)
   - Formatting or whitespace issues
   - Missing braces or parentheses (syntax)
   
   For each misconception, provide:
   - **Topic**: MUST be one of these exact values:
     - "Variables" (declaring, assigning, using variables in expressions, operator precedence)
     - "Data Types" (int vs double, type conversions, integer division)
     - "Constants" (Math library methods: Math.pow, Math.sqrt)
     - "Reading input from the keyboard" (Scanner usage, prompts, parsing input)
     - "Other" (if doesn't fit any of the above course topics)
   - Name and description (what concept they misunderstand)
   - Confidence (0-1)
   - Evidence (code snippet, file path, line numbers)

Ensure you calculate the total points and percentage correctly.
""".strip()
    return prompt
