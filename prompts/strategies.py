"""
Prompt strategies for grading and misconception detection research.

This module contains multiple prompt architectures for A/B testing.
The key research question: Can LLMs discover misconceptions WITHOUT being given examples?
"""

from __future__ import annotations

import json
from typing import Any
from enum import Enum


class PromptStrategy(str, Enum):
    """Available prompt strategies for testing."""

    BASELINE = "baseline"  # Current approach with examples (control)
    MINIMAL = "minimal"  # No examples, minimal guidance
    SOCRATIC = "socratic"  # Chain-of-thought reasoning
    CONTRASTIVE = "contrastive"  # Compare against reference solution
    RUBRIC_ONLY = "rubric_only"  # Grade only, no misconception hunting


def build_baseline_prompt(question: str, rubric: dict[str, Any], student_code: str) -> str:
    """
    BASELINE (Control): Current approach that lists example misconceptions.

    This is what we're testing AGAINST - it biases the LLM by showing examples.
    """
    rubric_str = json.dumps(rubric, indent=2)

    return f"""
You are an expert grader for a Computer Science assignment.

**Question:**
{question}

**Rubric:**
{rubric_str}

**Student Submission:**
```java
{student_code}
```

Evaluate the student's submission based on the provided rubric.
Provide a structured output containing:
1. Scores for each category in the rubric.
2. Specific feedback for each category.
3. Identification of any **misconceptions** (IMPORTANT - read carefully):

   A **misconception** is a fundamental misunderstanding of a concept, NOT a simple typo or syntax error.
   
   **REPORT these as misconceptions:**
   - Using `int` instead of `double` for decimal calculations
   - Using `^` instead of `Math.pow()` for exponentiation  
   - Wrong formula (e.g., `(v1 + v0) / t` instead of `(v1 - v0) / t`)
   - Not understanding integer division (`5/2` gives `2`, not `2.5`)
   - Incorrect operator precedence
   - Misinterpreting the problem requirements
   
   **DO NOT report these as misconceptions:**
   - Missing semicolons, brackets, or braces (syntax typo)
   - Misspelled variable names (typo)
   - Missing import statements (mechanical)
   - Formatting or whitespace issues
   
   For each misconception, specify:
   - **Topic**: One of: "Variables", "Data Types", "Constants", "Reading input from the keyboard", or "Other"
   - The task name from the rubric where it appears

4. Overall feedback.
""".strip()


def build_minimal_prompt(question: str, rubric: dict[str, Any], student_code: str) -> str:
    """
    MINIMAL: No example misconceptions. Let the LLM discover them naturally.

    This tests whether LLMs can identify misconceptions without being primed.
    """
    rubric_str = json.dumps(rubric, indent=2)

    return f"""
You are evaluating a CS1 student's Java submission.

## Problem
{question}

## Student Code
```java
{student_code}
```

## Rubric
{rubric_str}

## Instructions

1. **Score each rubric criterion** (points awarded, brief justification)

2. **Identify conceptual misunderstandings**
   
   A conceptual misunderstanding is when the student demonstrates a flawed mental model 
   about how programming or the problem domain works. This is different from:
   - Typos or syntax errors
   - Forgetting something mechanical (like closing a resource)
   - Style preferences
   
   For each misunderstanding you find, provide:
   
   **Required:**
   - `name`: Short label (e.g., "Integer division truncation")
   - `description`: What behavior this misconception reflects
   - `topic`: One of "Variables", "Data Types", "Constants", "Reading input from the keyboard", or "Other"
   - `student_belief`: What does the student appear to believe? State from their perspective.
   - `correct_understanding`: What is the correct understanding?
   - `confidence`: How confident are you this is a real misconception? (0.0-1.0)
   - `evidence`: Quote the specific code that demonstrates this
   
   **Classification:**
   - `severity`: How bad is this? (critical/major/minor/surface)
   - `category`: What type? (conceptual/procedural/syntactic/semantic)
   
   **Analysis:**
   - `symptoms`: Observable problems in the code/output (list)
   - `root_cause`: Why might the student have this misconception?
   - `affects_output`: Does this change the program's output? (true/false)
   - `is_recurring`: Does this pattern appear multiple times? (true/false)
   
   **Remediation:**
   - `remediation_hint`: How could the student fix this?
   - `related_concepts`: What should they review? (list)

3. **Provide overall feedback** (strengths and areas for improvement)
""".strip()


