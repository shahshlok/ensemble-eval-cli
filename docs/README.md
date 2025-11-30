# Documentation

This directory contains comprehensive documentation for the Ensemble Evaluation CLI's misconception detection system.

## Documents

| Document | Description |
|----------|-------------|
| [Misconception Detection](misconception-detection.md) | Core system architecture and data flow |
| [Topic Taxonomy](topic-taxonomy.md) | How topics are classified and normalized |
| [Filtering & Subcategories](filtering-subcategories.md) | Syntax error filtering and Other category breakdown |
| [Report Generation](report-generation.md) | How reports are generated and what metrics mean |

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
