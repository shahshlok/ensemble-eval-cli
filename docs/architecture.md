# Architecture Overview: Cognitive Alignment Research Framework

**Status:** Analysis 3 Complete | Ensemble Voting Implemented  
**Last Updated:** December 22, 2025

---

## System Overview

This document describes the **complete architecture** of `ensemble-eval-cli`, a research framework for measuring LLM "Cognitive Alignment"—the ability to identify deep student misconceptions in CS code.

---

## High-Level Pipeline (3 Stages + Ensemble)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        COMPLETE RESEARCH PIPELINE                           │
└─────────────────────────────────────────────────────────────────────────────┘

STAGE 1: SYNTHETIC INJECTION (Data Generation)
┌─────────────────────────────────────────┐
│ data/{a1,a2,a3}/groundtruth.json        │
│ └─ 10 Notional Machine categories       │
│ └─ 50+ misconceptions with theory       │
│                                         │
│ ↓ dataset_generator.py                  │
│                                         │
│ authentic_seeded/{a1,a2,a3}/            │
│ └─ 120 students × 3 questions = 360     │
│ └─ 40% clean, 60% seeded with bugs      │
└─────────────────────────────────────────┘

STAGE 2: BLIND DETECTION (LLM Analysis)
┌──────────────────────────────────────────────────────────────┐
│ 6 LLM Models × 4 Prompt Strategies × 3 Assignments           │
│                                                              │
│ Models:                  Strategies:                         │
│ ├─ GPT-5.2               ├─ Baseline (simple ask)            │
│ ├─ GPT-5.2 Reasoning     ├─ Taxonomy (with categories)       │
│ ├─ Claude Haiku          ├─ CoT (step-by-step tracing)       │
│ ├─ Claude Haiku Reasoning├─ Socratic (mental model probing)  │
│ ├─ Gemini Flash          │                                   │
│ └─ Gemini Flash Reasoning│ Total: 72 detection runs          │
│                          │                                   │
│ ↓ llm_miscons_cli.py                                          │
│                                                              │
│ detections/{a1,a2,a3}/{strategy}/student_Q*.json            │
│ └─ 360 files × 4 strategies × 6 models = 8,640 detections   │
└──────────────────────────────────────────────────────────────┘

STAGE 3: SEMANTIC ALIGNMENT (Matching)
┌────────────────────────────────────────────────────┐
│ Semantic Embedding Pipeline:                       │
│                                                    │
│ 1. Load detection (LLM output)                    │
│    ├─ inferred_category_name                     │
│    ├─ student_thought_process                    │
│    └─ conceptual_gap                             │
│                                                    │
│ 2. Embed with OpenAI text-embedding-3-large      │
│    ├─ Detection text vector (1536D)              │
│    └─ Ground truth text vector (1536D)           │
│                                                    │
│ 3. Compute cosine similarity                     │
│    └─ If score > threshold (0.65): Match found   │
│                                                    │
│ 4. Classify result:                              │
│    ├─ TP: matched correct misconception          │
│    ├─ FP_CLEAN: wrong match                      │
│    ├─ FP_HALLUCIN: false detection              │
│    └─ FN: missed the actual misconception        │
│                                                    │
│ ↓ analyze.py (single-strategy mode)              │
│                                                    │
│ runs/multi/run_analysis2.2/                      │
│ └─ Precision: 0.313, Recall: 0.872, F1: 0.461   │
└────────────────────────────────────────────────────┘

