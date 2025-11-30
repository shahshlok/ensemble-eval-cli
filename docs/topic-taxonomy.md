# Topic Taxonomy

## What is this about?

When AI models identify misconceptions, they describe them in their own words. One model might say "Data type issue" while another says "Wrong variable type" - but they mean the same thing.

**Topic taxonomy** is how we organize these varied descriptions into consistent categories that match what the course is actually teaching.

---

## The Problem: AI Models Are Creative

Here's what actually happens when two AI models analyze the same code:

```
Student Code:
    int velocity = 3.5;

Model A says:
    "Data type misconception - used int for decimal"

Model B says:  
    "Variable type error - should be double not int"

Model C says:
    "Inappropriate use of integer data types"
```

All three mean the same thing! But if we count them separately, our statistics are wrong.

**Without normalization:**
- "Data type misconception": 10 occurrences
- "Variable type error": 8 occurrences  
- "Inappropriate use of integer": 5 occurrences

**With normalization:**
- "Data Types": 23 occurrences ✓

---

## The Solution: Map Everything to Course Topics

### The 4 Course Topics

These come directly from the Assignment 2 rubric - what the assignment tests:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    THE 4 COURSE LEARNING OBJECTIVES                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────┐          ┌─────────────────────┐                  │
│  │      VARIABLES      │          │     DATA TYPES      │                  │
│  │                     │          │                     │                  │
│  │  • Declaring        │          │  • int vs double    │                  │
│  │  • Assigning        │          │  • Type conversions │                  │
│  │  • Using in         │          │  • Integer division │                  │
│  │    expressions      │          │  • Casting          │                  │
│  │  • Operator         │          │                     │                  │
│  │    precedence       │          │                     │                  │
│  └─────────────────────┘          └─────────────────────┘                  │
│                                                                             │
│  ┌─────────────────────┐          ┌─────────────────────┐                  │
│  │     CONSTANTS       │          │   READING INPUT     │                  │
│  │                     │          │                     │                  │
│  │  • Math.pow()       │          │  • Scanner usage    │                  │
│  │  • Math.sqrt()      │          │  • Getting user     │                  │
│  │  • Math library     │          │    input            │                  │
│  │  • Exponentiation   │          │  • Parsing values   │                  │
│  │                     │          │                     │                  │
│  └─────────────────────┘          └─────────────────────┘                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Why Keep Topics "Pure"?

**Imagine you're an instructor** looking at the report:

```
Topic Breakdown:
- Data Types: 24% of class affected
- Variables: 16% of class affected
```

You want to trust these numbers. If "Data Types" includes random stuff that doesn't belong, the number is meaningless.

**Our approach:** Only put misconceptions in a course topic if they **clearly** belong there. Everything else goes to "Other."

---

## The "Other" Category

Some misconceptions don't fit the 4 course topics:

| Misconception | Why it doesn't fit |
|--------------|-------------------|
| "Solved the wrong problem" | Not about Variables, Data Types, Constants, or Input |
| "Wrong formula for area" | It's about math, not about the programming concepts |
| "Incorrect output format" | About output, which isn't one of the 4 topics |

**Instead of forcing these into wrong categories, we put them in "Other" and break it down:**

```
OTHER (24 total)
    │
    ├── Problem Understanding (10)
    │   "Student solved a different problem"
    │   "Misinterpreted requirements"
    │
    ├── Formula Application (5)
    │   "Wrong distance formula"
    │   "Incorrect area calculation"  
    │
    ├── Output Issues (1)
    │   "Wrong output format"
    │
    └── Miscellaneous (8)
        Everything else
```

---

## How Normalization Works

### The Mapping Table

We have a lookup table that maps AI-generated phrases to our standard topics:

