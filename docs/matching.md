# Semantic Alignment & Matching

**Status:** Ensemble Voting Implementation Complete  
**Updated:** December 22, 2025

---

## The Challenge

The LLM might describe a misconception as:

> "The student thinks variables update automatically like Excel cells when they assign the formula too early"

While ground truth defines it as:

> "NM_STATE_01: The Reactive State Machine (Spreadsheet View)"

**Same concept. Different language.**

The matching problem is: how do we align these automatically without losing precision?

---

## Solution: Semantic Embedding + Ensemble Consensus

### 1. Semantic Matching (Analysis 2.2)

Uses OpenAI text-embedding-3-large to compute similarity.

```
┌──────────────────────────────────────────────────────────────┐
│              SEMANTIC MATCHING PROCESS                       │
└──────────────────────────────────────────────────────────────┘

LLM Detection:
  inferred_category_name: "Early Calculation Error"
  student_thought_process: "Student computed formula before reading inputs"
  conceptual_gap: "Variables are not reactive"

          ↓
  
Build searchable text:
  "Early Calculation Error. Student computed formula before reading inputs.
   Variables are not reactive."

          ↓
  
Embed with OpenAI:
  Vector: [0.123, -0.456, 0.789, ... ] (1536 dimensions)

          ↓
  
Compare against ALL ground truth definitions:
  - NM_STATE_01: "Spreadsheet View" → similarity = 0.78 ✓
  - NM_IO_01: "Prompt Logic" → similarity = 0.42
  - NM_TYP_01: "Integer Division" → similarity = 0.31
  - ... (50+ comparisons)

          ↓
  
Find max score:
  Best match: NM_STATE_01 (0.78)
  Threshold: 0.65
  Result: MATCH ✓
```

### 2. Filtering Layers

Before scoring, we filter hallucinations:

```
Detection → Keyword Filter → Semantic Filter → Score
             (fast)          (fallback)        (final)

Example: "No misconception found"
  ├─ Keyword filter: Contains "no" + "misconception" → DISCARD
  └─ Never reaches semantic matching

Example: "Auto-update error"
  ├─ Keyword filter: No match
  ├─ Semantic filter: Similar to NM_STATE_01? → YES (0.78)
  └─ Proceed to scoring
```

### Threshold Decision

```
Semantic Similarity Score vs Result Classification

1.0 ┌─────────────────────────────────────────┐
    │                                         │
0.8 │  TP ZONE (Correct matches)              │
    │  ├─ Mean TP score: 0.765                │
0.7 │  │                                       │
    │  ├─ Threshold: 0.65 ───────────         │
0.6 │  │ AMBIGUOUS ZONE (Close calls)         │
    │  │ ├─ Some FPs cluster here (0.58-0.70) │
0.5 │  │ │                                     │
    │  ├─ Noise Floor: 0.55 ────────          │
    │  │ FP ZONE (Hallucinations)              │
0.4 │  │ ├─ Most FP Socratic: 0.628           │
    │  │ └─ Mean FP Baseline: 0.598            │
0.3 │  │                                       │
    │  └─ DISCARD zone                        │
    └─────────────────────────────────────────┘
        Score ranges by strategy
```

**Why 0.65?**
- Separates TP zone (0.76+) from FP zone (0.60–0.64)
- Filters ~80% of hallucinations
- Retains ~87% of true positives

---

## 3. Ensemble Voting (Analysis 3) ⭐ BREAKTHROUGH

Single-strategy matching had 69% false positive rate. Solution: require consensus.

