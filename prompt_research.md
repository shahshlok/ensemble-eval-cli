# Prompt Engineering Research Brief

## Research Goal

Evaluate whether LLMs can **autonomously discover and categorize misconceptions** in CS1 student code without being given a predefined list. This is a research question, not an engineering task.

---

## Context

- **Course**: COSC 111 - Intro to Programming (Java)
- **Assignment**: Simple formula-based problems (acceleration, fuel cost, distance, Heron's formula)
- **Task**: Grade student submissions AND identify conceptual misconceptions
- **Key Constraint**: The LLM should NOT be given a list of known misconceptions - we want to measure its ability to independently discover them

---

## Current Prompt (Baseline)

```
You are an expert grader for a Computer Science assignment.

**Question:**
{question_text}

**Rubric:**
{rubric_json}

**Student Submission:**
```java
{student_code}
```

Evaluate the student's submission based on the provided rubric.
Provide a structured output containing:
1. Scores for each category in the rubric.
2. Specific feedback for each category.
3. Identification of any **misconceptions** (IMPORTANT - read carefully):

   A **misconception** is a fundamental misunderstanding of a concept, NOT a simple typo or syntax error.
   
   **REPORT these as misconceptions:**
   - Using `int` instead of `double` for decimal calculations
   - Using `^` instead of `Math.pow()` for exponentiation  
   - Wrong formula (e.g., `(v1 + v0) / t` instead of `(v1 - v0) / t`)
   - Not understanding integer division (`5/2` gives `2`, not `2.5`)
   - Incorrect operator precedence
   - Misinterpreting the problem requirements
   
   **DO NOT report these as misconceptions:**
   - Missing semicolons, brackets, or braces (syntax typo)
   - Misspelled variable names (typo)
   - Missing import statements (mechanical)
   - Formatting or whitespace issues
   
   For each misconception, specify:
   - **Topic**: One of: "Variables", "Data Types", "Constants", "Reading input from the keyboard", or "Other"
   - The task name from the rubric where it appears

4. Overall feedback.
```

### Problems with Current Prompt

1. **Leading the witness**: By listing examples of misconceptions, we bias the LLM toward those specific patterns
2. **Closed taxonomy**: Forcing topics into 5 categories may miss nuances
3. **Conflating grading with diagnosis**: Two different cognitive tasks in one prompt
4. **No reasoning trace**: LLM jumps directly to conclusions without showing work

---

## Research Questions

### Primary Questions

1. **Can LLMs discover misconceptions without explicit examples?**
   - If we remove the "REPORT these as misconceptions" list, what do they find?
   - Do they hallucinate misconceptions that don't exist?
   - Do they miss obvious misconceptions?

2. **What prompt structure maximizes misconception detection accuracy?**
   - Direct prompting vs. Chain-of-Thought
   - Single-pass vs. multi-pass analysis
   - Role-playing (expert grader vs. debugging assistant vs. tutor)

3. **How do different LLMs compare in misconception detection?**
   - GPT-4/5 vs. Gemini vs. Claude vs. open-source models
   - Do smaller models miss subtleties that larger ones catch?

4. **Does separating grading from misconception detection improve either task?**
   - Two separate prompts vs. one combined prompt
   - Sequential (grade first, then diagnose) vs. parallel

### Secondary Questions

5. What taxonomy of misconceptions do LLMs naturally produce?
6. How consistent are misconception labels across multiple runs?
7. Do LLMs distinguish severity levels without being asked?

---

## Proposed Prompt Architectures to Test

### Architecture A: Minimal Guidance (True Zero-Shot)

```
You are evaluating a CS1 student's Java code.

**Problem Statement:**
{question_text}

**Student Code:**
```java
{student_code}
```

**Rubric:**
{rubric_json}

For each rubric criterion, provide:
- Points awarded (0 to max)
- Justification

Then, identify any **conceptual misunderstandings** the student demonstrates. 
A conceptual misunderstanding is a flawed mental model about how programming works - 
not a typo or syntax error.

For each misunderstanding found:
- Describe what the student seems to believe
- Explain why this belief is incorrect
- Quote the specific code that demonstrates this
```

**Hypothesis**: Without examples, the LLM will identify misconceptions more organically but may be inconsistent in categorization.

---

### Architecture B: Socratic Chain-of-Thought

```
You are a teaching assistant helping a professor understand where a student went wrong.

**Problem:**
{question_text}

**Student's Code:**
```java
{student_code}
```

Let's analyze this step by step:

**STEP 1 - What should correct code do?**
Describe the expected behavior and the correct approach.

**STEP 2 - What does this student's code actually do?**
Trace through their code and describe its actual behavior.

**STEP 3 - Where do they diverge?**
Identify specific points where the student's approach differs from the correct one.

**STEP 4 - Why might the student have done this?**
For each divergence, hypothesize what misunderstanding led to this choice.
Distinguish between:
- Careless mistakes (typos, forgetting something)
- Conceptual errors (misunderstanding how something works)

**STEP 5 - Grade against rubric**
{rubric_json}
```

**Hypothesis**: Forcing explicit reasoning will surface misconceptions that direct prompting misses.

---

### Architecture C: Contrastive Analysis

```
**Problem:**
{question_text}

**Reference Solution:**
```java
{reference_solution}
```

**Student Submission:**
```java
{student_code}
```

Compare the student's solution to the reference.

For each difference you find:
1. Quote both versions
2. Classify the difference:
   - STYLE: Different but equally correct approach
   - TYPO: Obvious mistake, student likely knows better
   - MISCONCEPTION: Student appears to misunderstand something fundamental
3. If MISCONCEPTION, explain what the student seems to believe incorrectly

Then score against this rubric:
{rubric_json}
```

**Hypothesis**: Explicit comparison to correct code will anchor the LLM and reduce hallucinations.

---

### Architecture D: Persona-Based (Expert Debugger)

```
You are a senior software engineer conducting a code review of a junior developer's first program.

The task was:
{question_text}

Their code:
```java
{student_code}
```

Write a code review that:
1. Identifies what works correctly
2. Identifies bugs and errors
3. Most importantly: identifies any signs that the developer has a **flawed mental model** 
   about Java or programming concepts

A flawed mental model means they don't just have a bug - they fundamentally misunderstand 
how something works. For example:
- Thinking ^ does exponentiation (it's XOR)
- Thinking integer division produces decimals
- Thinking variable names affect computation

Be specific. Quote code. Explain the likely misconception.

Finally, score their work:
{rubric_json}
```

**Hypothesis**: The "code review" framing may elicit more nuanced analysis than "grading".

---

### Architecture E: Two-Stage Pipeline

**Stage 1: Pure Grading**
```
Grade this student submission against the rubric. 
Do not provide feedback or identify errors - just scores.

{question_text}
{student_code}
{rubric_json}

Output: scores only
```

**Stage 2: Misconception Analysis (separate call)**
```
A student wrote this code for the following problem:
{question_text}

```java
{student_code}
```

This code has some issues. Analyze what conceptual misunderstandings 
the student might have. Focus on fundamental misunderstandings about 
programming concepts, not surface-level mistakes.

For each misconception:
- What does the student seem to believe?
- What code demonstrates this?
- What is the correct understanding?
```

**Hypothesis**: Separating tasks may improve both grading accuracy and misconception detection.

---

## Evaluation Methodology

### Ground Truth

We have a **seeded dataset** (`authentic_seeded/`) where we know exactly which misconceptions were injected into each submission. The manifest maps:

```
Student_ID -> [list of injected misconception IDs]
```

### Metrics

| Metric | Definition |
|--------|------------|
| **Misconception Precision** | TP / (TP + FP) - Of misconceptions reported, how many are real? |
| **Misconception Recall** | TP / (TP + FN) - Of actual misconceptions, how many were found? |
| **Grading MAE** | Mean Absolute Error between LLM scores and ground truth |
| **Grading QWK** | Quadratic Weighted Kappa - agreement accounting for chance |
| **Consistency** | Agreement between multiple runs of same prompt |
| **Taxonomy Alignment** | How well do LLM categories match our catalog topics? |

### Experiment Design

1. **Baseline**: Current prompt on seeded dataset
2. **Treatment A-E**: Each architecture above on same dataset
3. **Ablations**: 
   - With/without rubric
   - With/without reference solution
   - With/without explicit misconception examples
4. **Model comparison**: Same prompt across Gemini, GPT, Claude

---

## Key Papers to Find

1. **Automated scoring with LLMs** - benchmarks, best practices
2. **Chain-of-thought for evaluation tasks** - does reasoning help?
3. **Misconception detection in CS education** - what's known about novice errors
4. **Rubric alignment** - how to ensure LLM follows grading criteria
5. **Multi-model ensembling** - how to aggregate disagreeing evaluations

### Specific Papers Identified

| Paper | Key Insight |
|-------|-------------|
| AutoSCORE (arXiv:2509.21910) | Two-agent system: extract rubric components, then score |
| StepGrade (arXiv:2503.20851) | Chain-of-thought improves grading interpretability |
| CoTAL (arXiv:2504.02323) | Human-in-the-loop prompt refinement, Evidence-Centered Design |
| CS1 Antipatterns (arXiv:2104.12542) | Catalog of 41 novice programming antipatterns |

---

## Questions for Deep Research Agent

1. **What prompt structures have been shown to improve LLM evaluation accuracy in educational contexts?**

2. **How do researchers measure misconception detection performance when ground truth is subjective?**

3. **Are there established taxonomies of CS1 misconceptions that we should align with?**

4. **What is the state of the art for LLM-based code analysis for pedagogical purposes?**

5. **How do multi-model ensembles handle disagreement in educational assessment?**

---

## Desired Outputs

1. **Recommended prompt architecture** with theoretical justification
2. **Evaluation protocol** for comparing prompt strategies
3. **Bibliography** of 10-15 most relevant papers
4. **Risk analysis** - what could go wrong with LLM misconception detection?