```python
TOPIC_MAPPING = {
    # These all map to "Data Types"
    "data types": "Data Types",
    "variables, data types": "Data Types",
    "inappropriate use of integer data types": "Data Types",
    "wrong data types for velocity/time": "Data Types",
    "type mismatch": "Data Types",
    "integer division": "Data Types",
    "int vs double": "Data Types",
    
    # These all map to "Variables"  
    "variables": "Variables",
    "variable declaration": "Variables",
    "operator precedence": "Variables",
    
    # These all map to "Constants"
    "math library": "Constants",
    "math.pow": "Constants",
    "exponentiation in java": "Constants",
    
    # These all map to "Reading input from the keyboard"
    "scanner": "Reading input from the keyboard",
    "input handling": "Reading input from the keyboard",
    
    # These go to "Other"
    "problem comprehension": "Other",
    "formula application": "Other",
    "output formatting": "Other",
    
    # These get FILTERED OUT (not real misconceptions)
    "syntax": "Syntax",
    "missing semicolon": "Syntax",
}
```

### The Normalization Process

```
AI says: "inappropriate use of integer data types"
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 1: Direct      │
            │   Match?              │
            │                       │
            │   Is it exactly       │
            │   "Data Types"?       │──── No
            │                       │
            └───────────────────────┘
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 2: Lookup      │
            │   Table?              │
            │                       │
            │   Is "inappropriate   │
            │   use of integer      │──── Yes! → "Data Types"
            │   data types" in      │
            │   our mapping?        │
            └───────────────────────┘
                        
                        
AI says: "geometry coordinate systems"
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 1: Direct      │
            │   Match?              │──── No
            └───────────────────────┘
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 2: Lookup      │
            │   Table?              │──── No
            └───────────────────────┘
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 3: Fuzzy       │
            │   Match?              │
            │                       │
            │   Does any mapping    │──── No
            │   key contain this    │
            │   phrase or vice      │
            │   versa?              │
            └───────────────────────┘
                        │
                        ▼
            ┌───────────────────────┐
            │   STEP 4: Default     │
            │                       │
            │   Put in "Other"      │──── "Other"
            │                       │
            └───────────────────────┘
```

---

## Real Examples

### Example 1: Clear Match

```
AI Output: "Student used int for velocity which should be double"
Topic Given: "variables, data types"

Normalization:
  "variables, data types" → lookup table → "Data Types" ✓
```

### Example 2: Fuzzy Match

```
AI Output: "Wrong operator precedence in calculation"
Topic Given: "operator precedence and usage"

Normalization:
  "operator precedence and usage" 
    → not in lookup table exactly
    → but "operator precedence" is a substring
    → maps to "Variables" ✓
```

### Example 3: Falls to Other

```
AI Output: "Student computed fuel cost instead of distance"
Topic Given: "task mismatch: distance vs fuel cost"

Normalization:
  "task mismatch: distance vs fuel cost"
    → not exact match
    → not in lookup table  
    → no fuzzy match
    → default to "Other"
```

---

## Why This Design?

### Research Validity

For a research paper, we need to say things like:

> "32% of students had misconceptions about Data Types"

This claim is only valid if "Data Types" contains **only** data type issues, not random other stuff.

### Instructor Usefulness  

An instructor needs actionable insights:

> "Focus extra lecture time on Data Types - many students are confused"

This advice only works if the category is accurate.

### Transparency

By having "Other" with subcategories, we're not hiding anything. You can see exactly what was categorized where and why.

---

## Technical Reference

### Constants

```python
CANONICAL_TOPICS = [
    "Variables",
    "Data Types", 
    "Constants",
    "Reading input from the keyboard",
]

OTHER_TOPIC = "Other"
SYNTAX_TOPIC = "Syntax"  # Gets filtered out
```

### Normalization Function

```python
def normalize_topic(topic: str) -> str:
    # Step 1: Direct match
    if topic in CANONICAL_TOPICS:
        return topic
    
    # Step 2: Lookup table (lowercase)
    topic_lower = topic.lower().strip()
    if topic_lower in TOPIC_MAPPING:
        return TOPIC_MAPPING[topic_lower]
    
    # Step 3: Fuzzy containment
    for key, canonical in TOPIC_MAPPING.items():
        if key in topic_lower or topic_lower in key:
            return canonical
    
    # Step 4: Default to Other
    return OTHER_TOPIC
```
