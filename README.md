# LLM Notional Machine Misconception Detection Framework

A research framework for detecting notional machine misconceptions in CS1 student code using Large Language Models. Part of a Bachelor's Honours Thesis at UBCO investigating cognitive alignment between LLMs and CS education theory.

**Status:** Analysis 3 Complete | Ensemble Voting Implemented | Publication-Ready

## Research Goal

> Can LLMs detect **Notional Machine** misconceptions—flawed mental models about how code executes?

This is **not** a grading tool. It measures the **Cognitive Alignment** of LLMs with CS Education theory.

**Key Finding:** LLMs show a **30% performance gap** between concrete errors (arrays: F1=0.890) and abstract mental models (variables: F1=0.592). This gap represents a fundamental limitation in LLM reasoning about student mental models.

## The 4-Stage Pipeline (Analysis 3)

```
┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│ 1. SYNTHETIC         │   │ 2. BLIND             │   │ 3. SEMANTIC          │   │ 4. ENSEMBLE          │
│    INJECTION         │──▶│    DETECTION         │──▶│    ALIGNMENT         │──▶│    VOTING ⭐         │
│                      │   │                      │   │                      │   │                      │
│ 10 misconceptions    │   │ 6 LLMs ×             │   │ Semantic embeddings  │   │ Majority consensus   │
│ × 120 students       │   │ 4 prompt strategies  │   │ Cosine similarity    │   │ ≥2 strategies agree  │
│ × 3 questions        │   │ → 8,640 detections   │   │ → single-strategy    │   │ → final metrics      │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘   └──────────────────────┘
       ↓                           ↓                           ↓                           ↓
    360 files              detections/{a1,a2,a3}/      Precision: 0.313       Precision: 0.649 ✅
 with known bugs         {strategy}/*.json             Recall: 0.872          Recall: 0.871
                                                       F1: 0.461              F1: 0.744 ✅
```

## Quick Start

### 1. Setup

```bash
# Clone and install
git clone https://github.com/shahshlok/ensemble-eval-cli
cd ensemble-eval-cli
uv sync

# Configure API keys (required)
export OPENROUTER_API_KEY="sk-or-..."    # For LLM detection
export OPENAI_API_KEY="sk-..."           # For semantic embeddings
```

### 2. Run Ensemble Analysis (Recommended)

```bash
# Analyze all assignments with ensemble voting (≥2 strategies must agree)
uv run python analyze.py analyze-ensemble \
  --run-name my-analysis \
  --ensemble-threshold 2 \
  --semantic-threshold 0.65

# Results saved to: runs/multi/run_my-analysis/
```

### 3. View Results

```bash
# Read the markdown report
cat runs/multi/run_my-analysis/report.md

# Or parse JSON metrics
python -m json.tool runs/multi/run_my-analysis/metrics.json | head -50
```

### Complete Workflow

```bash
# 1. Generate synthetic students with known misconceptions
uv run python utils/generators/dataset_generator.py

# 2. Run LLM detection with all 4 strategies
uv run python analyze.py detect \
  --assignment multi \
  --strategy baseline,taxonomy,cot,socratic

# 3. Run ensemble analysis (recommended)
uv run python analyze.py analyze-ensemble \
  --run-name final-results \
  --ensemble-threshold 2

# 4. Compare against previous runs
ls -lh runs/multi/
```

## Documentation

| Document | Purpose |
|----------|---------|
| [Architecture](docs/architecture.md) | System design overview with 4-stage pipeline diagram |
| [Analysis Pipeline](docs/analysis-pipeline.md) | Complete data flow from injection through ensemble voting |
| [CLI Reference](docs/cli-reference.md) | Commands, options, and output files |
| [Metrics Guide](docs/metrics-guide.md) | Precision, recall, F1, and confidence intervals |
| [Matching & Semantics](docs/matching.md) | Semantic embedding methodology and ensemble consensus |
| [Notional Machines](docs/notional-machines.md) | All 10 misconception categories with examples |
| [Complexity Gradient](docs/complexity-gradient.md) | Why A1 (59%) is harder than A3 (89%) |
| [Development](docs/development.md) | Extending the framework with new misconceptions |
| [AGENTS.md](AGENTS.md) | AI agent guidance for future development |

### Where to Start

1. **For understanding the research:** Read [Architecture](docs/architecture.md)
2. **For running analyses:** Follow [CLI Reference](docs/cli-reference.md)
3. **For interpreting results:** See [Metrics Guide](docs/metrics-guide.md)
4. **For the core finding:** Read [Complexity Gradient](docs/complexity-gradient.md)

## Project Structure

