# Lean SQLite Evaluation DB Plan

Branch: `nov10-add-db-lean`
Goal: **keep JSON files exactly as they are today**, but add a _minimal_, modular SQLite layer that:

- Stores each evaluation JSON record in a single table.
- Validates JSON against the existing `evaluation_schema.json` before insert.
- Optionally re-validates on export.
- Lets you **recreate the `data/` JSON files from the DB** whenever you want to do offline analysis.

No JSONB, no virtual columns, no FTS, no views, no complex analyzer. Just “validated JSON in, validated JSON out”.

---

## 1. Requirements (What This Lean Version Should Do)

- **Keep current JSON structure**
    - Files in `data/results_{strategy}_YYYY-MM-DDTHH-MM-SS.json` stay unchanged.
    - Each file is a JSON array of evaluation objects (current format).

- **Database responsibilities**
    - Store each evaluation record as raw JSON (text) plus minimal metadata.
    - Track which _run_ a record belongs to (run ID + timestamp).
    - Track which _strategy_ produced it (`direct`, `reverse`, `eme`).
    - Enforce uniqueness: at most one row per `(run, student, strategy)`.

- **Validation**
    - Use `evaluation_schema.json` via `jsonschema`.
    - Validate each JSON object **before** inserting into DB.
    - Optionally: provide a “health check” / export mode that re-validates on read.

- **Export / analysis**
    - Provide an **interactive “Start Analysis” flow in the existing `edubench` CLI** that:
        - Presents a simple menu (similar to the grading strategy menu) for analysis actions.
        - For now, the only action is: **“Restore JSON files from database into `data/`”**.
        - Reads from the DB and recreates one JSON file per run under `data/`, using the original filenames (e.g. `results_direct_2025-11-13T17-52-45.json`).
        - Each exported file is a JSON array with the same structure as the original temporary file.
    - After running this analysis option, `data/` should look exactly like it did immediately after the benchmark run, so you can do ad‑hoc analysis on the files.

Non-goals for the lean version:

- No JSONB reliance, no SQLite 3.51-specific features.
- No FTS5 / triggers / views.
- No large `EvaluationAnalyzer` helper – keep query/analysis outside DB.

---

## 2. Minimal Schema Design

Use a new schema file for the lean version (e.g. `db/lean_schema.sql`) so it does not conflict with the existing heavy schema.

### 2.1 Tables

```sql
-- One row per batch of evaluations (per JSON file or logical run)
CREATE TABLE IF NOT EXISTS runs (
    run_id        INTEGER PRIMARY KEY,
    run_timestamp TEXT NOT NULL,        -- e.g. "2025-11-13T17-52-45" or full ISO
    source_file   TEXT NOT NULL,        -- original filename, e.g. "results_direct_2025-11-13T17-52-45.json"
    strategy      TEXT NOT NULL,        -- "direct", "reverse", "eme"
    notes         TEXT                  -- optional manual notes
);

-- One row per student evaluation (per student per strategy per run)
CREATE TABLE IF NOT EXISTS evaluations (
    eval_id        INTEGER PRIMARY KEY,
    run_id         INTEGER NOT NULL REFERENCES runs(run_id) ON DELETE CASCADE,
    student_name   TEXT    NOT NULL,    -- from evaluation["student"]
    strategy       TEXT    NOT NULL,    -- duplicate of runs.strategy for convenience
    raw_json       TEXT    NOT NULL,    -- full evaluation JSON as text

    UNIQUE (run_id, student_name, strategy)
);

CREATE INDEX IF NOT EXISTS idx_evaluations_run ON evaluations(run_id);
CREATE INDEX IF NOT EXISTS idx_evaluations_student ON evaluations(student_name);
```

Notes:

- `run_timestamp` is parsed from the filename (e.g. `results_direct_2025-11-13T17-52-45.json` → `2025-11-13T17-52-45`).
- `strategy` is stored both in `runs` and `evaluations` for convenience; it is derived from the filename (`results_{strategy}_...`).
- `raw_json` is **opaque text**; all structure is enforced via JSON Schema on the application side.

---

## 3. Automatic Ingestion from `edubench` CLI

Instead of a separate loader CLI, ingestion happens automatically as part of the existing `edubench` workflow:

- `cli.py` already writes results to `data/results_{mode}_{iso_date}.json` inside `_save_results(mode, results)`.
- After each file is written, we immediately ingest it into the DB.

### 3.1 Library module

Create a lean DB helper module, e.g. `db/lean.py`, with functions:

- `init_db(db_path: str) -> None`
    - Opens (or creates) `db_path` and executes `db/lean_schema.sql`.
    - Safe to call multiple times; uses `CREATE TABLE IF NOT EXISTS`.

