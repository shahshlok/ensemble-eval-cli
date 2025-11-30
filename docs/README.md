# Documentation

This directory contains comprehensive documentation for the Ensemble Evaluation CLI's misconception detection system.

## Recommended Reading Path

To fully understand the system, we recommend reading the documentation in the following order:

1. **[Misconception Detection System](misconception-detection.md)**
   *   **Start here** to get a high-level overview of the entire pipeline, from student code to the final report.
   *   *Key takeaways:* System architecture, data flow, and the role of LLMs.

2. **[Topic Taxonomy](topic-taxonomy.md)**
   *   **Read this next** to understand how the system organizes misconceptions into the 4 canonical course topics vs. the "Other" category.
   *   *Key takeaways:* The "pure" topic strategy and how normalization works.

3. **[Filtering & Subcategories](filtering-subcategories.md)**
   *   **Then dive deeper** into how the system refines the data by removing syntax errors and breaking down the "Other" category.
   *   *Key takeaways:* Syntax filtering logic and semantic subcategorization.

4. **[Report Generation](report-generation.md)**
   *   **Finally**, learn how the processed data is synthesized into actionable insights.
   *   *Key takeaways:* Understanding the metrics, tables, and progression analysis in the final report.

## Quick Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    MISCONCEPTION DETECTION PIPELINE                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  Student Code    ──►  LLM Grading  ──►  Raw Misconceptions                 │
│       │                   │                    │                            │
│       │                   │                    ▼                            │
│       │                   │         ┌─────────────────────┐                │
│       │                   │         │  Topic Normalization │                │
│       │                   │         │  (4 Course Topics)   │                │
│       │                   │         └──────────┬──────────┘                │
│       │                   │                    │                            │
│       │                   │                    ▼                            │
│       │                   │         ┌─────────────────────┐                │
│       │                   │         │  Syntax Filtering    │                │
│       │                   │         │  (Remove non-concept)│                │
│       │                   │         └──────────┬──────────┘                │
│       │                   │                    │                            │
│       │                   │                    ▼                            │
│       │                   │         ┌─────────────────────┐                │
│       │                   │         │  Other Subcategories │                │
│       │                   │         │  (Semantic grouping) │                │
│       │                   │         └──────────┬──────────┘                │
│       │                   │                    │                            │
│       │                   │                    ▼                            │
│       │                   │              Report.md                          │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Key Concepts

### 1. Course Topics (Pure)
The 4 topics from the assignment rubric are kept "pure" - only misconceptions that clearly belong to these topics are classified here:
- **Variables** - Declaring, assigning, using in expressions
- **Data Types** - int vs double, type conversions
- **Constants** - Math library (Math.pow, Math.sqrt)
- **Reading input from the keyboard** - Scanner usage

### 2. Other Category
Misconceptions that don't fit the 4 course topics go to "Other", which is further broken down into semantic subcategories for visibility.

### 3. Syntax Filtering
Mechanical errors (missing semicolons, typos) are filtered out - they're not conceptual misconceptions.

## Recent Changes (Phase 1)

1. ✅ Kept original 4 course topics pure
2. ✅ Added "Other" as catch-all with subcategory breakdown
3. ✅ Implemented syntax error filtering
4. ✅ Improved LLM prompts to distinguish misconceptions from mistakes
