# The Diagnostic Ceiling: Measuring LLM Alignment with Student Mental Models in Introductory Programming

## Abstract

Large Language Models (LLMs) are increasingly deployed for automated code feedback, yet their ability to diagnose *why* students make errors—not just *what* is wrong—remains unmeasured. We evaluate six LLMs across 300 synthetic student submissions containing 18 distinct misconceptions grounded in Notional Machine theory. Using semantic embedding alignment, we measure whether LLM explanations match the cognitive root cause of errors. Our results reveal a **Diagnostic Ceiling**: LLMs achieve 93-99% recall on structural misconceptions (e.g., API misuse, syntax errors) but only 16-65% on semantic misconceptions involving state reasoning and control flow logic. We further demonstrate that ensemble voting—requiring multiple models to agree—improves precision from 32% to 68% with negligible recall loss (F1: 0.469 → 0.763). These findings provide educators with a practical taxonomy: which misconceptions are safe for AI diagnosis versus those requiring human intervention.

**Keywords:** Large Language Models, Misconception Detection, Notional Machines, Automated Feedback, Computer Science Education

---

## 1. Introduction

The proliferation of Large Language Models (LLMs) in educational technology has raised fundamental questions about their pedagogical utility. While these systems excel at detecting syntactic errors and suggesting corrections, effective teaching requires understanding *why* a student made an error—the underlying mental model that produced the mistake.

Consider a student who writes `x = x + 1` expecting the variable to automatically update throughout their program, as cells do in a spreadsheet. A syntax checker sees correct code. A bug detector might flag unexpected output. But neither identifies the core issue: the student holds a **Reactive State Machine** mental model, treating variables as live bindings rather than storage locations. Without diagnosing this misconception, feedback remains superficial.

Computer Science Education research has long studied these mental models through the lens of **Notional Machines**—the abstract models students construct to understand program execution [1, 2]. Prior work has catalogued common misconceptions: the belief that `if` and `else if` are mutually exclusive by default, that integer division preserves decimals, or that strings are mutable. What remains unknown is whether LLMs can identify these specific mental models when analyzing student code.

This paper presents the first large-scale empirical evaluation of LLM alignment with Notional Machine theory. We make three contributions:

1. **Empirical evidence** that LLMs exhibit massive variance (16% to 99% recall) in misconception detection based on category type, not assignment complexity.

2. **A practical taxonomy** classifying misconceptions into those safe for AI diagnosis versus those requiring human oversight.

3. **A methodological contribution** demonstrating that ensemble voting improves diagnostic precision by 113% with minimal recall cost.

Our findings suggest that LLMs are not uniformly capable misconception detectors. Rather, they exhibit a **Diagnostic Ceiling**—performing well on structural errors visible in code patterns, but struggling with semantic errors requiring deeper state reasoning.

### Research Questions

- **RQ1:** How does LLM misconception detection vary across Notional Machine categories?
- **RQ2:** Which specific misconceptions fall below the Diagnostic Ceiling?
- **RQ3:** Can ensemble voting improve diagnostic precision without sacrificing recall?
- **RQ4:** Do elaborate prompting strategies outperform simple classification prompts?

---

## 2. Related Work

### 2.1 Automated Feedback in Programming Education

Automated grading and feedback systems have evolved from simple test-case checkers to sophisticated program analysis tools [3]. Keuning et al.'s systematic review identified that most systems focus on correctness verification rather than misconception diagnosis [4]. Recent work has explored using program repair techniques to generate feedback, but these approaches address *what* to fix rather than *why* the error occurred [5].

### 2.2 Notional Machines and Mental Models

The Notional Machine concept, introduced by du Boulay [1], describes the abstract machine that students must understand to reason about program execution. Sorva's comprehensive review [2] catalogued common misconceptions and their pedagogical implications. Key categories include:

- **State misconceptions:** Misunderstanding how variables store and update values
- **Control flow misconceptions:** Incorrect models of loop and conditional execution
- **Type misconceptions:** Confusion about type conversion and representation

Our work operationalizes these theoretical categories as measurable detection targets.

### 2.3 LLMs in Computer Science Education

Recent studies have examined LLM capabilities in educational contexts, including code explanation [6], hint generation [7], and assessment [8]. However, these studies typically evaluate output quality rather than alignment with established learning theory. Our work bridges this gap by measuring whether LLM diagnoses correspond to specific Notional Machine failures.

---

## 3. Methodology

### 3.1 Dataset Construction

