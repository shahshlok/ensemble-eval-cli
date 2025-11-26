# Misconception Clustering Methodology

## Overview
To address the issue of fragmented misconception reporting (where the same misconception is reported with slightly different names by different LLMs), we have implemented a **fuzzy clustering algorithm**.

## The Problem
LLMs often describe the same error using slightly different phrasing:
- "Incorrect formula application"
- "Formula Misapplication"
- "Incorrect application of formula"

Without clustering, these appear as 3 separate misconceptions with 1 occurrence each, obscuring the fact that "Formula issues" are actually a common problem (3 occurrences).

## The Solution: Fuzzy Clustering
We use the `difflib.SequenceMatcher` from Python's standard library to identify and group similar strings.

### Algorithm
1.  **Sort by Frequency**: We start with the most common misconception names to establish them as "canonical" centers for clusters.
2.  **Iterative Matching**: For each misconception name:
    - We compare it against existing canonical names.
    - We calculate a **similarity ratio** (0.0 to 1.0).
    - If the ratio exceeds the **threshold (0.8)**, the name is merged into the existing cluster.
    - If not, it becomes a new canonical name.

### Threshold: 0.8
The threshold of `0.8` was chosen empirically:
- **> 0.8**: Merges highly similar phrases (e.g., "Missing Semicolon" vs "Missing semicolon").
- **< 0.8**: Keeps distinct concepts separate (e.g., "Syntax Error" vs "Logic Error").

## Impact on Accuracy
**Is the data "100% correct"?**

The clustering is **heuristic**. It trades semantic precision for better aggregation.
- **Pros**: drastically reduces noise and highlights major trends.
- **Cons**: may occasionally merge two distinct but similarly-named errors (e.g., "Incorrect application of distance formula" vs "Incorrect application of Heron's formula" -> Ratio 0.84).

In the context of a high-level educational report, this aggregation is generally preferred as it highlights the *type* of struggle (e.g., "Formula Application") rather than the specific instance.
