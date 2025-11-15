# EduBench Documentation Hub 📚

Welcome to the EduBench docs. This folder is the **beginner‑friendly entry point** to the entire codebase.

If you are new, start with the first two sections below and then dive deeper as needed.

---

## 1. Big Picture

- **What is EduBench?**  
  EduBench is a small framework to **benchmark AI graders** on student code. It runs multiple grading strategies over Java submissions and compares:
  - **GPT‑5 Nano** (via OpenAI)
  - **GPT‑OSS 120B** (via EduAI)

- **Core ideas**
  - Student code in → **two model grades out** → metrics + JSON files → SQLite database.
  - Everything is driven via a **Typer CLI** (`bench`) or a **one‑off script** (`single_submission.py`).

High‑level flow:

```text
Java submissions (.java)
        │
        ▼
   CLI / scripts
        │
        ▼
  Grading modes (direct / reverse / EME)
        │
        ▼
 Model clients (OpenAI + EduAI)
        │
        ▼
 JSON results  ──>  Validation  ──>  SQLite (evaluations.db)
        │                          ▲
        └──────────── Restore JSON ┘
```

For a more detailed component view, see `docs/ARCHITECTURE.md`.

---

## 2. Quickstart for Beginners

Read this together with the top‑level `README.md`.

### 2.1. Install and set up

From the project root:

```bash
uv sync           # install dependencies from pyproject.toml
```

Set up your `.env` file with:

- a **programming question**
- a **rubric JSON**
- API keys for the models

Example (simplified):

```env
QUESTION="Write a Java program that prints numbers 1..100."

RUBRIC='{
  "totalPoints": 100,
  "categories": [
    {"name": "correctness", "points": 60, "description": "Program prints numbers 1..100"},
    {"name": "style", "points": 20, "description": "Readable, idiomatic Java"},
    {"name": "documentation", "points": 20, "description": "Meaningful comments"}
  ]
}'

OPENAI_API_KEY=sk-your-openai-key
EDUAI_API_KEY=sk-your-eduai-key
EDUAI_ENDPOINT=https://eduai.ok.ubc.ca/api/chat
EDUAI_MODEL=ollama:gpt-oss:120b
```

Place student submissions as `.java` files under:

```text
student_submissions/<student_id>/*.java
```

For example:

```text
student_submissions/
  Smith_John_123456/
    solution.java
  Doe_Jane_654321/
    Main.java
```

### 2.2. Run a benchmark (all students)

Interactive mode (recommended):

```bash
uv run bench benchmark
```

You will see a menu:

```text
Select a grading strategy:

[1] Direct Grading           Grade student code directly against rubric
[2] Reverse Grading          Generate ideal solution first, then compare
[3] Ensemble (EME)           RIAYN‑style ensemble prompt
[4] Run All                  Compare all three strategies side‑by‑side
[5] Analysis                 Restore JSON files from database and analyze results
```

Then:

- EduBench will ask you to choose:
  - a `question_*.md` file (e.g. `question_cuboid.md`)
  - a matching `rubric_*.json` file (e.g. `rubric_cuboid.json`)
- It will discover `*.java` files in `student_submissions/`.
- It runs the chosen strategy (or all strategies) with a progress bar.
- Results are written to:
  - JSON files in `data/results_{strategy}_{timestamp}.json`
  - `evaluations.db` via the database manager.

You will also see a Rich table showing, per student:

- GPT‑5 Nano score and %
- GPT‑OSS 120B score and %
- Average %
- Difference %
- Agreement flag (✅ / 🚩)

### 2.3. Analyze / restore results later

To rebuild JSON files from the database:

```bash
uv run bench benchmark
# Select option [5] Analysis
# Then [1] Restore JSON from Database
```

This uses `db/manager.py` under the hood.  
For more details, see `docs/DATABASE.md`.

---

## 3. Running a Single Submission

If you just want to quickly grade **one student’s file** with the EME ensemble pipeline, use `single_submission.py`:

```bash
python single_submission.py path/to/Student123/Main.java
```

What happens:

1. Loads `.env` (question + rubric).
2. Finds the Java file you passed (or the first `.java` inside a directory).
3. Runs `utils.evaluator.evaluate_submission(...)` using the EME prompt.
4. Shows a progress bar for the one evaluation.
5. Prints a Rich table and a small grade summary like:

```text
=== Grade Summary ===
Student: Student123
GPT-5:  85.0/100.0 (85.0%)
EduAI:  82.0/100.0 (82.0%)
Avg %:  83.5%
Diff %: 3.0%
Flag:   ✅
```

Useful options:

```bash
python single_submission.py path/to/Student123/Main.java \
  --show-raw \
  --dump-json data/single_student_result.json
```

- `--show-raw` prints the raw model responses (good for debugging).
- `--dump-json` saves a clean JSON summary to the path you provide.

---

## 4. How the CLI and Pipeline Work

This is a simplified diagram of the main benchmark command (`cli.py`):

```text
bench benchmark
        │
        ▼
 _display_banner()
        │
        ▼
prompt: choose mode (1–5)
        │
        ├─► if mode == 'analysis':
        │        _run_analysis_menu()
        │        └─► option 1 → restore_json_files(...)
        │
        └─► else (direct / reverse / eme / all)
                 │
                 ▼
         _run_benchmark_async(...)
                 │
                 ├─► _load_question_and_rubric()
                 │        ├─ discover question_*.md
                 │        └─ discover rubric_*.json
                 │
                 ├─► _discover_submissions(student_submissions/)
                 │
                 └─► run mode(s):
                          _run_single_mode(...) OR _run_all_modes(...)
                          │
                          └─► evaluate_submission(...) per .java
                                   │
                                   └─► call GPT‑5 + EduAI in parallel
```

Key modules involved:

- `cli.py` – entry point and menus.
- `modes/*.py` – different grading strategies.
- `prompts/*.py` – how the prompt is phrased for the model.
- `utils/evaluator.py` – calls models, computes metrics.
- `utils/ai_clients.py` – OpenAI (GPT‑5), EduAI, and OpenRouter clients.
- `db/manager.py` – stores and restores results via SQLite.

For a more detailed walkthrough of each module, see:

- `docs/ARCHITECTURE.md`
- `docs/JSON_OUTPUT.md`
- `docs/DATABASE.md`

---

## 5. Detailed Guides

Use these when you need more depth:

- **Database & persistence** – `docs/DATABASE.md`  
  How `evaluations.db` is structured, how JSON is validated and ingested, and how to restore runs.

- **Structured JSON model outputs** – `docs/JSON_OUTPUT.md`  
  How GPT‑5 Nano and OpenRouter (Gemini) are configured to always return JSON, with examples.

- **System architecture & module map** – `docs/ARCHITECTURE.md`  
  End‑to‑end pipeline diagrams and a file‑by‑file explanation of the main modules.

---

## 6. Where to Go Next

- To **understand how grading prompts work**, read `prompts/*.py`, then `docs/JSON_OUTPUT.md`.
- To **change how runs are stored or queried**, start with `db/manager.py` and `docs/DATABASE.md`.
- To **extend or refactor the CLI**, study `cli.py` and `docs/ARCHITECTURE.md`.

If you get stuck or you’re unsure where a piece of logic lives, start with the *Project Structure* section in `README.md`, then come back here and follow the links.