We generated 300 synthetic student submissions across three introductory Java assignments:
- **A1 (Variables/Math):** Basic arithmetic, variable assignment, method calls
- **A2 (Loops/Control):** Iteration, conditionals, accumulation patterns
- **A3 (Arrays/Strings):** Array indexing, string manipulation, object references

For each assignment, we defined misconceptions grounded in Notional Machine theory (Table 1). Each misconception entry includes:
- A unique identifier (e.g., `NM_STATE_01`)
- The Notional Machine category
- A description of the student's incorrect mental model
- Instructions for generating code exhibiting the misconception

**Table 1: Misconception Categories and Examples**

| Category | Example Misconception | Description |
|----------|----------------------|-------------|
| Reactive State Machine | Spreadsheet View | Variables auto-update like spreadsheet cells |
| Independent Switch | Dangling Else | Indentation determines else binding |
| Fluid Type Machine | Integer Division Blindness | Division always produces decimals |
| Teleological Control | Off-by-One Intent | Loop runs "about" the right number of times |
| Human Index Machine | 1-Based Offset | Arrays start at index 1 |
| Mutable String Machine | String Identity Trap | Strings can be modified in place |
| Void Machine | Void Assumption | Methods modify state without return values |

Using GPT-4, we generated student code with exactly one injected misconception per file. This ensures clean labeling: each submission has a single ground truth mental model. Approximately 75% of generated files were "clean" (no misconception) to evaluate false positive rates.

### 3.2 Detection Instrument

We evaluated six LLMs:
- GPT-5.2 (standard and reasoning modes)
- Claude Haiku 4.5 (standard and reasoning modes)
- Gemini 3 Flash Preview (standard and reasoning modes)

Each model analyzed student code using four prompting strategies:

1. **Baseline:** Simple error classification without theoretical framing
2. **Taxonomy:** Explicit list of Notional Machine categories provided
3. **Chain-of-Thought:** Line-by-line execution tracing
4. **Socratic:** Mental model probing through guided questions

Models output structured JSON containing:
- `inferred_category_name`: The detected misconception category
- `student_thought_process`: Narrative explanation of the student's reasoning
- `evidence`: Line numbers supporting the diagnosis

### 3.3 Semantic Alignment Measurement

A key challenge is that LLMs may use different terminology than our ground truth. A model might describe "the student thinks variables update automatically" while our label is "Reactive State Machine." To address this, we employ semantic embedding alignment:

1. Embed the LLM's `student_thought_process` using OpenAI's `text-embedding-3-large`
2. Embed the ground truth `explanation` and `student_thinking` fields
3. Compute cosine similarity between embeddings
4. Threshold: similarity ≥ 0.65 indicates a True Positive

