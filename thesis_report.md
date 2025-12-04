# LLM Misconception Detection: Research Analysis

Generated: 2025-12-04T05:11:14.090288+00:00

## Executive Summary

### Strategy Comparison Matrix
| Strategy | Precision | Recall | F1 Score | TP | FP | FN | Conf. Gap |
|----------|-----------|--------|----------|----|----|----|-----------|
| **rubric_only** | 0.313 | 0.638 | **0.420** | 141 | 310 | 80 | 0.007 |
| **baseline** | 0.346 | 0.653 | **0.452** | 128 | 242 | 68 | 0.021 |
| **minimal** | 0.343 | 0.704 | **0.462** | 159 | 304 | 67 | 0.017 |
| **socratic** | 0.311 | 0.642 | **0.418** | 68 | 151 | 38 | 0.020 |

## Model Showdown: GPT-5.1 vs Gemini-2.5-Flash

Aggregate performance comparison across all strategies.

### Aggregate Metrics
| Model | TP | FP | Precision | Recall (est) |
|-------|----|----|-----------|--------------|
| **gemini-2.5-flash** | 281 | 604 | 0.318 | 0.499 |
| **gpt-5.1** | 215 | 403 | 0.348 | 0.382 |

### Statistical Significance (McNemar's Test)
Testing the null hypothesis that both models have equal sensitivity (recall).

**Comparison: gpt-5.1 vs gemini-2.5-flash**
- **Statistic**: 23.3011
- **P-Value**: 1.3852e-06
- **Result**: Statistically Significant Difference (p < 0.05)

**Contingency Table**
| | gemini-2.5-flash Correct | gemini-2.5-flash Wrong |
|---|---|---|
| **gpt-5.1 Correct** | 173 | 40 |
| **gpt-5.1 Wrong** | 97 | 253 |

## Performance by Category

Breakdown of detection performance by misconception category (Topic).
| Category | Recall | Precision | TP | FN | FP |
|----------|--------|-----------|----|----|----|
| Algebraic Reasoning | 0.61 | 1.00 | 40 | 26 | 0 |
| Data Types | 0.80 | 0.48 | 79 | 20 | 85 |
| Input | 0.18 | 0.29 | 16 | 73 | 39 |
| Input / Data Types | 0.88 | 0.55 | 42 | 6 | 35 |
| Methods | 0.53 | 1.00 | 19 | 17 | 0 |
| State / Input | 0.96 | 0.93 | 53 | 2 | 4 |
| State / Representation | 1.00 | 0.27 | 3 | 0 | 8 |
| State / Variables | 0.35 | 0.51 | 58 | 109 | 55 |

## Deep Dive: Misconception Difficulty


### Hardest Misconceptions (Low Recall)
| ID | Name | Topic | Recall | TP | FN |
|----|------|-------|--------|----|----|
| INP-02 | One Scanner Call Reads All | Input | 0.04 | 2 | 47 |
| STA-02 | Concrete Instance Fallacy | State / Variables | 0.05 | 3 | 57 |
| STA-01 | Spreadsheet View | State / Variables | 0.15 | 4 | 22 |
| API-01 | Argument Commutativity | Methods | 0.30 | 3 | 7 |
| INP-01 | Prompt-Logic Mismatch | Input | 0.35 | 14 | 26 |

### Most Confusing Misconceptions (High FP)
| ID | Name | Topic | FP Count |
|----|------|-------|----------|
| STA-07 | Swapped Variables After Read | State / Variables | 47 |
| TYP-02 | Integer Coordinates | Data Types | 37 |
| INP-03 | Scanner Type Mismatch | Input / Data Types | 35 |
| INP-01 | Prompt-Logic Mismatch | Input | 30 |
| TYP-05 | Narrowing Cast in Division | Data Types | 18 |

## Hallucination Analysis

Recurring false positives that do not match any known misconception ID.

### Rubric_Only Hallucinations
- **"Hardcoding Input Values Instead of Reading from User"** (7 times)
  - Example: Thompson_Erica_221372 Q3
  - Models: google/gemini-2.5-flash
