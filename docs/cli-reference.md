# CLI Reference: Command-Line Tools

**Status:** Analysis 3 Complete  
**Updated:** December 22, 2025

---

## Quick Reference

| Command | Entry Point | Purpose | Status |
|---------|-------------|---------|--------|
| `uv run python analyze.py` | `analyze.py` | Single-strategy and ensemble analysis | ✅ Primary tool |
| `uv run python llm_miscons_cli.py` | `llm_miscons_cli.py` | Run LLM detection | ✅ Available |
| `uv run python dataset_generator.py` | `dataset_generator.py` | Generate synthetic students | ✅ Available |

---

## Main Command: `analyze.py`

### Purpose
Analyzes LLM detection outputs against ground truth using semantic matching and ensemble voting.

### Basic Usage

```bash
# Single-strategy analysis (Analysis 2.2 mode)
uv run python analyze.py analyze-multi \
  --run-name my-analysis \
  --semantic-threshold 0.65 \
  --noise-floor 0.55

# Ensemble voting analysis (Analysis 3 mode) ⭐ RECOMMENDED
uv run python analyze.py analyze-ensemble \
  --run-name my-analysis \
  --ensemble-threshold 2 \
  --semantic-threshold 0.65 \
  --noise-floor 0.55
```

---

## Command 1: `analyze-multi` (Single-Strategy Analysis)

Analyzes detections from a single strategy or all strategies independently.

### Signature

```bash
uv run python analyze.py analyze-multi [OPTIONS]
```

### Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `--run-name` | str | auto | Custom run identifier (no spaces) |
| `--semantic-threshold` | float | 0.65 | Cosine similarity threshold for match |
| `--noise-floor` | float | 0.55 | Below this score, discard detection |
| `--assignment` | str | multi | Which assignment(s): a1, a2, a3, or multi |
| `--strategy` | str | all | Which strategy: baseline, taxonomy, cot, socratic, or all |
| `--output-dir` | str | runs/multi | Where to save results |
| `--verbose` | flag | false | Print detailed progress |

### Example: Analyze All Strategies (Analysis 2.2)

```bash
uv run python analyze.py analyze-multi \
  --run-name analysis2.2 \
  --assignment multi \
  --strategy all \
  --semantic-threshold 0.65 \
  --noise-floor 0.55 \
  --output-dir runs/multi \
  --verbose
```

**Output:** `runs/multi/run_analysis2.2/`
- `report.md` — Markdown report with embedded charts
- `metrics.json` — Numeric results (precision, recall, F1)
- `results.csv` — Per-file breakdown
- `compliance.csv` — TP/FP/FN classification
- `assets/` — 8 PNG charts

### Example: Analyze Single Assignment

```bash
uv run python analyze.py analyze-multi \
  --run-name a1-only \
  --assignment a1 \
  --strategy baseline \
  --semantic-threshold 0.65
```

---

## Command 2: `analyze-ensemble` (Ensemble Voting Analysis) ⭐ NEW

Applies ensemble voting: requires ≥N strategies to agree on the same misconception.

### Signature

```bash
uv run python analyze.py analyze-ensemble [OPTIONS]
```

### Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `--run-name` | str | auto | Custom run identifier |
| `--ensemble-threshold` | int | 2 | Min strategies that must agree (1-4) |
| `--semantic-threshold` | float | 0.65 | Cosine similarity threshold |
| `--noise-floor` | float | 0.55 | Discard scores < this |
| `--assignment` | str | multi | Which assignment(s) |
| `--output-dir` | str | runs/multi | Where to save |
| `--verbose` | flag | false | Detailed logging |

### Example: Ensemble Voting (N≥2) — RECOMMENDED

```bash
uv run python analyze.py analyze-ensemble \
  --run-name analysis3 \
  --ensemble-threshold 2 \
  --semantic-threshold 0.65 \
  --noise-floor 0.55 \
  --verbose
```

**Expected Output:**
```
┌─────────────────────────────────────────┐
│         ENSEMBLE VOTING RESULTS          │
├─────────────────────────────────────────┤
│ Applying ensemble filter (2/4 required) │
│                                          │
│ Metrics:                                │
│ ├─ Precision: 0.649 (+107% vs 2.2)  ✅ │
│ ├─ Recall: 0.871 (stable) ✅           │
│ ├─ F1: 0.744 (+61%) ✅                │
│ ├─ True Positives: 2,150              │
│ ├─ False Positives: 1,164 (-75%)      │
│ └─ False Negatives: 318               │
│                                         │
│ By Assignment:                         │
│ ├─ A3 (Arrays): F1 = 0.890           │
│ ├─ A2 (Loops): F1 = 0.751            │
│ └─ A1 (Variables): F1 = 0.592 ⚠️     │
│                                         │
│ Saved to: runs/multi/run_analysis3/   │
└─────────────────────────────────────────┘
```

