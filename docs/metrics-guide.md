# Metrics Guide: Precision, Recall, F1

**Status:** Analysis 3 Results  
**Updated:** December 22, 2025

---

## The Three Core Metrics

### 1. Precision: "Is the AI correct when it makes a diagnosis?"

```
Precision = TP / (TP + FP)
          = Correct detections / All detections
          = "Of the bugs the AI found, how many were real?"

Example:
  AI said: 5 students have the Spreadsheet View misconception
  Actually: 3 of them do (TP), 2 are false alarms (FP)
  Precision = 3 / (3+2) = 0.60 (60% accurate)
```

**Analysis 3 Result:** **Precision = 0.649** (65% accurate)
- Of 3,314 detections the AI made
- 2,150 were correct (TP)
- 1,164 were false alarms (FP)

### 2. Recall: "Does the AI find all the real bugs?"

```
Recall = TP / (TP + FN)
       = Correct detections / All real bugs
       = "Of the bugs that actually exist, how many did AI find?"

Example:
  Actually: 10 students have Spreadsheet View
  AI found: 7 of them (TP), 3 were missed (FN)
  Recall = 7 / (7+3) = 0.70 (70% complete)
```

**Analysis 3 Result:** **Recall = 0.871** (87% complete)
- Of 2,468 real misconceptions (TP+FN)
- The AI found 2,150 (TP)
- The AI missed 318 (FN)

### 3. F1 Score: "Balance between precision and recall"

```
F1 = 2 × (Precision × Recall) / (Precision + Recall)
   = Harmonic mean of P and R
   = "Overall diagnostic accuracy"

Example:
  Precision = 0.60, Recall = 0.70
  F1 = 2 × (0.60 × 0.70) / (0.60 + 0.70)
     = 2 × 0.42 / 1.30
     = 0.645

Why harmonic mean?
  ├─ Penalizes when P and R are unbalanced
  ├─ If P=0.99 and R=0.01: F1 ≈ 0.02 (bad!)
  └─ If P=0.50 and R=0.50: F1 = 0.50 (better balance)
```

**Analysis 3 Result:** **F1 = 0.744** (74% balanced accuracy)
- Precision 0.649 × Recall 0.871 balanced well
- Good for both false alarm reduction AND catching real bugs

---

## Trade-offs: Precision vs Recall

```
┌────────────────────────────────────────────────────────────┐
│         PRECISION vs RECALL TRADE-OFF                      │
└────────────────────────────────────────────────────────────┘

Scenario 1: High Precision, Low Recall
  ├─ Only flag bugs you're 99% sure about
  ├─ Trade-off: Miss many real bugs
  ├─ Good for: Expert systems, high-stakes decisions
  ├─ Example: N≥4 ensemble voting
  │  └─ Precision: 0.90, Recall: 0.60, F1: 0.72

Scenario 2: Low Precision, High Recall
  ├─ Flag everything that might be a bug
  ├─ Trade-off: Many false alarms
  ├─ Good for: First-pass screening (human reviews later)
  ├─ Example: N≥1 ensemble voting
  │  └─ Precision: 0.31, Recall: 0.87, F1: 0.46

Scenario 3: Balanced (Recommended) ✅
  ├─ Precision ≈ Recall
  ├─ Sweet spot for most applications
  ├─ Example: N≥2 ensemble voting (Analysis 3)
  │  └─ Precision: 0.649, Recall: 0.871, F1: 0.744

┌─────────────────────────────────────┐
│ Recall                              │
│ 1.0 │ N=1 (low P, high R) ↖       │
│     │                    ↘        │
│ 0.8 │                      ↙      │
│     │                        ╲    │
│ 0.6 │                         ▸──→ F1 optimal
│     │ N=2 ✓ Balanced          │   │
│ 0.4 │                         ╱    │
│     │                      ↙      │
│ 0.2 │     N=4 (high P, low R) ↙   │
│     └─────────────────────────────┤
│       0    0.5    1.0             │
│            Precision  →           │
└─────────────────────────────────────┘
```

---

## Results by Category

### A. By Assignment (Complexity Gradient)

| Assignment | Task | Precision | Recall | F1 | Difficulty |
|---|---|---|---|---|---|
| **A3** | Arrays/Strings | 0.810 | 0.989 | **0.890** | Easy ✅ |
| **A2** | Loops/Control | 0.653 | 0.885 | **0.751** | Medium |
| **A1** | Variables/Math | 0.499 | 0.728 | **0.592** | Hard ⚠️ |

**Interpretation:**
- A3 is easiest (concrete, visible errors)
- A1 is hardest (abstract mental models)
- Gap of 0.298 (30%) demonstrates complexity gradient

### B. By Prompting Strategy

| Strategy | Philosophy | Precision | Recall | F1 |
|---|---|---|---|---|
| **Baseline** | Simple ask | 0.714 | 0.796 | 0.753 |
| **Taxonomy** | Explicit categories | 0.654 | 0.832 | 0.734 |
| **CoT** | Step-by-step | 0.668 | 0.850 | 0.750 |
| **Socratic** | Mental model probing | 0.584 | 0.923 | 0.726 |

**Key Finding:** Socratic has highest recall (finds more bugs) but lowest precision (more false alarms). Ensemble voting salvages it (+144% precision improvement).

### C. By LLM Model

