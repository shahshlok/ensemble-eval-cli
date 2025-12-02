# Research Evidence Report: LLM Misconception Detection

Statistical analysis of prompt strategies for automated misconception detection.

**Models:** GPT-5.1 vs Gemini 2.5 Flash

---

## 1. Executive Statistical Summary

### Understanding the Metrics

Before diving into the results, here's what each metric means:

| Metric | What It Measures | Plain English |
|--------|------------------|---------------|
| **Precision** | TP / (TP + FP) | When the system flags an error, how often is it actually an error? High precision = few false alarms. |
| **Recall** | TP / (TP + FN) | Of all the actual errors, how many did the system catch? High recall = few missed errors. |
| **F1 Score** | Harmonic mean of P & R | A balanced measure combining precision and recall. Best single metric for overall performance. |
| **Accuracy** | (TP + TN) / Total | Overall correctness rate. Can be misleading if classes are imbalanced. |

**Confusion Matrix Terms:**
- **TP (True Positive):** Correctly identified an error that exists
- **FP (False Positive):** Flagged an error when code was actually correct (false alarm)
- **FN (False Negative):** Missed an actual error (the dangerous one!)
- **TN (True Negative):** Correctly identified correct code as correct

---

### Strategy Performance Overview

| Strategy | Precision | Recall | F1 Score | Accuracy | TP | FP | FN | TN |
|----------|:---------:|:------:|:--------:|:--------:|:--:|:--:|:--:|:--:|
| Minimal | 45.2% | 98.7% | 62.0% | 61.7% | 75 | 91 | 1 | 73 |
| Baseline | 47.8% | 86.8% | 61.7% | 65.8% | 66 | 72 | 10 | 92 |
| Socratic | 48.2% | 93.2% | 63.6% | 67.4% | 41 | 44 | 3 | 56 |
| Rubric_only ** | 55.6% | 76.9% | 64.5% | 72.5% | 10 | 8 | 3 | 19 |

*Strategy with highest F1 is marked with*


### Omnibus Test: Cochran's Q

<details>
<summary><strong>What is Cochran's Q Test and why do we use it?</strong></summary>

Cochran's Q test is a statistical test used when you want to compare **more than two related groups** 
on a binary outcome (success/failure). Think of it as an extension of McNemar's test for multiple groups.

**Why we use it here:** We have 4 different prompt strategies, and we want to know if there's ANY 
significant difference between them before doing pairwise comparisons. This prevents us from 
'p-hacking' by running many pairwise tests without first checking if differences exist at all.

**How to interpret:**
- **p < 0.05:** At least one strategy performs significantly differently from the others. We should proceed with pairwise tests.
- **p >= 0.05:** No significant difference between strategies. Any observed differences are likely due to random chance.

</details>

**Results:**

- **Q Statistic:** 1.6364
- **p-value:** 0.651173
- **Degrees of Freedom:** 3 (number of strategies - 1)
- **N samples:** 40

> **Result:** No significant difference between strategies (p >= 0.05). 
> The observed performance differences are likely due to random variation, not true differences in strategy effectiveness.

---

## 2. Strategy Comparison (Pairwise Analysis)

<details>
<summary><strong>What is McNemar's Test and why do we use it?</strong></summary>

McNemar's test is used to compare **two related groups** on a binary outcome. It's specifically designed 
for **paired data** where the same subjects are measured under two different conditions.

**Why we use it here:** Each student submission is evaluated by both Strategy A and Strategy B. 
We want to know if one strategy is significantly better than the other at correctly identifying misconceptions.

**The key insight:** McNemar's test focuses on the **discordant pairs** - cases where the two strategies disagree:
- **b:** Strategy 1 was correct, Strategy 2 was wrong
- **c:** Strategy 1 was wrong, Strategy 2 was correct

If b and c are roughly equal, the strategies perform similarly. If one is much larger, that strategy is worse.