STAGE 4: ENSEMBLE VOTING (Consensus Filtering) ⭐ NEW
┌──────────────────────────────────────────────────────────────┐
│ Majority Consensus: Require ≥2 strategies to agree           │
│                                                              │
│ For each (student, question, misconception_id):             │
│   Count which strategies detected it                        │
│   If agreement_count >= 2:                                  │
│     → VALIDATE detection (count as result)                 │
│   Else:                                                     │
│     → REJECT detection (filter as hallucination)           │
│                                                              │
│ ↓ analyze.py (ensemble-voting mode)                          │
│                                                              │
│ runs/multi/run_analysis3/                                   │
│ ├─ Precision: 0.649 (+107% improvement) ✅                 │
│ ├─ Recall: 0.871 (stable) ✅                               │
│ ├─ F1: 0.744 (+61% improvement) ✅                         │
│ └─ False Positives: -75% reduction (4,722 → 1,164) ✅      │
└──────────────────────────────────────────────────────────────┘
```

---

## Core Components

### 1. Data Layer

| Component | Location | Purpose | Size |
|-----------|----------|---------|------|
| **Ground Truth** | `data/{a1,a2,a3}/groundtruth.json` | 10 Notional Machine categories with 50+ misconceptions | ~200KB |
| **Questions** | `data/{a1,a2,a3}/q*.md` | Problem statements for each question | ~50KB |
| **Generated Code** | `authentic_seeded/{a1,a2,a3}/{Student}/*.java` | 360 student files with seeded bugs | ~5MB |
| **Manifest** | `authentic_seeded/{a1,a2,a3}/manifest.json` | Maps student→question→misconception | ~100KB |

### 2. Detection Layer (LLM Analysis)

| Component | Location | Purpose | Output |
|-----------|----------|---------|--------|
| **Main CLI** | `llm_miscons_cli.py` | Orchestrates API calls to 6 models × 4 strategies | 8,640 JSON files |
| **Strategies** | `prompts/strategies.py` | 4 prompt architectures (baseline, taxonomy, cot, socratic) | Prompt templates |
| **LLM Clients** | `utils/llm/openrouter.py` | API wrappers for OpenRouter, OpenAI, Anthropic, Google | API responses |
| **Raw Detections** | `detections/{a1,a2,a3}/{strategy}/*.json` | Unprocessed LLM outputs | JSON with misconceptions |

### 3. Semantic Matching Layer

| Component | Location | Purpose | Key Logic |
|-----------|----------|---------|-----------|
| **Semantic Matcher** | `utils/matching/semantic.py` | Embedding-based alignment (OpenAI text-embedding-3-large) | Cosine similarity > 0.65 |
| **Null Detector** | `analyze.py::is_null_template_misconception()` | Filters "no misconception" false positives | Keyword + semantic match |
| **Noise Floor** | `analyze.py::apply_noise_floor()` | Silently filters ultra-low-confidence detections | Removes score < 0.55 |
| **Result Classifier** | `analyze.py::classify_detection()` | Maps similarity to TP/FP/FN | Ground truth comparison |

### 4. Ensemble Voting Layer ⭐ NEW

| Component | Location | Purpose | Algorithm |
|-----------|----------|---------|-----------|
| **Ensemble Filter** | `analyze.py::apply_ensemble_filter()` | Consensus voting across 4 strategies | Majority vote (N≥2) |
| **Agreement Map** | `analyze.py` | Groups detections by (student, question, misconception_id) | Counts strategies per ID |
| **Validation** | `analyze.py` | Keeps only high-agreement detections | Rejects single-strategy outliers |

### 5. Analysis & Reporting Layer

| Component | Location | Purpose | Output |
|-----------|----------|---------|--------|
| **Metrics** | `utils/statistics.py` | Precision, Recall, F1, Bootstrap CIs | Numeric results |
| **Visualization** | `utils/statistics.py` | Charts & heatmaps | PNG assets |
| **Report Generator** | `analyze.py` | Markdown report with embedded results | `report.md` |
| **Data Export** | `analyze.py` | CSV and JSON exports | `results.csv`, `data.json` |

---

## Directory Structure

```
ensemble-eval-cli/
├── data/                              # Ground truth taxonomy
│   ├── a1/
│   │   ├── groundtruth.json           # 10 NM categories, 25 misconceptions
│   │   ├── q1.md                      # Question 1 prompt
│   │   ├── q2.md                      # Question 2 prompt
│   │   └── q3.md                      # Question 3 prompt
│   ├── a2/
│   │   └── ... (same structure)
│   └── a3/
│       └── ... (same structure)
│
├── authentic_seeded/                  # Generated student code
│   ├── a1/
│   │   ├── manifest.json              # Student assignment (360 entries)
│   │   └── {Student_ID}/
│   │       ├── a1q1.java              # Generated Java code
│   │       ├── a1q2.java
│   │       └── a1q3.java
│   ├── a2/ └── ...
│   └── a3/ └── ...
│
├── detections/                        # LLM detection outputs (72 subdirs)
│   ├── a1_multi/
│   │   ├── baseline/                  # 6 models × 360 students
│   │   │   ├── gpt-5.2/
│   │   │   │   ├── student_q1.json
│   │   │   │   ├── student_q2.json
│   │   │   │   └── ...
│   │   │   ├── gpt-5.2:reasoning/
│   │   │   ├── claude-haiku/
│   │   │   └── ... (6 models)
│   │   ├── taxonomy/  └── ...
│   │   ├── cot/       └── ...
│   │   └── socratic/  └── ...
│   ├── a2_multi/ └── ...
│   └── a3_multi/ └── ...
│
├── runs/                              # Analysis results
│   ├── multi/
│   │   ├── run_analysis2.2/
│   │   │   ├── config.json            # Analysis parameters
│   │   │   ├── metrics.json           # Precision, Recall, F1
│   │   │   ├── report.md              # Full markdown report
│   │   │   ├── results.csv            # Detailed per-file results
│   │   │   └── assets/
│   │   │       ├── assignment_comparison.png
│   │   │       ├── model_comparison.png
│   │   │       ├── strategy_f1.png
│   │   │       └── ... (8 PNG charts)
│   │   └── run_analysis3/
│   │       └── ... (same structure with ensemble results)
│   └── index.json                     # Registry of all runs
│
├── prompts/
│   ├── __init__.py
│   └── strategies.py                  # 4 prompt builders
│
├── pydantic_models/                   # Data models
│   ├── evaluation.py                  # Metrics dataclasses
│   └── submission/
│       └── models.py                  # Detection output schema
│
├── utils/
│   ├── llm/
│   │   ├── openai.py                  # OpenAI API client
│   │   ├── anthropic.py               # Anthropic API client
│   │   ├── gemini.py                  # Google API client
│   │   └── openrouter.py              # OpenRouter unified wrapper
│   ├── matching/
│   │   ├── semantic.py                # Cosine similarity matching
│   │   ├── fuzzy.py                   # Token overlap (legacy)
│   │   └── hybrid.py                  # Fused matching (legacy)
│   ├── generators/
│   │   └── dataset_generator.py       # Creates manifest & synthetic code
│   ├── statistics.py                  # Metrics & visualization
│   ├── grading.py                     # TP/FP/FN classification
│   └── execution.py                   # Test execution
│
├── docs/                              # This documentation
│   ├── architecture.md                # You are here
│   ├── analysis-pipeline.md           # Complete flow guide
│   ├── cli-reference.md               # Command-line tools
│   ├── matching.md                    # Semantic alignment
│   ├── metrics-guide.md               # Precision/Recall/F1 explained
│   ├── notional-machines.md           # 10 NM categories with examples
│   ├── complexity-gradient.md         # A1-A3 performance gap analysis
│   └── understanding-the-report.md    # How to read run reports
│
├── analyze.py                         # Main analysis script
├── llm_miscons_cli.py                 # Detection CLI
├── dataset_generator.py               # Data generation CLI
├── pyproject.toml                     # UV dependencies
└── README.md                          # Quick start
```

---

## Data Flow Diagram (Detailed)

```
┌────────────────────────────────────────────────────────────────────────────┐
│                         COMPLETE DATA PIPELINE                             │
└────────────────────────────────────────────────────────────────────────────┘

INPUT: Notional Machine Theory (5 Categories, 50+ Misconceptions)
       │
       ▼
┌─────────────────────────────────────┐
│  STAGE 1: Synthetic Injection       │
├─────────────────────────────────────┤
│                                     │
│  1. Load groundtruth.json           │
│     ├─ NM_STATE_01 (Spreadsheet)    │
│     ├─ NM_IO_01 (Prompt Logic)      │
│     ├─ NM_TYP_01 (Integer Division) │
│     ├─ ... (50+ misconceptions)     │
│     └─ ... (10 categories)          │
│                                     │
│  2. Create manifest.json            │
│     ├─ 360 students                 │
│     ├─ 40% clean (no bug)           │
│     └─ 60% seeded (1 misconception) │
│                                     │
│  3. Generate Java files             │
│     ├─ GPT-5.2 creates code         │
│     ├─ Exhibits injected bugs       │
│     └─ authentic_seeded/a{1,2,3}/   │
│                                     │
└────┬────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│  STAGE 2: Blind Detection                │
├──────────────────────────────────────────┤
│                                          │
│  For each Java file:                     │
│    For each Strategy ∈ {baseline,        │
│                         taxonomy,        │
│                         cot,             │
│                         socratic}:       │
│      For each Model ∈ {GPT,              │
│                        Claude,           │
│                        Gemini,           │
│                        ...}:             │
│        ├─ Send code + prompt            │
│        ├─ LLM analyzes code             │
│        ├─ Outputs: misconceptions[]     │
│        └─ Save to detections/           │
│                                          │
│  Output: 8,640 detection JSON files      │
│  ├─ 360 files × 4 strategies × 6 models │
│  └─ detections/{a1,a2,a3}/{strategy}/   │
│                                          │
└────┬───────────────────────────────────┘
     │
     ▼
┌────────────────────────────────────────┐
│  STAGE 3: Semantic Alignment           │
├────────────────────────────────────────┤
│                                        │
│  For each detection:                   │
│    1. Extract text:                    │
│       ├─ inferred_category_name        │
│       └─ student_thought_process       │
│                                        │
│    2. Embed with OpenAI                │
│       ├─ Detection vector (1536D)      │
│       └─ Ground truth vectors (1536D)  │
│                                        │
│    3. Compute similarity:              │
│       ├─ Cosine distance to each GT    │
│       ├─ Find max_score                │
│       └─ Check if > 0.65               │
│                                        │
│    4. Filter noise:                    │
│       ├─ If score < 0.55: discard      │
│       ├─ If "no bug" pattern: discard  │
│       └─ Else: proceed                 │
│                                        │
│    5. Classify:                        │
│       ├─ If matched_id == expected:    │
│       │  → TP (True Positive)          │
│       ├─ Else if matched_id != null:   │
│       │  → FP_CLEAN (Wrong match)      │
│       ├─ Else if score > threshold:    │
│       │  → FP_HALLUCIN (False alarm)   │
│       └─ Else:                         │
│          → FN (Missed detection)       │
│                                        │
│  Analysis 2.2 Results:                 │
│  ├─ Precision: 0.313                   │
│  ├─ Recall: 0.872                      │
│  ├─ F1: 0.461                          │
│  └─ Problem: 4,722 false positives     │
│                                        │
└────┬─────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│  STAGE 4: Ensemble Voting ⭐             │
├──────────────────────────────────────────┤
│                                          │
│  Group detections by:                    │
│  ├─ student ID                           │
│  ├─ question number                      │
│  └─ misconception ID                     │
│                                          │
│  For each group:                         │
│    Count strategies that agree:          │
│    ├─ baseline says NM_STATE_01?         │
│    ├─ taxonomy says NM_STATE_01?         │
│    ├─ cot says NM_STATE_01?              │
│    ├─ socratic says NM_STATE_01?         │
│    └─ agreement_count = 2 out of 4 ✓     │
│                                          │
│  Decision rules:                         │
│    ├─ If agreement_count >= 2:           │
│    │  → VALIDATE detection               │
│    └─ Else:                              │
│       → REJECT detection (filter)        │
│                                          │
│  Analysis 3 Results:                     │
│  ├─ Precision: 0.649 (+107%) ✅         │
│  ├─ Recall: 0.871 (stable) ✅           │
│  ├─ F1: 0.744 (+61%) ✅                 │
│  └─ False Positives: -75% reduction ✅   │
│                                          │
└────┬──────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────┐
│  STAGE 5: Reporting & Analysis      │
├──────────────────────────────────────┤
│                                      │
│  Compute metrics:                    │
│  ├─ Precision = TP / (TP + FP)       │
│  ├─ Recall = TP / (TP + FN)          │
│  ├─ F1 = 2 × P × R / (P + R)         │
│  └─ Bootstrap 95% CIs                │
│                                      │
│  By assignment (complexity gradient):│
│  ├─ A3: F1 = 0.890 (easy)            │
│  ├─ A2: F1 = 0.751 (medium)          │
│  └─ A1: F1 = 0.592 (hard) ⚠️         │
│                                      │
│  By model:                           │
│  ├─ Claude Haiku Reasoning: 0.819    │
│  ├─ GPT-5.2 Reasoning: 0.788         │
│  └─ Gemini Flash: 0.661              │
│                                      │
│  By strategy:                        │
│  ├─ Baseline: 0.753                  │
│  ├─ CoT: 0.750                       │
│  ├─ Taxonomy: 0.734                  │
│  └─ Socratic: 0.726                  │
│                                      │
│  Output files:                       │
│  ├─ report.md (narrative results)    │
│  ├─ metrics.json (numeric data)      │
│  ├─ results.csv (per-file breakdown) │
│  └─ assets/*.png (8 charts)          │
│                                      │
└──────────────────────────────────────┘

OUTPUT: Publication-ready results demonstrating complexity gradient
```

---

## Key Insights from Architecture

### 1. Semantic Matching is Critical
The system doesn't do string matching. It uses **embedding-based alignment** (OpenAI text-embedding-3-large) to bridge the gap between how LLMs describe misconceptions and how we define them theoretically.

**Example:**
- LLM says: "The student thinks the program magically understands their variable names"
- Ground Truth: "NM_IO_01: Prompt-Logic Mismatch"
- **Similarity Score: 0.78 ✓** (matched despite different language)

### 2. Ensemble Voting is the Breakthrough
Single-strategy detection had 69% hallucination rate. By requiring ≥2 strategies to agree:
- **Hallucinations reduced:** 4,722 → 1,164 (-75%)
- **Accuracy improved:** F1 = 0.461 → 0.744 (+61%)
- **Recall stable:** 0.872 → 0.871 (negligible loss)

### 3. Complexity Gradient is Real
Performance correlates with conceptual depth:
- **A3 (Arrays/Strings):** F1 = 0.890 (concrete, visible errors)
- **A2 (Loops/Control):** F1 = 0.751 (medium abstraction)
- **A1 (Variables/Math):** F1 = 0.592 (abstract mental models)
- **Gap: 30%** between A1 and A3 (50% relative drop)

This is the **core thesis finding**: LLMs struggle with abstract state reasoning.

---

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Package Manager** | uv | Latest |
| **Python** | Python | 3.12+ |
| **Data Processing** | pandas | Latest |
| **API Clients** | openai, anthropic, google-generativeai | Latest |
| **Embeddings** | sentence-transformers | Latest |
| **Visualization** | matplotlib, seaborn | Latest |
| **Statistics** | scipy, numpy | Latest |
| **CLI** | typer, click | Latest |

---

## Next Steps

See `future.md` for:
1. **Thesis Write-up** — Publication-ready for ITiCSE/SIGCSE
2. **Ablation Study** — Test N≥3, N≥4 ensemble thresholds
3. **Per-Model Ensemble** — Compare strategy vs model voting
