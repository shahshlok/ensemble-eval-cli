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

| Decision | Choice |
|----------|--------|
| Language | Java only |
| Assignment | data/a2 (Q1-Q4) |
| Data | Synthetic submissions (no ethics approval for real data) |
| Ensemble voting | Deferred (focus on misconceptions) |
| Bloom's taxonomy | Deferred (see future.txt) |
| Target audience | Instructors, TAs, researchers |

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

| REAL Misconception (Detect) | NOT a Misconception (Filter Out) |
|-----------------------------|----------------------------------|
| Using `int` instead of `double` for decimals | Missing semicolon |
| Using `^` instead of `Math.pow()` | Misspelled variable name |
| Wrong formula: `(v1 + v0) / t` vs `(v1 - v0) / t` | Extra whitespace |
| Integer division confusion (`5/2 = 2`) | Missing import statement |
| Not closing Scanner (resource leak) | Formatting issues |
| Wrong operator precedence in expressions | Typos in string literals |

**Key distinction:** Misconception = misunderstanding of a concept. Mistake = mechanical error.

---

## Topic Taxonomy

**Original course topics from Assignment 2 rubric** (kept pure and clean):

| Topic | Description | Examples |
|-------|-------------|----------|
| **Variables** | Declaring, assigning, using in expressions | Operator precedence |
| **Data Types** | Choosing appropriate types | int vs double, type casting, integer division |
| **Constants** | Math library usage | Math.pow, Math.sqrt |
| **Reading input from the keyboard** | Scanner usage | Scanner methods, prompts, parsing |

**Catch-all category:**
| **Other** | Doesn't fit course topics | Problem understanding, formula application, output formatting |

**Filtered out (not real misconceptions):**
| **Syntax** | Mechanical errors | Missing semicolons, typos, missing imports |

---

## 8-Week Timeline

| Week | Focus | Deliverables |
|------|-------|--------------|
| **1** (Dec 1-7) | Tool improvements | Fix topic taxonomy, add misconception filtering |
| **2** (Dec 8-14) | Seeded validation | Misconception catalog, seeded submission generator |
| **3** (Dec 15-21) | Experiments | Run detection on seeded data, measure precision/recall |
| **4** (Dec 22-28) | Visualizations | Heatmaps, progression charts, model agreement |
| **5** (Dec 29-Jan 4) | Analysis | Statistical analysis, write results section |
| **6** (Jan 5-11) | Paper draft | Introduction, methodology, results |
| **7** (Jan 12-18) | Paper draft | Related work (from lit review), discussion |
| **8** (Jan 19-25) | Polish | Revisions, formatting, submission |

---

## Implementation Tasks (Priority Order)

### Phase 1: Core Improvements (Week 1) - COMPLETED

- [x] **1.1 Keep original topic taxonomy**
  - Kept original 4 course topics: Variables, Data Types, Constants, Reading input from the keyboard
  - These align with the actual learning objectives from the assignment rubric

- [x] **1.2 Add misconception filtering**
  - Created `is_syntax_error()` function to detect and filter mechanical errors
  - Filters: missing semicolons, typos, misspellings, missing imports, formatting
  - Result: 7 syntax errors filtered, 49 real misconceptions remain

- [x] **1.3 Improve prompt for misconception quality**
  - Updated `direct_prompt.py` and `grading.py` with clear examples
  - REPORT: wrong data types, wrong formulas, operator precedence issues
  - DO NOT REPORT: semicolons, typos, imports, formatting

### Phase 2: Validation Framework (Week 2)

- [ ] **2.1 Create misconception catalog**
  - Define 10-15 common misconceptions for A2
  - Document expected behavior for each

- [ ] **2.2 Build seeded submission generator**
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

| File | Changes |
|------|---------|
| `utils/misconception_analyzer.py` | New topic taxonomy, misconception filtering |
| `prompts/direct_prompt.py` | Better misconception vs mistake guidance |
| `pydantic_models/models/evaluation.py` | Update `CANONICAL_TOPICS` |
| `cli.py` | Add visualization export command |
| NEW: `utils/seeded_generator.py` | Seeded submission generator |
| NEW: `utils/validation.py` | Precision/recall calculation |
| NEW: `utils/visualizations.py` | Chart generation |

---

## Next Immediate Action

While literature review runs, start **Phase 1: Core Improvements**:
1. Fix the topic taxonomy
2. Add misconception filtering
3. Improve the grading prompt

Ready to begin implementation?