- **"Incorrect data type for input variables"** (6 times)
  - Example: Singh_Valerie_623807 Q3
  - Models: google/gemini-2.5-flash
- **"Operator Precedence Error in Semi-Perimeter Calculation"** (5 times)
  - Example: Lawson_Jonathan_767435 Q4
  - Models: google/gemini-2.5-flash

### Baseline Hallucinations
- **"Hardcoding Input Values"** (5 times)
  - Example: Kennedy_Beverly_992649 Q4
  - Models: google/gemini-2.5-flash
- **"Incorrect operator precedence in semi-perimeter formula"** (4 times)
  - Example: Lawson_Jonathan_767435 Q4
  - Models: openai/gpt-5.1
- **"Hardcoding sample values instead of reading user input"** (4 times)
  - Example: Thompson_Erica_221372 Q3
  - Models: openai/gpt-5.1

### Minimal Hallucinations
- **"Hardcoding Input Values Instead of Reading from User"** (5 times)
  - Example: Kennedy_Beverly_992649 Q4
  - Models: google/gemini-2.5-flash
- **"Incorrect Data Type for Input Variables"** (4 times)
  - Example: Adams_Jennifer_401503 Q3
  - Models: google/gemini-2.5-flash
- **"Hardcoding Input Values"** (4 times)
  - Example: Murphy_Julie_887463 Q3
  - Models: google/gemini-2.5-flash

### Socratic Hallucinations
- **"Incorrect data type for input variables"** (4 times)
  - Example: Adams_Jennifer_401503 Q3
  - Models: google/gemini-2.5-flash
- **"Operator Precedence Error in Semi-Perimeter Calculation"** (3 times)
  - Example: Lawson_Jonathan_767435 Q4
  - Models: google/gemini-2.5-flash
- **"Division by zero"** (3 times)
  - Example: Martin_Melissa_602227 Q1
  - Models: google/gemini-2.5-flash

## Interesting Discoveries (Clean Files)

Potential genuine issues found in clean files.
### Rubric_Only
- **Cruz_Jeremy_130806** Q4: Incorrect handling of two numeric inputs on single line
  - Matched to: INP-02 (0.72)
- **Graves_Mary_457617** Q4: Incorrect Scanner usage for paired inputs
  - Matched to: INP-02 (0.71)
- **Adams_Courtney_257740** Q4: Incorrect input parsing for multiple values on one line
  - Matched to: INP-01 (0.72)
### Baseline
- **Fisher_Laura_816341** Q4: Sequential nextDouble calls for two inputs on one line
  - Matched to: INP-01 (0.72)
### Minimal
- **Schmidt_Tara_761866** Q2: Lack of output formatting
  - Matched to: STA-05 (0.77)
- **Herrera_Glenda_448690** Q4: Incorrect Scanner usage for paired inputs
  - Matched to: INP-01 (0.73)
- **Fisher_Laura_816341** Q4: Incorrect Scanner usage for paired inputs
  - Matched to: INP-01 (0.73)
- **Keith_Brady_216822** Q4: Incorrect Scanner usage for paired inputs
  - Matched to: INP-01 (0.73)
- **Campbell_Lonnie_394543** Q4: Incorrect Scanner usage for paired inputs (x, y)
  - Matched to: INP-02 (0.70)
### Socratic
- **Daniels_Holly_275197** Q2: Floating point precision and formatting for currency
  - Matched to: STA-05 (0.79)
- **Moore_Nancy_573046** Q2: Floating-point precision in output
  - Matched to: STA-05 (0.78)
- **Fitzgerald_Jamie_203673** Q4: Incorrect input prompt alignment for multiple values on one line
  - Matched to: INP-01 (0.73)
- **Cruz_Jeremy_130806** Q4: Incorrect Scanner usage for paired inputs
  - Matched to: INP-02 (0.71)
- **Adams_Courtney_257740** Q4: Incorrect Scanner usage for multiple double inputs on one line
  - Matched to: INP-02 (0.75)