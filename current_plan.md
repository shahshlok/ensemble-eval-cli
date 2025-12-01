# Research Plan: LLM-Based Misconception Detection in CS1 Student Code

## Executive Summary

This research investigates whether Large Language Models can autonomously identify student misconceptions in introductory programming without being explicitly trained on misconception taxonomies. We employ a novel **seeded validation methodology** with ground-truth labeled submissions to rigorously evaluate detection accuracy across multiple prompting strategies and LLM ensembles.

**Target Venues:** SIGCSE, ITiCSE, L@S, ICER, EDM, LAK, NeurIPS Education Track, AAAI

---

## 1. Dataset Overview

### 1.1 Verified Data Inventory

| Component | Count | Status |
|-----------|-------|--------|
| Prompt Strategies | 4 | baseline, minimal, socratic, rubric_only |
| LLM Models | 2 | gemini-2.5-flash-lite, gpt-5-nano |
| Students (seeded) | 10 | Representative sample with controlled errors |
| Questions | 4 | q1-q4 (varying complexity) |
| Total Submissions | 40 | 10 students x 4 questions |
| Evaluations per Strategy | 40 | Complete coverage |
| **Total Evaluation Records** | **160** | 4 strategies x 40 submissions |
| **Total Model Judgments** | **320** | 160 evals x 2 models |

### 1.2 Ground Truth Distribution

From `authentic_seeded/manifest.json` (240 entries for 60 students, 10 evaluated):

| Category | Misconception IDs | Count in Manifest |
|----------|-------------------|-------------------|
| Data Types | DT001, DT002, DT003 | ~25 |
| Variables | VAR001, VAR002, VAR003, VAR004 | ~20 |
| Constants | CONST001, CONST002, CONST003 | ~15 |
| Input/Scanner | INPUT001, INPUT002, INPUT003 | ~20 |
| Other | OTHER001, OTHER002 | ~15 |
| Correct (no error) | null | ~145 |

### 1.3 Misconception Taxonomy

15 distinct misconception types across 5 topic areas, each with:
- Unique ID and name
- Severity rating (low/medium/high)
- Example incorrect/correct code
- Detection keywords for mapping

---

## 2. Research Questions

### Primary RQ (Novel Contribution)
> **RQ1:** Can LLMs discover student misconceptions *without* being given explicit examples, and how does this compare to primed detection?

### Secondary RQs
> **RQ2:** How does prompting strategy (minimal vs. socratic vs. baseline) affect misconception detection precision, recall, and F1?

> **RQ3:** Do different LLMs exhibit systematic biases in which misconception types they detect or miss?

> **RQ4:** What is the agreement rate between multiple LLMs, and does ensemble voting improve detection accuracy?

> **RQ5:** Is there a trade-off between grading accuracy and misconception detection thoroughness?

> **RQ6:** Which misconception categories are most/least detectable by LLMs, and why?

---

## 3. Analysis Framework

### 3.1 Core Metrics (Per Strategy, Per Model, Aggregate)

```
┌─────────────────────────────────────────────────────────────────┐
│                    DETECTION METRICS                            │
├─────────────────────────────────────────────────────────────────┤
│  Precision = TP / (TP + FP)     "When we flag, are we right?"   │
│  Recall    = TP / (TP + FN)     "Do we catch actual errors?"    │
│  F1 Score  = 2(P*R)/(P+R)       "Balanced performance"          │
│  Accuracy  = (TP+TN) / Total    "Overall correctness"           │
│  Specificity = TN / (TN + FP)   "Avoiding false alarms"         │
└─────────────────────────────────────────────────────────────────┘
```

### 3.2 Advanced Analyses

