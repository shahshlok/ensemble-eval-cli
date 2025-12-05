# Research Questions

Based on the project's thesis and goals outlined in `AGENTS.md`.

## Thesis Statement
> "We conducted a controlled ablation study to define the 'Diagnostic Ceiling' of modern LLMs. We prove that while LLMs have solved Syntax/API misconceptions (approaching 100% recall), they remain statistically incompetent at diagnosing State/Logic flow (often <25% recall), regardless of model size or prompting strategy. Furthermore, complex pedagogical prompting (e.g., Socratic) often degrades diagnostic performance compared to zero-shot classification."

## Primary Research Questions

### 1. The Diagnostic Ceiling
**Question:** What is the theoretical upper bound of LLM detection capabilities on controlled (seeded) data?

**Explanation:**
Before deploying LLMs in real classrooms, we must establish a baseline of "competence" under ideal conditions (Sensitivity Analysis). If an LLM cannot detect a clearly defined, isolated error in a synthetic submission, it cannot be trusted to find ambiguous errors in real student code. This question seeks to define the "best case scenario"—the maximum potential recall for current models.

### 2. Cognitive Alignment (formerly Failure Boundaries)
**Question:** Does LLM diagnostic performance correlate with the **cognitive depth** of the misconception?

**Explanation:**
We hypothesize a "Depth-Performance Gap."
*   **Surface Errors:** Syntax slips and API misuse (e.g., `Math.pow` args).
*   **Deep Misconceptions:** Structural flaws in the **Notional Machine** (e.g., `STA-01 Spreadsheet View`) or **Negative Transfer** (e.g., `ALG-01 Precedence Blindness`).
This RQ investigates if models are "blind" specifically to errors that require modeling the student's *intent* vs. the code's *execution*. If models fail on Notional Machine violations while solving Syntax errors, they are acting as "Compiler++" rather than "Pedagogical Tutors."

### 3. The Pedagogical Discord (formerly Prompting Paradox)
**Question:** Do "pedagogically valid" prompts improve or degrade diagnostic accuracy?

**Explanation:**
Educational theory suggests tutors should act as Socratic guides, interpreting the student's intent (addressing the **Superbug**). However, preliminary data suggests that "Act as a Tutor" prompts perform *worse* than "Find the Bug" prompts. This RQ investigates if the linguistic complexity of pedagogical role-playing introduces noise that masks the model's ability to analyze strict logic/state, effectively decoupling "sounding like a teacher" from "seeing like a teacher."

### 4. The Rubric Blinder Effect
**Question:** Does the presence of a grading rubric strictly constrain the model's attention to surface features?

**Explanation:**
Rubrics in CS1 are typically functional (e.g., "Program outputs correct distance: 2 points"). We hypothesize that providing these strict criteria biases the LLM towards acting as a "Grader" (verifying compliance) rather than a "Diagnostician" (analyzing intent). Removing the rubric might paradoxically *increase* detection of deep misconceptions by freeing the model's attention to look for structural anomalies rather than checkbox items.

### 5. Generalizability across Domains
**Question:** Is the "State vs. Syntax" failure pattern robust across different CS1 topics?

**Explanation:**
To prove these limitations are fundamental to LLM reasoning (and not just artifacts of one assignment), we replicate the study across distinct cognitive domains (e.g., Kinematics/Variables vs. String Processing/Arrays). We expect the specific misconceptions to change, but the *structural inability* to detect Notional Machine violations to remain constant.
