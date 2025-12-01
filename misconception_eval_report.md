# Misconception Detection Evaluation Report

**Strategy:** minimal
**Total Submissions:** 40
**Ground Truth with Errors:** 13
**Ground Truth Correct:** 27

---

## Executive Summary

| Metric | Value | Interpretation |
|--------|-------|----------------|
| **Precision** | 60.0% | When we flag an error, we're right 60% of the time |
| **Recall** | 46.2% | We catch 46% of actual errors |
| **F1 Score** | 52.2% | Harmonic mean of precision and recall |
| **Accuracy** | 72.5% | Overall correctness rate |

## Confusion Matrix

```
                    PREDICTED
                 Error    Correct
            ┌─────────┬─────────┐
    Error   │ TP:   6 │ FN:   7 │
  A         ├─────────┼─────────┤
  C Correct │ FP:   4 │ TN:  23 │
  T         └─────────┴─────────┘
```

- **TP (True Positive):** Correctly identified an error
- **FN (False Negative):** Missed an actual error
- **FP (False Positive):** Flagged error when code was correct
- **TN (True Negative):** Correctly identified correct code

---

## Per-Model Performance

| Model | TP | FP | FN | TN | Precision | Recall | F1 |
|-------|:--:|:--:|:--:|:--:|:---------:|:------:|:---:|
| gemini-2.5-flash-lite | 6 | 4 | 7 | 23 | 60.0% | 46.2% | 52.2% |
| gpt-5-nano | 6 | 1 | 7 | 26 | 85.7% | 46.2% | 60.0% |

---

## Per-Misconception Detection Rate