```
ensemble-eval-cli/
├── data/                       # Ground truth misconception definitions
│   ├── a1/groundtruth.json     # 8 categories (Variables & Math)
│   ├── a2/groundtruth.json     # 6 categories (Loops & Control)
│   ├── a3/groundtruth.json     # 5 categories (Arrays & Strings)
│   └── a{1,2,3}/q{1,2,3}.md    # Question prompts
│
├── authentic_seeded/           # Generated student code with known bugs
│   ├── a1/manifest.json        # Student→misconception mapping
│   ├── a1/{Student_Name}/      # Java files (120 students × 3 questions)
│   ├── a2/...
│   └── a3/...
│
├── detections/                 # LLM detection outputs
│   ├── a1_multi/{strategy}/    # baseline, taxonomy, cot, socratic
│   ├── a2_multi/{strategy}/    # 360 files × 4 strategies × 6 models
│   └── a3_multi/{strategy}/
│
├── runs/multi/                 # Analysis results
│   ├── run_analysis2.2/        # Single-strategy results
│   ├── run_analysis3/          # Ensemble voting results (FINAL) ⭐
│   └── run_{id}/               # Custom runs
│       ├── report.md           # Markdown report with charts
│       ├── metrics.json        # Precision, recall, F1, etc.
│       ├── data.json           # Detailed per-file results
│       ├── results.csv         # Tabular results
│       ├── compliance.csv      # TP/FP/FN classification
│       └── assets/*.png        # 8 visualization charts
│
├── prompts/
│   └── strategies.py           # 4 prompt strategies
│
├── pydantic_models/            # Data validation schemas
│   ├── evaluation.py           # DetectionResult, MatchResult
│   ├── submission/             # Student submission models
│   └── context/                # Context models
│
├── utils/
│   ├── generators/             # Synthetic data generation
│   │   └── dataset_generator.py
│   ├── llm/                    # LLM API clients
│   │   ├── openai.py
│   │   ├── anthropic.py
│   │   └── gemini.py
│   ├── matching/               # Semantic matching
│   │   ├── semantic.py         # Embedding-based matching
│   │   ├── fuzzy.py            # String similarity
│   │   └── hybrid.py           # Combined approach
│   ├── execution.py            # Code execution sandbox
│   ├── grading.py              # Metric computation
│   └── statistics.py           # Bootstrap CI, significance tests
│
├── docs/                       # Comprehensive documentation
│   ├── architecture.md         # System design
│   ├── analysis-pipeline.md    # 4-stage pipeline details
│   ├── cli-reference.md        # Command reference
│   ├── metrics-guide.md        # Metrics explained
│   ├── matching.md             # Semantic alignment
│   ├── notional-machines.md    # Taxonomy with examples
│   ├── complexity-gradient.md  # Core finding
│   └── development.md          # Extension guide
│
├── tests/
│   ├── test_llm_miscons2.py   # Detection tests
│   ├── test_anthropic_client.py
│   ├── test_openai_client.py
│   └── test_gemini_client.py
│
├── analyze.py                  # Main analysis CLI ⭐
├── pipeline.py                 # Full pipeline orchestrator
├── pyproject.toml              # Dependencies
├── AGENTS.md                   # AI agent guidance
└── README.md                   # This file
```

## The 10 Notional Machine Categories

### A1: Scalar State (Variables & Math) — 8 categories

| ID | Category | Example |
|---|---|---|
| **NM_STATE_01** | Reactive State Machine | Spreadsheet View (Early Calculation) |
| **NM_IO_01** | Anthropomorphic I/O | Prompt-Logic Mismatch |
| **NM_IO_02** | Anthropomorphic I/O | The Ghost Read |
| **NM_TYP_01** | Fluid Type Machine | Integer Division Blindness |
| **NM_TYP_02** | Fluid Type Machine | Narrowing Cast in Division |
| **NM_SYN_01** | Algebraic Syntax Machine | XOR as Power |
| **NM_SYN_02** | Algebraic Syntax Machine | Precedence Blindness |
| **NM_API_01** | Void Machine | The Void Assumption |

### A2: Temporal State (Loops & Control) — 6 categories

| ID | Category | Example |
|---|---|---|
| **NM_FLOW_01** | Teleological Flow | Accumulator Amnesia (Scope Error) |
| **NM_FLOW_02** | Teleological Flow | The Intent Loop (Off-by-One) |
| **NM_FLOW_03** | Teleological Flow | Infinite Loop (State Stagnation) |
| **NM_FLOW_04** | Teleological Flow | Sabotaging the Future (Inner Loop Mod) |
| **NM_LOGIC_01** | Logical Reasoning | Mutually Exclusive Fallacy |
| **NM_LOGIC_02** | Logical Reasoning | Dangling Else (Indentation Trap) |

### A3: Spatial State (Arrays & Strings) — 5 categories

| ID | Category | Example |
|---|---|---|
| **NM_MEM_01** | Spatial Adjacency | Parallel Array Desync |
| **NM_MEM_02** | Spatial Adjacency | Index-Value Confusion |
| **NM_MEM_03** | Spatial Adjacency | String Identity Trap (Immutability) |
| **NM_MEM_04** | Spatial Adjacency | The 1-Based Offset (OOB) |
| **NM_MEM_05** | Spatial Adjacency | Lossy Swap (Data Destruction) |

See [Notional Machines](docs/notional-machines.md) for detailed examples and code samples.

---

## Detailed Misconception Reference

### A1: Scalar State (Variables & Math) — 8 Misconceptions

---

#### **NM_STATE_01: Spreadsheet View (Early Calculation)**

**Category:** The Reactive State Machine  
**Cognitive Issue:** Variables are treated like Excel cells that update automatically

**Student Belief:**
> "I'll define formulas at the top of my code using variables. When I later assign values to those variables, the formula will automatically recalculate."

