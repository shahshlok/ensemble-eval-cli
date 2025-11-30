# Filtering & Subcategories

## What's the point?

This document explains two important data cleaning steps:

1. **Filtering** - Removing things that aren't real misconceptions
2. **Subcategorizing** - Breaking down the "Other" category so it's useful

---

## Part 1: Filtering Syntax Errors

### The Problem

AI models sometimes report things like "missing semicolon" as misconceptions. But is forgetting a semicolon really a **misconception**?

**No.** A misconception means the student doesn't understand something. Forgetting a semicolon is just a typo - they know what a semicolon is, they just forgot one.

### What's the difference?

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    MISCONCEPTION vs SYNTAX ERROR                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   MISCONCEPTION (Keep)                 SYNTAX ERROR (Filter Out)            │
│   ────────────────────                 ─────────────────────────            │
│                                                                             │
│   Student writes:                      Student writes:                      │
│     int velocity = 3.5;                  System.out.println("Hi")           │
│                                                              ▲              │
│   They THINK int can hold              Missing semicolon ────┘              │
│   decimal values. They're                                                   │
│   WRONG about how types work.          They KNOW they need a semicolon.    │
│                                        They just FORGOT it.                 │
│   → Teaching opportunity               → Just point it out, they'll fix it │
│   → Needs explanation                  → No conceptual gap                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### What we filter out

| Filtered | Why |
|----------|-----|
| Missing semicolons | Typo, not conceptual |
| Misspelled variables | Typo |
| Missing import statements | Mechanical, not conceptual |
| Missing brackets/braces | Typo |
| Formatting issues | Style, not understanding |
| Whitespace problems | Style |

### What we keep

| Kept | Why |
|------|-----|
| Using `int` instead of `double` | Doesn't understand types |
| Using `^` instead of `Math.pow()` | Doesn't know Java operators |
| Wrong formula | Doesn't understand the math |
| Integer division confusion | Doesn't know `5/2 = 2` |
| Wrong operator precedence | Doesn't understand order of operations |

### How filtering works

We filter in **two layers**:

```
                    Raw Misconception from AI
                              │
                              ▼
                 ┌────────────────────────┐
                 │  LAYER 1: Topic Check  │
                 │                        │
                 │  Is the topic "Syntax" │
                 │  or "syntax errors"?   │
                 └────────────┬───────────┘
                              │
              ┌───────────────┴───────────────┐
              │                               │
           Yes│                            No │
              ▼                               ▼
        ┌──────────┐             ┌────────────────────────┐
        │ FILTERED │             │  LAYER 2: Keyword Check│
        │   OUT    │             │                        │
        └──────────┘             │  Does the name or      │
                                 │  description contain:  │
                                 │  "semicolon", "typo",  │
                                 │  "misspell", "bracket",│
                                 │  "import statement"?   │
                                 └────────────┬───────────┘
                                              │
                              ┌───────────────┴───────────────┐
                              │                               │
                           Yes│                            No │
                              ▼                               ▼
                        ┌──────────┐                   ┌──────────┐
                        │ FILTERED │                   │   KEPT   │
                        │   OUT    │                   │          │
                        └──────────┘                   └──────────┘
```

### Results

```
Before filtering: 56 misconceptions detected
After filtering:  49 real misconceptions
Removed:           7 syntax errors (12.5%)
```

### Why this matters for research

If you're writing a paper that says "we detected 56 misconceptions," but 7 of those are just missing semicolons, your research is weaker.

By filtering, we can confidently say: "We detected 49 conceptual misconceptions" - a more meaningful claim.

---

## Part 2: Breaking Down "Other"

### The Problem

After categorizing misconceptions into the 4 course topics, some don't fit anywhere:

```
Topic Breakdown:
├── Data Types: 6 misconceptions
├── Variables: 8 misconceptions  
├── Constants: 4 misconceptions
├── Reading Input: 7 misconceptions
└── Other: 24 misconceptions  ← What's in here??
```

"Other" is almost half the total! That's a black box - not useful for instructors.

### The Solution

