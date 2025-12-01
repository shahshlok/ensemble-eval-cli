# Immediate Implementation Plan: Statistical Analytics & Reporting

## 1. Objective
Develop a robust analytics engine (`utils/analytics.py`) to rigorously compare LLM misconception detection strategies. This script will produce the quantitative evidence required for the thesis, focusing on statistical significance, inter-model agreement, and granular error analysis.

## 2. `utils/analytics.py` Specification

### Core Responsibilities
1.  **Multi-Strategy Ingestion**: Load evaluation data from `student_evals/{minimal, baseline, socratic, rubric_only}`.
2.  **Ground Truth Alignment**: Match every model prediction against the `authentic_seeded/manifest.json`.
3.  **Statistical Computation**: Calculate significance tests to validate research hypotheses.

### Metrics & Statistical Tests

#### A. Strategy Comparison (RQ2)
*   **Primary Metrics**: Precision, Recall, F1-Score (Macro & Weighted averages).
*   **Hypothesis Test**: **Cochran's Q Test** (omnibus test for differences across all 4 strategies) followed by post-hoc **McNemar's Test** (pairwise comparison, e.g., Socratic vs. Minimal) with Bonferroni correction.
    *   *Why*: To prove that performance differences aren't random noise.

#### B. Model Agreement (RQ3 & RQ4)
*   **Metric**: **Cohen's Kappa** and **Krippendorff's Alpha**.
    *   *Why*: To quantify the reliability of LLMs as raters. "Do GPT-4 and Gemini see the same errors?"
*   **Venn Analysis**: Overlap percentage of unique misconceptions detected by each model.

#### C. Granular Misconception Analysis (RQ6)
*   **Item-Level Difficulty**: Detection rate per Misconception ID (e.g., "DT001 detection rate: 95%", "VAR002: 12%").
*   **Confusion Matrix**: For each misconception type, show where models get confused (e.g., labeling "Integer Division" as "Logic Error").

## 3. Report Structure: `research_evidence_report.md`

This report will serve as the primary "Results" section for the thesis.

### Section 1: Executive Statistical Summary
*   **High-Level Table**: Strategy vs. P/R/F1.
*   **Significance Statement**: "Strategy X outperformed Strategy Y with p < 0.05."

### Section 2: Strategy Comparison (The "Best Prompt" Question)
*   **Visual Data**: ASCII/Markdown table showing the "winner" for each question type.
*   **Statistical Proof**: Results of McNemar's tests (e.g., `Minimal vs Socratic: Chi2=4.5, p=0.03*`).

### Section 3: Inter-Rater Reliability (The "Trust" Question)
*   **Kappa Scores**: Table of model agreement per strategy.
*   **Disagreement Analysis**: Examples of submissions where models strongly disagreed.

### Section 4: Misconception Deep Dive (The "Content" Question)
*   **Hardest-to-Detect Errors**: Ranked list of misconceptions with lowest recall.
*   **Hallucination Rates**: Which specific misconceptions generate the most False Positives?

### Section 5: Raw Data Appendix
*   Links to generated CSVs (for LaTeX/Excel export).

## 4. Implementation Steps

1.  **Build `utils/analytics.py`**:
    *   Class `AnalyticsEngine`: Loads data, computes basic confusion matrices.
    *   Class `StatisticalTester`: Implements McNemar/Kappa/Cochran logic.
    *   Class `ReportGenerator`: Formats the markdown output.
2.  **Run Analysis**: Execute across the full 160-eval dataset.
3.  **Review**: Check `research_evidence_report.md` for thesis-readiness.