**Reality:**
- Variables hold fixed values at the point of assignment
- Assignments are **one-time operations**, not continuous bindings
- Later changes to operands do NOT update dependent calculations

**Code Example (Buggy):**
```java
double v0 = 0, v1 = 0, t = 0;
double a = (v1 - v0) / t;  // Calculates 0/0 = NaN or 0 ❌
v0 = scan.nextDouble();    // Read AFTER calculation
v1 = scan.nextDouble();
t = scan.nextDouble();
System.out.println(a);     // Prints NaN (stale computation)
```

**Correct Approach:**
```java
double v0 = scan.nextDouble();
double v1 = scan.nextDouble();
double t = scan.nextDouble();
double a = (v1 - v0) / t;  // Calculate AFTER reading ✅
System.out.println(a);
```

**Why LLMs Struggle:** This requires understanding that students think in **continuous reactive terms** (like spreadsheets) rather than **sequential imperative execution**. Without output traces, the error is invisible.

**LLM Detection Difficulty:** Hard (requires inferring invisible mental model about variable semantics)

---

#### **NM_IO_01: Prompt-Logic Mismatch**

**Category:** The Anthropomorphic I/O Machine  
**Cognitive Issue:** Computer magically knows which input corresponds to which variable

**Student Belief:**
> "When I print a prompt like 'Enter v0, then v1', the computer reads my English instructions and knows to assign the first input to v0 and the second to v1."

**Reality:**
- The scanner reads input in **execution order**, not prompt order
- Prompt text is only for **human communication**
- Variable assignment follows code sequence, not logical intent

**Code Example (Buggy):**
```java
System.out.println("Enter v0, then v1:");
double v1 = scan.nextDouble();  // Wrong order! ❌
double v0 = scan.nextDouble();

// User types: 10 20
// Expected: v0=10, v1=20
// Actual: v0=20, v1=10 (reversed!)
```

**Correct Approach:**
```java
System.out.println("Enter v0, then v1:");
double v0 = scan.nextDouble();  // Read in correct order ✅
double v1 = scan.nextDouble();
```

**Why LLMs Struggle:** This requires recognizing that the student has **anthropomorphized** the computer (believing it reads English). LLMs must infer what the student *thinks* vs what the code *does*.

**LLM Detection Difficulty:** Medium (visible in code structure, but requires intent inference)

---

#### **NM_IO_02: The Ghost Read**

**Category:** The Anthropomorphic I/O Machine  
**Cognitive Issue:** Print statements trigger input reading

**Student Belief:**
> "When I call `System.out.println()`, it reads input from the user."

**Reality:**
- `println()` only **outputs** to console
- `scan.nextDouble()` is what **reads** from user input
- Calling a method without assignment doesn't change variables

**Code Example (Buggy):**
```java
int x = 0;
System.out.println("Enter x: " + x);  // Outputs x's current value
// Student expects this to READ input and update x ❌
System.out.println(x);  // Prints 0 (unchanged)

// Correct:
System.out.println("Enter x:");
x = scan.nextDouble();  // Explicitly READ and ASSIGN ✅
```

**Why LLMs Struggle:** Requires understanding the confusion between **output methods** and **input methods**. The student conflates user interaction with variable modification.

**LLM Detection Difficulty:** Medium (visible in code, but requires understanding method semantics)

---

#### **NM_TYP_01: Integer Division Blindness**

**Category:** The Fluid Type Machine  
**Cognitive Issue:** Type of result container determines type of computation

**Student Belief:**
> "If I store the result in a `double` variable, Java will automatically include decimals in the calculation."

**Reality:**
- `5 / 2` computes as **integers first** (→ 2), then converts to 2.0
- At least **one operand must be double** for floating-point division
- Type casting on assignment is **too late**; division already happened

**Code Example (Buggy):**
```java
int a = 5, b = 2;
double result = a / b;  // result = 2.0, not 2.5! ❌
```

**Correct Approaches:**
```java
// Option 1: Cast before division
double result = (double) a / b;  // 5.0 / 2 = 2.5 ✅

// Option 2: Use double literals
double result = a / 2.0;  // 5 / 2.0 = 2.5 ✅

// Option 3: Convert to double first
double result = ((double) a) / b;  // Explicit conversion ✅
```

**Why LLMs Struggle:** Requires understanding Java's **type promotion and operator precedence**. Students often think types work like containers that magically convert the result.

**LLM Detection Difficulty:** Hard (requires understanding type system semantics)

---

#### **NM_TYP_02: Narrowing Cast in Division**

**Category:** The Fluid Type Machine  
**Cognitive Issue:** Casting after operation prevents information loss

**Student Belief:**
> "If I cast to `int` after division, it will preserve decimals in the result."

