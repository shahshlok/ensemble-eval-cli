# Misconception Detection System

## What does this system do?

In simple terms: **It reads student code and figures out what concepts they don't understand.**

When a student makes an error, there are two possibilities:
1. **They made a typo** - They know what to do, just made a mechanical mistake
2. **They have a misconception** - They fundamentally misunderstand something

This system finds the second type - the conceptual gaps that need teaching intervention.

---

## Why use AI for this?

**The manual approach doesn't scale:**
- 100 students × 4 questions = 400 submissions to review
- Each submission might have multiple issues
- TAs are expensive and inconsistent

**The AI approach:**
- Reviews all 400 submissions in minutes
- Applies consistent criteria
- Multiple AI models cross-check each other
- Produces structured, analyzable data

---

## How it works (Step by Step)

### Step 1: Gather the Inputs

We need three things for each student:

```
┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
│  STUDENT CODE   │   │    QUESTION     │   │     RUBRIC      │
│                 │   │                 │   │                 │
│  Their Q1.java  │   │  What they      │   │  What topics    │
│  submission     │   │  were asked     │   │  we're testing  │
│                 │   │  to do          │   │                 │
└─────────────────┘   └─────────────────┘   └─────────────────┘
```

### Step 2: Ask the AI

We send all three to an AI model with specific instructions:

> "Look at this student's code. Based on the question and rubric, identify any **misconceptions** - places where they fundamentally misunderstand a concept. Don't report typos or syntax errors."

We use **two different AI models** (Gemini and GPT) to cross-check. If both find the same issue, we're more confident it's real.

### Step 3: AI Returns Structured Data

The AI doesn't just say "there's a problem." It returns structured information:

```
Misconception Found:
├── Topic: "Data Types"
├── Name: "Using int instead of double"  
├── Description: "Student used int for velocity which needs decimals"
├── Confidence: 0.9 (very confident)
└── Evidence: "int velocity = 3.5;" on line 5
```

### Step 4: We Clean and Organize

Raw AI output is messy. Different models say things differently:
- Model A: "Data type issue"
- Model B: "Wrong variable type"
- Model A: "Missing semicolon" (this shouldn't be here!)

We **normalize** everything to standard categories and **filter out** syntax errors.

### Step 5: Generate the Report

Finally, we aggregate everything into a readable report:

```
Most Difficult Topics:
1. Data Types - 6 students affected (24%)
2. Variables - 8 students affected (32%)
...
```

---

## The Architecture (Visual)

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                           SYSTEM ARCHITECTURE                                │
└──────────────────────────────────────────────────────────────────────────────┘

INPUT LAYER                    PROCESSING LAYER                 OUTPUT LAYER
───────────                    ────────────────                 ────────────

┌─────────────┐                                                
│   Student   │                                                
│ Submissions │────┐                                           
│   (.java)   │    │                                           
└─────────────┘    │          ┌────────────────────┐           
                   │          │                    │           
┌─────────────┐    ├─────────►│   PROMPT BUILDER   │           
│   Question  │────┤          │                    │           
│    (Q1-Q4)  │    │          │  Combines all      │           
└─────────────┘    │          │  inputs into       │           
                   │          │  a clear prompt    │           
┌─────────────┐    │          │  for the AI        │           
│   Rubric    │────┘          │                    │           
│   (Topics)  │               └─────────┬──────────┘           
└─────────────┘                         │                      
                                        ▼                      
                              ┌────────────────────┐           
                              │                    │           
                              │    AI ENSEMBLE     │           
                              │                    │           
                              │  ┌──────┐ ┌──────┐ │           
                              │  │Gemini│ │ GPT  │ │           
                              │  └──────┘ └──────┘ │           
                              │                    │           
                              │  Both models       │           
                              │  analyze the       │           
                              │  same code         │           
                              │                    │           
                              └─────────┬──────────┘           
                                        │                      
                                        ▼                      
                              ┌────────────────────┐          ┌─────────────┐
                              │                    │          │             │
                              │     ANALYZER       │─────────►│   REPORT    │
                              │                    │          │    (.md)    │
                              │  • Normalize       │          │             │
                              │  • Filter syntax   │          │  "44% of    │
                              │  • Group by topic  │          │   students  │
                              │  • Count patterns  │          │   had Data  │
                              │                    │          │   Type      │
                              └────────────────────┘          │   issues"   │
                                                              │             │
                                                              └─────────────┘
```

---

## What the AI Looks For

We explicitly tell the AI what counts as a misconception:

### ✅ REPORT These (Real Misconceptions)

| Issue | Why it's a misconception |
|-------|-------------------------|
| Using `int` instead of `double` | Doesn't understand when decimals are needed |
| Using `^` for exponents | Doesn't know `^` is XOR in Java, not power |
| Wrong formula `(v1+v0)/t` | Doesn't understand the physics/math |
| Integer division confusion | Doesn't know `5/2 = 2` in Java |

### ❌ DON'T Report These (Not Misconceptions)

| Issue | Why it's not a misconception |
|-------|------------------------------|
| Missing semicolon | Typo - they know what to do |
| Misspelled variable | Typo - would fix if pointed out |
| Missing import | Mechanical - not conceptual |
| Bad indentation | Style issue - not understanding |

---

## The Output: What You Get

### EvaluationDocument (JSON)

For each student × question, we save a JSON file:

```json
{
  "student_id": "Chen_Wei_200023",
  "question_id": "q1",
  "models": {
    "gemini-2.5-flash-lite": {
      "scores": { "total": 3.5, "max": 4.0 },
      "misconceptions": [
        {
          "topic": "Data Types",
          "name": "Using int instead of double",
          "confidence": 0.9
        }
      ]
    },
    "gpt-5-nano": {
      "scores": { "total": 3.0, "max": 4.0 },
      "misconceptions": [
        {
          "topic": "Data Types", 
          "name": "Integer type for decimal values",
          "confidence": 0.85
        }
      ]
    }
  }
}
```

Notice both models found the same issue (phrased differently). That's cross-validation!

### Misconception Report (Markdown)

The final human-readable report with:
- Topic breakdown (which areas are hardest)
- Common misconceptions (what specific issues appear most)
- Per-question analysis (which questions cause problems)
- Student progression (are they learning?)

---

## Technical Details (For Developers)

### Key Files

| File | Purpose |
|------|---------|
| `cli.py` | Main entry point, orchestrates grading |
| `utils/grading.py` | Builds prompts, calls AI models |
| `utils/misconception_analyzer.py` | Processes results, generates reports |
| `pydantic_models/` | Data structures for type safety |

### Key Functions

```python
# Grade one student's code with all models
async def grade_student_with_models(student_code, question_text, rubric_data):
    prompt = construct_prompt(question_text, rubric_data, student_code)
    results = await asyncio.gather(
        grade_with_model("gemini-2.5-flash-lite", prompt),
        grade_with_model("gpt-5-nano", prompt),
    )
    return results

# Analyze all evaluations
analyzer = MisconceptionAnalyzer(evals_dir="student_evals")
analyzer.load_evaluations()
analyzer.extract_misconceptions(filter_syntax_errors=True)
analyzer.generate_markdown_report()
```

### Data Flow

```
cli.py (orchestrate)
    ↓
grading.py (call AI)
    ↓
openrouter_sdk.py (API calls)
    ↓
JSON files saved to student_evals/
    ↓
misconception_analyzer.py (analyze)
    ↓
misconception_report.md (output)
```
