# LLM Misconception Detection - Final Design

## Final Decisions

| Decision | Choice |
|----------|--------|
| **Output Format** | `detections/{strategy}/{student}_{question}.json` |
| **Pydantic Model** | Keep ALL fields in Misconception (thesis needs reasoning) |
| **Matching Strategy** | Fuzzy name match first, semantic embeddings fallback |
| **CLEAN file detections** | Classify as False Positive, note in "Interesting Discoveries" section |
| **CLI Style** | Interactive, in new file `llm-miscons-cli.py` |
| **Prompt Strategies** | Keep existing: minimal, baseline, socratic, rubric_only |

---

## Architecture

### Commands
```
uv run miscons      -> Generate seeded dataset (already works)
uv run llm-miscons  -> Interactive CLI to detect misconceptions with LLMs
uv run analyze      -> Compare detections vs manifest, generate accuracy report
```

### Output Structure
```
detections/
├── minimal/
│   ├── Allen_Kathryn_839472_Q1.json   # Contains both GPT-5.1 and Gemini results
│   ├── Allen_Kathryn_839472_Q2.json
│   └── ...
├── baseline/
│   └── ...
├── socratic/
│   └── ...
└── rubric_only/
    └── ...
```

### Matching Pipeline
```
LLM Detection → Fuzzy Name Match → (if fail) → Semantic Embedding Match → Classification
                     ↓                                    ↓
              "Precedence Blindness"              Compare description vectors
                matches ALG-01                   against groundtruth explanations
```

### Metrics to Compute
- **True Positives (TP)**: LLM found the injected misconception
- **False Positives (FP)**: LLM found misconception in CLEAN file OR wrong misconception in SEEDED file
- **False Negatives (FN)**: LLM missed the injected misconception
- **Precision**: TP / (TP + FP)
- **Recall**: TP / (TP + FN)
- **F1 Score**: 2 * (Precision * Recall) / (Precision + Recall)
- **Per-model comparison**: GPT-5.1 vs Gemini 2.5 Flash
- **Per-strategy comparison**: minimal vs baseline vs socratic vs rubric_only
- **Interesting Discoveries**: FPs that reveal genuine issues in "CLEAN" code

---

## Implementation Plan

1. **`llm-miscons-cli.py`** - Interactive CLI
   - Menu: Select strategy, select students (all/sample/N), run detection
   - Calls both models in parallel per student/question
   - Saves to `detections/{strategy}/`

2. **`utils/matching/`** - Matching module
   - `fuzzy.py`: Name-based fuzzy matching against groundtruth
   - `semantic.py`: OpenAI embedding similarity fallback
   - `classifier.py`: TP/FP/FN classification logic

3. **`analyze-cli.py`** or integrated analyze command
   - Load detections + manifest
   - Run matching pipeline
   - Generate accuracy report (markdown + JSON)
   - Include "Interesting Discoveries" section

4. **Update `pyproject.toml`**
   ```toml
   [project.scripts]
   miscons = "utils.generators.dataset_generator:interactive_main"
   llm-miscons = "llm_miscons_cli:app"
   analyze = "analyze_cli:app"
   ```