We validated this threshold by examining the score distribution separation between true and false positives (Cliff's Delta = 0.840, large effect).

### 3.4 Ensemble Voting

To reduce false positives, we implemented two ensemble approaches:

- **Strategy Ensemble:** Detection counts only if ≥2 of 4 prompting strategies agree
- **Model Ensemble:** Detection counts only if ≥2 of 6 models agree

Ensemble voting trades recall for precision by requiring consensus.

### 3.5 Statistical Analysis

We employ:
- **Bootstrap confidence intervals** (1000 resamples) for precision, recall, and F1
- **McNemar's test** for paired strategy comparison
- **Cochran's Q test** for omnibus comparison across strategies
- **Cliff's Delta** for effect size measurement

---

## 4. Results

### 4.1 Overall Performance

Across 22,003 detection instances (300 students × 4 questions × 6 models × 4 strategies, minus filtered noise), we observed:

| Metric | Value | 95% CI |
|--------|-------|--------|
| True Positives | 6,745 | — |
| False Positives | 14,236 | — |
| False Negatives | 1,022 | — |
| Precision | 0.322 | [0.315, 0.328] |
| Recall | 0.868 | [0.861, 0.876] |
| F1 Score | 0.469 | [0.462, 0.476] |

The high recall (87%) indicates LLMs frequently detect *something* wrong. The low precision (32%) reveals they often diagnose the wrong misconception or hallucinate errors in correct code.

### 4.2 RQ1: Category-Level Variance

**Finding: Detection difficulty varies by misconception category, not assignment complexity.**

Contrary to our initial hypothesis that more complex assignments would be harder to diagnose, we found that misconception *category* determines detection success (Table 2).

**Table 2: Detection Recall by Notional Machine Category**

| Category | Recall | N | Classification |
|----------|--------|---|----------------|
| The Void Machine | 0.994 | 175 | Easy |
| The Mutable String Machine | 0.990 | 716 | Easy |
| The Human Index Machine | 0.973 | 841 | Easy |
| The Algebraic Syntax Machine | 0.972 | 457 | Easy |
| The Semantic Bond Machine | 0.954 | 965 | Easy |
| The Teleological Control Machine | 0.931 | 2,240 | Easy |
| The Anthropomorphic I/O Machine | 0.881 | 514 | Easy |
| The Reactive State Machine | 0.654 | 312 | Medium |
| The Independent Switch | 0.625 | 664 | Medium |
| The Fluid Type Machine | 0.590 | 883 | Medium |

The pattern reveals a **structural vs. semantic dichotomy**:
- **Structural misconceptions** (API misuse, syntax confusion, type errors): 88-99% recall
- **Semantic misconceptions** (state reasoning, control flow logic): 59-65% recall

### 4.3 RQ2: The Diagnostic Ceiling

**Finding: Three specific misconceptions fall dramatically below the diagnostic ceiling.**

**Table 3: Per-Misconception Detection Rates**

| Misconception | Category | Recall | Implication |
|---------------|----------|--------|-------------|
| Dangling Else | Independent Switch | **0.16** | Human review required |
| Narrowing Cast | Fluid Type Machine | **0.31** | Human review required |
| Spreadsheet View | Reactive State Machine | **0.65** | Borderline |
| ... | ... | ... | ... |
| String Immutability | Mutable String Machine | 0.99 | Safe for automation |
| Void Assumption | Void Machine | 0.99 | Safe for automation |

The **Dangling Else** misconception—where students believe indentation determines `else` binding—is detected only 16% of the time. This misconception requires understanding that the student's *intent* (visible in indentation) differs from the *actual* binding (determined by syntax). LLMs see syntactically valid code and miss the cognitive mismatch.

Similarly, **Narrowing Cast** errors (31% recall) involve implicit type conversion where students expect decimal preservation. The code executes without error; the misconception lies in the student's expectation.

### 4.4 RQ3: Ensemble Voting Effectiveness

**Finding: Model ensemble voting doubles precision with negligible recall loss.**

**Table 4: Ensemble Comparison**

| Method | Precision | Recall | F1 | Precision Gain |
|--------|-----------|--------|-----|----------------|
| Raw (No Ensemble) | 0.321 | 0.868 | 0.469 | — |
| Strategy Ensemble (≥2/4) | 0.640 | 0.868 | 0.737 | +99% |
| Model Ensemble (≥2/6) | **0.684** | 0.862 | **0.763** | +113% |

Model ensemble achieves the best balance: precision improves from 32% to 68% (a 113% relative gain) while recall drops only 0.6 percentage points. This suggests that when multiple models agree on a misconception diagnosis, the diagnosis is likely correct.

The F1 improvement from 0.469 to 0.763 represents a **63% relative gain**, making ensemble voting a practical deployment strategy.

### 4.5 RQ4: Prompting Strategy Comparison

**Finding: Simple prompts outperform elaborate strategies.**

**Table 5: Performance by Prompting Strategy**

| Strategy | Precision | Recall | F1 |
|----------|-----------|--------|-----|
| Baseline | 0.373 | 0.850 | **0.519** |
| Taxonomy | 0.366 | 0.890 | 0.518 |
| Chain-of-Thought | 0.345 | 0.841 | 0.489 |
| Socratic | 0.251 | 0.890 | 0.391 |

McNemar's test confirms significant differences:
- Baseline vs. CoT: χ² = 23.58, p < 0.0001 (Baseline wins)
- Baseline vs. Socratic: χ² = 0.07, p = 0.79 (No significant difference)

Cochran's Q test (Q = 57.59, p < 0.000001) confirms significant variation across strategies.

The **Socratic strategy** performs worst despite its pedagogical grounding. We hypothesize that asking models to probe mental models causes them to over-generate possibilities, increasing false positives.

---

## 5. Discussion

### 5.1 Implications for AI-Assisted Grading

Our results suggest a practical deployment model for LLM-based misconception detection:

1. **Automate with confidence** for structural misconceptions (Void Machine, Mutable String, Human Index): >97% recall means human review is rarely needed.

2. **Flag for human review** for semantic misconceptions below the Diagnostic Ceiling (Dangling Else, Narrowing Cast): 16-31% recall means most cases will be missed.

3. **Use ensemble voting** when precision matters: Model ensemble reduces false positives by 67% relative to single-model diagnosis.

### 5.2 Why Semantic Misconceptions Are Hard

The Diagnostic Ceiling appears where misconceptions require reasoning about *student intent* rather than *code behavior*. Consider the Dangling Else:

```java
if (x > 0)
    if (y > 0)
        print("both positive");
else
    print("x not positive");  // Student intent: bind to outer if
```

This code is syntactically valid. The `else` binds to the inner `if` per Java rules, but the student's indentation reveals they expected outer binding. Detecting this requires the LLM to:
1. Recognize the indentation pattern as intentional
2. Infer the student's expected behavior
3. Compare expected vs. actual semantics

Current LLMs appear to lack this "theory of mind" for code—they analyze what the code *does*, not what the student *thought* it would do.

### 5.3 The Case Against Elaborate Prompting

Our finding that Baseline outperforms Chain-of-Thought contradicts common prompting wisdom. We hypothesize two explanations:

1. **Task mismatch:** CoT is designed for reasoning problems with verifiable solutions. Misconception diagnosis requires empathy with incorrect reasoning—a different cognitive task.

2. **Hallucination amplification:** Longer reasoning chains provide more opportunities for the model to invent plausible-sounding but incorrect diagnoses.

### 5.4 Threats to Validity

**Internal validity:** Our semantic threshold (0.65) was not validated against human judgment. However, the large effect size in score separation (Cliff's Delta = 0.840) suggests the threshold meaningfully discriminates true from false positives.

**External validity:** Students were synthetically generated. While this ensures clean labeling, real student errors may exhibit different patterns. Future work should validate on authentic submissions.

**Construct validity:** We operationalize "understanding" as semantic similarity between LLM explanation and ground truth. This may miss cases where the LLM correctly identifies the misconception using novel terminology.

---

## 6. Limitations and Future Work

1. **Synthetic data:** Our students are LLM-generated. While common in software engineering research, this limits ecological validity. We plan to validate findings on a corpus of real student submissions from introductory courses.

2. **Single language:** All assignments use Java. Mental model findings likely generalize, but syntax-specific misconceptions (e.g., Dangling Else) may not transfer to Python's significant whitespace.

3. **Misconception coverage:** We evaluated 18 misconceptions across 10 Notional Machine categories. A comprehensive evaluation would include additional categories (e.g., recursion, object-oriented misconceptions).

4. **Threshold sensitivity:** Future work should conduct human annotation studies to validate the 0.65 semantic similarity threshold.

---

## 7. Conclusion

This paper presents the first empirical measurement of LLM alignment with Notional Machine theory in computer science education. Our evaluation of six models across 300 students and 18 misconceptions reveals a **Diagnostic Ceiling**: LLMs achieve near-perfect recall on structural misconceptions but struggle with semantic errors requiring reasoning about student intent.

We provide educators with actionable guidance: use LLMs confidently for detecting API misuse and syntax confusion, but maintain human oversight for state reasoning and control flow logic errors. Ensemble voting offers a practical middle ground, doubling precision with minimal recall cost.

As LLMs become ubiquitous in educational technology, understanding their limitations is as important as celebrating their capabilities. The Diagnostic Ceiling is not a failure of current models—it is a map of where human teachers remain essential.

---

## References

[1] du Boulay, B. (1986). Some difficulties of learning to program. *Journal of Educational Computing Research*, 2(1), 57-73.

[2] Sorva, J. (2013). Notional machines and introductory programming education. *ACM Transactions on Computing Education*, 13(2), 1-31.

[3] Ihantola, P., Ahoniemi, T., Karavirta, V., & Seppälä, O. (2010). Review of recent systems for automatic assessment of programming assignments. *Proceedings of Koli Calling*, 86-93.

[4] Keuning, H., Jeuring, J., & Heeren, B. (2018). A systematic literature review of automated feedback generation for programming exercises. *ACM Transactions on Computing Education*, 19(1), 1-43.

[5] Gulwani, S., Radiček, I., & Zuleger, F. (2018). Automated clustering and program repair for introductory programming assignments. *ACM SIGPLAN Notices*, 53(4), 465-480.

[6] MacNeil, S., Tran, A., Mogil, D., Bernstein, S., Ross, E., & Huang, Z. (2023). Experiences from using code explanations generated by large language models in a web software development e-book. *Proceedings of SIGCSE*, 931-937.

[7] Phung, T., Pădurean, V., Cambronero, J., Gulwani, S., Kohn, T., Ramanujan, R., & Singla, A. (2023). Generating high-precision feedback for programming syntax errors using large language models. *Proceedings of EDM*.

[8] Savelka, J., Agarwal, A., An, M., Bober, C., & Sakr, M. (2023). Thrilled by your progress! Large language models (GPT-4) no longer struggle to pass assessments in higher education programming courses. *Proceedings of ACE*, 78-87.
