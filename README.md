# EME Testing (Lean Branch)

Minimal harness for generating evaluation JSON files for student submissions and (in this
branch) moving toward a lean SQLite-backed store for those JSON records.

## Current Pieces

- `modes/` – grading strategies (direct, reverse, EME) that produce JSON results under `data/`.
- `data/` – evaluation JSON files (arrays of per-student records).
- `evaluation_schema.json` – JSON Schema describing the expected evaluation structure.
- `utils/validation.py` – reusable validation helpers (no DB coupling).
- `validate_data.py` – tiny CLI wrapper around the validation helpers.
- `LEAN_DB_PLAN.md` – design notes for the new lean SQLite layer (not implemented yet).

If you don’t care about the CLI, you can ignore `validate_data.py` and import
`utils.validation.validate_json_file` directly in your own scripts.