| Analysis | Description | Research Value |
|----------|-------------|----------------|
| **Misconception-Specific Detection Rates** | Per-ID recall (e.g., "DT002 detected 100%, INPUT001 detected 0%") | Identifies blind spots |
| **Severity-Weighted Recall** | Weight recall by misconception severity (high=3, medium=2, low=1) | Practical importance |
| **Topic-Level Aggregation** | Performance by topic (Data Types vs Variables vs Constants) | Curriculum implications |
| **Model Agreement (Cohen's Kappa)** | Inter-rater reliability between Gemini and GPT | Ensemble validity |
| **Confidence Calibration** | Do LLM confidence scores predict accuracy? | Trust/uncertainty |
| **False Positive Taxonomy** | Categorize FP types (style vs real issue in "correct" code) | Error analysis |
| **Mapping Accuracy** | When LLM detects error, does description match GT ID? | Semantic precision |

### 3.3 Comparative Analyses

```
Strategy Comparison Matrix
─────────────────────────────────────────────────────
           │ Precision │ Recall │  F1  │ Notes
───────────┼───────────┼────────┼──────┼──────────────
minimal    │   60.0%   │ 46.2%  │52.2% │ No priming
baseline   │    TBD    │  TBD   │ TBD  │ With examples
socratic   │    TBD    │  TBD   │ TBD  │ Chain-of-thought
rubric_only│    TBD    │  TBD   │ TBD  │ No misconception ask
─────────────────────────────────────────────────────
```

---

## 4. Report Generation Architecture

### 4.1 Multi-Level Reports

```
reports/
├── executive_summary.md          # 1-page overview for stakeholders
├── full_research_report.md       # Complete analysis (main paper)
├── strategy_comparison.md        # RQ2: Side-by-side strategy analysis
├── model_comparison.md           # RQ3: Gemini vs GPT analysis
├── misconception_analysis.md     # RQ6: Per-misconception deep dive
├── ensemble_analysis.md          # RQ4: Agreement and voting
├── statistical_tests.md          # Significance testing results
└── appendices/
    ├── raw_metrics.json          # Machine-readable results
    ├── confusion_matrices.md     # All confusion matrices
    ├── false_positive_catalog.md # Detailed FP analysis
    └── sample_outputs.md         # Representative LLM outputs
```

### 4.2 Visualizations (for Paper Figures)

| Figure | Type | Shows |
|--------|------|-------|
| Fig 1 | Grouped Bar Chart | Precision/Recall/F1 by strategy |
| Fig 2 | Heatmap | Misconception detection rate by ID x Strategy |
| Fig 3 | Confusion Matrix Grid | 2x4 grid (2 models x 4 strategies) |
| Fig 4 | Radar Chart | Topic-level performance comparison |
| Fig 5 | Scatter Plot | Confidence vs Accuracy calibration |
| Fig 6 | Venn Diagram | Model agreement overlap |
| Fig 7 | Waterfall Chart | Error cascade (GT -> Detection -> Mapping) |

---

## 5. Implementation Plan

### Phase 1: Core Evaluation Pipeline (prompt_evaluator.py)

```python
class PromptEvaluator:
    """Cross-strategy evaluation and comparison engine."""
    
    def __init__(self, strategies: list[str]):
        self.strategies = strategies
        self.evaluators = {s: MisconceptionEvaluator(strategy=s) for s in strategies}
    
    def run_all_evaluations(self) -> dict[str, EvaluationMetrics]:
        """Run evaluation for all strategies."""
        
    def compare_strategies(self) -> StrategyComparison:
        """Generate side-by-side comparison."""
        
    def compute_inter_model_agreement(self) -> AgreementMetrics:
        """Cohen's Kappa and agreement analysis."""
        
    def generate_comprehensive_report(self) -> str:
        """Generate full research report."""
```

### Phase 2: Statistical Analysis

```python
def statistical_tests(results: dict) -> StatisticalReport:
    """
    Perform rigorous statistical analysis:
    1. McNemar's test for paired strategy comparisons
    2. Cochran's Q for multiple strategy comparison
    3. Cohen's Kappa for inter-model agreement
    4. Chi-square for misconception category independence
    5. Bootstrap confidence intervals for all metrics
    """
```

### Phase 3: Report Generation

```python
def generate_paper_ready_report(results: dict) -> PaperReport:
    """
    Generate publication-ready outputs:
    1. LaTeX tables for metrics
    2. Matplotlib/Seaborn figures (PDF export)
    3. Statistical test summaries
    4. Qualitative examples (best/worst cases)
    """
```

---

## 6. Key Insights to Extract

### 6.1 For Research Contribution

| Finding Type | Example | Significance |
|--------------|---------|--------------|
| **Strategy Effect** | "Socratic prompting improves recall by 15% over minimal" | Prompt engineering guidance |
| **Blind Spots** | "All strategies miss INPUT001 (missing import)" | LLM limitation understanding |
| **Semantic Gap** | "LLMs detect 'integer division' but call it 'truncation'" | Vocabulary mismatch |
| **Over-Detection** | "Both models flag 'Scanner not closed' as error on correct code" | Calibration needs |
| **Model Differences** | "Gemini more conservative (higher precision), GPT more aggressive (higher recall)" | Model selection guidance |

### 6.2 For Practical Application

| Recommendation | Based On | Impact |
|----------------|----------|--------|
| "Use socratic for initial detection, baseline for confirmation" | F1 comparison | Workflow design |
| "Ensemble voting with 2+ models reduces FP by 40%" | Agreement analysis | Deployment strategy |
| "Explicitly prompt for INPUT/Scanner issues" | Per-category recall | Prompt improvement |
| "Treat confidence < 0.7 as uncertain" | Calibration analysis | UI/UX design |

---

## 7. Novel Contributions (Paper Framing)

### 7.1 Methodological Contributions

1. **Seeded Validation Methodology**: First use of intentionally-seeded student code with ground-truth misconception labels for LLM evaluation in CS education
   
2. **Multi-Strategy Ablation**: Systematic comparison of prompting strategies for misconception detection (not just grading)

3. **Ensemble LLM Analysis**: Inter-model agreement and voting strategies for educational AI

### 7.2 Empirical Contributions

1. **Misconception Detectability Taxonomy**: Which CS1 misconceptions are LLM-detectable vs. human-only?

2. **Prompt Strategy Guidelines**: Evidence-based recommendations for educators deploying LLM graders

3. **Calibration Analysis**: How well do LLM confidence scores predict actual accuracy?

### 7.3 Practical Contributions

1. **Open Evaluation Framework**: Reusable pipeline for evaluating LLM misconception detection

2. **Misconception Catalog**: Curated taxonomy of 15 CS1 misconceptions with examples

3. **Seeded Dataset**: Synthetic but realistic student submissions for benchmarking

---

## 8. Report Structure (Full Paper)

```
1. Introduction
   - Motivation: Why misconception detection matters
   - Gap: Current LLM grading focuses on scores, not understanding
   - Contribution summary

2. Related Work
   - Misconception research in CS education
   - LLMs for code assessment
   - Prompt engineering for education

3. Methodology
   3.1 Seeded Validation Approach
   3.2 Misconception Taxonomy
   3.3 Prompt Strategies
   3.4 Evaluation Metrics

4. Experimental Setup
   4.1 Dataset: 40 submissions, 4 questions, 15 misconception types
   4.2 Models: Gemini Flash Lite, GPT-5 Nano
   4.3 Strategies: Minimal, Baseline, Socratic, Rubric-Only

5. Results
   5.1 Overall Performance (Table: P/R/F1 by strategy)
   5.2 Strategy Comparison (Figure: grouped bars)
   5.3 Misconception-Level Analysis (Heatmap)
   5.4 Model Agreement (Kappa, Venn)
   5.5 Error Analysis (FP/FN examples)

6. Discussion
   6.1 RQ1: Unprimed vs. Primed Detection
   6.2 RQ2-3: Strategy and Model Effects
   6.3 Implications for Practice
   6.4 Limitations

7. Conclusion & Future Work
```

---

## 9. Execution Checklist

### Immediate (This Session)
- [x] Verify all 160 evaluation files exist
- [x] Confirm 2 models per evaluation
- [ ] Create `utils/prompt_evaluator.py`
- [ ] Run cross-strategy comparison
- [ ] Generate initial metrics table

### Short-Term (Next Session)
- [ ] Implement statistical tests
- [ ] Generate visualizations
- [ ] Write detailed false positive analysis
- [ ] Create per-misconception breakdown

### Medium-Term (Paper Writing)
- [ ] Draft methodology section
- [ ] Create publication-quality figures
- [ ] Write results narrative
- [ ] Compile supplementary materials

---

## 10. Expected Outcomes

### Quantitative
- 4x4 metrics table (4 strategies x 4 metrics)
- Per-misconception detection rates (15 IDs)
- Inter-model agreement (Cohen's Kappa)
- Statistical significance values

### Qualitative  
- Catalog of false positive types
- Representative detection successes/failures
- LLM explanation quality assessment

### Actionable
- Strategy recommendation flowchart
- Confidence threshold guidelines
- Prompt improvement suggestions

---

## Appendix: Data Schema Reference

### Evaluation Document Structure
```json
{
  "evaluation_id": "eval_xxx",
  "context": { "question_id": "q1", ... },
  "submission": { "student_id": "Name_ID_Label", ... },
  "models": {
    "google/gemini-2.5-flash-lite": {
      "scores": { "total_points_awarded": 4.0, ... },
      "category_scores": [...],
      "feedback": { "overall_comment": "...", ... },
      "misconceptions": [
        {
          "topic": "Data Types",
          "name": "Integer division truncation",
          "description": "...",
          "confidence": 0.9,
          "evidence": [{ "snippet": "..." }],
          "severity": "major",
          "category": "conceptual",
          "student_belief": "...",
          "remediation_hint": "..."
        }
      ]
    },
    "openai/gpt-5-nano": { ... }
  }
}
```

### Ground Truth Schema
```json
{
  "student_id": "Anderson_Noah_200113",
  "question_id": "q3",
  "misconception_id": "DT003",  // or null if correct
  "is_correct": false
}
```
