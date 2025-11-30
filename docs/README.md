# Misconception Detection System - Documentation

## What is this project?

This tool helps **instructors and teaching assistants** understand where students are struggling in programming assignments. Instead of manually reading through hundreds of student submissions, we use AI (Large Language Models like GPT and Gemini) to automatically identify **misconceptions** - places where students misunderstand a concept.

### Example

A student writes:
```java
int velocity = 3.5;  // Wrong! velocity should be double, not int
```

This isn't just a typo - it shows the student doesn't understand when to use `int` vs `double`. That's a **misconception** about Data Types.

Compare to:
```java
System.out.println("Hello World")  // Missing semicolon
```

This is just a **syntax error** - the student knows what they want to do, they just forgot a semicolon. We filter these out because they don't reveal conceptual gaps.

---

## Who is this for?

| Audience | What they care about |
|----------|---------------------|
| **Instructors** | "Which topics are my students struggling with most?" |
| **TAs** | "Which students need extra help?" |
| **Researchers** | "Can AI reliably detect student misconceptions?" |
| **Developers** | "How does the system work technically?" |

---

## How it works (Simple Version)

```
Student Code  →  AI Analysis  →  Misconception Report
     ↓              ↓                    ↓
  Q1.java      "This student       "44% of class
               used int instead     struggled with
               of double"           Data Types"
```

1. **Students submit code** (Java files for Q1-Q4)
2. **AI models grade the code** and identify misconceptions
3. **We clean the data** by removing syntax errors and organizing by topic
4. **Report shows patterns** - which topics are hardest, which students struggle most

---

## Key Concepts (Plain English)

### What is a Misconception?

A **misconception** is when a student fundamentally misunderstands a concept. It's not a typo or careless mistake - it reveals a gap in their knowledge.

| Misconception (We Track) | Not a Misconception (We Filter) |
|--------------------------|--------------------------------|
| Using `int` for decimal numbers | Forgetting a semicolon |
| Using `^` for power (it's XOR in Java) | Misspelling a variable name |
| Wrong formula: `(a+b)/c` instead of `(a-b)/c` | Missing an import statement |

**Why this matters**: If 40% of your class uses `int` instead of `double`, that's a teaching opportunity. If 40% forgot semicolons, that's just carelessness.

### What are the 4 Course Topics?

These come directly from the assignment rubric - what the assignment is designed to test:

| Topic | What it means | Example misconception |
|-------|--------------|----------------------|
| **Variables** | Declaring and using variables | Wrong operator precedence |
| **Data Types** | Choosing int vs double | Using int for decimals |
| **Constants** | Using Math library | Using `^` instead of `Math.pow()` |
| **Reading input** | Using Scanner | Not closing Scanner |

### What is "Other"?

Some misconceptions don't fit neatly into the 4 topics above. Instead of forcing them into the wrong category, we put them in "Other" and break it down further:

- **Problem Understanding** - Student solved the wrong problem
- **Formula Application** - Wrong math formula
- **Output Issues** - Wrong output format
- **Miscellaneous** - Everything else

---

## Documentation Guide

| Document | For Who | What You'll Learn |
|----------|---------|-------------------|
| [Misconception Detection](misconception-detection.md) | Everyone | How the whole system works, start to finish |
| [Topic Taxonomy](topic-taxonomy.md) | Researchers, Devs | How we categorize misconceptions into topics |
| [Filtering & Subcategories](filtering-subcategories.md) | Researchers, Devs | How we remove noise and break down "Other" |
| [Report Generation](report-generation.md) | Everyone | What each part of the report means |

---

## Quick Start

### For Instructors: Reading the Report

Open `misconception_report.md` and look for:

1. **"Most Difficult Areas"** - Which topics have the most misconceptions
2. **"Per-Question Analysis"** - Which questions are hardest
3. **"Progression Analysis"** - Are students improving from Q3 to Q4?

### For Developers: Running the System

```bash
# Grade student submissions
uv run python cli.py grade

# Analyze misconceptions
uv run python cli.py analyze
```

---

## Glossary

| Term | Definition |
|------|------------|
| **Misconception** | A fundamental misunderstanding of a concept (not a typo) |
| **Topic** | A category of learning objective (Variables, Data Types, etc.) |
| **Syntax Error** | A mechanical coding error (missing semicolon, typo) - filtered out |
| **LLM** | Large Language Model - AI like GPT or Gemini |
| **Normalization** | Converting different phrasings to a standard category |
| **Other** | Catch-all for misconceptions that don't fit the 4 main topics |

---

## The Pipeline (Visual)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         THE BIG PICTURE                                     │
└─────────────────────────────────────────────────────────────────────────────┘

  STEP 1: INPUT                STEP 2: AI GRADING            STEP 3: ANALYSIS
  ─────────────               ────────────────              ────────────────
                              
  ┌──────────┐                ┌──────────────┐              ┌──────────────┐
  │ Student  │                │   Gemini     │              │   Organize   │
  │   Code   │ ──────────────►│     +        │─────────────►│   by Topic   │
  │ (Q1.java)│                │   GPT-5      │              │              │
  └──────────┘                └──────────────┘              └──────┬───────┘
                                                                   │
  ┌──────────┐                     Finds:                          │
  │ Question │                 "Student used int                   ▼
  │   Text   │                  instead of double"         ┌──────────────┐
  └──────────┘                                             │   Filter     │
                                                           │   Syntax     │
  ┌──────────┐                                             │   Errors     │
  │  Rubric  │                                             └──────┬───────┘
  │ (Topics) │                                                    │
  └──────────┘                                                    ▼
                                                           ┌──────────────┐
                                                           │   Generate   │
                                                           │   Report     │
                                                           └──────────────┘
                                                                   │
                                                                   ▼
                                                           ┌──────────────┐
                                                           │ "44% of      │
                                                           │  class had   │
                                                           │  Data Type   │
                                                           │  issues"     │
                                                           └──────────────┘
```

---

## Recent Changes

### Phase 1 (Completed)

| Change | Why |
|--------|-----|
| Keep 4 course topics "pure" | So instructor can trust the topic breakdown |
| Add "Other" category | For misconceptions that don't fit |
| Filter syntax errors | They're not real misconceptions |
| Break down "Other" | Visibility into what's being caught |
| Improve AI prompts | Help AI distinguish misconceptions from typos |