| ID | Name | Total | Detected | Rate | Status |
|:---|:-----|:-----:|:--------:|:----:|:------:|
| CONST001 | Using ^ instead of Math.pow()  | 1 | 1 | 100% | ✓ |
| CONST002 | Missing Math.sqrt() for square | 1 | 1 | 100% | ✓ |
| DT002 | Integer division truncation | 1 | 1 | 100% | ✓ |
| DT003 | Type mismatch in Scanner input | 2 | 0 | 0% | ✗ |
| INPUT001 | Missing Scanner import | 2 | 0 | 0% | ✗ |
| INPUT002 | Scanner not reading correct nu | 1 | 0 | 0% | ✗ |
| INPUT003 | Not closing Scanner resource | 1 | 0 | 0% | ✗ |
| OTHER001 | Computing wrong quantity (diff | 1 | 1 | 100% | ✓ |
| OTHER002 | Hardcoded values instead of us | 1 | 0 | 0% | ✗ |
| VAR001 | Incorrect operator precedence | 2 | 2 | 100% | ✓ |

---

## Detailed Results by Submission

### True Positives (Correctly Detected Errors)

#### Ramirez_Zoey_200134 - q3

**Ground Truth:** CONST001 (Using ^ instead of Math.pow() for exponentiation)

**gemini-2.5-flash-lite:**

- **Incorrect exponentiation operator** → **CONST001**
  - *Topic:* Other | *Severity:* major | *Category:* semantic
  - *Confidence:* 100%
  - *Description:* The student believes that the `^` operator in Java is used for exponentiation (raising to a power), similar to its usage in some mathematical contexts or other programming languages.
  - *Student believes:* The `^` symbol in Java performs exponentiation, so `(x2-x1)^2` calculates the square of the difference.
  - *Correct understanding:* In Java, the `^` operator is the bitwise XOR operator. To square a number, you should either multiply it by itself (e.g., `diff * diff`) or use the `Math.pow(base, exponent)` method (e.g., `Math.pow(diff, 2)`).
  - *Symptoms:* Incorrect calculation of the distance., The squared differences are not calculated correctly.
  - *Root cause:* Confusion between mathematical notation/operators in other contexts and Java's specific operator meanings.
  - *Remediation:* Replace `variable ^ 2` with `variable * variable` or `Math.pow(variable, 2)`.
  - *Related concepts:* Java operators, Math library functions
  - *Evidence:*
    - `double distance = Math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2);`

**gpt-5-nano:**

- **Exponentiation operator misuse**
  - *Topic:* Other | *Severity:* critical | *Category:* procedural
  - *Confidence:* 85%
  - *Description:* Student uses ^ to denote squaring, assuming ^ is exponentiation. In Java, ^ is a bitwise XOR operator for integral types and is not used for exponentiation with doubles.
  - *Student believes:* The ^ operator raises a number to a power, so (dx) ^ 2 should square the difference.
  - *Correct understanding:* In Java, ^ is bitwise XOR (not exponentiation). To square a difference, use Math.pow(dx, 2) or (dx * dx). The distance should be computed as Math.sqrt(dx*dx + dy*dy).
  - *Root cause:* Assuming ^ represents exponentiation due to common mathematical notation
  - *Remediation:* Use Math.pow(x2 - x1, 2) or (x2 - x1)*(x2 - x1) for the squared term, and similarly for (y2 - y1). Then compute distance = Math.sqrt(dx*dx + dy*dy).
  - *Related concepts:* Java Math.pow, Arithmetic with doubles, Operator precedence
  - *Evidence:*
    - `double distance = Math.sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2);`

---

#### Lopez_Abigail_200117 - q1

**Ground Truth:** OTHER001 (Computing wrong quantity (different problem))

**gemini-2.5-flash-lite:**

- **Incorrect formula application** → **VAR001**
  - *Topic:* Variables | *Severity:* major | *Category:* procedural
  - *Confidence:* 100%
  - *Description:* The student substituted an incorrect mathematical expression into the code, seemingly confusing the order of operations or misinterpreting the required formula.
  - *Student believes:* The student appears to believe that the formula for acceleration involves adding the starting velocity to the product of the final velocity and time (`v0 + v1 * t`).
  - *Correct understanding:* The correct formula for acceleration is `a = (v1 - v0) / t`. This involves subtracting the initial velocity from the final velocity and then dividing the result by the time elapsed.
  - *Symptoms:* Code calculates `startVelocity + endVelocity * time` instead of `(endVelocity - startVelocity) / time`., Output shows an incorrect numerical value., Output message incorrectly refers to 'velocity' instead of 'acceleration'.
  - *Root cause:* Possible confusion with the order of operations, or a misunderstanding of typical physics formulas where velocity might be calculated differently (e.g., `v = v0 + a*t`). The student may have incorrectly applied a formula or mixed up variable roles.
  - *Remediation:* Carefully re-write the calculation line to match the given formula `(v1 - v0) / t`. Ensure parentheses are used correctly if needed, and that the correct variables are used in the subtraction and division.
  - *Related concepts:* Translating mathematical formulas to code, Order of operations (PEMDAS/BODMAS), Variable assignment
  - *Evidence:*
    - `double velocity=startVelocity + endVelocity * time;`

- **Incorrect output message**
  - *Topic:* Other | *Severity:* minor | *Category:* semantic
  - *Confidence:* 100%
  - *Description:* The student used an output message that does not accurately reflect the quantity the program is supposed to calculate.
  - *Student believes:* The student appears to believe that the calculated value represents 'velocity' rather than 'acceleration'.
  - *Correct understanding:* The program is intended to calculate and display the average acceleration based on the given input and formula.
  - *Symptoms:* Output message is 'The velocity is ...' instead of 'The average acceleration is ...'.
  - *Root cause:* Likely a direct consequence of incorrectly implementing the acceleration formula, leading the student to mistakenly label the output, or simply not reading the sample output carefully.
  - *Remediation:* Adjust the print statement to accurately describe the result, e.g., `System.out.println("The average acceleration is " + velocity);` after correcting the calculation.
  - *Related concepts:* Program output specification, Clarity in communication
  - *Evidence:*
    - `System.out.println("The velocity is " + velocity);`

**gpt-5-nano:**

- **Incorrect formula (v0 + v1*t) used to compute acceleration**
  - *Topic:* Variables | *Severity:* major | *Category:* semantic
  - *Confidence:* 80%
  - *Description:* Student computes velocity-like expression instead of the acceleration formula, effectively mixing up the operation and the operands.
  - *Student believes:* The acceleration can be obtained by v0 + v1 * t.
  - *Correct understanding:* Acceleration should be calculated as (v1 - v0) / t.
  - *Symptoms:* Output does not reflect acceleration, Expression used does not match the formula from the prompt
  - *Root cause:* Misunderstanding of the given formula and operator precedence; conflating velocity with acceleration
  - *Remediation:* Compute acceleration as (endVelocity - startVelocity) / time and print 'The average acceleration is ' followed by the result
  - *Related concepts:* Order of operations, Arithmetic operators, Storing intermediate results in a separate variable
  - *Evidence:*
    - `double velocity=startVelocity + endVelocity * time;`

---

#### Torres_Daniel_200148 - q1

**Ground Truth:** DT002 (Integer division truncation)

**gemini-2.5-flash-lite:**

- **Integer division truncation** → **DT002**
  - *Topic:* Data Types | *Severity:* major | *Category:* conceptual
  - *Confidence:* 95%
  - *Description:* The student incorrectly assumes that casting to `int` before a division is acceptable or necessary, leading to loss of precision and incorrect results. They do not understand that this truncates decimal values.
  - *Student believes:* I need to cast my numbers to integers before doing calculations, or at least it's okay if they become integers.
  - *Correct understanding:* When calculating acceleration, velocities and time can have decimal values. Casting these `double` values to `int` truncates the decimal part, leading to significant errors. The calculation should be performed using `double` types throughout to maintain precision.
  - *Symptoms:* Calculation `(int)(vEnd - vStart) / (int)t` results in integer division, Output is incorrect even when input values are provided correctly
  - *Root cause:* Confusion about data type casting and its impact on mathematical operations, particularly division. Possibly a misunderstanding that `int` is always preferred or that casting doesn't lose information.
  - *Remediation:* Remove the `(int)` casts from `vEnd - vStart` and `t` in the calculation line. Ensure the calculation is `(vEnd - vStart) / t`.
  - *Related concepts:* Data type casting in Java (implicit vs. explicit), Floating-point arithmetic precision, Integer division behavior
  - *Evidence:*
    - `double a = (int)(vEnd - vStart) / (int)t;`

**gpt-5-nano:**

- **Integer division truncation** → **DT002**
  - *Topic:* Data Types | *Severity:* major | *Category:* procedural
  - *Confidence:* 80%
  - *Description:* Casting both operands to int before the division causes the division to be integer division, losing the fractional part.
  - *Student believes:* The student believes that converting the operands to int before performing the division yields the desired numeric result for the acceleration.
  - *Correct understanding:* To compute acceleration as a floating-point value, perform the subtraction and division using doubles (e.g., (vEnd - vStart) / t) without casting to int, so decimals are preserved.
  - *Related concepts:* Java Data Types, Type promotion, Floating-point vs integer division, Scanner input handling
  - *Evidence:*
    - `double a = (int)(vEnd - vStart) / (int)t;`

---

#### Smith_Mason_200158 - q3

**Ground Truth:** CONST002 (Missing Math.sqrt() for square root)

**gemini-2.5-flash-lite:**

- **Missing Square Root Calculation**
  - *Topic:* Other | *Severity:* major | *Category:* procedural
  - *Confidence:* 100%
  - *Description:* The student understands how to calculate the differences between coordinates, square them, and sum them up, but neglects to take the final square root to obtain the actual Euclidean distance.
  - *Student believes:* I have calculated the distance by squaring and summing the differences.
  - *Correct understanding:* The final step in calculating the Euclidean distance is to take the square root of the sum of the squared differences.
  - *Symptoms:* Program outputs the squared distance instead of the actual distance., The computed value is consistently larger than the expected distance (unless the distance is 0 or 1).
  - *Root cause:* The student might have focused on the intermediate steps or overlooked the final operation required by the distance formula.
  - *Remediation:* Wrap the existing calculation for the sum of squares with `Math.sqrt(...)`.
  - *Related concepts:* Mathematical formulas, Order of operations
  - *Evidence:*
    - `double distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);`

**gpt-5-nano:**

- **Missing square root in distance calculation**
  - *Topic:* Other | *Severity:* major | *Category:* semantic
  - *Confidence:* 78%
  - *Description:* Student computes (x2-x1)^2 + (y2-y1)^2 and assigns it to distance, effectively using the squared distance instead of the actual distance.
  - *Student believes:* Distance is the sum of squared differences without taking a square root.
  - *Correct understanding:* Distance should be the square root of the sum of squared differences: distance = Math.sqrt((x2 - x1)^2 + (y2 - y1)^2).
  - *Evidence:*
    - `double distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);`
    - `System.out.println("The distance of the two points is " + distance);`

---

#### Hill_Michael_200140 - q1

**Ground Truth:** VAR001 (Incorrect operator precedence)

**gemini-2.5-flash-lite:**

- **Incorrect operator precedence** → **VAR001**
  - *Topic:* Variables | *Severity:* major | *Category:* semantic
  - *Confidence:* 100%
  - *Description:* The student incorrectly assumes that subtraction will be performed before division when they are on the same line without explicit grouping.
  - *Student believes:* The expression `vEnd - vStart / t` will calculate `vEnd - vStart` first, and then divide the result by `t`.
  - *Correct understanding:* In Java, division has higher precedence than subtraction. Therefore, `vStart / t` is calculated first, and then `vEnd` is subtracted from that result.
  - *Symptoms:* The calculated acceleration value is incorrect., The formula is not mathematically equivalent to the provided equation.
  - *Root cause:* Lack of understanding or recalling operator precedence rules in Java.
  - *Remediation:* Use parentheses around the subtraction part of the formula to ensure it is calculated first: `(vEnd - vStart) / t`.
  - *Related concepts:* Operator precedence, Order of operations, Mathematical expressions in code
  - *Evidence:*
    - `double a = vEnd - vStart / t;`

**gpt-5-nano:**

- **Incorrect precedence/translation of formula**
  - *Topic:* Other | *Severity:* major | *Category:* procedural
  - *Confidence:* 78%
  - *Description:* Assumes the expression `vEnd - vStart / t` correctly computes `(v1 - v0) / t` instead of applying the subtraction before division.
  - *Student believes:* I believe the code `a = vEnd - vStart / t` correctly implements the formula `(v1 - v0) / t`.
  - *Correct understanding:* The correct computation is `(vEnd - vStart) / t`; parentheses are required to ensure subtraction happens before division.
  - *Evidence:*
    - `double a = vEnd - vStart / t;`

---

#### Ramirez_Zoey_200134 - q1

**Ground Truth:** VAR001 (Incorrect operator precedence)

**gemini-2.5-flash-lite:**

- **Incorrect order of operations in formula translation** → **VAR001**
  - *Topic:* Variables | *Severity:* major | *Category:* procedural
  - *Confidence:* 100%
  - *Description:* The student incorrectly translated the mathematical formula for acceleration into code, leading to an inaccurate calculation because the order of operations was not handled as intended by the formula.
  - *Student believes:* The student appears to believe that typing `endVelocity - startVelocity / time` will perform the subtraction before the division, or that the order of operations in Java directly mirrors the way it's written to achieve the desired mathematical outcome without explicit grouping.
  - *Correct understanding:* In Java, division `/` has higher precedence than subtraction `-`. Therefore, `endVelocity - startVelocity / time` is evaluated as `endVelocity - (startVelocity / time)`. To correctly implement `(v1 - v0) / t`, parentheses must be used: `(endVelocity - startVelocity) / time`.
  - *Symptoms:* Calculation of acceleration is incorrect., The division `startVelocity / time` is performed before the subtraction `endVelocity - result`.
  - *Root cause:* Lack of understanding or attention to operator precedence in Java, or a misunderstanding of how to ensure correct mathematical grouping in code.
  - *Remediation:* Use parentheses `()` to explicitly group operations that should be performed first, like `(endVelocity - startVelocity) / time`.
  - *Related concepts:* Operator precedence, Order of operations, Parentheses for grouping expressions
  - *Evidence:*
    - `double acceleration = endVelocity - startVelocity / time;`

**gpt-5-nano:**

- **Operator precedence error** → **VAR001**
  - *Topic:* Variables | *Severity:* major | *Category:* procedural
  - *Confidence:* 85%
  - *Description:* Student computed acceleration as endVelocity - startVelocity / time instead of (endVelocity - startVelocity) / time due to incorrect understanding of operator precedence.
  - *Student believes:* I can perform the subtraction and division in the expression as written without grouping the numerator.
  - *Correct understanding:* The formula requires grouping the numerator: (v1 - v0) / t.
  - *Evidence:*
    - `double acceleration = endVelocity - startVelocity / time;`

---

### False Negatives (Missed Errors)

#### Torres_Daniel_200148 - q3

**Ground Truth:** INPUT003 (Not closing Scanner resource)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Ramirez_Zoey_200134 - q4

**Ground Truth:** DT003 (Type mismatch in Scanner input)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Flores_Emily_200126 - q4

**Ground Truth:** INPUT002 (Scanner not reading correct number of inputs)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Anderson_Noah_200113 - q3

**Ground Truth:** DT003 (Type mismatch in Scanner input)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Lopez_Abigail_200117 - q2

**Ground Truth:** INPUT001 (Missing Scanner import)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Lopez_Abigail_200117 - q3

**Ground Truth:** INPUT001 (Missing Scanner import)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

#### Rodriguez_Owen_200104 - q2

**Ground Truth:** OTHER002 (Hardcoded values instead of user input)

**gemini-2.5-flash-lite:** No misconceptions detected

**gpt-5-nano:** No misconceptions detected

---

### False Positives (Incorrect Detections)

#### Flores_Emily_200126 - q2

**Ground Truth:** None

**gemini-2.5-flash-lite:**

- **Scanner resource not closed** → **INPUT003**
  - *Topic:* Other | *Severity:* surface | *Category:* syntactic
  - *Confidence:* 60%
  - *Description:* The student did not explicitly close the Scanner resource after it was finished being used.
  - *Student believes:* It is not necessary to close the Scanner resource after reading input in a simple program.
  - *Correct understanding:* It is good practice to close resources like Scanner when they are no longer needed to free up system resources.
  - *Symptoms:* Scanner object is instantiated but never closed using `s.close()`.
  - *Root cause:* Lack of awareness of resource management best practices in Java.
  - *Remediation:* Add `s.close();` after the last line that uses the scanner.
  - *Related concepts:* Resource management, Java I/O streams
  - *Evidence:*
    - `Scanner s = new Scanner(System.in); 		double distance=s.nextDouble(); 		System.out.print("Enter mile`

**gpt-5-nano:** No misconceptions detected

---

#### Anderson_Noah_200113 - q4

**Ground Truth:** None

**gemini-2.5-flash-lite:**

- **Use of undefined variable**
  - *Topic:* Variables | *Severity:* minor | *Category:* syntactic
  - *Confidence:* 100%
  - *Description:* The student attempted to print a variable that was not declared or assigned a value within the program's scope.
  - *Student believes:* The student might believe that `v0` is a predefined or accessible variable, or they might have intended to print another variable but mistakenly typed `v0`.
  - *Correct understanding:* All variables used in a program must be declared and initialized (or assigned a value) before they can be used. If a variable is not defined, it leads to a compile-time or runtime error.
  - *Symptoms:* The line `System.out.println("v0=" + v0);` is present in the code., The variable `v0` is not declared or initialized anywhere in the code.
  - *Root cause:* Likely a typo or residual debugging code not removed before submission.
  - *Remediation:* Remove any lines that print undefined variables. If it was intended to print a different variable, ensure that variable is correctly declared and assigned a value.
  - *Related concepts:* Variable declaration and initialization, Scope of variables, Debugging practices
  - *Evidence:*
    - `System.out.println("v0=" + v0);`

**gpt-5-nano:**

- **Undefined variable v0 causing compile-time error**
  - *Topic:* Other | *Severity:* critical | *Category:* semantic
  - *Confidence:* 60%
  - *Description:* Code attempts to print a variable v0 that has not been defined, indicating a missing or misnamed variable.
  - *Student believes:* v0 contains the computed area or some meaningful value that should be printed.
  - *Correct understanding:* There is no v0 variable; the code should print the computed area stored in a real variable (area), and any non-existent variables must be removed.
  - *Evidence:*
    - `System.out.println("v0=" + v0);`

---

#### Hill_Michael_200140 - q2

**Ground Truth:** None

**gemini-2.5-flash-lite:**

- **Unformatted Currency Output**
  - *Topic:* Other | *Severity:* minor | *Category:* syntactic
  - *Confidence:* 90%
  - *Description:* The student displays a calculated currency value with excessive decimal places, which is not typical for currency representation.
  - *Student believes:* The student believes that printing the raw `double` value of the cost is sufficient and doesn't require specific formatting for currency.
  - *Correct understanding:* Currency values should generally be formatted to two decimal places to represent dollars and cents correctly. In Java, this can be achieved using `String.format()` or `DecimalFormat`.
  - *Symptoms:* Output includes many decimal places for the cost, e.g., $34.297872340425535.
  - *Root cause:* Lack of awareness or knowledge about standard currency formatting conventions in programming.
  - *Remediation:* Use `String.format("The cost of driving is $%.2f", cost);` to format the output to two decimal places.
  - *Related concepts:* String formatting, Number formatting
  - *Evidence:*
    - `System.out.println("The cost of driving is $" + cost);`

**gpt-5-nano:** No misconceptions detected

---

#### Robinson_Noah_200127 - q2

**Ground Truth:** None

**gemini-2.5-flash-lite:**

- **Potential for imprecise floating-point arithmetic**
  - *Topic:* Data Types | *Severity:* surface | *Category:* conceptual
  - *Confidence:* 60%
  - *Description:* The student used `double` for all calculations, which is standard but has inherent limitations in precision for financial calculations. While acceptable for CS1, it's a point for future learning.
  - *Student believes:* Using `double` is always sufficient for representing and calculating currency.
  - *Correct understanding:* While `double` is often used for general-purpose floating-point numbers, specific types like `BigDecimal` are preferred for financial calculations in production environments to avoid small precision errors inherent in binary floating-point representations.
  - *Symptoms:* The calculated cost has many decimal places., Potential for small rounding errors in complex financial calculations.
  - *Root cause:* Lack of exposure to specialized data types for financial calculations.
  - *Remediation:* For important financial calculations, investigate using `BigDecimal` in Java.
  - *Related concepts:* Floating-point arithmetic limitations, Java `BigDecimal` class
  - *Evidence:*
    - `double distance = reader.nextDouble(); double milesPerGallon = reader.nextDouble(); double price = r`

**gpt-5-nano:** No misconceptions detected

---

### True Negatives (Correctly Identified as Correct)

*23 submissions correctly identified as having no misconceptions.*

<details>
<summary>Click to expand list</summary>

- Ramirez_Zoey_200134 - q2
- Walker_Chloe_200124 - q4
- Walker_Chloe_200124 - q2
- Hill_Michael_200140 - q3
- Rodriguez_Owen_200104 - q1
- Anderson_Noah_200113 - q2
- Smith_Mason_200158 - q1
- Torres_Daniel_200148 - q4
- Flores_Emily_200126 - q3
- Hill_Michael_200140 - q4
- Torres_Daniel_200148 - q2
- Walker_Chloe_200124 - q3
- Robinson_Noah_200127 - q1
- Lopez_Abigail_200117 - q4
- Robinson_Noah_200127 - q4
- Rodriguez_Owen_200104 - q3
- Rodriguez_Owen_200104 - q4
- Flores_Emily_200126 - q1
- Smith_Mason_200158 - q4
- Smith_Mason_200158 - q2
- Robinson_Noah_200127 - q3
- Anderson_Noah_200113 - q1
- Walker_Chloe_200124 - q1

</details>
