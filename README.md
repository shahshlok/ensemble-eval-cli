# LLM Notional Machine Misconception Detection Framework

A research framework for detecting notional machine misconceptions in CS1 student code using Large Language Models. Part of a Bachelor's Honours Thesis at UBCO investigating cognitive alignment between LLMs and CS education theory.

**Status:** Analysis 3 Complete | Ensemble Voting Implemented | Publication-Ready

## Research Goal

> Can LLMs detect **Notional Machine** misconceptions—flawed mental models about how code executes?

This is **not** a grading tool. It measures the **Cognitive Alignment** of LLMs with CS Education theory.

**Key Finding:** LLMs show a **30% performance gap** between concrete errors (arrays: F1=0.890) and abstract mental models (variables: F1=0.592). This gap represents a fundamental limitation in LLM reasoning about student mental models.

## The 4-Stage Pipeline (Analysis 3)

```
┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│ 1. SYNTHETIC         │   │ 2. BLIND             │   │ 3. SEMANTIC          │   │ 4. ENSEMBLE          │
│    INJECTION         │──▶│    DETECTION         │──▶│    ALIGNMENT         │──▶│    VOTING ⭐         │
│                      │   │                      │   │                      │   │                      │
│ 10 misconceptions    │   │ 6 LLMs ×             │   │ Semantic embeddings  │   │ Majority consensus   │
│ × 120 students       │   │ 4 prompt strategies  │   │ Cosine similarity    │   │ ≥2 strategies agree  │
│ × 3 questions        │   │ → 8,640 detections   │   │ → single-strategy    │   │ → final metrics      │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘   └──────────────────────┘
       ↓                           ↓                           ↓                           ↓
    360 files              detections/{a1,a2,a3}/      Precision: 0.313       Precision: 0.649 ✅
 with known bugs         {strategy}/*.json             Recall: 0.872          Recall: 0.871
                                                       F1: 0.461              F1: 0.744 ✅
```

## Quick Start

### 1. Setup

```bash
# Clone and install
git clone https://github.com/shahshlok/ensemble-eval-cli
cd ensemble-eval-cli
uv sync

# Configure API keys (required)
export OPENROUTER_API_KEY="sk-or-..."    # For LLM detection
export OPENAI_API_KEY="sk-..."           # For semantic embeddings
```

### 2. Run Ensemble Analysis (Recommended)

```bash
# Analyze all assignments with ensemble voting (≥2 strategies must agree)
uv run python analyze.py analyze-ensemble \
  --run-name my-analysis \
  --ensemble-threshold 2 \
  --semantic-threshold 0.65

# Results saved to: runs/multi/run_my-analysis/
```

### 3. View Results

```bash
# Read the markdown report
cat runs/multi/run_my-analysis/report.md

# Or parse JSON metrics
python -m json.tool runs/multi/run_my-analysis/metrics.json | head -50
```

### Complete Workflow

```bash
# 1. Generate synthetic students with known misconceptions
uv run python utils/generators/dataset_generator.py

# 2. Run LLM detection with all 4 strategies
uv run python analyze.py detect \
  --assignment multi \
  --strategy baseline,taxonomy,cot,socratic

# 3. Run ensemble analysis (recommended)
uv run python analyze.py analyze-ensemble \
  --run-name final-results \
  --ensemble-threshold 2

# 4. Compare against previous runs
ls -lh runs/multi/
```

## Documentation

| Document | Purpose |
|----------|---------|
| [Architecture](docs/architecture.md) | System design overview with 4-stage pipeline diagram |
| [Analysis Pipeline](docs/analysis-pipeline.md) | Complete data flow from injection through ensemble voting |
| [CLI Reference](docs/cli-reference.md) | Commands, options, and output files |
| [Metrics Guide](docs/metrics-guide.md) | Precision, recall, F1, and confidence intervals |
| [Matching & Semantics](docs/matching.md) | Semantic embedding methodology and ensemble consensus |
| [Notional Machines](docs/notional-machines.md) | All 10 misconception categories with examples |
| [Complexity Gradient](docs/complexity-gradient.md) | Why A1 (59%) is harder than A3 (89%) |
| [Development](docs/development.md) | Extending the framework with new misconceptions |
| [AGENTS.md](AGENTS.md) | AI agent guidance for future development |

### Where to Start

1. **For understanding the research:** Read [Architecture](docs/architecture.md)
2. **For running analyses:** Follow [CLI Reference](docs/cli-reference.md)
3. **For interpreting results:** See [Metrics Guide](docs/metrics-guide.md)
4. **For the core finding:** Read [Complexity Gradient](docs/complexity-gradient.md)