### Example: Ablation Study (Compare N≥2 vs N≥3)

```bash
# N≥2 (Moderate Consensus)
uv run python analyze.py analyze-ensemble \
  --run-name ablation_n2 \
  --ensemble-threshold 2

# N≥3 (Strong Consensus)
uv run python analyze.py analyze-ensemble \
  --run-name ablation_n3 \
  --ensemble-threshold 3

# N≥4 (Unanimous)
uv run python analyze.py analyze-ensemble \
  --run-name ablation_n4 \
  --ensemble-threshold 4
```

**Expected Results:**
| Threshold | Precision | Recall | F1 |
|-----------|-----------|--------|-----|
| N≥2 | 0.649 | 0.871 | **0.744** |
| N≥3 | ~0.75 | ~0.80 | ~0.77 |
| N≥4 | ~0.90 | ~0.60 | ~0.72 |

---

## Output Files

After running either `analyze-multi` or `analyze-ensemble`, you get:

### 1. `report.md` — Main Results Report

Markdown file with:
- Executive summary (key metrics)
- Results by assignment (complexity gradient)
- Results by strategy (prompt effectiveness)
- Results by model (LLM comparison)
- Misconception-specific recall (hardest categories)
- Visualizations (PNG images embedded)
- Interpretation & conclusions

**Example Reading:**
```
## By Assignment (Complexity Gradient)

| Assignment | Precision | Recall | F1 |
|--------|-----------|--------|-----|
| A3: Arrays/Strings | 0.810 | 0.989 | 0.890 ✅ |
| A2: Loops/Control | 0.653 | 0.885 | 0.751 |
| A1: Variables/Math | 0.499 | 0.728 | 0.592 ⚠️ |

**Interpretation:**
30% F1 drop from A3→A1 suggests LLMs struggle with abstract state.
```

### 2. `metrics.json` — Numeric Results

```json
{
  "analysis_3": {
    "overall": {
      "precision": 0.649,
      "recall": 0.871,
      "f1": 0.744,
      "tp": 2150,
      "fp": 1164,
      "fn": 318
    },
    "by_assignment": {
      "a1": {
        "precision": 0.499,
        "recall": 0.728,
        "f1": 0.592
      },
      "a2": {
        "precision": 0.653,
        "recall": 0.885,
        "f1": 0.751
      },
      "a3": {
        "precision": 0.810,
        "recall": 0.989,
        "f1": 0.890
      }
    },
    "by_strategy": {
      "baseline": {"precision": 0.714, "recall": 0.796, "f1": 0.753},
      "taxonomy": {"precision": 0.654, "recall": 0.832, "f1": 0.734},
      "cot": {"precision": 0.668, "recall": 0.850, "f1": 0.750},
      "socratic": {"precision": 0.584, "recall": 0.923, "f1": 0.726}
    },
    "by_model": {
      "claude-haiku:reasoning": {"precision": 0.784, "recall": 0.857, "f1": 0.819},
      "gpt-5.2:reasoning": {"precision": 0.702, "recall": 0.897, "f1": 0.788},
      "gpt-5.2": {"precision": 0.690, "recall": 0.895, "f1": 0.779},
      "claude-haiku": {"precision": 0.697, "recall": 0.813, "f1": 0.751},
      "gemini-3-flash:reasoning": {"precision": 0.551, "recall": 0.887, "f1": 0.680},
      "gemini-3-flash": {"precision": 0.531, "recall": 0.877, "f1": 0.661}
    }
  }
}
```

### 3. `results.csv` — Per-File Results

```
student,question,strategy,model,expected_id,matched_id,score,result
Anderson_Charles_664944,Q1,baseline,gpt-5.2,NM_STATE_01,NM_STATE_01,0.78,TP
Anderson_Charles_664944,Q1,taxonomy,gpt-5.2,NM_STATE_01,NM_STATE_01,0.82,TP
Anderson_Charles_664944,Q1,cot,gpt-5.2,NM_STATE_01,NM_STATE_01,0.79,TP
Anderson_Charles_664944,Q1,socratic,gpt-5.2,NM_STATE_01,NM_IO_01,0.58,FP_CLEAN
Baker_Carolyn_647344,Q2,baseline,claude-haiku,NM_IO_01,NM_IO_01,0.81,TP
...
```