We **subcategorize** the "Other" category into meaningful groups:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       "OTHER" BREAKDOWN                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  OTHER (24 total)                                                           │
│       │                                                                     │
│       │                                                                     │
│       ├───► PROBLEM UNDERSTANDING (10)                                      │
│       │     │                                                               │
│       │     │  Students who solved the wrong problem or                     │
│       │     │  misunderstood what was being asked                          │
│       │     │                                                               │
│       │     ├── "Misinterpreting Problem Requirements"                      │
│       │     ├── "Complete misunderstanding of the problem's objective"      │
│       │     └── "Wrong approach / Solution not matching prompt"             │
│       │                                                                     │
│       │                                                                     │
│       ├───► FORMULA APPLICATION (5)                                         │
│       │     │                                                               │
│       │     │  Students who used wrong math formulas                        │
│       │     │  (not about Math library - that's "Constants")               │
│       │     │                                                               │
│       │     ├── "Incorrect application of distance formula"                 │
│       │     ├── "Formula Misapplication"                                    │
│       │     └── "Incorrect application of Heron's formula"                  │
│       │                                                                     │
│       │                                                                     │
│       ├───► OUTPUT ISSUES (1)                                               │
│       │     │                                                               │
│       │     │  Students with output formatting problems                     │
│       │     │                                                               │
│       │     └── "Incorrect Output Value"                                    │
│       │                                                                     │
│       │                                                                     │
│       └───► MISCELLANEOUS (8)                                               │
│             │                                                               │
│             │  Everything that doesn't match the above patterns             │
│             │                                                               │
│             ├── "Incorrect sign in velocity change"                         │
│             ├── "Incorrect squaring operator"                               │
│             └── "Incorrect data type usage" (in Other context)              │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### How subcategorization works

We use **keyword matching** on the misconception name:

```python
SUBCATEGORY_RULES = [
    ("Formula Application", {
        "keywords": ["formula", "distance", "acceleration", "heron", "area"]
    }),
    ("Problem Understanding", {
        "keywords": ["misinterpret", "misunderstand", "wrong problem", "wrong approach"]
    }),
    ("Output Issues", {
        "keywords": ["output", "display", "print"]
    }),
]
```

**Important:** We match on the **NAME** first, not the description. Why?

### The False Positive Problem

Consider this misconception:

```
Name: "Incorrect data type usage"
Description: "The student uses int for variables, even though 
             the correct PROBLEM would need double values..."
```

The description contains the word "problem" - but this isn't about problem understanding! It's about data types.

If we matched on description, we'd wrongly categorize it.

**Solution:** Match on NAME first (more reliable), only fall back to description with very specific phrases.

```
            Misconception Name
                   │
                   ▼
          ┌─────────────────┐
          │ PASS 1: Match   │
          │ keywords in     │
          │ NAME only       │
          └────────┬────────┘
                   │
         ┌─────────┴─────────┐
         │                   │
      Match                No Match
         │                   │
         ▼                   ▼
    ┌─────────┐    ┌─────────────────┐
    │ Assign  │    │ PASS 2: Match   │
    │ to that │    │ specific phrases│
    │ subcat  │    │ in DESCRIPTION  │
    └─────────┘    └────────┬────────┘
                           │
                  ┌────────┴────────┐
                  │                 │
               Match            No Match
                  │                 │
                  ▼                 ▼
             ┌─────────┐      ┌──────────────┐
             │ Assign  │      │ Assign to    │
             │ to that │      │ "Miscellaneous│
             │ subcat  │      └──────────────┘
             └─────────┘
```

---

## Summary

### What we do

| Step | What | Why |
|------|------|-----|
| 1. Filter syntax errors | Remove semicolons, typos, imports | They're not conceptual |
| 2. Normalize topics | Map to 4 course topics + Other | Consistent categories |
| 3. Subcategorize Other | Break into Problem/Formula/Output/Misc | Visibility into catch-all |

### The numbers

```
Raw from AI:           56 items
After syntax filter:   49 items (7 removed)

In course topics:      25 items
  - Variables:          8
  - Data Types:         6
  - Constants:          4
  - Reading Input:      7

In Other:              24 items
  - Problem Understanding: 10
  - Miscellaneous:         8
  - Formula Application:   5
  - Output Issues:         1
```

### Why this matters

1. **Research validity** - We can claim "49 real misconceptions" with confidence
2. **Instructor usefulness** - Clear breakdown of what students struggle with
3. **Transparency** - Nothing hidden in a black-box "Other" category
