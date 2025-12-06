# Response to Critique: Implementing "Hybrid Verification"

You are completely right. The "Validity Circularity" of using an LLM to audit an LLM is a major vulnerability for a "Diagnostic Ceiling" paper. If the Auditor shares the Generator's training bias, we verify nothing.

I accept your recommendation to implement a **Hybrid Verification Pipeline** that maximizes deterministic truth.

## The New Verification Strategy (Hierarchy of Truth)

We will move from "Prompt Obedience" to "Outcome Verification" using this three-tier approach:

### Tier 1: Compiler Verification (Absolute Truth)

**For:** Syntax Errors, Type Mismatches (e.g., `TYP-01 Integer Division Trap`, `SYN-S01 Semicolon after Loop`).

- **Mechanism:** Run `javac TargetFile.java`.
- **Validation Rule:**
    - **Expected Syntax Error:** Code **MUST** fail to compile. If `exit_code == 0`, the LLM "fixed" the syntax → **Discard**.
    - **Expected Logic Error:** Code **MUST** compile. If `exit_code != 0`, the LLM introduced a random syntax bug → **Discard**.

### Tier 2: Differential Execution (High Truth)

**For:** Logic, State, Control Flow (e.g., `STA-01 Spreadsheet View`, `FLO-01 Off-by-One`).

- **The "Gold Standard":** In our generation pipeline, we _already_ generate a "Clean/Correct" version of the code for the "Perfect" student distribution.
- **Mechanism:**
    1.  Run `Clean.java` with Input `X` → Result `Y`.
    2.  Run `Seeded.java` with Input `X` → Result `Z`.
- **Validation Rule:** `Z != Y`.
- **Why this works:** We don't need to write unit tests to define _exactly_ how the bug manifests (which is hard). We just need to prove that **the code behaves differently than the correct solution.** If `Z == Y`, the LLM auto-corrected the logic → **Discard**.

### Tier 3: Semantic Auditor (Probabilistic Truth)

**For:** Mental Models, Comments, Intent (e.g., "Student thinks `^` is power").

- **Mechanism:** LLM (e.g., `gpt-4o-mini`) check.
- **Scope:** Strictly limited to verifying non-executable artifacts (comments, variable names).
- **Validation Rule:** "Does the code explicitly contain the comment/thinking requested?"

## Impact on Validity

This change allows us to state:

> "Our 'Seeded' dataset contains **zero** instances of auto-corrected valid code. All syntax errors were verified by compiler failure, and all logic errors were verified by differential execution against a correct baseline."

This effectively closes the "False Negative Trap." I am ready to move to **Question 2 (Taxonomy Mapping)**.
