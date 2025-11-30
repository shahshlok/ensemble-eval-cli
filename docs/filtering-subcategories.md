# Filtering & Subcategories

## Overview

This document explains two key features:
1. **Syntax Error Filtering** - Removing mechanical errors that aren't conceptual misconceptions
2. **Other Subcategories** - Breaking down the catch-all "Other" category for visibility

## Syntax Error Filtering

### What Gets Filtered

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         SYNTAX ERROR FILTERING                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    FILTERED OUT (Not Misconceptions)                │   │
│  ├─────────────────────────────────────────────────────────────────────┤   │
│  │                                                                      │   │
│  │  • Missing semicolons      "Student forgot ; at line 10"            │   │
│  │  • Typos                   "Misspelled 'velocity' as 'velosity'"    │   │
│  │  • Missing imports         "Missing import for Scanner"              │   │
│  │  • Bracket/brace errors    "Missing closing brace"                  │   │
│  │  • Formatting issues       "Inconsistent indentation"               │   │
│  │  • Whitespace problems     "Extra spaces in expression"             │   │
│  │                                                                      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
│                                    │                                        │
│                                    ▼                                        │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    KEPT (Real Misconceptions)                       │   │
│  ├─────────────────────────────────────────────────────────────────────┤   │
│  │                                                                      │   │
│  │  • int instead of double   "Used int for decimal calculations"      │   │
│  │  • Wrong operator          "Used ^ instead of Math.pow()"           │   │
│  │  • Wrong formula           "Used (v1+v0)/t instead of (v1-v0)/t"    │   │
│  │  • Integer division        "Didn't understand 5/2 gives 2"          │   │
│  │  • Operator precedence     "Wrong order of operations"              │   │
│  │                                                                      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Implementation

Two-layer filtering:

```python
# Layer 1: Topic-based filtering
if normalized_topic == SYNTAX_TOPIC:
    filtered_count += 1
    continue

# Layer 2: Keyword-based filtering
SYNTAX_ERROR_KEYWORDS = [
    "semicolon",
    "missing semicolon",
    "typo",
    "misspell",
    "misspelled",
    "spelling",
    "whitespace",
    "formatting",
    "indentation",
    "bracket",
    "brace",
    "parenthesis",
    "compilation error",
    "syntax error",
    "missing import",
    "import statement",
]

def is_syntax_error(name: str, description: str) -> bool:
    """Check if a misconception is actually just a syntax error."""
    combined = f"{name} {description}".lower()
    
    for keyword in SYNTAX_ERROR_KEYWORDS:
        if keyword in combined:
            return True
    
    return False
```

### Results

```
Before filtering: 56 misconceptions
After filtering:  49 misconceptions
Filtered out:      7 syntax errors
```

### Why Filter?

**Research Validity**: A missing semicolon doesn't indicate a conceptual misunderstanding. The student would fix it if pointed out. Real misconceptions reveal gaps in understanding that need targeted instruction.

## Other Category Subcategories

### Problem

The "Other" category contains misconceptions that don't fit the 4 course topics. Without breakdown, it's a black box:

```
Other: 24 (49%)   ← What's in here?
```

### Solution

Semantic subcategorization using keyword matching:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      OTHER CATEGORY BREAKDOWN                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  "Other" (24 total)                                                         │
│       │                                                                     │
│       ├──► Problem Understanding (10)                                       │
│       │    • Misinterpreting requirements                                   │
│       │    • Wrong approach                                                 │
│       │    • Solving different problem                                      │
│       │                                                                     │
│       ├──► Formula Application (5)                                          │
│       │    • Wrong formula usage                                            │
│       │    • Distance/area calculation errors                               │
│       │                                                                     │
│       ├──► Output Issues (1)                                                │
│       │    • Incorrect output formatting                                    │
│       │                                                                     │
│       └──► Miscellaneous (8)                                                │
│            • Doesn't match any pattern                                      │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Implementation

Two-pass keyword matching (NAME first, then DESCRIPTION):

```python
def get_other_subcategories(self) -> dict[str, list[MisconceptionRecord]]:
    """Group misconceptions in 'Other' into sub-categories."""
    
    SUBCATEGORY_RULES = [
        ("Formula Application", {
            "name_keywords": ["formula", "distance", "acceleration", 
                             "heron", "area", "geometry", "calculation"],
            "description_keywords": []
        }),
        ("Problem Understanding", {
            "name_keywords": ["misinterpret", "misunderstand", "wrong problem",
                             "wrong approach", "different problem", 
                             "problem requirement", "problem interpretation",
                             "misappl", "disregard", "objective"],
            "description_keywords": ["solved a different", "wrong problem", 
                                    "misunderstood the"]
        }),
        ("Output Issues", {
            "name_keywords": ["output", "display", "print"],
            "description_keywords": []
        }),
        ("Algorithm/Logic", {
            "name_keywords": ["algorithm", "logic error"],
            "description_keywords": []
        }),
    ]

    for record in other_records:
        name_lower = record.name.lower()
        desc_lower = record.description.lower()
        
        matched = False
        
        # First pass: match on NAME only (more reliable)
        for subcat, rules in SUBCATEGORY_RULES:
            for keyword in rules["name_keywords"]:
                if keyword in name_lower:
                    subcategories[subcat].append(record)
                    matched = True
                    break
        
        # Second pass: match on DESCRIPTION (stricter keywords)
        if not matched:
            for subcat, rules in SUBCATEGORY_RULES:
                for keyword in rules["description_keywords"]:
                    if keyword in desc_lower:
                        subcategories[subcat].append(record)
                        matched = True
                        break
        
        # Fallback: Miscellaneous
        if not matched:
            subcategories["Miscellaneous"].append(record)
```

### Why Two-Pass?

**Problem**: Description often contains common words that cause false categorization.

Example:
- Name: "Incorrect data type usage"
- Description: "...if the correct **problem** were being solved..."

If we match on description, "problem" would categorize this as "Problem Understanding" - wrong!

**Solution**: Match on NAME first (more reliable), only fall back to DESCRIPTION with stricter keywords.

### Report Output

```markdown
### 'Other' Category Breakdown

The 'Other' category contains 24 misconceptions that don't fit the 4 course topics.
These are grouped by semantic similarity:

| Sub-category | Count | Examples |
|--------------|-------|----------|
| Problem Understanding | 10 | "Misinterpreting Problem Requirements", "Wrong approach" |
| Miscellaneous | 8 | "Incorrect sign in velocity change", "Incorrect squaring" |
| Formula Application | 5 | "Formula Misapplication", "Incorrect distance formula" |
| Output Issues | 1 | "Incorrect Output Value" |
```

## Summary

| Feature | Purpose | Result |
|---------|---------|--------|
| Syntax Filtering | Remove mechanical errors | 7 filtered, 49 real misconceptions |
| Other Subcategories | Visibility into catch-all | 4 semantic groups |
| Two-Pass Matching | Accurate categorization | No false positives |
