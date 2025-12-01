# Research Implementation Plan

**Project:** LLM-Based Misconception Detection in Introductory Programming  
**Deadline:** End of January 2026  
**Status:** Literature review in progress (deep research agents running)

---

## Research Focus

**Primary Research Question:**  
Can LLMs reliably detect student misconceptions, and can automated misconception detection help instructors identify at-risk students and struggling topics?

**Novel Contribution (Primary):**  
> "A systematic study of LLM-based misconception detection in introductory programming, with a tool/methodology for instructors to identify at-risk topics"

**Supporting Contributions:**
- A taxonomy for classifying programming misconceptions (vs syntax errors)
- Multi-model agreement as a validity signal
- Seeded misconception validation methodology (synthetic benchmark)

---

## Constraints & Decisions

| Decision         | Choice                                                   |
| ---------------- | -------------------------------------------------------- |
| Language         | Java only                                                |
| Assignment       | data/a2 (Q1-Q4)                                          |
| Data             | Synthetic submissions (no ethics approval for real data) |
| Ensemble voting  | Deferred (focus on misconceptions)                       |
| Bloom's taxonomy | Deferred (see future.txt)                                |
| Target audience  | Instructors, TAs, researchers                            |

---

## Ground Truth Strategy: Seeded Misconception Validation

Since we don't have real student data, we create a synthetic benchmark:

1. **Define a misconception catalog** - List of known misconceptions we expect to detect
2. **Generate seeded submissions** - Create synthetic code with specific, known misconceptions injected
3. **Run detection** - Have LLMs analyze the seeded submissions
4. **Measure precision/recall** - Did the LLMs find what we injected?

**Why this works for publication:**
- Reproducible by other researchers
- You control the ground truth
- Common methodology in ML/NLP research
- Novel contribution in CS education context

---

## What Counts as a Misconception

| REAL Misconception (Detect)                       | NOT a Misconception (Filter Out) |
| ------------------------------------------------- | -------------------------------- |
| Using `int` instead of `double` for decimals      | Missing semicolon                |
| Using `^` instead of `Math.pow()`                 | Misspelled variable name         |
| Wrong formula: `(v1 + v0) / t` vs `(v1 - v0) / t` | Extra whitespace                 |
| Integer division confusion (`5/2 = 2`)            | Missing import statement         |
| Not closing Scanner (resource leak)               | Formatting issues                |
| Wrong operator precedence in expressions          | Typos in string literals         |

**Key distinction:** Misconception = misunderstanding of a concept. Mistake = mechanical error.

---

## Topic Taxonomy (Revised)

Current problem: "Variables" is a catch-all for everything.

**New canonical topics for Assignment 2:**

| Topic                       | Description                     | Examples                                 |
| --------------------------- | ------------------------------- | ---------------------------------------- |
| **Data Types**              | Choosing appropriate types      | int vs double, type casting              |
| **Input Handling**          | Scanner usage, parsing          | Scanner methods, prompts                 |
| **Mathematical Operations** | Formulas, operators             | Math.pow, Math.sqrt, operator precedence |
| **Output Formatting**       | Displaying results              | Print statements, string formatting      |
| **Syntax**                  | Language mechanics (FILTER OUT) | Semicolons, braces, spelling             |

---

## 8-Week Timeline

| Week                 | Focus             | Deliverables                                           |
| -------------------- | ----------------- | ------------------------------------------------------ |
| **1** (Dec 1-7)      | Tool improvements | Fix topic taxonomy, add misconception filtering        |
| **2** (Dec 8-14)     | Seeded validation | Misconception catalog, seeded submission generator     |
| **3** (Dec 15-21)    | Experiments       | Run detection on seeded data, measure precision/recall |
| **4** (Dec 22-28)    | Visualizations    | Heatmaps, progression charts, model agreement          |
| **5** (Dec 29-Jan 4) | Analysis          | Statistical analysis, write results section            |
| **6** (Jan 5-11)     | Paper draft       | Introduction, methodology, results                     |
| **7** (Jan 12-18)    | Paper draft       | Related work (from lit review), discussion             |
| **8** (Jan 19-25)    | Polish            | Revisions, formatting, submission                      |

---

## Implementation Tasks (Priority Order)

### Phase 1: Core Improvements (Week 1)

- [x] **1.1 Fix topic taxonomy**
  - Update `CANONICAL_TOPICS` in `misconception_analyzer.py`
  - Update `TOPIC_MAPPING` to use new categories
  - Remove "Variables" catch-all

- [x] **1.2 Add misconception filtering**
  - Create filter to exclude syntax errors (missing semicolon, typos)
  - Add `is_real_misconception()` function
  - Update prompt to distinguish misconceptions from mistakes

- [x] **1.3 Improve prompt for misconception quality**
  - Update `direct_prompt.py` to define misconception vs mistake
  - Add examples of what to report vs filter

### Phase 2: Validation Framework (Week 2)

- [x] **2.1 Create misconception catalog**
  - Define 10-15 common misconceptions for A2
  - Document expected behavior for each

- [x] **2.2 Build seeded submission generator**
  - Script to generate Java files with known misconceptions
  - Metadata file tracking what was injected where

- [ ] **2.3 Validation pipeline**
  - Run grading on seeded submissions
  - Compare detected vs injected misconceptions
  - Calculate precision/recall

### Phase 3: Visualization (Week 4)

- [ ] **3.1 Heatmap: Topic × Question**
  - Which topics are hardest in which questions?

- [ ] **3.2 Student progression chart**
  - Q1→Q2→Q3→Q4 flow (generalized, not hardcoded)

- [ ] **3.3 Model agreement visualization**
  - Which misconceptions do models agree/disagree on?

- [ ] **3.4 Export figures for paper**
  - PNG/PDF outputs suitable for publication

### Phase 4: Analysis & Paper (Weeks 5-8)

- [ ] **4.1 Statistical analysis**
  - Precision/recall on seeded data
  - Inter-model agreement metrics
  - Topic difficulty rankings

- [ ] **4.2 Paper writing**
  - Use literature review results
  - Document methodology
  - Present findings

---

## Files to Modify

| File                                   | Changes                                     |
| -------------------------------------- | ------------------------------------------- |
| `utils/misconception_analyzer.py`      | New topic taxonomy, misconception filtering |
| `prompts/direct_prompt.py`             | Better misconception vs mistake guidance    |
| `pydantic_models/models/evaluation.py` | Update `CANONICAL_TOPICS`                   |
| `cli.py`                               | Add visualization export command            |
| NEW: `utils/seeded_generator.py`       | Seeded submission generator                 |
| NEW: `utils/validation.py`             | Precision/recall calculation                |
| NEW: `utils/visualizations.py`         | Chart generation                            |

---