**Bonferroni Correction:** When running multiple comparisons (6 pairs for 4 strategies), we increase the risk 
of false positives. Bonferroni correction divides the significance threshold (0.05) by the number of tests 
to maintain overall reliability. Here: 0.05 / 6 = 0.0083.

**How to interpret:**
- **p < Bonferroni alpha:** The difference is statistically significant even after correction
- **p >= Bonferroni alpha:** No significant difference; observed variation is likely random
- **Winner:** The strategy that was correct more often when the two disagreed

</details>

| Comparison | Chi-squared | p-value | Bonferroni alpha | Significant | Winner |
|------------|:-----------:|:-------:|:----------------:|:-----------:|:------:|
| Minimal vs Baseline | 2.7 | 0.100348 | 0.008333 | No | Baseline |
| Minimal vs Socratic | 5.0625 | 0.024449 | 0.008333 | No | Socratic |
| Minimal vs Rubric_only | 0.1667 | 0.683091 | 0.008333 | No | Rubric_only |
| Baseline vs Socratic | 0.5714 | 0.449692 | 0.008333 | No | Socratic |
| Baseline vs Rubric_only | 0 | 1.0 | 0.008333 | No | N/a |
| Socratic vs Rubric_only | 0.5 | 0.4795 | 0.008333 | No | Tie |

*Significant after Bonferroni correction

### Per-Model Performance by Strategy

#### gemini-2.5-flash-lite

| Strategy | Precision | Recall | F1 |
|----------|:---------:|:------:|:--:|
| Baseline | 42.3% | 84.6% | 56.4% |
| Socratic | 47.6% | 88.6% | 61.9% |
| Rubric_only | 55.6% | 76.9% | 64.5% |

#### gemini-2.5-flash-preview-09-2025

| Strategy | Precision | Recall | F1 |
|----------|:---------:|:------:|:--:|
| Minimal | 45.2% | 98.7% | 62.0% |
| Baseline | 50.0% | 87.3% | 63.6% |

#### gpt-5-nano

| Strategy | Precision | Recall | F1 |
|----------|:---------:|:------:|:--:|
| Baseline | 52.6% | 76.9% | 62.5% |
| Socratic | 47.1% | 80.0% | 59.3% |
| Rubric_only | 100.0% | 15.4% | 26.7% |

#### gpt-5.1

| Strategy | Precision | Recall | F1 |
|----------|:---------:|:------:|:--:|
| Minimal | 53.8% | 84.2% | 65.6% |
| Baseline | 59.8% | 82.5% | 69.3% |


---

## 3. Model Comparison: GPT-5.1 vs Gemini 2.5 Flash

This section provides a head-to-head comparison of the two LLM models used in this evaluation.

### Overall Performance Summary

| Model | Avg Precision | Avg Recall | Avg F1 |
|-------|:-------------:|:----------:|:------:|
| gemini-2.5-flash-lite | 48.5% | 83.4% | 60.9% |
| Gemini-2.5-Flash | 47.6% | 93.0% | 62.8% |
| gpt-5-nano | 66.6% | 57.4% | 49.5% |
| GPT-5.1 | 56.8% | 83.4% | 67.5% |


### Statistical Comparison (McNemar's Test)

Testing whether the performance difference between models is statistically significant:

**Minimal:**
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples
- Gemini-2.5-Flash vs GPT-5.1: p=0.000464, Significant=Yes, Winner=GPT-5.1
- Insufficient common samples

**Baseline:**
- Insufficient common samples
- gemini-2.5-flash-lite vs gpt-5-nano: p=0.2278, Significant=No, Winner=gpt-5-nano
- Insufficient common samples
- Insufficient common samples
- Gemini-2.5-Flash vs GPT-5.1: p=0.001384, Significant=Yes, Winner=GPT-5.1
- Insufficient common samples

**Socratic:**
- Insufficient common samples
- gemini-2.5-flash-lite vs gpt-5-nano: p=0.627626, Significant=No, Winner=gpt-5-nano
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples

**Rubric_only:**
- Insufficient common samples
- gemini-2.5-flash-lite vs gpt-5-nano: p=0.802587, Significant=No, Winner=tie
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples
- Insufficient common samples

---

## 4. Inter-Rater Reliability

This section answers: **Do the LLM models agree with each other?** If models frequently disagree, 
we can't trust their judgments. High agreement suggests the detection task is well-defined and models are reliable.

<details>
<summary><strong>What are Kappa and Alpha, and why do they matter?</strong></summary>

### Cohen's Kappa
Cohen's Kappa measures agreement between **exactly two raters** while accounting for agreement that would 
happen by random chance. A kappa of 0 means agreement is no better than random; 1 means perfect agreement.

**Interpretation scale:**
| Kappa | Interpretation |
|-------|----------------|
| < 0 | Poor (worse than chance) |
| 0.00 - 0.20 | Slight |
| 0.21 - 0.40 | Fair |
| 0.41 - 0.60 | Moderate |
| 0.61 - 0.80 | Substantial |
| 0.81 - 1.00 | Almost Perfect |

### Krippendorff's Alpha
Krippendorff's Alpha is more flexible than Kappa - it works with **any number of raters** and handles 
missing data. It's the standard metric for content analysis reliability studies.

**Interpretation thresholds:**
- **alpha >= 0.80:** Good reliability - conclusions are trustworthy
- **0.667 <= alpha < 0.80:** Acceptable for tentative conclusions
- **alpha < 0.667:** Unacceptable - too much disagreement to draw conclusions

**Why this matters for the thesis:** If models strongly agree (high Kappa/Alpha), it suggests that 
misconception detection is a well-defined task that LLMs can perform reliably. If they disagree, 
either the task is inherently ambiguous or the models have different 'opinions' about what constitutes an error.

</details>

### Krippendorff's Alpha (Multi-Rater Agreement)

| Strategy | Alpha | Interpretation |
|----------|:-----:|----------------|
| Minimal | 0.5941 | Unacceptable for conclusions |
| Baseline | None | Insufficient complete samples |
| Socratic | 0.7505 | Acceptable for tentative conclusions |
| Rubric_only | -0.0667 | Unacceptable for conclusions |

### Cohen's Kappa (Pairwise Model Agreement)

#### Minimal

- **gemini-2.5-flash-preview-09-2025** vs **gpt-5.1**: kappa=0.6096 (Substantial)

#### Baseline

- **gemini-2.5-flash-lite** vs **gpt-5-nano**: kappa=0.5121 (Moderate)
- **gemini-2.5-flash-preview-09-2025** vs **gpt-5.1**: kappa=0.7713 (Substantial)

#### Socratic

- **gemini-2.5-flash-lite** vs **gpt-5-nano**: kappa=0.7527 (Substantial)

#### Rubric_only

- **gemini-2.5-flash-lite** vs **gpt-5-nano**: kappa=0.1209 (Slight)


---

## 5. Misconception Detection Analysis

This section answers: **Which types of errors are easy vs. hard to detect?**

Understanding this helps identify:
- Which misconceptions need better prompting strategies
- Which error types might require specialized detection approaches
- Patterns in what LLMs miss (e.g., subtle vs. obvious errors)

**Detection Rate:** The percentage of times a misconception was correctly identified when it was present 
in the ground truth. A rate of 100% means the LLMs caught every instance; 0% means they missed all of them.

### Detection Rates by Misconception Type