- `ingest_results_file(db_path: str, file_path: Path) -> None`
    - Parses `strategy` and `run_timestamp` from `file_path.name`.
    - Loads JSON array from `file_path`.
    - Validates the list using the same logic as `utils.validation`:
        - JSON Schema (`evaluation_schema.json`) if `jsonschema` is installed.
        - Basic metrics checks.
    - If validation fails: log errors and abort ingestion for that file (no partial insert).
    - If valid:
        1. Insert one row into `runs` with `run_timestamp`, `source_file`, `strategy`.
        2. Insert one row per evaluation into `evaluations` with:
           - `run_id` from the inserted run.
           - `student_name = record["student"]`.
           - `strategy`.
           - `raw_json = json.dumps(record, separators=(",", ":"))`.

### 3.2 Wiring into `_save_results`

In `cli.py`:

- Decide on a default DB path, e.g. `evaluations.db` at the project root.
- In `_save_results(mode, results)`:
    1. Write the JSON file to `data/results_{mode}_{iso_date}.json` (existing behaviour).
    2. Call `db.lean.init_db(db_path)` once (could be guarded by a simple “initialized” flag).
    3. Call `db.lean.ingest_results_file(db_path, output_path)` to push the file into the DB.

This ensures that **every time you run `uv run edubench benchmark ...`**, the DB is automatically updated with the new results, while `data/` remains a temporary workspace.

---

## 4. Export / Restore via `edubench` CLI

Export is exposed as an **interactive analysis mode** on the existing `edubench` CLI rather than a separate script or a bunch of flags.

### 4.1 CLI interface (proposed)

Add a new command to `cli.py`, for example:

```bash
# Start interactive analysis workflow
uv run edubench analysis
```

Behaviour (for now):

- On start, display a short menu similar to the grading mode selection, e.g.:

  1. Restore JSON files from database into `data/`
  2. (future) Show quick summary stats
  3. Exit

- If the user selects option 1:
    - Read from the SQLite DB (default `evaluations.db`).
    - Recreate one JSON file per run under the `data/` directory, using the original `source_file` name stored in `runs`.
    - Overwrite any existing files with the same name in `data/`.
- Other options can be added later without touching the DB layer (CLI stays a thin orchestration layer).

Optional flags (future-friendly, still keeping CLI simple):

- `--db PATH` – custom DB path (default `evaluations.db`).
- `--data-dir PATH` – where to restore files (default `data/`).
- `--validate` – re-run JSON Schema validation on each record before writing.

### 4.2 Export behaviour

Pseudocode:

1. Open DB connection (read-only is fine).
2. Select all runs (or filtered by strategy / time if options are added later):

    ```sql
    SELECT run_id, run_timestamp, source_file, strategy
    FROM runs
    ORDER BY run_timestamp, strategy;
    ```

3. For each run:
    - Query its evaluations:

        ```sql
        SELECT raw_json
        FROM evaluations
        WHERE run_id = ?
        ORDER BY student_name;
        ```

    - For each row, parse `raw_json` with `json.loads`.
    - Optionally re-validate each record with JSON Schema if `--validate` is set.
    - Collect all records into a list.
    - Write the list as a JSON array to:

        ```text
        data_dir / source_file
        ```

      where `source_file` is exactly the original filename like `results_direct_2025-11-13T17-52-45.json`.

After running `uv run edubench analysis --restore-json`, the `data/` directory should contain one JSON file per run, each identical in structure (and filename) to the original files that were ingested.

---

## 5. Implementation Plan (Step-by-Step)

On branch `nov10-add-db-lean`:

1. **Add lean schema file**
    - `db/lean_schema.sql` with the `runs` + `evaluations` definitions from section 2.

2. **Implement DB helper module**
    - New module: `db/lean.py` with:
        - `init_db(db_path: str)`.
        - `ingest_results_file(db_path: str, file_path: Path)`.
        - (Optionally later) a helper like `restore_json_files(db_path: str, data_dir: Path, validate: bool)`.
    - Reuse `evaluation_schema.json` and `jsonschema` via `utils.validation` for validation.

3. **Wire ingestion into `edubench` benchmark flow**
    - In `cli.py`:
        - Decide on default `db_path` (e.g. `"evaluations.db"`).
        - In `_run_single_mode` / `_run_all_modes`, rely on `_save_results` to:
            - Write the file under `data/`.
            - Then call `db.lean.init_db(db_path)` and `db.lean.ingest_results_file(db_path, output_path)`.

4. **Add interactive `edubench analysis` command**
    - New Typer command in `cli.py`, e.g. `analysis`:
        - Displays an interactive analysis menu (mirroring the style of the grading mode menu).
        - For now, implements option “Restore JSON files from DB into `data/`”, which:
            - Calls `db.lean.restore_json_files(db_path, data_dir, validate=False)` (or similar helper).
        - Can grow later with additional analysis options while keeping DB and validation logic in their own modules.

5. **Update README**
    - Document the lean flow:
        - “Run benchmark” → JSON written to `data/` and ingested into DB automatically.
        - “Restore JSON” → `uv run edubench analysis --restore-json` to rebuild `data/` from the DB.