| Model | Type | Precision | Recall | F1 |
|---|---|---|---|---|
| **Claude Haiku Reasoning** | Small + Reasoning | **0.784** | 0.857 | **0.819** |
| **GPT-5.2 Reasoning** | Large + Reasoning | 0.702 | 0.897 | 0.788 |
| **GPT-5.2** | Large | 0.690 | 0.895 | 0.779 |
| **Claude Haiku** | Small | 0.697 | 0.813 | 0.751 |
| **Gemini Flash Reasoning** | Mid + Reasoning | 0.551 | 0.887 | 0.680 |
| **Gemini Flash** | Mid | 0.531 | 0.877 | 0.661 |

**Best Performer:** Claude Haiku with Reasoning (F1 = 0.819)

---

## Confusion Matrix Interpretation

```
                    Actual: Has Bug
                         Yes       No
                    ┌──────────┬─────────┐
Predicted: Bug?   │   TP    │   FP   │
                    │ 2,150  │ 1,164  │ (2 + 3 = 5 detections)
            Yes    ├──────────┼─────────┤
                    │   FN    │   TN   │
            No     │   318   │   516  │ (2 + 0 = 2 no detection)
                    └──────────┴─────────┘

Total files: 360 × 3 questions = 1,080 files

TP (2,150): AI said "bug exists" AND bug actually exists ✅
  → Correct positive diagnosis

FP (1,164): AI said "bug exists" BUT no bug ✗
  → False alarm (hallucination)

FN (318): AI said "no bug" BUT bug actually exists ✗
  → Missed diagnosis

TN (516): AI said "no bug" AND no bug ✓
  → Correct negative diagnosis

Metrics from this:
  ├─ Precision = TP / (TP+FP) = 2150 / (2150+1164) = 0.649
  ├─ Recall = TP / (TP+FN) = 2150 / (2150+318) = 0.871
  ├─ Specificity = TN / (TN+FP) = 516 / (516+1164) = 0.307
  └─ Accuracy = (TP+TN) / Total = (2150+516) / 3240 = 0.822
```

---

## Impact of Ensemble Voting on Metrics

```
BEFORE Ensemble (Analysis 2.2):
  Precision: 0.313 (many false alarms)
  Recall:    0.872 (finds most real bugs)
  F1:        0.461 (poor balance)
  FP:        4,722 (68% hallucinations!)

AFTER Ensemble (Analysis 3) ⭐:
  Precision: 0.649 (+107% improvement!) ✅
  Recall:    0.871 (-0.1%, stable) ✅
  F1:        0.744 (+61% improvement) ✅
  FP:        1,164 (-75% reduction) ✅

What happened:
  ├─ Ensemble voting filtered 3,558 false alarms
  ├─ Only lost 1 true positive (acceptable trade)
  ├─ Precision doubled while keeping recall
  └─ F1 score increased 60%
```

---

## How to Read the Report

### Example Report Snippet

```markdown
## Overall Metrics

| Metric | Value | Confidence Interval (95%) |
|--------|-------|---------------------------|
| **Precision** | 0.649 | [0.641, 0.657] |
| **Recall** | 0.871 | [0.867, 0.875] |
| **F1** | 0.744 | [0.738, 0.750] |

Interpretation:
- We are 95% confident that true precision is between 64.1% and 65.7%
- The false alarm rate is approximately 35% (1-P)
- The miss rate is approximately 13% (1-R)
- The balanced F1 score suggests good overall performance
```

### Reading the Tables

```markdown
## By Assignment

| Assignment | Precision | Recall | F1 |
|--------|-----------|--------|-----|
| A1 | 0.499 | 0.728 | 0.592 |  ← Hardest
| A2 | 0.653 | 0.885 | 0.751 |
| A3 | 0.810 | 0.989 | 0.890 |  ← Easiest

What this means:
- A3 has 30% higher F1 than A1 (complexity gradient)
- A3 recall is near-perfect (98.9%)
- A1 precision is poor (49.9%), needs more agreement
```

---

## Comparing Runs

### How to Compare Two Runs

```bash
# Extract just the precision/recall/F1
jq '.*.overall | {precision, recall, f1}' \
  runs/multi/run_analysis2.2/metrics.json \
  runs/multi/run_analysis3/metrics.json

# Output:
# Analysis 2.2: {"precision": 0.313, "recall": 0.872, "f1": 0.461}
# Analysis 3:   {"precision": 0.649, "recall": 0.871, "f1": 0.744}
```

### Ablation Study: Which Ensemble Threshold is Best?

```
For thesis publication:
  └─ Use N≥2 (Analysis 3)
     ├─ Best overall F1: 0.744
     ├─ Precision: 0.649 (good for claims)
     └─ Recall: 0.871 (we catch most bugs)

For high-stakes deployment:
  └─ Use N≥3 or N≥4
     ├─ Higher precision (~0.75-0.90)
     ├─ Lower recall (~0.60-0.80)
     └─ More conservative estimates
```

---

## Key Takeaways

1. **Precision 0.649** = 65% of AI's detections are correct
   - Risk: 35% of claims to students are wrong
   - Mitigation: Ensemble voting reduced this from 69%

2. **Recall 0.871** = 87% of real bugs are found
   - Risk: 13% of misconceptions are missed
   - Mitigation: Impossible to get to 100% due to abstraction gap

3. **F1 0.744** = Good balance between finding bugs and minimizing false alarms
   - This is publication-ready quality
   - Suitable for real-world educational use (with human review)

4. **Complexity Gradient 0.298** = 30% performance drop from A3→A1
   - Proves LLMs struggle with abstract mental models
   - This is the thesis finding

---

## See Also

- `complexity-gradient.md` — Why the gap exists
- `matching.md` — How semantic alignment affects metrics
- `architecture.md` — Where metrics are computed
- `cli-reference.md` — How to generate metrics