## Project Structure

```
ensemble-eval-cli/
├── data/                       # Ground truth misconception definitions
│   ├── a1/groundtruth.json     # 8 categories (Variables & Math)
│   ├── a2/groundtruth.json     # 6 categories (Loops & Control)
│   ├── a3/groundtruth.json     # 5 categories (Arrays & Strings)
│   └── a{1,2,3}/q{1,2,3}.md    # Question prompts
│
├── authentic_seeded/           # Generated student code with known bugs
│   ├── a1/manifest.json        # Student→misconception mapping
│   ├── a1/{Student_Name}/      # Java files (120 students × 3 questions)
│   ├── a2/...
│   └── a3/...
│
├── detections/                 # LLM detection outputs
│   ├── a1_multi/{strategy}/    # baseline, taxonomy, cot, socratic
│   ├── a2_multi/{strategy}/    # 360 files × 4 strategies × 6 models
│   └── a3_multi/{strategy}/
│
├── runs/multi/                 # Analysis results
│   ├── run_analysis2.2/        # Single-strategy results
│   ├── run_analysis3/          # Ensemble voting results (FINAL) ⭐
│   └── run_{id}/               # Custom runs
│       ├── report.md           # Markdown report with charts
│       ├── metrics.json        # Precision, recall, F1, etc.
│       ├── data.json           # Detailed per-file results
│       ├── results.csv         # Tabular results
│       ├── compliance.csv      # TP/FP/FN classification
│       └── assets/*.png        # 8 visualization charts
│
├── prompts/
│   └── strategies.py           # 4 prompt strategies
│
├── pydantic_models/            # Data validation schemas
│   ├── evaluation.py           # DetectionResult, MatchResult
│   ├── submission/             # Student submission models
│   └── context/                # Context models
│
├── utils/
│   ├── generators/             # Synthetic data generation
│   │   └── dataset_generator.py
│   ├── llm/                    # LLM API clients
│   │   ├── openai.py
│   │   ├── anthropic.py
│   │   └── gemini.py
│   ├── matching/               # Semantic matching
│   │   ├── semantic.py         # Embedding-based matching
│   │   ├── fuzzy.py            # String similarity
│   │   └── hybrid.py           # Combined approach
│   ├── execution.py            # Code execution sandbox
│   ├── grading.py              # Metric computation
│   └── statistics.py           # Bootstrap CI, significance tests
│
├── docs/                       # Comprehensive documentation
│   ├── architecture.md         # System design
│   ├── analysis-pipeline.md    # 4-stage pipeline details
│   ├── cli-reference.md        # Command reference
│   ├── metrics-guide.md        # Metrics explained
│   ├── matching.md             # Semantic alignment
│   ├── notional-machines.md    # Taxonomy with examples
│   ├── complexity-gradient.md  # Core finding
│   └── development.md          # Extension guide
│
├── tests/
│   ├── test_llm_miscons2.py   # Detection tests
│   ├── test_anthropic_client.py
│   ├── test_openai_client.py
│   └── test_gemini_client.py
│
├── analyze.py                  # Main analysis CLI ⭐
├── pipeline.py                 # Full pipeline orchestrator
├── pyproject.toml              # Dependencies
├── AGENTS.md                   # AI agent guidance
└── README.md                   # This file
```

## The 10 Notional Machine Categories

### A1: Scalar State (Variables & Math) — 8 categories

| ID | Category | Example |
|---|---|---|
| **NM_STATE_01** | Reactive State Machine | Spreadsheet View (Early Calculation) |
| **NM_IO_01** | Anthropomorphic I/O | Prompt-Logic Mismatch |
| **NM_IO_02** | Anthropomorphic I/O | The Ghost Read |
| **NM_TYP_01** | Fluid Type Machine | Integer Division Blindness |
| **NM_TYP_02** | Fluid Type Machine | Narrowing Cast in Division |
| **NM_SYN_01** | Algebraic Syntax Machine | XOR as Power |
| **NM_SYN_02** | Algebraic Syntax Machine | Precedence Blindness |
| **NM_API_01** | Void Machine | The Void Assumption |

### A2: Temporal State (Loops & Control) — 6 categories

