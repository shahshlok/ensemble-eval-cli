# Report Generation

## Overview

The `generate_markdown_report()` method produces a comprehensive misconception analysis report. This document explains each section and the metrics used.

## Report Structure

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          REPORT STRUCTURE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  1. Header                                                                  │
│     • Generated timestamp                                                   │
│     • Total students analyzed                                               │
│     • Total misconceptions detected                                         │
│                                                                             │
│  2. Most Difficult Areas                                                    │
│     • Topic breakdown by % of class affected                                │
│     • Shows all 5 categories (4 course + Other)                            │
│                                                                             │
│  3. 'Other' Category Breakdown                                              │
│     • Subcategories within Other                                            │
│     • Examples for each subcategory                                         │
│                                                                             │
│  4. Most Common Misconceptions                                              │
│     • Top 10 by occurrence count                                            │
│     • Model agreement for each                                              │
│                                                                             │
│  5. Per-Question Analysis                                                   │
│     • Q1-Q4 breakdown                                                       │
│     • Misconception rate per question                                       │
│     • Topic distribution per question                                       │
│                                                                             │
│  6. Progression Analysis (Q3 → Q4)                                          │
│     • Student progression tracking                                          │
│     • Persistence vs improvement rates                                      │
│                                                                             │
│  7. Model Agreement Analysis                                                │
│     • Misconceptions detected per model                                     │
│                                                                             │
│  8. Legend                                                                  │
│     • Formulas and metric explanations                                      │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Section Details

### 1. Header

```markdown
# Misconception Analysis Report

**Generated:** 2025-11-30 03:58:18
**Total Students Analyzed:** 25
**Total Misconceptions Detected:** 49
```

### 2. Most Difficult Areas

Shows topics ranked by percentage of class affected:

```markdown
| Rank | Topic | Total Misconceptions | Students Affected | Avg Confidence |
|------|-------|---------------------|-------------------|----------------|
| 1 | Other | 24 | 6/25 (24%) | 0.88 |
| 2 | Variables | 8 | 6/25 (24%) | 0.86 |
| 3 | Data Types | 6 | 4/25 (16%) | 0.89 |
| 4 | Constants | 4 | 4/25 (16%) | 0.81 |
| 5 | Reading input from the keyboard | 7 | 3/25 (12%) | 0.86 |
```

**Metrics:**
- **Total Misconceptions**: Count of misconceptions in this topic
- **Students Affected**: `unique_students / total_students`
- **Avg Confidence**: Mean of LLM confidence scores (0.0-1.0)

### 3. 'Other' Category Breakdown

```markdown
### 'Other' Category Breakdown

The 'Other' category contains 24 misconceptions that don't fit the 4 course topics.
These are grouped by semantic similarity:

| Sub-category | Count | Examples |
|--------------|-------|----------|
| Problem Understanding | 10 | "Misinterpreting Problem Requirements", ... |
| Miscellaneous | 8 | "Incorrect sign in velocity change", ... |
| Formula Application | 5 | "Formula Misapplication", ... |
| Output Issues | 1 | "Incorrect Output Value" |
```

### 4. Most Common Misconceptions

```markdown
| Rank | Misconception | Topic | Occurrences | Models Agreeing |
|------|---------------|-------|-------------|-----------------|
| 1 | Incorrect data type usage | Data Types | 2 | 1 (gemini-2.5-flash-lite) |
| 2 | Incorrect operator precedence | Variables | 2 | 2 (gpt-5-nano, gemini-2.5-flash-lite) |
```

**Metrics:**
- **Occurrences**: Total times this misconception was detected
- **Models Agreeing**: Number of different models that found this (higher = more confident)

### 5. Per-Question Analysis

```markdown
| Question | Submissions | Misconception Rate | Top Misconception | Topic Breakdown |
|----------|-------------|-------------------|-------------------|-----------------|
| Q1 | 25 | 7/25 (28%) | Incorrect operator precedence | Other: 7, Variables: 5 |
| Q2 | 25 | 4/25 (16%) | Incorrect input handling | Other: 5, Reading: 3 |
| Q3 | 24 | 6/24 (25%) | Misinterpreting Problem Requirements | Other: 5, Constants: 4 |
| Q4 | 23 | 8/23 (35%) | Incorrect input handling | Other: 7, Reading: 2 |
```

**Metrics:**
- **Misconception Rate**: `students_with_misconceptions / submissions`
- **Topic Breakdown**: Count per topic for that question

### 6. Progression Analysis

Tracks student learning between Q3 and Q4:

```markdown
| Category | Count | Percentage |
|----------|-------|------------|
| Struggled in both Q3 & Q4 | 4 | 17% |
| Improved (Q3 issues → Q4 clean) | 2 | 9% |
| Regressed (Q3 clean → Q4 issues) | 4 | 17% |
| Consistent (no issues in either) | 13 | 57% |
```

**Key Metrics:**
```
Persistence Rate = struggled_both / struggled_in_Q3 × 100%
Improvement Rate = improved / struggled_in_Q3 × 100%
```

### 7. Model Agreement Analysis

```markdown
| Model | Misconceptions Detected |
|-------|------------------------|
| gemini-2.5-flash-lite | 31 |
| gpt-5-nano | 18 |
```

Shows which models are more/less aggressive in detecting misconceptions.

## Generation Code

```python
def generate_markdown_report(self, output_path: str = "misconception_report.md") -> str:
    """Generate a markdown report of the analysis."""
    
    class_analysis = self.analyze_class()
    
    lines = [
        "# Misconception Analysis Report",
        "",
        f"**Generated:** {class_analysis.generated_at.strftime('%Y-%m-%d %H:%M:%S')}",
        f"**Total Students Analyzed:** {class_analysis.total_students}",
        f"**Total Misconceptions Detected:** {class_analysis.total_misconceptions}",
    ]
    
    # Build topic summary
    topic_summary = defaultdict(lambda: {"total": 0, "students": set(), "avg_confidence": []})
    for record in self.misconception_records:
        topic_summary[record.topic]["total"] += 1
        topic_summary[record.topic]["students"].add(record.student_id)
        topic_summary[record.topic]["avg_confidence"].append(record.confidence)
    
    # Add Other subcategories
    other_subcats = self.get_other_subcategories()
    if other_subcats:
        # ... render subcategory table
    
    # Continue building report...
    
    content = "\n".join(lines)
    with open(output_path, "w") as f:
        f.write(content)
    
    return content
```

## Customization

### Changing Top N

To show more/fewer items in "Most Common Misconceptions":

```python
# In generate_markdown_report()
for i, stat in enumerate(class_analysis.misconception_type_stats[:10], 1):  # Change 10
```

### Adding New Sections

Add new sections by appending to the `lines` list:

```python
lines.extend([
    "---",
    "",
    "## New Section Title",
    "",
    # ... section content
])
```

## Output Location

Default: `misconception_report.md` in project root.

Can be customized:
```python
analyzer.generate_markdown_report("reports/analysis_2025-11-30.md")
```
