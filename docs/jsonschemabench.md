# JSONSchemaBench (arXiv:2501.10868)

## Why this matters for our harness
Structured output compliance is not guaranteed even when a JSON schema is provided. JSONSchemaBench positions structured output generation as a separate evaluation problem with its own failure modes (format compliance, constraint coverage, and output quality). This supports our decision to track **schema compliance** separately from **misconception detection accuracy**.

## High-level summary (from the arXiv abstract)
- Structured output generation is a critical capability for LLM applications.
- Constrained decoding is widely used to enforce JSON schema compliance.
- Systematic evaluation of constrained decoding is still limited.
- JSONSchemaBench provides a benchmark of 10K real-world JSON schemas and a framework to evaluate:
  - Efficiency of generating schema-compliant output
  - Coverage of diverse constraint types
  - Quality of generated outputs
- The paper evaluates multiple constrained decoding frameworks and reports practical limitations and strengths.

## Relevance to our evaluation design
- **Compliance vs. quality:** A model can be schema-compliant but still semantically wrong. We should report compliance metrics separately from detection metrics.
- **Instrument integrity:** A strict schema is not enough; we need to observe and quantify non-compliance (e.g., "no misconception" entries inside the list).

## Citation info (fill in authors later)
- Title: JSONSchemaBench: A Rigorous Benchmark of Structured Outputs for Language Models
- Venue: arXiv preprint (arXiv:2501.10868)
- Year: 2025
- URL: https://arxiv.org/abs/2501.10868

### BibTeX (author list TBD)
```
@article{jsonschemabench2025,
  title={JSONSchemaBench: A Rigorous Benchmark of Structured Outputs for Language Models},
  author={TBD},
  journal={arXiv preprint arXiv:2501.10868},
  year={2025},
  url={https://arxiv.org/abs/2501.10868}
}
```