| ID | Category | Example |
|---|---|---|
| **NM_FLOW_01** | Teleological Flow | Accumulator Amnesia (Scope Error) |
| **NM_FLOW_02** | Teleological Flow | The Intent Loop (Off-by-One) |
| **NM_FLOW_03** | Teleological Flow | Infinite Loop (State Stagnation) |
| **NM_FLOW_04** | Teleological Flow | Sabotaging the Future (Inner Loop Mod) |
| **NM_LOGIC_01** | Logical Reasoning | Mutually Exclusive Fallacy |
| **NM_LOGIC_02** | Logical Reasoning | Dangling Else (Indentation Trap) |

### A3: Spatial State (Arrays & Strings) — 5 categories

| ID | Category | Example |
|---|---|---|
| **NM_MEM_01** | Spatial Adjacency | Parallel Array Desync |
| **NM_MEM_02** | Spatial Adjacency | Index-Value Confusion |
| **NM_MEM_03** | Spatial Adjacency | String Identity Trap (Immutability) |
| **NM_MEM_04** | Spatial Adjacency | The 1-Based Offset (OOB) |
| **NM_MEM_05** | Spatial Adjacency | Lossy Swap (Data Destruction) |

See [Notional Machines](docs/notional-machines.md) for detailed examples and code samples.

## Detection Models & Strategies

### 6 LLM Models (3 providers × base + reasoning variants)

| Model | Provider | Type | Base | Reasoning |
|-------|----------|------|------|-----------|
| **GPT-5.2** | OpenAI | Large | ✓ | ✓ |
| **Claude Haiku** | Anthropic | Small | ✓ | ✓ |
| **Gemini Flash** | Google | Medium | ✓ | ✓ |

### 4 Prompt Strategies (Different ways to ask for diagnoses)

| Strategy | Philosophy | Precision | Recall | F1 |
|----------|-----------|-----------|--------|-----|
| **Baseline** | Simple error classification (control) | 0.714 | 0.796 | 0.753 |
| **Taxonomy** | Explicit notional machine categories | 0.654 | 0.832 | 0.734 |
| **CoT** | Chain-of-thought execution tracing | 0.668 | 0.850 | 0.750 |
| **Socratic** | Mental model probing (creative) | 0.584 | 0.923 | 0.726 |

**Insight:** Socratic finds the most bugs (recall=0.923) but creates hallucinations. Ensemble voting recovers precision while keeping high recall.

## Key Results (Analysis 3 — Ensemble Voting)

### Overall Performance

| Metric | Value | Change vs Analysis 2.2 |
|--------|-------|------------------------|
| **Precision** | 0.649 | **+107%** ↑ |
| **Recall** | 0.871 | -0.1% (stable) |
| **F1 Score** | 0.744 | **+61%** ↑ |
| **False Positives** | 1,164 | **-75%** ↓ |
| **True Positives** | 2,150 | -0.05% (minimal) |

**Impact:** Ensemble voting filtered 3,558 hallucinations (-75%) while only losing 1 true positive.

### By Assignment (Complexity Gradient)

| Assignment | Task | Precision | Recall | F1 | Status |
|---|---|---|---|---|---|
| **A3** | Arrays/Strings | 0.810 | 0.989 | **0.890** | ✅ Easy |
| **A2** | Loops/Control | 0.653 | 0.885 | **0.751** | 🟡 Medium |
| **A1** | Variables/Math | 0.499 | 0.728 | **0.592** | ⚠️ Hard |

**Key Finding:** 30% performance gap between A3 and A1. LLMs excel at concrete errors (syntax, arrays) but struggle with abstract mental models (variable state).

### Best Performing Models

| Model | F1 | Precision | Recall |
|-------|-----|-----------|--------|
| **Claude Haiku Reasoning** | **0.819** | 0.784 | 0.857 |
| **GPT-5.2 Reasoning** | 0.788 | 0.702 | 0.897 |
| **GPT-5.2 (base)** | 0.779 | 0.690 | 0.895 |
| Claude Haiku (base) | 0.751 | 0.697 | 0.813 |
| Gemini Flash Reasoning | 0.680 | 0.551 | 0.887 |
| Gemini Flash (base) | 0.661 | 0.531 | 0.877 |

## Core Thesis Finding

### The Complexity Gradient

LLMs show **consistent performance degradation** as conceptual abstraction increases:

```
Concrete Syntax Errors    Abstract Mental Models
(easy for LLMs)          (hard for LLMs)
        ↓                       ↓
Syntax: ~99% F1  ──→  Arrays: 89% F1  ──→  Loops: 75% F1  ──→  Variables: 59% F1
         (runtime)            (indexing)      (temporal)          (state)
```