def build_socratic_prompt(question: str, rubric: dict[str, Any], student_code: str) -> str:
    """
    SOCRATIC: Chain-of-thought reasoning through the code.

    Forces the LLM to reason step-by-step, which may surface misconceptions
    that direct prompting misses.
    """
    rubric_str = json.dumps(rubric, indent=2)

    return f"""
You are a teaching assistant analyzing a CS1 student's code submission.

## Problem Statement
{question}

## Student's Code
```java
{student_code}
```

## Rubric
{rubric_str}

---

Analyze this submission step by step:

### STEP 1: What should correct code do?
Describe the expected algorithm and key implementation details.

### STEP 2: Trace the student's code
Walk through their code line by line. What does each part actually do?

### STEP 3: Identify divergences
Where does the student's approach differ from correct implementation?
For each divergence, note:
- The specific code
- What it does vs. what it should do

### STEP 4: Diagnose the WHY
For each divergence from Step 3, determine:
- Is this a **careless mistake** (typo, forgot something, would know better)?
- Is this a **conceptual error** (student misunderstands how something works)?

For conceptual errors, explain:
- What does the student seem to believe?
- What is the correct understanding?
- Topic category: "Variables", "Data Types", "Constants", "Reading input from the keyboard", or "Other"

### STEP 5: Score against rubric
Assign points for each criterion with justification.

### STEP 6: Summary
- Total score
- Key misconceptions found (if any)
- Overall feedback
""".strip()


def build_contrastive_prompt(
    question: str,
    rubric: dict[str, Any],
    student_code: str,
    reference_solution: str,
) -> str:
    """
    CONTRASTIVE: Compare student code against a reference solution.

    Explicit comparison may help the LLM identify deviations more accurately.
    Requires a reference solution to be provided.
    """
    rubric_str = json.dumps(rubric, indent=2)

    return f"""
You are comparing a student's code submission against a reference solution.

## Problem
{question}

## Reference Solution (Correct)
```java
{reference_solution}
```

## Student Submission
```java
{student_code}
```

## Rubric
{rubric_str}

---

## Instructions

1. **Identify differences** between the student's code and the reference.
   For each difference:
   - Quote both versions
   - Classify as:
     - **STYLE**: Different but equally valid approach
     - **TYPO**: Obvious mistake, student likely knows the correct way
     - **MISCONCEPTION**: Student appears to misunderstand a concept
   
2. **For each MISCONCEPTION**, explain:
   - What the student seems to believe
   - Why this belief is incorrect
   - Topic: "Variables", "Data Types", "Constants", "Reading input from the keyboard", or "Other"

3. **Score against rubric** with justifications

4. **Overall feedback** for the student
""".strip()


def build_rubric_only_prompt(question: str, rubric: dict[str, Any], student_code: str) -> str:
    """
    RUBRIC_ONLY: Grade only, no misconception detection.

    Control condition to measure if misconception hunting affects grading accuracy.
    """
    rubric_str = json.dumps(rubric, indent=2)

    return f"""
You are grading a CS1 student's Java submission.

## Problem
{question}

## Student Code
```java
{student_code}
```

## Rubric
{rubric_str}

## Instructions

Grade this submission strictly according to the rubric.

For each rubric criterion:
- Award points (0 to max)
- Provide brief justification

Then provide:
- Total score
- Overall feedback (1-2 sentences)

Focus only on whether the code meets the rubric requirements.
""".strip()


# Strategy registry
STRATEGIES = {
    PromptStrategy.BASELINE: build_baseline_prompt,
    PromptStrategy.MINIMAL: build_minimal_prompt,
    PromptStrategy.SOCRATIC: build_socratic_prompt,
    PromptStrategy.RUBRIC_ONLY: build_rubric_only_prompt,
}

# Contrastive requires reference solution, handled separately
STRATEGIES_WITH_REFERENCE = {
    PromptStrategy.CONTRASTIVE: build_contrastive_prompt,
}


def build_prompt(
    strategy: PromptStrategy,
    question: str,
    rubric: dict[str, Any],
    student_code: str,
    reference_solution: str | None = None,
) -> str:
    """
    Build a prompt using the specified strategy.

    Args:
        strategy: Which prompt architecture to use
        question: The assignment question text
        rubric: The grading rubric as a dict
        student_code: The student's submission
        reference_solution: Required for CONTRASTIVE strategy

    Returns:
        The constructed prompt string
    """
    if strategy == PromptStrategy.CONTRASTIVE:
        if reference_solution is None:
            raise ValueError("CONTRASTIVE strategy requires a reference_solution")
        return build_contrastive_prompt(question, rubric, student_code, reference_solution)

    builder = STRATEGIES.get(strategy)
    if builder is None:
        raise ValueError(f"Unknown strategy: {strategy}")

    return builder(question, rubric, student_code)
