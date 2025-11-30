# Topic Taxonomy

## Overview

The topic taxonomy maps LLM-generated topic strings to canonical course topics. This ensures consistent categorization despite LLM output variability.

## Design Principles

1. **Keep course topics pure** - Only clearly matching misconceptions go to the 4 course topics
2. **Catch-all for ambiguity** - "Other" captures everything that doesn't clearly fit
3. **Filter mechanical errors** - Syntax errors are not conceptual misconceptions

## Canonical Topics

These are the 4 learning objectives from Assignment 2's rubric:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        CANONICAL TOPICS (FROM RUBRIC)                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐             │
│  │   VARIABLES     │  │   DATA TYPES    │  │   CONSTANTS     │             │
│  │                 │  │                 │  │                 │             │
│  │ • Declaring     │  │ • int vs double │  │ • Math.pow()    │             │
│  │ • Assigning     │  │ • Type casting  │  │ • Math.sqrt()   │             │
│  │ • Expressions   │  │ • Int division  │  │ • Math library  │             │
│  │ • Precedence    │  │ • Conversions   │  │                 │             │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘             │
│                                                                             │
│  ┌─────────────────────────────────┐  ┌─────────────────────────────────┐  │
│  │   READING INPUT FROM KEYBOARD  │  │           OTHER                 │  │
│  │                                 │  │                                 │  │
│  │ • Scanner usage                 │  │ • Problem Understanding         │  │
│  │ • Prompts                       │  │ • Formula Application           │  │
│  │ • Parsing input                 │  │ • Output Issues                 │  │
│  │ • Resource management           │  │ • Miscellaneous                 │  │
│  └─────────────────────────────────┘  └─────────────────────────────────┘  │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │   SYNTAX (FILTERED OUT - NOT A MISCONCEPTION)                       │   │
│  │                                                                      │   │
│  │ • Missing semicolons  • Typos  • Missing imports  • Formatting      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Topic Mapping

### Variables
```python
TOPIC_MAPPING = {
    "variables": "Variables",
    "variable declaration": "Variables",
    "declaring variables": "Variables",
    "operator precedence": "Variables",
    "incorrect operator precedence": "Variables",
}
```

### Data Types
```python
TOPIC_MAPPING = {
    "data types": "Data Types",
    "variables, data types": "Data Types",
    "variable declaration and data types": "Data Types",
    "inappropriate use of integer data types": "Data Types",
    "wrong data types for velocity/time": "Data Types",
    "type mismatch": "Data Types",
    "integer division": "Data Types",
    "type conversion": "Data Types",
    "casting": "Data Types",
    "int vs double": "Data Types",
}
```

### Constants
```python
TOPIC_MAPPING = {
    "constants": "Constants",
    "variables, constants": "Constants",
    "math library": "Constants",
    "mathematical formulas and libraries": "Constants",
    "exponentiation in java": "Constants",
    "math.sqrt": "Constants",
    "math.pow": "Constants",
}
```

### Reading Input from the Keyboard
```python
TOPIC_MAPPING = {
    "reading input from the keyboard": "Reading input from the keyboard",
    "input/output": "Reading input from the keyboard",
    "scanner": "Reading input from the keyboard",
    "input handling": "Reading input from the keyboard",
    "incorrect input handling": "Reading input from the keyboard",
    "resource management / input handling": "Reading input from the keyboard",
    "resource management": "Reading input from the keyboard",
}
```

### Other (Catch-all)
```python
TOPIC_MAPPING = {
    # Formula-related (not Math library)
    "formula application": OTHER_TOPIC,
    "incorrect formula application": OTHER_TOPIC,
    "distance calculation": OTHER_TOPIC,
    "acceleration formula / physics": OTHER_TOPIC,
    "heron's formula / triangle area": OTHER_TOPIC,
    
    # Problem understanding
    "problem comprehension": OTHER_TOPIC,
    "problem interpretation": OTHER_TOPIC,
    "task understanding": OTHER_TOPIC,
    
    # Output formatting
    "output formatting": OTHER_TOPIC,
    "displaying output": OTHER_TOPIC,
}
```

### Syntax (Filtered Out)
```python
TOPIC_MAPPING = {
    "syntax": SYNTAX_TOPIC,
    "syntax errors": SYNTAX_TOPIC,
    "java syntax": SYNTAX_TOPIC,
    "missing semicolon": SYNTAX_TOPIC,
    "compilation error": SYNTAX_TOPIC,
    "typo": SYNTAX_TOPIC,
}
```

## Normalization Function

```python
def normalize_topic(topic: str) -> str | None:
    """Normalize an LLM-generated topic to a canonical topic."""
    
    # Check direct match
    if topic in CANONICAL_TOPICS:
        return topic
    
    if topic == SYNTAX_TOPIC:
        return SYNTAX_TOPIC
    
    if topic == OTHER_TOPIC:
        return OTHER_TOPIC

    # Try lowercase lookup
    topic_lower = topic.lower().strip()
    if topic_lower in TOPIC_MAPPING:
        return TOPIC_MAPPING[topic_lower]

    # Fuzzy matching: check if any mapping key is contained in the topic
    for key, canonical in TOPIC_MAPPING.items():
        if key in topic_lower or topic_lower in key:
            return canonical

    # Default fallback: Other
    return OTHER_TOPIC
```

## Why This Design?

### Problem: LLM Output Variability

LLMs produce many variations of the same concept:
- "Data types"
- "Variables, Data types"
- "Inappropriate use of integer data types"
- "Wrong data types for velocity/time"

Without normalization, each would be counted separately, diluting the analysis.

### Solution: Layered Normalization

1. **Direct Match** - Check if topic is already canonical
2. **Lowercase Lookup** - Exact match in mapping table
3. **Fuzzy Containment** - Check if mapping key is substring
4. **Fallback** - Default to "Other"

### Why Keep Topics Pure?

For research validity, the 4 course topics should only contain misconceptions that clearly relate to those learning objectives. Ambiguous cases go to "Other" where they can be further analyzed without polluting the core metrics.