**Reality:**
- Casting happens **after** division completes
- `(int)` cast **truncates** (doesn't preserve) decimals
- `5 / 2 = 2` first, then `(int) 2 = 2` (no change)

**Code Example (Buggy):**
```java
int a = 5, b = 2;
int result = (int) (a / b);  // result = 2, not 2 (truncated to 2) ❌
```

**Correct Approach:**
```java
double result = (double) a / b;  // result = 2.5 ✅
```

**Why LLMs Struggle:** Requires understanding that casting is a **type conversion**, not a **precision recovery** operation. Students think cast changes the computation, not just the type.

**LLM Detection Difficulty:** Hard (requires understanding casting semantics)

---

#### **NM_SYN_01: XOR as Power**

**Category:** The Algebraic Syntax Machine  
**Cognitive Issue:** Mathematical notation directly translates to code

**Student Belief:**
> "The `^` operator means exponentiation, like it does in algebra and mathematics."

**Reality:**
- `^` is the **bitwise XOR** (exclusive OR) operator in Java
- Exponentiation uses `Math.pow(base, exponent)`
- `2 ^ 3 = 1` (bitwise), not `8`

**Code Example (Buggy):**
```java
double result = 2 ^ 3;  // result = 1 (bitwise XOR), not 8! ❌
// Binary: 010 XOR 011 = 001 = 1
```

**Correct Approach:**
```java
double result = Math.pow(2, 3);  // result = 8.0 ✅
// Or: 2 * 2 * 2 = 8
```

**Why LLMs Struggle:** Requires recognizing that student is applying **mathematical intuition** rather than understanding **programming syntax**. The symbol `^` has different meaning in different contexts.

**LLM Detection Difficulty:** Medium-Easy (visible syntax error, but requires domain knowledge)

---

#### **NM_SYN_02: Precedence Blindness**

**Category:** The Algebraic Syntax Machine  
**Cognitive Issue:** Arithmetic follows left-to-right evaluation or student's intended grouping

**Student Belief:**
> "Operators evaluate left-to-right, or my intended grouping is what matters."

**Reality:**
- Java follows **standard mathematical operator precedence**
- Division and multiplication have **higher precedence** than addition/subtraction
- Parentheses are required to override precedence

**Code Example (Buggy):**
```java
double result = 10 - 5 / 2;
// Student expects: (10 - 5) / 2 = 2.5
// Actually computes: 10 - (5 / 2) = 10 - 2 = 8 ❌
```

**Correct Approach:**
```java
double result = (10 - 5) / 2;  // Use parentheses ✅
// Now: (10 - 5) / 2 = 5 / 2 = 2.5
```

**Why LLMs Struggle:** Requires understanding that students apply **left-to-right evaluation** (intuitive) instead of **mathematical precedence** (actual). The error is in reasoning about order of operations.

**LLM Detection Difficulty:** Medium (visible in code, but requires understanding precedence rules)

---

#### **NM_API_01: The Void Assumption**

**Category:** The Void Machine  
**Cognitive Issue:** Calling a method modifies the variable in place

**Student Belief:**
> "When I call `Math.sqrt(x)`, it modifies `x` to be its square root. The method does the work in place."

**Reality:**
- Methods that return values **do not modify** their arguments
- You must **assign** the return value: `x = Math.sqrt(x)`
- `void` methods might modify objects, but primitive values cannot be modified in place (Java passes by value)

**Code Example (Buggy):**
```java
double x = 16;
Math.sqrt(x);  // Method called but result ignored! ❌
System.out.println(x);  // Still prints 16.0
```

**Correct Approach:**
```java
double x = 16;
x = Math.sqrt(x);  // Assign the returned value ✅
System.out.println(x);  // Now prints 4.0
```

**Why LLMs Struggle:** Requires understanding **function semantics** vs **variable mutation**. Students expect methods to be procedures that modify state, not functions that return values.

**LLM Detection Difficulty:** Easy (visible assignment pattern, but requires understanding method return values)

---

### A2: Temporal State (Loops & Control) — 6 Misconceptions

---

#### **NM_FLOW_01: Accumulator Amnesia (Scope Error)**

**Category:** The Teleological Flow Machine  
**Cognitive Issue:** Variable scope shadows accumulator, breaking loop invariant

**Student Belief:**
> "I'll declare the accumulator inside the loop, and it will keep adding up across iterations."

**Reality:**
- Declaring a variable **inside a loop** creates a **new variable each iteration**
- The inner variable **shadows** (hides) the outer variable
- Changes to the inner variable don't affect the outer one
- The outer accumulator remains unchanged

**Code Example (Buggy):**
```java
int sum = 0;
for (int i = 0; i < 5; i++) {
    int sum = 0;  // Redeclares sum! Shadows outer scope ❌
    sum += i;     // Modifies inner sum (0), not outer (0)
}
System.out.println(sum);  // Still 0 (outer sum never updated)
```

**Correct Approach:**
```java
int sum = 0;
for (int i = 0; i < 5; i++) {
    sum += i;  // Updates outer sum ✅
}
System.out.println(sum);  // 0 + 1 + 2 + 3 + 4 = 10
```

**Why LLMs Struggle:** Requires understanding **variable scope** and **shadowing**. Student intends for accumulator to persist, but code creates a new variable each iteration. LLMs must trace scope rules.

**LLM Detection Difficulty:** Hard (requires understanding scope semantics)

---

#### **NM_FLOW_02: The Intent Loop (Off-by-One)**

**Category:** The Teleological Flow Machine  
**Cognitive Issue:** Loop bounds don't match intended iteration count

**Student Belief:**
> "If I want to count to 5, I'll use `<= 5` because 5 is the goal."

**Reality:**
- `<= 5` includes 5, making loop run 6 times (0, 1, 2, 3, 4, 5)
- `< 5` runs 5 times (0, 1, 2, 3, 4)
- Off-by-one errors are subtle but cause wrong results

**Code Example (Buggy):**
```java
int sum = 0;
for (int i = 0; i <= 5; i++) {  // Runs 6 times, not 5! ❌
    sum += i;
}
System.out.println(sum);  // 0+1+2+3+4+5 = 15 (should be 10)
```

**Correct Approach:**
```java
int sum = 0;
for (int i = 0; i < 5; i++) {  // Runs 5 times ✅
    sum += i;  // 0+1+2+3+4 = 10
}
```

**Why LLMs Struggle:** Off-by-one errors are **extremely common** and require careful counting. LLMs must trace loop iterations and verify bounds.

**LLM Detection Difficulty:** Medium (requires loop iteration tracing)

---

#### **NM_FLOW_03: Infinite Loop (State Stagnation)**

**Category:** The Teleological Flow Machine  
**Cognitive Issue:** Loop condition never becomes false

**Student Belief:**
> "I'll start the loop and the condition will eventually become false."

**Reality:**
- The **loop condition must change** each iteration for loop to terminate
- If loop variable is never modified, condition never changes
- Loop runs infinitely, hanging the program

**Code Example (Buggy):**
```java
int i = 0;
while (i < 10) {
    System.out.println(i);
    // Missing i++! ❌
}
// Output: 0 0 0 0 0 ... (infinite loop)
```

**Correct Approach:**
```java
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;  // Increment so condition eventually becomes false ✅
}
// Output: 0 1 2 3 4 5 6 7 8 9 (exits)
```

**Why LLMs Struggle:** Requires understanding **loop invariants** and **termination conditions**. Student forgot that loop variables must change, or forgot the increment statement.

**LLM Detection Difficulty:** Easy (visible missing increment, but requires understanding loop semantics)

---

#### **NM_FLOW_04: Sabotaging the Future (Inner Loop Modification)**

**Category:** The Teleological Flow Machine  
**Cognitive Issue:** Modifying outer loop variable inside inner loop breaks control flow

**Student Belief:**
> "I can use the outer loop variable to skip ahead by incrementing it inside the inner loop."

**Reality:**
- Modifying the **outer loop variable** inside the **inner loop** causes **unpredictable behavior**
- Outer loop iteration count becomes wrong
- Loop runs fewer times than expected or skips iterations
- Control flow becomes non-sequential and hard to trace

**Code Example (Buggy):**
```java
for (int i = 0; i < 5; i++) {
    System.out.println("Outer: " + i);
    for (int j = 0; j < 5; j++) {
        i++;  // Modifying outer loop variable! ❌
    }
}
// Expected: i goes 0, 1, 2, 3, 4
// Actual: Outer loop skips iterations, runs unpredictably
```

**Correct Approach:**
```java
for (int i = 0; i < 5; i++) {
    System.out.println("Outer: " + i);
    for (int j = 0; j < 5; j++) {
        // Inner loop only modifies j, not i ✅
        System.out.println("  Inner: " + j);
    }
}
```

**Why LLMs Struggle:** Requires understanding **nested loop invariants** and **variable scope**. Student tried to optimize by skipping iterations, but broke loop semantics.

**LLM Detection Difficulty:** Hard (requires tracing nested loop control flow)

---

#### **NM_LOGIC_01: Mutually Exclusive Fallacy**

**Category:** The Logical Reasoning Machine  
**Cognitive Issue:** Treating non-exclusive conditions as if they're mutually exclusive

**Student Belief:**
> "These conditions are mutually exclusive, so I don't need `else if`; multiple `if` statements are fine."

**Reality:**
- Separate `if` statements execute **independently**
- Both blocks execute if both conditions are true
- Use `else if` to make conditions truly mutually exclusive

**Code Example (Buggy):**
```java
if (x > 10)
    System.out.println("Large");
if (x > 5)  // Not else-if! ❌
    System.out.println("Medium");

// If x = 15:
// Both print! Output: "Large" and "Medium"
```

**Correct Approach:**
```java
if (x > 10)
    System.out.println("Large");
else if (x > 5)  // Only executes if first condition is false ✅
    System.out.println("Medium");

// If x = 15: Only "Large" prints
// If x = 7: Only "Medium" prints
```

**Why LLMs Struggle:** Requires understanding **boolean logic** and **conditional semantics**. Student applied natural language reasoning (exclusivity) instead of understanding code semantics (independence).

**LLM Detection Difficulty:** Medium (requires understanding if/else-if semantics)

---

#### **NM_LOGIC_02: Dangling Else (Indentation Trap)**

**Category:** The Logical Reasoning Machine  
**Cognitive Issue:** Indentation suggests else belongs to outer if, but Java ignores indentation

**Student Belief:**
> "The `else` belongs to the outer `if` statement because of my indentation."

**Reality:**
- Java **ignores indentation** for syntax
- `else` always binds to the **nearest if** (Java's rule)
- Indentation is only for **human readability**

**Code Example (Buggy):**
```java
if (condition1)
    if (condition2)
        statement1;
else  // Student intended: else for condition1
    statement2;  // But Java binds it to condition2! ❌

// With condition1=true, condition2=false:
// statement2 executes (student expected it NOT to)
```

**Correct Approach:**
```java
if (condition1) {
    if (condition2)
        statement1;
    else  // Clearly attached to inner if ✅
        statement2;
}

// Or:
if (condition1) {
    if (condition2)
        statement1;
} else {  // Clearly attached to outer if ✅
    statement2;
}
```

**Why LLMs Struggle:** Requires understanding **Java's parsing rules** for else binding. Student relies on indentation (human convention) instead of Java's rules (machine syntax).

**LLM Detection Difficulty:** Medium-Easy (visible in code, requires understanding else binding rules)

---

### A3: Spatial State (Arrays & Strings) — 5 Misconceptions

---

#### **NM_MEM_01: Parallel Array Desync**

**Category:** The Spatial Adjacency Machine  
**Cognitive Issue:** Separate arrays require manual synchronization

**Student Belief:**
> "I'll use parallel arrays to store related data. Changes to one array automatically sync with the other."

**Reality:**
- Arrays are **independent data structures**
- Updates to one array do NOT affect another
- Must manually keep parallel arrays in sync
- **Modern practice:** Use objects/structs instead of parallel arrays

**Code Example (Buggy):**
```java
int[] ids = {101, 102, 103};
String[] names = {"Alice", "Bob", "Carol"};

ids[0] = 999;  // Update IDs
// names[0] is still "Alice", NOT automatically synced! ❌

// Arrays are out of sync
```

**Correct Approach:**
```java
// Option 1: Class to pair data
class Student {
    int id;
    String name;
}
Student[] students = {
    new Student(101, "Alice"),
    new Student(102, "Bob")
};  // ✅ Data stays paired

// Option 2: Keep parallel arrays explicitly synced
// (Less preferred, but possible)
```

**Why LLMs Struggle:** Requires understanding **data structure independence**. Student thinks parallel arrays are like database relations (automatically synchronized), but they're actually independent collections.

**LLM Detection Difficulty:** Hard (requires understanding data structure semantics)

---

#### **NM_MEM_02: Index-Value Confusion**

**Category:** The Spatial Adjacency Machine  
**Cognitive Issue:** Confusing array indices with array values

**Student Belief:**
> "`arr[20]` means the element with value 20, not the element at position 20."

**Reality:**
- `arr[i]` means the element **at position i** (the index)
- If array has 5 elements, valid indices are **0 through 4**
- Accessing `arr[20]` when array size is 5 throws `ArrayIndexOutOfBoundsException`

**Code Example (Buggy):**
```java
int[] arr = {10, 20, 30};  // Size 3, indices 0-2
System.out.println(arr[20]);  // Trying to access position 20! ❌
// ArrayIndexOutOfBoundsException
```

**Correct Approach:**
```java
int[] arr = {10, 20, 30};
System.out.println(arr[1]);  // Position 1 → value 20 ✅
System.out.println(arr[2]);  // Position 2 → value 30 ✅
```

**Why LLMs Struggle:** Requires understanding **array indexing** semantics. Student confused the **index** (position) with the **value** (element).

**LLM Detection Difficulty:** Easy (produces runtime exception, visible in code)

---

#### **NM_MEM_03: String Identity Trap (Immutability)**

**Category:** The Spatial Adjacency Machine  
**Cognitive Issue:** Strings are mutable, or assignment creates a link

**Student Belief:**
> "When I assign `s2 = s1`, they're linked. Changing `s2` also changes `s1`."

**Reality:**
- Strings in Java are **immutable**
- Assignment only copies the **reference**, not creates a link
- Assigning a new string to `s2` just points `s2` to a different string
- `s1` is unaffected

**Code Example (Buggy):**
```java
String s1 = "hello";
String s2 = s1;
s2 = "world";
System.out.println(s1);  // Still "hello", not "world"! ❌
// s1 and s2 pointed to "hello", now s2 points to "world"
```

**Correct Concept:**
```java
String s1 = "hello";
String s2 = s1;  // Both reference the same string "hello"
System.out.println(s1);  // "hello"
System.out.println(s2);  // "hello"

s2 = "world";  // s2 now points to "world"
System.out.println(s1);  // Still "hello" (unchanged) ✅
```

**Why LLMs Struggle:** Requires understanding **reference semantics** and **immutability**. Student thinks assignment creates a link or that objects are mutable like in some other languages.

**LLM Detection Difficulty:** Hard (requires understanding reference and immutability semantics)

---

#### **NM_MEM_04: The 1-Based Offset (Out-of-Bounds)**

**Category:** The Spatial Adjacency Machine  
**Cognitive Issue:** Arrays are 1-indexed, like in some other languages

**Student Belief:**
> "Arrays with 5 elements have indices 1 through 5."

**Reality:**
- Java uses **0-based indexing**
- Array with 5 elements has indices **0 through 4**
- Accessing `arr[5]` throws `ArrayIndexOutOfBoundsException`

**Code Example (Buggy):**
```java
int[] arr = new int[5];  // Indices 0-4
arr[5] = 10;  // Trying to access index 5! ❌
// ArrayIndexOutOfBoundsException
```

**Correct Approach:**
```java
int[] arr = new int[5];  // Indices 0-4
arr[0] = 10;  // First element ✅
arr[4] = 50;  // Last element ✅
```

**Why LLMs Struggle:** Requires understanding **0-based indexing** in Java. Some languages use 1-based indexing (Lua, MATLAB), so students may transfer that assumption.

**LLM Detection Difficulty:** Easy (produces out-of-bounds exception, visible in code)

---

#### **NM_MEM_05: Lossy Swap (Data Destruction)**

**Category:** The Spatial Adjacency Machine  
**Cognitive Issue:** Swapping values without temporary variable loses data

**Student Belief:**
> "I can swap two values by assigning them to each other back and forth."

**Reality:**
- Assigning `a = b` overwrites `a`'s value
- Without a **temporary variable**, the original value of `a` is lost
- Cannot recover it later

**Code Example (Buggy):**
```java
int a = 5, b = 10;
a = b;  // a = 10, but original 5 is lost! ❌
b = a;  // b = 10 (not 5), both are now 10

System.out.println(a);  // 10
System.out.println(b);  // 10 (failed swap!)
```

**Correct Approach:**
```java
int a = 5, b = 10;
int temp = a;  // Save original a ✅
a = b;         // a = 10
b = temp;      // b = 5 (restored original a)

System.out.println(a);  // 10
System.out.println(b);  // 5 (success!)
```

**Why LLMs Struggle:** Requires understanding **variable assignment semantics** and **destructive operations**. Student didn't realize that assignment overwrites, and planned a sequential swap without understanding that data is lost.

**LLM Detection Difficulty:** Medium (requires understanding assignment and data flow)

---

## Theoretical Foundation

### What is a Notional Machine?

A **notional machine** is the mental model a student has of how a computer executes code. When this mental model diverges from actual language semantics, it creates a **misconception**.

Key characteristics:
- **Invisible** (student's thinking, not observable from code alone)
- **Persistent** (students maintain these beliefs across many problems)
- **Systematic** (each misconception produces predictable errors)
- **Teachable** (can be corrected with explicit instruction)

### Why LLMs Struggle with Misconceptions

**The Complexity Gradient** (A3: 89% → A1: 59%) shows that:

1. **Concrete errors** (syntax, array bounds) are easy to detect
   - Visible in output or runtime exceptions
   - Match training data patterns (buggy code → wrong output)

2. **Abstract mental models** (variable state, I/O assumptions) are hard to detect
   - Invisible without detailed execution tracing
   - Require inference of student's beliefs
   - Not well-represented in typical training data

LLMs are trained on (code, output) pairs but rarely see (code, student_mental_model) pairs. Detecting misconceptions requires reasoning about **pedagogical epistemology**, not just code execution.

---

### 6 LLM Models (3 providers × base + reasoning variants)

| Model | Provider | Type | Base | Reasoning |
|-------|----------|------|------|-----------|
| **GPT-5.2** | OpenAI | Large | ✓ | ✓ |
| **Claude Haiku** | Anthropic | Small | ✓ | ✓ |
| **Gemini Flash** | Google | Medium | ✓ | ✓ |

### 4 Prompt Strategies (Different ways to ask for diagnoses)

| Strategy | Philosophy | Precision | Recall | F1 |
|----------|-----------|-----------|--------|-----|
| **Baseline** | Simple error classification (control) | 0.714 | 0.796 | 0.753 |
| **Taxonomy** | Explicit notional machine categories | 0.654 | 0.832 | 0.734 |
| **CoT** | Chain-of-thought execution tracing | 0.668 | 0.850 | 0.750 |
| **Socratic** | Mental model probing (creative) | 0.584 | 0.923 | 0.726 |

**Insight:** Socratic finds the most bugs (recall=0.923) but creates hallucinations. Ensemble voting recovers precision while keeping high recall.

## Key Results (Analysis 3 — Ensemble Voting)

### Overall Performance

| Metric | Value | Change vs Analysis 2.2 |
|--------|-------|------------------------|
| **Precision** | 0.649 | **+107%** ↑ |
| **Recall** | 0.871 | -0.1% (stable) |
| **F1 Score** | 0.744 | **+61%** ↑ |
| **False Positives** | 1,164 | **-75%** ↓ |
| **True Positives** | 2,150 | -0.05% (minimal) |

**Impact:** Ensemble voting filtered 3,558 hallucinations (-75%) while only losing 1 true positive.

### By Assignment (Complexity Gradient)

| Assignment | Task | Precision | Recall | F1 | Status |
|---|---|---|---|---|---|
| **A3** | Arrays/Strings | 0.810 | 0.989 | **0.890** | ✅ Easy |
| **A2** | Loops/Control | 0.653 | 0.885 | **0.751** | 🟡 Medium |
| **A1** | Variables/Math | 0.499 | 0.728 | **0.592** | ⚠️ Hard |

**Key Finding:** 30% performance gap between A3 and A1. LLMs excel at concrete errors (syntax, arrays) but struggle with abstract mental models (variable state).

### Best Performing Models

| Model | F1 | Precision | Recall |
|-------|-----|-----------|--------|
| **Claude Haiku Reasoning** | **0.819** | 0.784 | 0.857 |
| **GPT-5.2 Reasoning** | 0.788 | 0.702 | 0.897 |
| **GPT-5.2 (base)** | 0.779 | 0.690 | 0.895 |
| Claude Haiku (base) | 0.751 | 0.697 | 0.813 |
| Gemini Flash Reasoning | 0.680 | 0.551 | 0.887 |
| Gemini Flash (base) | 0.661 | 0.531 | 0.877 |

## Core Thesis Finding

### The Complexity Gradient

LLMs show **consistent performance degradation** as conceptual abstraction increases:

```
Concrete Syntax Errors    Abstract Mental Models
(easy for LLMs)          (hard for LLMs)
        ↓                       ↓
Syntax: ~99% F1  ──→  Arrays: 89% F1  ──→  Loops: 75% F1  ──→  Variables: 59% F1
         (runtime)            (indexing)      (temporal)          (state)
```

**Why?** LLMs are trained on (code, output) pairs but rarely see (code, mental_model) pairs. Detecting misconceptions requires reasoning about **student epistemology**, not just code semantics.

See [Complexity Gradient](docs/complexity-gradient.md) for detailed analysis.

---

## Ensemble Voting: The Solution

Single strategies hallucinate (69% false positive rate in Analysis 2.2). Solution: require ≥2 strategies to agree.

```python
# For each student, question, misconception:
if (agreement_count >= 2):
    VALIDATE(detection)  # Count as result
else:
    REJECT(detection)    # Filter as hallucination
```

**Results:**
- Precision: 0.313 → 0.649 (+107%)
- Recall: 0.872 → 0.871 (stable!)
- F1: 0.461 → 0.744 (+61%)
- False Positives: -75%

See [Matching & Semantics](docs/matching.md) for ensemble algorithm details.

---

## Environment Variables

```bash
# Required for LLM detection (inference API)
export OPENROUTER_API_KEY="sk-or-..."

# Required for semantic embeddings (used in matching)
export OPENAI_API_KEY="sk-..."

# Optional: debugging and caching
export VERBOSE=true
export CACHE_EMBEDDINGS=true
```

---

## Research Context

| Field | Value |
|-------|-------|
| **Project** | Honours Thesis: LLM Detection of Notional Machine Misconceptions |
| **Institution** | University of British Columbia Okanagan (UBCO) |
| **Researcher** | Shlok Shah |
| **Academic Year** | 2024-2025 |
| **Target Venue** | ITiCSE / SIGCSE |
| **Supervisor** | Dr. [TBD] |

---

## Limitations & Future Work

### Current Limitations

1. **Small Dataset:** 360 student files across 3 assignments
2. **Single Language:** Java only (CS1 teaching language)
3. **Synthetic Injection:** Students are real, but bugs are artificially injected
4. **Binary Classification:** Presence/absence of misconception (no degree of understanding)

### Future Extensions

1. **A4 Assignment:** Objects and references (heap/indirection complexity)
2. **Cross-language:** Python, C++, JavaScript
3. **Confidence Weighting:** Weight ensemble votes by LLM confidence
4. **Interactive Diagnosis:** Socratic dialogue with students for real-time feedback
5. **Misconception Chains:** Track how misconceptions lead to subsequent errors
6. **Student Modeling:** Predict which misconceptions a student likely has

See [Development](docs/development.md) for extension guidelines.

---

## How to Cite

If you use this framework in your research, please cite:

```bibtex
@software{shah2025lmm_misconceptions,
  author = {Shah, Shlok},
  title = {LLM Notional Machine Misconception Detection Framework},
  year = {2025},
  institution = {University of British Columbia Okanagan},
  note = {Honours Thesis Research Project},
  url = {https://github.com/shahshlok/ensemble-eval-cli}
}
```

Or in APA format:

```
Shah, S. (2025). LLM notional machine misconception detection framework. 
University of British Columbia Okanagan [Unpublished Honours Thesis].
```

---

## License

This project is part of academic research at UBCO. Licensing terms TBD pending publication.

For inquiries: [Contact Info TBD]

---

## Acknowledgments

- **Supervisors/Committee:** [TBD]
- **CS Education Theory:** Based on notional machine research by [Ben du Boulay](https://en.wikipedia.org/wiki/Notional_machine) and others
- **LLM Providers:** OpenAI, Anthropic, Google
- **Tools:** Built with Python, Typer, Pydantic, Pandas, scikit-learn

---

## Quick Links

- **GitHub:** https://github.com/shahshlok/ensemble-eval-cli
- **Documentation:** See `docs/` directory
- **Results:** See `runs/multi/run_analysis3/report.md`
- **Issues & Feedback:** GitHub Issues or [contact TBD]

---

## Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| `API key invalid` | Check `OPENROUTER_API_KEY` and `OPENAI_API_KEY` environment variables |
| `No detections found` | Ensure LLM detection ran first: `python analyze.py detect` |
| `Out of memory` | Process smaller assignments: `--assignment a1` instead of `multi` |
| `Embedding timeout` | Reduce batch size or check OpenAI API status |

See [CLI Reference](docs/cli-reference.md) for complete troubleshooting.

---

**Last Updated:** December 22, 2025  
**Status:** ✅ Analysis 3 Complete, Ensemble Voting Implemented, Publication-Ready