**Why?** LLMs are trained on (code, output) pairs but rarely see (code, mental_model) pairs. Detecting misconceptions requires reasoning about **student epistemology**, not just code semantics.

See [Complexity Gradient](docs/complexity-gradient.md) for detailed analysis.

---

## Ensemble Voting: The Solution

Single strategies hallucinate (69% false positive rate in Analysis 2.2). Solution: require ≥2 strategies to agree.

```python
# For each student, question, misconception:
if (agreement_count >= 2):
    VALIDATE(detection)  # Count as result
else:
    REJECT(detection)    # Filter as hallucination
```

**Results:**
- Precision: 0.313 → 0.649 (+107%)
- Recall: 0.872 → 0.871 (stable!)
- F1: 0.461 → 0.744 (+61%)
- False Positives: -75%

See [Matching & Semantics](docs/matching.md) for ensemble algorithm details.

---

## Environment Variables

```bash
# Required for LLM detection (inference API)
export OPENROUTER_API_KEY="sk-or-..."

# Required for semantic embeddings (used in matching)
export OPENAI_API_KEY="sk-..."

# Optional: debugging and caching
export VERBOSE=true
export CACHE_EMBEDDINGS=true
```

---

## Research Context

| Field | Value |
|-------|-------|
| **Project** | Honours Thesis: LLM Detection of Notional Machine Misconceptions |
| **Institution** | University of British Columbia Okanagan (UBCO) |
| **Researcher** | Shlok Shah |
| **Academic Year** | 2024-2025 |
| **Target Venue** | ITiCSE / SIGCSE |
| **Supervisor** | Dr. [TBD] |

---

## Limitations & Future Work

### Current Limitations

1. **Small Dataset:** 360 student files across 3 assignments
2. **Single Language:** Java only (CS1 teaching language)
3. **Synthetic Injection:** Students are real, but bugs are artificially injected
4. **Binary Classification:** Presence/absence of misconception (no degree of understanding)

### Future Extensions

1. **A4 Assignment:** Objects and references (heap/indirection complexity)
2. **Cross-language:** Python, C++, JavaScript
3. **Confidence Weighting:** Weight ensemble votes by LLM confidence
4. **Interactive Diagnosis:** Socratic dialogue with students for real-time feedback
5. **Misconception Chains:** Track how misconceptions lead to subsequent errors
6. **Student Modeling:** Predict which misconceptions a student likely has

See [Development](docs/development.md) for extension guidelines.

---

## How to Cite

If you use this framework in your research, please cite:

```bibtex
@software{shah2025lmm_misconceptions,
  author = {Shah, Shlok},
  title = {LLM Notional Machine Misconception Detection Framework},
  year = {2025},
  institution = {University of British Columbia Okanagan},
  note = {Honours Thesis Research Project},
  url = {https://github.com/shahshlok/ensemble-eval-cli}
}
```

Or in APA format:

```
Shah, S. (2025). LLM notional machine misconception detection framework. 
University of British Columbia Okanagan [Unpublished Honours Thesis].
```

---

## License

This project is part of academic research at UBCO. Licensing terms TBD pending publication.

For inquiries: [Contact Info TBD]

---

## Acknowledgments

- **Supervisors/Committee:** [TBD]
- **CS Education Theory:** Based on notional machine research by [Ben du Boulay](https://en.wikipedia.org/wiki/Notional_machine) and others
- **LLM Providers:** OpenAI, Anthropic, Google
- **Tools:** Built with Python, Typer, Pydantic, Pandas, scikit-learn

---

## Quick Links

- **GitHub:** https://github.com/shahshlok/ensemble-eval-cli
- **Documentation:** See `docs/` directory
- **Results:** See `runs/multi/run_analysis3/report.md`
- **Issues & Feedback:** GitHub Issues or [contact TBD]

---

## Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| `API key invalid` | Check `OPENROUTER_API_KEY` and `OPENAI_API_KEY` environment variables |
| `No detections found` | Ensure LLM detection ran first: `python analyze.py detect` |
| `Out of memory` | Process smaller assignments: `--assignment a1` instead of `multi` |
| `Embedding timeout` | Reduce batch size or check OpenAI API status |

See [CLI Reference](docs/cli-reference.md) for complete troubleshooting.

---

**Last Updated:** December 22, 2025  
**Status:** ✅ Analysis 3 Complete, Ensemble Voting Implemented, Publication-Ready
