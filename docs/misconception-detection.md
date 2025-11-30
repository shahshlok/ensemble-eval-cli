# Misconception Detection System

## Overview

The misconception detection system analyzes student code submissions using multiple LLMs to identify conceptual misunderstandings. It distinguishes between **real misconceptions** (conceptual gaps) and **mechanical errors** (typos, syntax).

## Architecture

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                           SYSTEM ARCHITECTURE                                │
└──────────────────────────────────────────────────────────────────────────────┘

┌─────────────┐     ┌─────────────┐     ┌─────────────────────────────────────┐
│   Student   │     │   Question  │     │              Rubric                 │
│ Submissions │     │    (Q1-Q4)  │     │  (Topics, Tasks, Points)            │
│   (.java)   │     │    (.md)    │     │              (.md)                  │
└──────┬──────┘     └──────┬──────┘     └──────────────────┬──────────────────┘
       │                   │                               │
       └───────────────────┼───────────────────────────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │     Prompt Builder     │
              │  (grading.py)          │
              │                        │
              │  • Question context    │
              │  • Rubric criteria     │
              │  • Student code        │
              │  • Misconception guide │
              └───────────┬────────────┘
                          │
                          ▼
       ┌──────────────────────────────────────────┐
       │           LLM Ensemble                   │
       │                                          │
       │  ┌─────────────┐    ┌─────────────┐     │
       │  │   Gemini    │    │   GPT-5     │     │
       │  │  Flash Lite │    │    Nano     │     │
       │  └──────┬──────┘    └──────┬──────┘     │
       │         │                  │            │
       │         └────────┬─────────┘            │
       │                  │                      │
       └──────────────────┼──────────────────────┘
                          │
                          ▼
              ┌────────────────────────┐
              │   EvaluationDocument   │
              │      (JSON)            │
              │                        │
              │  • Scores              │
              │  • Feedback            │
              │  • Misconceptions[]    │
              │    - topic             │
              │    - name              │
              │    - description       │
              │    - evidence          │
              │    - confidence        │
              └───────────┬────────────┘
                          │
                          ▼
              ┌────────────────────────┐
              │  MisconceptionAnalyzer │
              │                        │
              │  1. Load evaluations   │
              │  2. Extract records    │
              │  3. Normalize topics   │
              │  4. Filter syntax      │
              │  5. Cluster similar    │
              │  6. Generate report    │
              └───────────┬────────────┘
                          │
                          ▼
              ┌────────────────────────┐
              │  misconception_report  │
              │        (.md)           │
              └────────────────────────┘
```

## Data Flow

### Step 1: Grading

```python
# cli.py orchestrates parallel grading
async def grade_student_with_models(student_code, question_text, rubric_data):
    prompt = construct_prompt(question_text, rubric_data, student_code)
    
    # Grade with multiple models in parallel
    results = await asyncio.gather(
        grade_with_model("gemini-2.5-flash-lite", prompt),
        grade_with_model("gpt-5-nano", prompt),
    )
    return results
```

### Step 2: LLM Response

Each LLM returns structured output matching the `LLMEvaluationResponse` schema:

```json
{
  "scores": { "total_points_awarded": 3.5, "max_points": 4.0 },
  "feedback": { "overall_comment": "...", "strengths": [...] },
  "misconceptions": [
    {
      "topic": "Data Types",
      "task": "Declaring variables with appropriate data types",
      "name": "Using int instead of double",
      "description": "Student used int for velocity which should be double",
      "confidence": 0.9,
      "evidence": [{ "snippet": "int velocity = ...", "line_start": 5 }]
    }
  ]
}
```

### Step 3: Analysis

```python
# misconception_analyzer.py
analyzer = MisconceptionAnalyzer(evals_dir="student_evals")
analyzer.load_evaluations()           # Load all JSON files
analyzer.extract_misconceptions()      # Extract and normalize
class_analysis = analyzer.analyze_class()  # Aggregate statistics
analyzer.generate_markdown_report()    # Generate report
```

## Key Components

### MisconceptionRecord

Internal representation of a single misconception:

```python
@dataclass
class MisconceptionRecord:
    student_id: str      # e.g., "Chen_Wei_200023"
    question_id: str     # e.g., "q1"
    model_name: str      # e.g., "google/gemini-2.5-flash-lite"
    topic: str           # Normalized topic (one of 4 + Other)
    task: str            # Task from rubric
    name: str            # Misconception name
    description: str     # Detailed description
    confidence: float    # 0.0 to 1.0
    evidence_count: int  # Number of evidence items
```

### ClassAnalysis

Aggregated class-wide statistics:

```python
@dataclass
class ClassAnalysis:
    total_students: int
    total_misconceptions: int
    topic_task_stats: list[TopicTaskStats]
    misconception_type_stats: list[MisconceptionTypeStats]
    question_stats: list[QuestionStats]
    progression_analysis: ProgressionAnalysis
    model_agreement_summary: dict[str, int]
```

## Prompt Engineering

The LLM prompt explicitly defines what is and isn't a misconception:

```
REPORT these as misconceptions:
- Using int instead of double for decimal calculations
- Using ^ instead of Math.pow() for exponentiation
- Wrong formula (e.g., (v1 + v0) / t instead of (v1 - v0) / t)
- Not understanding integer division (5/2 gives 2, not 2.5)

DO NOT report these as misconceptions:
- Missing semicolons (syntax typo)
- Misspelled variable names (typo)
- Missing import statements (mechanical)
- Formatting or whitespace issues
```

## Output

The system generates `misconception_report.md` with:

1. **Topic Summary** - Most difficult areas by % of class affected
2. **Other Breakdown** - Subcategories within the catch-all
3. **Common Misconceptions** - Top 10 by occurrence
4. **Per-Question Analysis** - Breakdown by Q1-Q4
5. **Progression Analysis** - Q3→Q4 learning patterns
6. **Model Agreement** - How many misconceptions each model found