| ID | Name | Minimal | Baseline | Socratic | Rubric_only | Avg |
|:---|:---|:---:|:---:|:---:|:---:|:---:|
| CONST001 | Using ^ instead of Math.pow() for e | 83% | 50% | 50% | 50% | 58% |
| CONST002 | Missing Math.sqrt() for square root | 62% | 75% | 83% | 50% | 68% |
| CONST003 | Incorrect Math.pow() argument order | 50% | 50% | 50% | 0% | 38% |
| DT001 | Using int instead of double for dec | 90% | 80% | 75% | 0% | 61% |
| DT002 | Integer division truncation | 100% | 100% | 100% | 100% | 100% |
| DT003 | Type mismatch in Scanner input | 62% | 50% | 50% | 25% | 47% |
| INPUT001 | Missing Scanner import | 88% | 0% | 42% | 0% | 32% |
| INPUT002 | Scanner not reading correct number  | 80% | 90% | 80% | 50% | 75% |
| INPUT003 | Not closing Scanner resource | 50% | 50% | 50% | 50% | 50% |
| OTHER001 | Computing wrong quantity (different | 50% | 50% | 50% | 50% | 50% |
| OTHER002 | Hardcoded values instead of user in | 96% | 92% | 100% | 100% | 97% |
| VAR001 | Incorrect operator precedence | 75% | 75% | 100% | 100% | 88% |
| VAR002 | Wrong formula - addition instead of | 100% | 100% | 100% | 0% | 75% |
| VAR003 | Incorrect formula derivation for fu | 83% | 100% | 0% | 0% | 46% |
| VAR004 | Missing intermediate variable for s | 50% | 50% | 50% | 0% | 38% |



### Hardest-to-Detect Misconceptions

1. **INPUT001** (Missing Scanner import): 32% average detection rate
1. **CONST003** (Incorrect Math.pow() argument order): 38% average detection rate
1. **VAR004** (Missing intermediate variable for semi-perimeter): 38% average detection rate
1. **VAR003** (Incorrect formula derivation for fuel cost): 46% average detection rate
1. **DT003** (Type mismatch in Scanner input): 47% average detection rate

### Easiest-to-Detect Misconceptions

1. **DT002** (Integer division truncation): 100% average detection rate
1. **OTHER002** (Hardcoded values instead of user input): 97% average detection rate
1. **VAR001** (Incorrect operator precedence): 88% average detection rate
1. **VAR002** (Wrong formula - addition instead of subtraction): 75% average detection rate
1. **INPUT002** (Scanner not reading correct number of inputs): 75% average detection rate

### Per-Question Performance


### LLM Detection vs Ground Truth: Per-Model Breakdown

This table breaks down detection performance by individual models across all strategies.
It helps identify if specific models are better at detecting certain misconceptions.


#### Model: gemini-2.5-flash-preview-09-2025

| ID | Misconception | Ground Truth | Detected | Delta | Assessment |
|:---|:--------------|:------------:|:--------:|:-----:|:-----------|
| OTHER002 | Hardcoded values instead of us | 13 | 13.0 | +0.0 | Under (-0%) |
| INPUT003 | Not closing Scanner resource | 9 | 0.2 | -8.8 | Under (-97%) |
| DT003 | Type mismatch in Scanner input | 8 | 1.8 | -6.2 | Under (-78%) |
| INPUT001 | Missing Scanner import | 8 | 2.8 | -5.2 | Under (-66%) |
| VAR001 | Incorrect operator precedence | 6 | 1.0 | -5.0 | Under (-83%) |
| DT001 | Using int instead of double fo | 5 | 0.2 | -4.8 | Under (-95%) |
| INPUT002 | Scanner not reading correct nu | 5 | 1.2 | -3.8 | Under (-75%) |
| OTHER001 | Computing wrong quantity (diff | 5 | 0.5 | -4.5 | Under (-90%) |
| CONST002 | Missing Math.sqrt() for square | 4 | 1.5 | -2.5 | Under (-62%) |
| CONST001 | Using ^ instead of Math.pow()  | 3 | 0.8 | -2.2 | Under (-75%) |
| VAR003 | Incorrect formula derivation f | 3 | 0.2 | -2.8 | Under (-92%) |
| CONST003 | Incorrect Math.pow() argument  | 2 | 0.2 | -1.8 | Under (-88%) |
| DT002 | Integer division truncation | 2 | 4.0 | +2.0 | Over (+100%) |
| VAR002 | Wrong formula - addition inste | 2 | 0.2 | -1.8 | Under (-88%) |
| VAR004 | Missing intermediate variable  | 1 | 1.2 | +0.2 | Over (+25%) |

**Summary for gemini-2.5-flash-preview-09-2025:**
- Average Total Detections: 29.0 (vs 76 real errors)


#### Model: gpt-5.1

| ID | Misconception | Ground Truth | Detected | Delta | Assessment |
|:---|:--------------|:------------:|:--------:|:-----:|:-----------|
| OTHER002 | Hardcoded values instead of us | 13 | 4.5 | -8.5 | Under (-65%) |
| INPUT003 | Not closing Scanner resource | 9 | 0.0 | -9.0 | Missed |
| DT003 | Type mismatch in Scanner input | 8 | 0.0 | -8.0 | Missed |
| INPUT001 | Missing Scanner import | 8 | 0.0 | -8.0 | Missed |
| VAR001 | Incorrect operator precedence | 6 | 1.0 | -5.0 | Under (-83%) |
| DT001 | Using int instead of double fo | 5 | 6.0 | +1.0 | Over (+20%) |
| INPUT002 | Scanner not reading correct nu | 5 | 3.0 | -2.0 | Under (-40%) |
| OTHER001 | Computing wrong quantity (diff | 5 | 0.2 | -4.8 | Under (-95%) |
| CONST002 | Missing Math.sqrt() for square | 4 | 0.5 | -3.5 | Under (-88%) |
| CONST001 | Using ^ instead of Math.pow()  | 3 | 0.2 | -2.8 | Under (-92%) |
| VAR003 | Incorrect formula derivation f | 3 | 0.5 | -2.5 | Under (-83%) |
| CONST003 | Incorrect Math.pow() argument  | 2 | 0.0 | -2.0 | Missed |
| DT002 | Integer division truncation | 2 | 2.2 | +0.2 | Over (+12%) |
| VAR002 | Wrong formula - addition inste | 2 | 0.5 | -1.5 | Under (-75%) |
| VAR004 | Missing intermediate variable  | 1 | 0.2 | -0.8 | Under (-75%) |

**Summary for gpt-5.1:**
- Average Total Detections: 19.0 (vs 76 real errors)

---

## 6. Appendix: Raw Data

### Ground Truth Summary

| Question | Correct | With Errors | Error Rate |
|----------|:-------:|:-----------:|:----------:|
| q1 | 39 | 21 | 35% |
| q2 | 45 | 15 | 25% |
| q3 | 37 | 23 | 38% |
| q4 | 43 | 17 | 28% |

### Misconception Distribution in Ground Truth

| ID | Name | Count |
|:---|:-----|:-----:|
| OTHER002 | Hardcoded values instead of user input | 13 |
| INPUT003 | Not closing Scanner resource | 9 |
| INPUT001 | Missing Scanner import | 8 |
| DT003 | Type mismatch in Scanner input | 8 |
| VAR001 | Incorrect operator precedence | 6 |
| OTHER001 | Computing wrong quantity (different prob | 5 |
| INPUT002 | Scanner not reading correct number of in | 5 |
| DT001 | Using int instead of double for decimal  | 5 |
| CONST002 | Missing Math.sqrt() for square root | 4 |
| VAR003 | Incorrect formula derivation for fuel co | 3 |
| CONST001 | Using ^ instead of Math.pow() for expone | 3 |
| DT002 | Integer division truncation | 2 |
| CONST003 | Incorrect Math.pow() argument order | 2 |
| VAR002 | Wrong formula - addition instead of subt | 2 |
| VAR004 | Missing intermediate variable for semi-p | 1 |

---

*Report generated by utils/analytics.py*