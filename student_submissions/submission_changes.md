# Realistic Student Submissions - Detailed Grading Guide

This document details the specific modifications made to the student submissions to simulate a realistic introductory CS class. Use this to cross-reference your grading pipeline's output.

## 1. The Syntax Strugglers (Compilation Failures)
*These submissions contain syntax errors and should fail to compile.*

### **Adams, Sophia (100050)**
*   **What they did:**
    *   Forgot the semicolon `;` after the `return` statement in the negative input check.
    *   Used lowercase `system.out.println` instead of `System.out.println` in the eligibility check.
*   **Grading Check:** Should fail compilation. Error log should mention "illegal start of expression" or "package system does not exist".

### **Brooks, Caleb (100051)**
*   **What they did:**
    *   Used lowercase `string` instead of `String` for the email variable declaration.
    *   Completely forgot the final closing brace `}` for the `main` method/class.
*   **Grading Check:** Should fail compilation. Error log should mention "reached end of file while parsing".

### **Campbell, Laura (100052)**
*   **What they did:**
    *   Forgot to `import java.util.Scanner;`.
    *   Used assignment `=` instead of equality `==` inside an `if` condition: `if(age = 18 ...`.
*   **Grading Check:** Should fail compilation. Error log should mention "cannot find symbol class Scanner" and "incompatible types: int cannot be converted to boolean".

### **Davis, Nina (100053)**
*   **What they did:**
    *   Capitalized the `Else` keyword in an `else if` block.
*   **Grading Check:** Should fail compilation. Error log should mention "not a statement" or "';' expected".

---

## 2. The Logic Fumblers (Runtime & Logic Errors)
*These submissions compile but produce incorrect output for specific test cases.*

### **Edwards, Liam (100054)**
*   **What they did:**
    *   **Integer Division Bug:** Calculated the accident surcharge using `premium * (1/4)`. In Java, `1/4` evaluates to `0` because both operands are integers.
*   **Grading Check:** The accident surcharge will always be $0. A driver with accidents will be undercharged.

### **Flores, Isabella (100055)**
*   **What they did:**
    *   **Boolean Logic Bug:** Used `&&` instead of `||` in the eligibility check: `if(age <= 17 && accident > 2)`.
*   **Grading Check:** A driver who is just underage (e.g., 16 with 0 accidents) will be incorrectly marked as **eligible**. A driver with many accidents (e.g., 40 with 5 accidents) will also be incorrectly marked as **eligible**. Only a driver who is BOTH underage AND has accidents will be rejected.

### **Green, Oscar (100056)**
*   **What they did:**
    *   **Order of Operations Bug:** Calculated the accident surcharge based on the *original base price* ($600) rather than the *current running total*.
    *   `total = total + (600 * 0.25);`
*   **Grading Check:** A driver under 24 with accidents will be undercharged. They should pay 25% on ($600 + $200), but they only pay 25% on $600.

### **Hall, Priya (100057)**
*   **What they did:**
    *   **Off-by-One Error:** Used `if (age <= 24)` for the age surcharge instead of `if (age < 24)`.
*   **Grading Check:** A 24-year-old driver will be incorrectly charged the $200 surcharge.

### **Inoue, Takumi (100058)**
*   **What they did:**
    *   **Control Flow Bug:** Printed "You are not eligible..." but forgot the `return;` statement.
*   **Grading Check:** The program will print the error message but then **continue executing**, printing the insurance cost and asking for an email anyway.

### **Jackson, Riley (100059)**
*   **What they did:**
    *   **Math Bug:** Misunderstood "25%" and simply added $25 to the bill: `bill += 25;`.
*   **Grading Check:** Drivers with accidents will be significantly undercharged (paying +$25 instead of +$150 or +$200).

---

## 3. The Style Disasters (Messy Code)
*These submissions work correctly but are poorly written. Grading should pass, but style checks (if any) should fail.*

### **Kim, Soojin (100060)**
*   **What they did:** Wrote the entire program on a single line (or very few lines) with zero indentation.
*   **Grading Check:** Functionally correct.

### **Lewis, Aiden (100061)**
*   **What they did:** Used terrible variable names: `a` for age, `b` for accidents, `c` for cost, `d` for email.
*   **Grading Check:** Functionally correct.

### **Morgan, Jade (100062)**
*   **What they did:** Added excessive, distracting comments and `TODO` / `FIXME` tags that don't make sense.
*   **Grading Check:** Functionally correct.

### **Nguyen, Tony (100063)**
*   **What they did:** Used inconsistent spacing (e.g., `if( age<18 )`) and parsed inputs manually from a string split instead of using `nextInt` directly.
*   **Grading Check:** Functionally correct.

### **Ortiz, Marco (100064)**
*   **What they did:** Mixed K&R and Allman brace styles randomly and inserted random blank lines throughout the code.
*   **Grading Check:** Functionally correct.

---

## 4. The Over-Complicators (Weird Implementations)
*These submissions are valid but use strange or advanced constructs for a beginner problem.*

### **Patel, Sneha (100065)**
*   **What they did:** Used an integer array `int[] data` to store age and accidents instead of named variables.
*   **Grading Check:** Functionally correct.

### **Quinn, Sam (100066)**
*   **What they did:** Created a `static inner class Calculator` to handle the logic, separating it from `main`.
*   **Grading Check:** Functionally correct.

### **Rivera, Lucy (100067)**
*   **What they did:** Read inputs as `String`s using `next()` and then parsed them using `Integer.parseInt()`.
*   **Grading Check:** Functionally correct, but fragile if non-numeric input is provided (though the spec assumes integers).

### **Smith, Hunter (100068)**
*   **What they did:** Used a "nested if/else hell" structure. Instead of sequential checks, they nested every condition inside the previous one.
*   **Grading Check:** Functionally correct, but logic is duplicated in branches.

### **Thompson, Ella (100069)**
*   **What they did:** Wrapped the entire logic in a `while(true)` loop and used `break` to exit.
*   **Grading Check:** Functionally correct.

---

## 5. The Scanner Victims (Input Handling Issues)
*These submissions struggle with `java.util.Scanner` quirks.*

### **Underwood, Max (100070)**
*   **What they did:** Used `nextInt()` for numbers and then immediately called `nextLine()` for the email.
*   **Grading Check:** The `nextLine()` call will consume the newline character left over from `nextInt()` and read an empty string for the email. The program will likely print "Invalid email" immediately without letting the user type.

### **Vasquez, Ivan (100071)**
*   **What they did:** Created a `new Scanner(System.in)` for *every single input*.
*   **Grading Check:** This usually works in simple interactive console runs but can fail or behave unexpectedly with automated testing tools that pipe input via stdin, as multiple scanners might buffer incorrectly.

### **Walker, Emily (100072)**
*   **What they did:** Closed the scanner `reader.close()` *before* reading the email.
*   **Grading Check:** The program will crash with an `IllegalStateException` when it tries to read the email.

---

## 6. The Control Group (Correct)
*   **Xu, Chen (100073)**
*   **Young, Bella (100074)**
*   **Zhang, Leo (100075)**
*   **Grading Check:** These should pass all tests and compile perfectly.