```
┌────────────────────────────────────────────────────────────────────┐
│                  ENSEMBLE CONSENSUS VOTING                         │
└────────────────────────────────────────────────────────────────────┘

Student: Anderson_Charles_664944
Question: Q2
Expected: NM_STATE_01 (Spreadsheet View)

STRATEGY VOTES:
├─ Baseline (simple ask)
│  ├─ LLM says: "Early Calculation Error"
│  ├─ Semantic match: NM_STATE_01 (0.78)
│  └─ VOTE: NM_STATE_01 ✓
│
├─ Taxonomy (with categories)
│  ├─ LLM says: "Spreadsheet View" 
│  ├─ Semantic match: NM_STATE_01 (0.81)
│  └─ VOTE: NM_STATE_01 ✓
│
├─ Chain-of-Thought (step-by-step)
│  ├─ LLM says: "Formula computed before input"
│  ├─ Semantic match: NM_STATE_01 (0.79)
│  └─ VOTE: NM_STATE_01 ✓
│
└─ Socratic (mental model probing)
   ├─ LLM says: "Believes in auto-updating variables"
   ├─ Semantic match: NM_STATE_01 (0.82)
   └─ VOTE: NM_STATE_01 ✓

CONSENSUS: 4/4 strategies agree → NM_STATE_01
CONFIDENCE: Very high (4 independent detections)
RESULT: TRUE POSITIVE ✅

─────────────────────────────────────────────────────────────────

Student: Baker_Carolyn_647344
Question: Q1
Expected: CLEAN (no bug)

STRATEGY VOTES:
├─ Baseline: "Redundant variable aliasing" → NM_TYP_01 (0.58)
├─ Taxonomy: [No detection]
├─ CoT: [No detection]
└─ Socratic: [No detection]

CONSENSUS: 1/4 strategies detected something
REQUIRED: ≥2 strategies
RESULT: REJECT (not validated) → FN/TN

This hallucination is filtered out!
```

### Results of Ensemble Voting

| Metric | Before (2.2) | After (3.0) | Change |
|--------|--------------|-------------|--------|
| **Precision** | 0.313 | **0.649** | **+107%** ↑ |
| **Recall** | 0.872 | 0.871 | -0.1% (stable) |
| **F1 Score** | 0.461 | **0.744** | **+61%** ↑ |
| False Positives | 4,722 | 1,164 | **-75%** ↓ |

**Key Insight:** By filtering out single-strategy outliers, we recover **3,558 hallucinations** (-75%) while losing only 1 TP.

---

## Implementation Details

### Semantic Matching Algorithm

```python
def semantic_match_misconception(
    detection_text: str,          # LLM's description
    ground_truth_list: list,      # All misconceptions
    threshold: float = 0.65
) -> tuple[str, float]:
    """
    Match LLM detection to ground truth via embeddings.
    
    Returns: (misconception_id, similarity_score)
    """
    # Step 1: Embed detection
    detection_vec = openai.Embedding.create(
        input=detection_text,
        model="text-embedding-3-large"
    )
    
    # Step 2: Embed all ground truth
    gt_vecs = {
        gt['id']: openai.Embedding.create(
            input=gt['explanation'] + " " + gt['student_thinking'],
            model="text-embedding-3-large"
        )
        for gt in ground_truth_list
    }
    
    # Step 3: Compute similarity
    scores = {
        gt_id: cosine_similarity(detection_vec, vec)
        for gt_id, vec in gt_vecs.items()
    }
    
    # Step 4: Return best match
    best_id = max(scores, key=scores.get)
    best_score = scores[best_id]
    
    if best_score >= threshold:
        return best_id, best_score
    else:
        return None, best_score
```

### Ensemble Filter Algorithm

```python
def apply_ensemble_filter(
    df: pd.DataFrame,
    ensemble_threshold: int = 2
) -> pd.DataFrame:
    """
    Filter detections requiring ≥ensemble_threshold strategies to agree.
    """
    # Step 1: Count strategy agreement per (student, question, misconception_id)
    agreement_map = {}
    for idx, row in df.iterrows():
        key = (row['student'], row['question'], row['matched_id'])
        if key not in agreement_map:
            agreement_map[key] = set()
        agreement_map[key].add(row['strategy'])
    
    # Step 2: Identify validated detections
    validated = {
        key for key, strategies in agreement_map.items()
        if len(strategies) >= ensemble_threshold
    }
    
    # Step 3: Filter dataframe
    filtered_rows = []
    for idx, row in df.iterrows():
        key = (row['student'], row['question'], row['matched_id'])
        
        if row['result'] == 'FN':
            # Keep all FNs unchanged
            filtered_rows.append(row)
        elif key in validated:
            # Keep validated detections
            filtered_rows.append(row)
        else:
            # Reject single-strategy outliers
            row['result'] = 'FN'  # Reclassify as missed
            filtered_rows.append(row)
    
    return pd.DataFrame(filtered_rows)
```

