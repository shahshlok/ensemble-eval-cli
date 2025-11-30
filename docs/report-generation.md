# Report Generation

## What is the report?

The final output of our system is a **Markdown report** (`misconception_report.md`) that summarizes everything we found. It's designed to be readable by instructors, TAs, and researchers without needing to understand the technical details.

---

## Report Structure

The report has these sections:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          REPORT STRUCTURE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   1. HEADER                                                                 │
│      • When was this generated?                                             │
│      • How many students analyzed?                                          │
│      • How many misconceptions found?                                       │
│                                                                             │
│   2. MOST DIFFICULT AREAS                                                   │
│      • Which topics have the most misconceptions?                           │
│      • What % of the class is affected?                                     │
│                                                                             │
│   3. 'OTHER' BREAKDOWN                                                      │
│      • What's in the catch-all category?                                    │
│      • Grouped by sub-type                                                  │
│                                                                             │
│   4. MOST COMMON MISCONCEPTIONS                                             │
│      • Top 10 specific misconceptions                                       │
│      • How many models agreed?                                              │
│                                                                             │
│   5. PER-QUESTION ANALYSIS                                                  │
│      • Which questions are hardest?                                         │
│      • What topics come up in each question?                                │
│                                                                             │
│   6. PROGRESSION ANALYSIS                                                   │
│      • Are students improving from Q3 to Q4?                                │
│      • Who struggled in both? Who improved?                                 │
│                                                                             │
│   7. MODEL AGREEMENT                                                        │
│      • How many misconceptions did each AI find?                            │
│      • Are they consistent?                                                 │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Section by Section

### 1. Header

```markdown
# Misconception Analysis Report

**Generated:** 2025-11-30 04:02:37
**Total Students Analyzed:** 25
**Total Misconceptions Detected:** 49
```

**What it tells you:**
- When the analysis was run
- How many students were included
- Total misconceptions found (after filtering syntax errors)

---

### 2. Most Difficult Areas

```markdown
| Rank | Topic | Total Misconceptions | Students Affected | Avg Confidence |
|------|-------|---------------------|-------------------|----------------|
| 1 | Other | 24 | 6/25 (24%) | 0.88 |
| 2 | Variables | 8 | 6/25 (24%) | 0.86 |
| 3 | Data Types | 6 | 4/25 (16%) | 0.89 |
```

**What it tells you:**
- **Which topics are hardest** - Ranked by how many students are affected
- **Scale of the problem** - "6 out of 25 students" gives context
- **AI confidence** - Higher = more certain the misconceptions are real

**How to read it:**

> "24% of students had issues with Variables. The AI was 86% confident on average."

**What to do with it:**

If a topic has high percentage affected:
- Consider spending more lecture time on it
- Create additional practice materials
- Review how it's being taught

---

### 3. 'Other' Breakdown

```markdown
| Sub-category | Count | Examples |
|--------------|-------|----------|
| Problem Understanding | 10 | "Misinterpreting Problem Requirements", "Wrong approach" |
| Miscellaneous | 8 | "Incorrect sign in velocity change", "Incorrect squaring" |
| Formula Application | 5 | "Formula Misapplication", "Incorrect distance formula" |
| Output Issues | 1 | "Incorrect Output Value" |
```

**What it tells you:**
- What's in the "Other" category (not hidden in a black box)
- Most common sub-types
- Specific examples

**How to read it:**

> "10 misconceptions were about Problem Understanding - students solving the wrong problem or misinterpreting requirements."

---

### 4. Most Common Misconceptions

```markdown
| Rank | Misconception | Topic | Occurrences | Models Agreeing |
|------|---------------|-------|-------------|-----------------|
| 1 | Incorrect data type usage | Data Types | 2 | 1 (gemini) |
| 2 | Incorrect operator precedence | Variables | 2 | 2 (gemini, gpt) |
```

**What it tells you:**
- **Specific issues** - Not just "Data Types" but exactly what's wrong
- **How common** - Occurrences across all students
- **Validation** - How many AI models agreed (2 = more confident)

**How to read "Models Agreeing":**

```
1 model agreed  → Could be a false positive, less certain
2 models agreed → Both AIs found the same thing, more confident
```

---

### 5. Per-Question Analysis

```markdown
| Question | Submissions | Misconception Rate | Top Misconception | Topic Breakdown |
|----------|-------------|-------------------|-------------------|-----------------|
| Q1 | 25 | 7/25 (28%) | Incorrect operator precedence | Other: 7, Variables: 5 |
| Q2 | 25 | 4/25 (16%) | Incorrect input handling | Other: 5, Reading: 3 |
| Q3 | 24 | 6/24 (25%) | Misinterpreting Problem | Other: 5, Constants: 4 |
| Q4 | 23 | 8/23 (35%) | Incorrect input handling | Other: 7, Reading: 2 |
```

