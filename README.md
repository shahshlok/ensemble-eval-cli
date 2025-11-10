# EME_testing

A modular Ensembling Method Evaluation (EME) harness for comparing GPT-5 (ChatGPT) against EduAI (GPT-OSS 120B) on Java student submissions. Prompt templates, model interfaces, evaluation logic, and display components live in isolated modules so researchers can iterate quickly—especially on the EME prompt.

## Quickstart

```bash
uv sync
uv run main.py
```

1. Duplicate `.env` and populate the API keys plus the `QUESTION` and `RUBRIC` values. The rubric must be valid JSON (`{"criteria": [...]}`). `EDUAI_API_KEY` maps to the `x-api-key` header for `https://eduai.ok.ubc.ca/api/chat`.
2. Drop Java submissions (one `.java` per student folder) inside `student_submissions/`.
3. Run the pipeline with `uv run main.py` to print a rich, colorized comparison table. Raw model outputs + metrics are saved to `data/results.json` for further analysis.

## Editable Prompt Layer

The evaluation prompt lives in `prompts/eme_prompt.py` through the `build_eme_prompt` function. Adjusting the instructions, swapping in CRE/PRE variations, or inserting ablation toggles can all be done there without touching the evaluator, model clients, or display code. Every submission runs through this one prompt builder, making it the single point of control for EME experiments.

## Project Layout

```
EME_testing/
├── data/results.json        # cached raw output
├── main.py                  # orchestration pipeline
├── prompts/eme_prompt.py    # tweakable EME prompt template
├── student_submissions/     # Java submissions (one folder per student)
└── utils/
    ├── ai_clients.py        # GPT-5 + EduAI wrappers
    ├── display.py           # Rich table renderer
    └── evaluator.py         # prompt builder + metric logic
```

## Notes

- GPT-5 calls run via the official OpenAI `responses.create` API. EduAI uses a `curl` subprocess to hit the `EDUAI_ENDPOINT` you configure.
- The EduAI payload mirrors UBC's example: single user message carrying the prompt, `model=ollama:gpt-oss:120b`, `streaming=false`, and an empty `courseCode` by default. Override `EDUAI_MODEL` or `EDUAI_COURSE_CODE` in `.env` if needed.
- Invalid JSON responses are skipped with a warning so a single bad call never aborts the batch.
- Modify `utils/display.py` if you need alternative visualizations or exports (CSV, parquet, etc.).