---

## Performance by Ensemble Threshold

```
N≥2 vs N≥3 vs N≥4 Comparison:

┌─────────────┬───────────┬───────────┬──────────┐
│  Threshold  │ Precision │  Recall   │   F1     │
├─────────────┼───────────┼───────────┼──────────┤
│  N ≥ 2 ✅   │   0.649   │   0.871   │  0.744   │
│  N ≥ 3      │  ~0.750   │  ~0.800   │ ~0.775   │
│  N ≥ 4      │  ~0.900   │  ~0.600   │ ~0.720   │
└─────────────┴───────────┴───────────┴──────────┘

Recommendation:
├─ For thesis: Use N≥2 (best overall F1)
├─ For high-stakes: Use N≥3 (higher precision)
└─ For maximum certainty: Use N≥4 (expert consensus)
```

---

## Integration with Prompting Strategies

Each strategy uses the same semantic pipeline:

```
┌────────────────────────────────────────────────────────────────┐
│ STRATEGY × SEMANTIC MATCHING × ENSEMBLE VOTING                 │
└────────────────────────────────────────────────────────────────┘

Baseline Strategy:
  Prompt: "Find bugs in this code"
  ├─ LLM output: "Line 5: Formula computed before input"
  ├─ Semantic match: NM_STATE_01 (0.78)
  └─ Ensemble weight: 1 vote

Taxonomy Strategy:
  Prompt: "This code might exhibit one of: Reactive State Machine,
           Anthropomorphic I/O, Fluid Type, Algebraic Syntax, Void"
  ├─ LLM output: "This is Reactive State Machine"
  ├─ Semantic match: NM_STATE_01 (0.81)
  └─ Ensemble weight: 1 vote

CoT Strategy:
  Prompt: "Trace execution step-by-step..."
  ├─ LLM output: "At line 5, 'a' is computed with v0=0, v1=0...
    This assumes variables are updated like a spreadsheet"
  ├─ Semantic match: NM_STATE_01 (0.79)
  └─ Ensemble weight: 1 vote

Socratic Strategy:
  Prompt: "What does the student believe about how variables work?"
  ├─ LLM output: "Student thinks variables auto-update when assigned"
  ├─ Semantic match: NM_STATE_01 (0.82)
  └─ Ensemble weight: 1 vote

─────────────────────────────────────────────────────────────────

ENSEMBLE RESULT: 4/4 strategies agree on NM_STATE_01
CONSENSUS CONFIDENCE: Very High
FINAL RESULT: TRUE POSITIVE ✅
```

---

## Why This Works

### 1. Semantic Embeddings Capture Meaning
- "Early Calculation" ≈ "Spreadsheet View" (cosine = 0.78)
- Embeddings understand conceptual similarity
- Survives paraphrasing and different vocabulary

### 2. Ensemble Voting Eliminates Outliers
- Socratic generates creative but sometimes wrong diagnoses
- When only 1 strategy detects something, it's likely a hallucination
- When 2+ strategies agree, confidence is high

### 3. Complexity Gradient Preserved
Even with ensemble, A1 (variables) remains harder than A3 (arrays):
- A3 easy: 0.890 F1 (concrete, visible bugs)
- A1 hard: 0.592 F1 (abstract mental models)
- Gap: 30% (confirms fundamental LLM limitation)

---

## Hyperparameter Justification

| Parameter | Value | Rationale |
|-----------|-------|-----------|
| **Semantic Threshold** | 0.65 | Splits TP zone (0.76+) from FP zone (0.60-) |
| **Noise Floor** | 0.55 | Filters ultra-low confidence detections silently |
| **Ensemble N** | 2 of 4 | Moderate consensus (70% hallucination filter) |

See `docs/complexity-gradient.md` for ablation studies of these values.

---

## See Also

- `architecture.md` — System design
- `metrics-guide.md` — Precision, Recall, F1 explained
- `complexity-gradient.md` — Why A1 is harder than A3