### 4. `compliance.csv` — Classification Summary

```
student,question,assignment,clean_or_seeded,expected_id,ensemble_result
Anderson_Charles_664944,Q1,A1,seeded,NM_STATE_01,TP
Anderson_Charles_664944,Q2,A1,clean,,TN
Anderson_Charles_664944,Q3,A1,seeded,NM_IO_01,FN
Baker_Carolyn_647344,Q1,A1,seeded,NM_STATE_01,TP
...
```

### 5. `assets/` — Visualization Charts (PNG)

Generated charts:
- `assignment_comparison.png` — Bar chart: F1 by assignment
- `strategy_f1.png` — Bar chart: F1 by prompting strategy
- `model_comparison.png` — Bar chart: F1 by LLM model
- `strategy_model_heatmap.png` — Heatmap: Strategy × Model performance
- `misconception_recall.png` — Bar chart: Recall by misconception category
- `hallucinations.png` — Distribution of false positive types
- `semantic_distribution.png` — Histogram of embedding similarity scores
- `category_recall.png` — Recall for each Notional Machine category

---

## Environment Variables

Required:
```bash
export OPENROUTER_API_KEY="sk-or-..."    # For LLM detection
export OPENAI_API_KEY="sk-..."           # For semantic embeddings
```

Optional:
```bash
export VERBOSE=true                      # Debug logging
export CACHE_EMBEDDINGS=true             # Cache embedding lookups
```

---

## Typical Workflow

### Step 1: Run Ensemble Analysis

```bash
uv run python analyze.py analyze-ensemble \
  --run-name my-experiment \
  --ensemble-threshold 2
```

### Step 2: Check Results

```bash
# Read the markdown report
cat runs/multi/run_my-experiment/report.md

# Or parse JSON metrics
python -m json.tool runs/multi/run_my-experiment/metrics.json | head -50
```

### Step 3: Compare Against Previous Runs

```bash
# See all runs
ls -lh runs/multi/

# Compare metrics
diff runs/multi/run_analysis2.2/metrics.json runs/multi/run_analysis3/metrics.json
```

### Step 4: Run Ablation Study (Optional)

```bash
# Test different ensemble thresholds
for N in 2 3 4; do
  uv run python analyze.py analyze-ensemble \
    --run-name ablation_n${N} \
    --ensemble-threshold $N
done

# Compare results
ls runs/multi/ablation_* | xargs -I {} bash -c "echo '{}:' && jq '.*.overall.f1' {}/metrics.json"
```

---

## Advanced Usage

### Analyzing Single Assignment Only

```bash
uv run python analyze.py analyze-ensemble \
  --assignment a1 \
  --run-name a1-only
```

### Custom Output Directory

```bash
mkdir -p ~/my_results
uv run python analyze.py analyze-ensemble \
  --output-dir ~/my_results \
  --run-name custom-location
```

### For Paper Submission

```bash
# Generate publication-ready results with verbose logging
uv run python analyze.py analyze-ensemble \
  --run-name paper-submission \
  --ensemble-threshold 2 \
  --semantic-threshold 0.65 \
  --noise-floor 0.55 \
  --verbose
```

Then copy to paper appendix:
```bash
cp runs/multi/run_paper-submission/report.md ~/thesis/results_appendix.md
cp -r runs/multi/run_paper-submission/assets ~/thesis/figures/
```

---

## Troubleshooting

### "No detections found"
- Check that `detections/` directory exists
- Verify detection files were created by `llm_miscons_cli.py`
- Ensure JSON format is correct

### "Embedding API key invalid"
```bash
export OPENAI_API_KEY="your-key-here"
uv run python analyze.py analyze-ensemble --run-name test
```

### "Out of memory"
- Process smaller assignments: `--assignment a1` (instead of `multi`)
- Reduce bootstrap iterations: `--quick`

### "Metrics look wrong"
- Check `--semantic-threshold` and `--noise-floor` values
- Verify ground truth files exist in `data/`
- Review `results.csv` for per-file classifications

---

## See Also

- `architecture.md` — System design overview
- `analysis-pipeline.md` — Complete data flow
- `metrics-guide.md` — Metrics explained (precision, recall, F1)
- `matching.md` — Semantic alignment details
