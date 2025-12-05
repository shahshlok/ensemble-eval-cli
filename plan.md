# Thesis Execution Plan: The "Notional Machine" Gap

**Objective:** rigorously prove that LLMs fail to model the **Notional Machine**, acting instead as "Surface-Level Pattern Matchers," and that standard pedagogical prompting (Rubrics, Socratic roles) paradoxically worsens this blindness.

---

## Phase 1: Metric Rigor (The Foundation)
**Goal:** Establish the "Honest N-Count" to accurately measure the "Diagnostic Ceiling" (RQ1) and "Cognitive Alignment" (RQ2).

### 1.1 Implement "Potential Recall" (The Ceiling)
*   **Definition:** `Potential Recall = (Files Detected by ANY Model) / (Total Seeded Files)`
*   **Action:** Update `analyze_cli.py` to calculate and report this metric.
*   **Why:** Distinguishes "Impossible to Detect" (Ceiling) from "Unreliable" (Low Average Recall).

### 1.2 Cognitive Depth Tagging (RQ2)
*   **Action:** Update `groundtruth.json` schema to include a `cognitive_depth` field:
    *   `surface`: Syntax errors, API misuse (e.g., `TYP-01`, `API-01`).
    *   `notional`: State logic, Control flow, Variable scoping (e.g., `STA-01`, `INP-02`).
*   **Analysis:** Re-run analysis to plot **Recall vs. Cognitive Depth**.
*   **Hypothesis:** `Recall(Surface) >> Recall(Notional)`.

---

## Phase 2: The Rubric Bias Experiment (RQ4)
**Goal:** Prove that grading rubrics blind LLMs to deep misconceptions by forcing "Compliance Mode."

### 2.1 "Pure Discovery" Prompt Suite
Create `prompts/pure_discovery.py` with three rubric-free strategies:
1.  **`discovery_minimal`**: "List conceptual misconceptions. Ignore syntax." (Baseline discovery)
2.  **`discovery_mental_model`**: "Reconstruct the student's mental model. Where does it diverge?" (Targeting intent)
3.  **`discovery_debugger`**: "Find the logic bug and explain the root cause." (Targeting runtime logic)

### 2.2 Experiment Execution (A3)
*   **Dataset:** `authentic_seeded/a3` (Temporal State / Loops).
*   **Models:** `gpt-5.1`, `gemini-2.5-flash-preview`.
*   **Comparison:** Compare `discovery_*` vs `rubric_only` / `baseline`.
*   **Hypothesis:** `discovery` prompts will have higher specific recall on `notional` errors than `rubric_only` prompts.

---

## Phase 3: Generalizability & State Complexity (RQ5)
**Goal:** Demonstrate that the failure pattern scales with **State Complexity**.

### 3.1 The A3 Run (Temporal State)
*   **Task:** Ensure A3 dataset is fully generated and verified (loops, conditionals).
*   **Analysis:** Compare A2 (Math State) vs A3 (Temporal State) recall.
*   **Hypothesis:** Recall drops as State Complexity increases (Linear < Loop < Array).

### 3.2 Cross-Domain Synthesis
*   Create a "State Complexity" chart aggregating results from A2 and A3.
*   Map individual misconceptions to the "Notional Machine" framework.

---

## Phase 4: Thesis Narrative Construction
**Goal:** Assemble the final report (`thesis_report.md`).

### 4.1 The "Cognitive Gap" Definitions
*   Formalize the academic definition of "Misconception" (Notional Machine deviation).
*   Present the "Prompting Paradox" and "Rubric Blinder" as consequences of the LLM's inability to model intent.

### 4.2 Statistical Significance
*   Apply McNemar's Test to all "Discovery vs Rubric" comparisons.
*   Report Confidence Intervals for all Recall metrics.

---

## Execution Checklist

- [ ] **Phase 1: Metrics**
    - [ ] Update `analyze_cli.py` with "Potential Recall".
    - [ ] Tag `groundtruth.json` with `cognitive_depth`.
- [ ] **Phase 2: Rubric Bias (RQ4)**
    - [ ] Implement `prompts/pure_discovery.py`.
    - [ ] Run `discovery` strategies on A3.
- [ ] **Phase 3: Generalizability (RQ5)**
    - [ ] Complete full A3 detection run.
    - [ ] Generate "State Complexity" comparison chart.
- [ ] **Phase 4: Writing**
    - [ ] Draft methods section in `thesis_report.md`.