**What it tells you:**
- **Which questions are hardest** - Q4 has 35% misconception rate
- **What's causing trouble** - Top misconception per question
- **Topic distribution** - What concepts each question tests

**How to read it:**

> "Q4 is the hardest - 35% of students had misconceptions. The main issue was input handling."

**What to do with it:**

If a question has very high misconception rate:
- Is the question unclear?
- Is it testing something not well covered in lectures?
- Should it be rewritten?

---

### 6. Progression Analysis (Q3 → Q4)

```markdown
| Category | Count | Percentage |
|----------|-------|------------|
| Struggled in both Q3 & Q4 | 4 | 17% |
| Improved (Q3 issues → Q4 clean) | 2 | 9% |
| Regressed (Q3 clean → Q4 issues) | 4 | 17% |
| Consistent (no issues in either) | 13 | 57% |
```

**What it tells you:**
- **Are students learning?** - Compare Q3 to Q4 performance
- **Persistent strugglers** - Students who had issues in both questions
- **Improvement rate** - Who overcame their misconceptions

**How to read it:**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       INTERPRETING PROGRESSION                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   "Struggled in both" (17%)                                                 │
│   → These students need intervention                                        │
│   → Their misconceptions persist across questions                           │
│                                                                             │
│   "Improved" (9%)                                                           │
│   → Good sign! They learned from Q3                                         │
│   → Or Q4 tests different concepts they understand better                   │
│                                                                             │
│   "Regressed" (17%)                                                         │
│   → Q4 might be harder or test new concepts                                 │
│   → Worth investigating what changed                                        │
│                                                                             │
│   "Consistent good" (57%)                                                   │
│   → Majority of class is doing fine                                         │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Key Metrics:**

```
Persistence Rate = struggled_both / struggled_in_Q3 × 100%
                 = 4 / 6 = 67%

"67% of students who struggled in Q3 also struggled in Q4"
→ Misconceptions are persisting, need intervention
```

---

### 7. Model Agreement

```markdown
| Model | Misconceptions Detected |
|-------|------------------------|
| gemini-2.5-flash-lite | 31 |
| gpt-5-nano | 18 |
```

**What it tells you:**
- **Model behavior** - Which AI is more/less aggressive in detecting issues
- **Consistency** - Large differences might indicate one model is too sensitive

**How to read it:**

> "Gemini found 31 misconceptions, GPT found 18. Gemini might be more sensitive, or GPT might miss some issues."

---

## How to Use the Report

### For Instructors

1. **Check "Most Difficult Areas"** - Which topics need more lecture time?
2. **Check "Per-Question Analysis"** - Are any questions too hard or unclear?
3. **Check "Progression Analysis"** - Are students improving?

### For TAs

1. **Check "Most Common Misconceptions"** - What to look for when grading
2. **Check "Progression Analysis"** - Which students need extra help?

### For Researchers

1. **Check all sections** - Data for your paper
2. **Note model agreement** - Important for validity claims
3. **Note filtering** - "49 real misconceptions" after filtering

---

## Generating the Report

### Command Line

```bash
uv run python cli.py analyze
```

### Programmatically

```python
from utils.misconception_analyzer import MisconceptionAnalyzer

analyzer = MisconceptionAnalyzer(evals_dir="student_evals")
analyzer.load_evaluations()
analyzer.extract_misconceptions(filter_syntax_errors=True)
analyzer.generate_markdown_report("misconception_report.md")
```

### Custom Output Path

```python
analyzer.generate_markdown_report("reports/analysis_2025-11-30.md")
```

---

## Example Report Output

Here's what a complete report looks like:

```markdown
# Misconception Analysis Report

**Generated:** 2025-11-30 04:02:37
**Total Students Analyzed:** 25
**Total Misconceptions Detected:** 49

---

### Most Difficult Areas (by % of class affected)

| Rank | Topic | Total Misconceptions | Students Affected | Avg Confidence |
|------|-------|---------------------|-------------------|----------------|
| 1 | Other | 24 | 6/25 (24%) | 0.88 |
| 2 | Variables | 8 | 6/25 (24%) | 0.86 |
| 3 | Data Types | 6 | 4/25 (16%) | 0.89 |
| 4 | Constants | 4 | 4/25 (16%) | 0.81 |
| 5 | Reading input from the keyboard | 7 | 3/25 (12%) | 0.86 |

### 'Other' Category Breakdown

The 'Other' category contains 24 misconceptions that don't fit the 4 course topics.
These are grouped by semantic similarity:

| Sub-category | Count | Examples |
|--------------|-------|----------|
| Problem Understanding | 10 | "Misinterpreting Problem Requirements", "Wrong approach" |
| Miscellaneous | 8 | "Incorrect sign in velocity change", "Incorrect squaring" |
| Formula Application | 5 | "Formula Misapplication", "Incorrect distance formula" |
| Output Issues | 1 | "Incorrect Output Value" |

...
```
