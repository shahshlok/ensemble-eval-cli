# Architecture Documentation

Comprehensive architecture guide for the Ensemble Model Evaluation (EME) Framework.

## Table of Contents

- [System Overview](#system-overview)
- [Data Flow](#data-flow)
- [Module Organization](#module-organization)
- [Schema Design](#schema-design)
- [Extension Points](#extension-points)
- [Design Decisions](#design-decisions)

---

## System Overview

The EME Framework is designed as a research tool for automated code grading using multiple LLM models. The architecture follows a pipeline design with clear separation of concerns.

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     INPUT LAYER                              │
├─────────────────────────────────────────────────────────────┤
│  • Assignment Question (markdown)                            │
│  • Grading Rubric (JSON)                                     │
│  • Student Submission (code files)                           │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   PROMPT GENERATION                          │
├─────────────────────────────────────────────────────────────┤
│  prompts/                                                    │
│  ├─ direct_prompt.py    (Strategy 1: Direct grading)        │
│  ├─ reverse_prompt.py   (Strategy 2: Ideal solution first)  │
│  └─ eme_prompt.py       (Strategy 3: Ensemble method)       │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   LLM INTEGRATION                            │
├─────────────────────────────────────────────────────────────┤
│  utils/                                                      │
│  ├─ openai_client.py    (OpenAI structured outputs)         │
│  └─ openrouter_sdk.py   (OpenRouter + Instructor)           │
│                                                              │
│  Supports: OpenAI, Anthropic, Google, Moonshot, Meta, etc.  │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               STRUCTURED RESPONSE PARSING                    │
├─────────────────────────────────────────────────────────────┤
│  pydantic_models/                                            │
│  ├─ Pydantic v2 validation                                  │
│  ├─ Type safety enforcement                                 │
│  └─ Automatic JSON serialization                            │
│                                                              │
│  Response Model: LLMEvaluationResponse                       │
│  ├─ Scores (total, percentage, confidence)                  │
│  ├─ CategoryScore[] (per-rubric breakdown)                  │
│  ├─ Feedback (strengths, improvements)                      │
│  └─ Misconception[] (with evidence)                         │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              EVALUATION DOCUMENT ASSEMBLY                    │
├─────────────────────────────────────────────────────────────┤
│  EvaluationDocument (root model)                             │
│  ├─ Context (course/assignment/question)                    │
│  ├─ Submission (student files, metadata)                    │
│  ├─ Rubric (grading criteria)                               │
│  └─ Models: dict[str, ModelEvaluation]                      │
│       ├─ Model A: LLMEvaluationResponse + metadata          │
│       ├─ Model B: LLMEvaluationResponse + metadata          │
│       └─ Model N: ...                                        │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│         COMPARISON & ENSEMBLE ANALYSIS (Future)              │
├─────────────────────────────────────────────────────────────┤
│  pydantic_models/comparison/                                 │
│  ├─ Score statistics (mean, median, std, SEM)               │
│  ├─ Pairwise model comparisons                              │
│  ├─ Category-level agreement                                │
│  ├─ Misconception overlap analysis                          │
│  ├─ Confidence pattern detection                            │
│  ├─ Reliability metrics (ICC, Pearson, Spearman, α)         │
│  ├─ Ensemble decision (mean/median/weighted)                │
│  └─ Quality assessment & flags                              │
│                                                              │
│  Status: Schema complete, computation NOT YET IMPLEMENTED    │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT LAYER                              │
├─────────────────────────────────────────────────────────────┤
│  • JSON files (student_evals/*.json)                         │
│  • [Future] SQLite database (evaluations.db)                │
│  • [Future] Dashboard visualizations                        │
│  • [Future] Statistical reports                             │
└─────────────────────────────────────────────────────────────┘
```

### Core Principles

1. **Schema-First Design**: Pydantic models define the contract; all data flows through validated schemas
2. **Separation of Concerns**: Clear boundaries between prompt generation, LLM interaction, and data modeling
3. **Provider Agnostic**: Swap LLM providers without changing data models
4. **Type Safety**: Full Python type hints enable IDE support and catch errors early
5. **Extensibility**: Easy to add new prompting strategies, models, or comparison metrics
6. **Reproducibility**: All evaluations include metadata (model, version, run ID, config)

---

## Data Flow

### Detailed Pipeline Steps

```
1. INPUT LOADING
   │
   ├─ Read question file (question_cuboid.md)
   ├─ Parse rubric JSON (rubric_cuboid.json)
   └─ Load student code (student_submissions/{id}/*.java)
   │
   ▼
2. CONTEXT BUILDING
   │
   ├─ Create Context object (course, assignment, question metadata)
   ├─ Create Submission object (student ID, files, timestamp)
   └─ Create Rubric object (categories, points)
   │
   ▼
3. PROMPT CONSTRUCTION
   │
   ├─ Choose strategy (direct/reverse/eme)
   ├─ Build prompt with:
   │  ├─ Question text
   │  ├─ Rubric JSON
   │  └─ Student code
   └─ Format as messages list
   │
   ▼
4. LLM QUERY (Parallel for N models)
   │
   For each model in [model1, model2, ..., modelN]:
   │
   ├─ Send messages to LLM provider
   ├─ Request structured output (LLMEvaluationResponse)
   ├─ Parse JSON response with Pydantic
   └─ Validate all fields
   │
   ▼
5. WRAP IN ModelEvaluation
   │
   ├─ Add model metadata (name, provider, version)
   ├─ Add run tracking (run_id)
   ├─ Add configuration (prompt IDs, temperature)
   └─ Include LLMEvaluationResponse data
   │
   ▼
6. ASSEMBLE EvaluationDocument
   │
   ├─ Generate evaluation_id (unique per student/question/run)
   ├─ Set schema_version ("1.0.0")
   ├─ Set created_at timestamp
   ├─ Set created_by (script name)
   ├─ Attach Context
   ├─ Attach Submission
   ├─ Attach Rubric
   └─ Attach Models dict {model_key: ModelEvaluation}
   │
   ▼
7. SERIALIZATION
   │
   ├─ Call eval_doc.model_dump_json(indent=2)
   ├─ Write to student_evals/{student_id}_eval.json
   └─ [Future] Insert into SQLite database
   │
   ▼
8. ANALYSIS (Future)
   │
   ├─ Load EvaluationDocument from JSON
   ├─ Compute Comparison object
   │  ├─ Calculate score statistics
   │  ├─ Compute reliability metrics
   │  ├─ Detect misconception patterns
   │  └─ Generate ensemble decision
   ├─ Add Comparison to EvaluationDocument
   └─ Re-save with comparison data
```

### Data Model Hierarchy

```
EvaluationDocument (root)
│
├─ evaluation_id: str
├─ schema_version: str
├─ created_at: datetime
├─ created_by: str
│
├─ context: Context
│  ├─ course_id: str
│  ├─ course_name: str
│  ├─ assignment_id: int
│  ├─ assignment_title: str
│  ├─ question_source_path: str
│  ├─ question_id: str
│  ├─ question_title: str
│  └─ rubric_source_path: str
│
├─ submission: Submission
│  ├─ student_id: str
│  ├─ student_name: str
│  ├─ submitted_at: datetime
│  ├─ programming_language: str
│  └─ files: list[StudentFile]
│     ├─ path: str
│     └─ language: str
│
├─ rubric: Rubric
│  ├─ rubric_id: str
│  ├─ title: str
│  ├─ total_points: int
│  └─ categories: list[RubricCategory]
│     ├─ category_id: str
│     ├─ name: str
│     ├─ max_points: int
│     └─ description: str
│
└─ models: dict[str, ModelEvaluation]
   │
   ├─ "model_key_1": ModelEvaluation
   │  ├─ model_name: str
   │  ├─ provider: str
   │  ├─ model_version: str | None
   │  ├─ run_id: str
   │  ├─ config: Config
   │  │  ├─ system_prompt_id: str
   │  │  ├─ rubric_prompt_id: str
   │  │  ├─ temperature: float | None
   │  │  └─ max_tokens: int | None
   │  │
   │  ├─ scores: Scores
   │  │  ├─ total_points_awarded: float
   │  │  ├─ max_points: int
   │  │  ├─ percentage: float
   │  │  └─ confidence: float | None
   │  │
   │  ├─ category_scores: list[CategoryScore]
   │  │  ├─ category_id: str
   │  │  ├─ points_awarded: float
   │  │  ├─ max_points: int
   │  │  ├─ reasoning: str
   │  │  └─ confidence: float | None
   │  │
   │  ├─ feedback: Feedback
   │  │  ├─ strengths: list[str]
   │  │  └─ areas_for_improvement: list[str]
   │  │
   │  └─ misconceptions: list[Misconception]
   │     ├─ description: str
   │     ├─ confidence: float
   │     ├─ evidence: list[Evidence]
   │     │  ├─ code_snippet: str
   │     │  ├─ line_number: int | None
   │     │  └─ explanation: str
   │     ├─ identified_by_model: str | None
   │     └─ validated: bool
   │
   ├─ "model_key_2": ModelEvaluation
   │  └─ ...
   │
   └─ "model_key_N": ModelEvaluation
      └─ ...
```

---

## Module Organization

### Directory Structure

```
ensemble-eval-cli/
│
├─ pydantic_models/          # Schema definitions (data layer)
│  ├─ __init__.py            # Package exports
│  ├─ evaluation.py          # Root EvaluationDocument
│  │
│  ├─ context/               # Educational metadata
│  │  ├─ __init__.py
│  │  └─ models.py           # Context model
│  │
│  ├─ submission/            # Student submission data
│  │  ├─ __init__.py
│  │  └─ models.py           # Submission, StudentFile
│  │
│  ├─ rubric/                # Grading criteria
│  │  ├─ __init__.py
│  │  └─ models.py           # Rubric, RubricCategory
│  │
│  ├─ models/                # Per-model evaluation
│  │  ├─ __init__.py
│  │  └─ evaluation.py       # ModelEvaluation, LLMEvaluationResponse,
│  │                         # Config, Scores, CategoryScore, Feedback,
│  │                         # Misconception, Evidence
│  │
│  └─ comparison/            # Multi-model analysis (schema only)
│     ├─ __init__.py
│     ├─ models.py           # Comparison (root)
│     ├─ score_analysis.py  # ScoreSummary, PairwiseComparison, etc.
│     ├─ misconception_analysis.py  # MisconceptionSummary
│     ├─ confidence_analysis.py     # ConfidenceAnalysis
│     ├─ reliability.py      # ReliabilityMetrics (ICC, correlations)
│     └─ ensemble.py         # EnsembleDecision, EnsembleQuality, Flags
│
├─ prompts/                  # Prompt generation (presentation layer)
│  ├─ __init__.py
│  ├─ direct_prompt.py       # Direct grading strategy
│  ├─ reverse_prompt.py      # Reverse grading strategy
│  └─ eme_prompt.py          # Ensemble method strategy
│
├─ utils/                    # LLM integrations (integration layer)
│  ├─ openai_client.py       # OpenAI structured outputs API
│  └─ openrouter_sdk.py      # OpenRouter + Instructor library
│
├─ tests/                    # Test suite
│  └─ simple_openai_test.py  # Basic OpenAI client test
│
├─ student_submissions/      # Input: Student code
│  └─ {student_id}/
│     └─ *.java
│
├─ student_evals/            # Output: Evaluation JSON files
│  └─ {student_id}_eval.json
│
├─ grade_sergio.py           # Example: Complete workflow
├─ question_cuboid.md        # Example: Assignment question
├─ rubric_cuboid.json        # Example: Grading rubric
│
├─ pyproject.toml            # Project configuration
├─ .env                      # API keys (not in repo)
│
├─ README.md                 # User guide
├─ API.md                    # API reference
└─ ARCHITECTURE.md           # This file
```

### Module Responsibilities

| Module | Responsibility | Dependencies |
|--------|---------------|--------------|
| `pydantic_models/` | Define all data schemas | Pydantic v2 |
| `prompts/` | Build evaluation prompts | None |
| `utils/openai_client.py` | OpenAI integration | openai, pydantic_models |
| `utils/openrouter_sdk.py` | OpenRouter integration | instructor, openai, pydantic_models |
| `grade_sergio.py` | Example evaluation script | All of the above |
| `tests/` | Test suite | pytest, pydantic_models, utils |

### Import Graph

```
grade_sergio.py
   ├─> pydantic_models.Context
   ├─> pydantic_models.Submission
   ├─> pydantic_models.Rubric
   ├─> pydantic_models.EvaluationDocument
   ├─> pydantic_models.ModelEvaluation
   ├─> pydantic_models.LLMEvaluationResponse
   └─> utils.openrouter_sdk.get_structured_response

utils/openai_client.py
   ├─> openai (OpenAI client)
   └─> pydantic_models.ModelEvaluation

utils/openrouter_sdk.py
   ├─> instructor (for structured outputs)
   ├─> openai (OpenAI client with custom base URL)
   └─> pydantic.BaseModel (generic type bound)

pydantic_models/evaluation.py
   ├─> pydantic_models.Context
   ├─> pydantic_models.Submission
   ├─> pydantic_models.Rubric
   └─> pydantic_models.ModelEvaluation

pydantic_models/comparison/models.py
   ├─> pydantic_models.comparison.ScoreSummary
   ├─> pydantic_models.comparison.PairwiseComparison
   ├─> pydantic_models.comparison.MisconceptionSummary
   ├─> pydantic_models.comparison.ConfidenceAnalysis
   ├─> pydantic_models.comparison.ReliabilityMetrics
   └─> pydantic_models.comparison.EnsembleDecision
```

---

## Schema Design

### Design Philosophy

The schema is designed for:

1. **Research Reproducibility**: Every evaluation includes full provenance (model, version, config, timestamp)
2. **Ensemble Analysis**: Multiple model evaluations stored side-by-side for comparison
3. **Misconception Research**: Inductive approach where models identify patterns in student code
4. **Publication Quality**: Rich statistical metrics (ICC, Krippendorff's alpha) for academic papers
5. **Extensibility**: Easy to add new metrics without breaking existing evaluations

### Schema Versioning

**Current Version**: `1.0.0`

The `schema_version` field in `EvaluationDocument` enables evolution:
- Breaking changes increment major version
- New optional fields increment minor version
- Bug fixes increment patch version

**Validation**: The `EvaluationDocument` validator checks that `schema_version` matches the library's `MODELS_VERSION`.

### Comparison Schema (Not Yet Implemented)

The comparison schema is fully designed but computation logic is pending:

```python
# Future usage (when implemented):
from ensemble_eval import compute_comparison

eval_doc = EvaluationDocument(**json_data)

# Compute all comparison metrics
comparison = compute_comparison(eval_doc.models)

# Add to evaluation document
eval_doc.comparison = comparison

# Comparison includes:
# - Score statistics (mean, median, std dev, SEM, CV)
# - Pairwise model differences (absolute, percentage)
# - Category-level agreement
# - Misconception overlap (which models found which misconceptions)
# - Confidence analysis (patterns, consistency)
# - Reliability metrics (ICC, Pearson, Spearman, Krippendorff's α)
# - Ensemble decision (recommended score + strategy)
# - Quality assessment (ensemble value, diversity, agreement)
# - Automated flags (needs review, high disagreement, low confidence)
```

---

## Extension Points

### Adding a New LLM Provider

1. Create `utils/{provider}_sdk.py`
2. Implement function matching this signature:
   ```python
   def get_structured_response(
       messages: list[dict[str, str]],
       response_model: type[T],
       model: str
   ) -> T:
       # Provider-specific implementation
       pass
   ```
3. Update evaluation script to use new provider

**Example**:
```python
# utils/anthropic_sdk.py
from anthropic import Anthropic
from pydantic import BaseModel
from typing import TypeVar

T = TypeVar("T", bound=BaseModel)

client = Anthropic(api_key=os.environ["ANTHROPIC_API_KEY"])

def get_structured_response(messages, response_model, model="claude-3-opus"):
    # Use Anthropic's API to get structured output
    response = client.messages.create(
        model=model,
        messages=messages,
        # Anthropic-specific configuration
    )
    # Parse and validate with Pydantic
    return response_model(**response.content)
```

### Adding a New Prompting Strategy

1. Create `prompts/{strategy}_prompt.py`
2. Implement function:
   ```python
   def build_{strategy}_prompt(
       question: str,
       rubric: str,
       student_code: str
   ) -> str:
       return f"""Your custom prompt template"""
   ```
3. Use in evaluation script

### Adding New Comparison Metrics

1. Add field to appropriate model in `pydantic_models/comparison/`
2. Implement computation function
3. Update `compute_comparison()` (when implemented) to include new metric

**Example**:
```python
# pydantic_models/comparison/reliability.py
class ReliabilityMetrics(BaseModel):
    # Existing fields...
    fleiss_kappa: float | None = Field(None, description="Fleiss' kappa for 3+ raters")

# comparison_engine.py (future)
def compute_fleiss_kappa(model_scores: list[float]) -> float:
    # Implementation
    pass
```

---

## Design Decisions

### Why Pydantic?

- **Type Safety**: Catch errors at validation time, not runtime
- **JSON Schema**: Automatic generation for documentation and validation
- **IDE Support**: Autocomplete and type checking
- **Validation**: Built-in validators for common patterns (ranges, formats)
- **Serialization**: Zero-effort JSON round-tripping

### Why Separate LLMEvaluationResponse from ModelEvaluation?

- `LLMEvaluationResponse`: Pure LLM output (what the model says)
- `ModelEvaluation`: LLM output + metadata (which model, when, how)

This separation enables:
- Reusing the same response with different metadata
- Testing LLM responses without full evaluation context
- Clear boundary between "LLM said this" and "we ran this model"

### Why Dict for Models Instead of List?

```python
models: dict[str, ModelEvaluation]  # ✅ Chosen design
# vs
models: list[ModelEvaluation]       # ❌ Alternative
```

**Reasoning**:
- Fast lookup by model name: `eval_doc.models["gpt-4o"]`
- Prevents duplicate model evaluations
- Clearer intent when comparing specific models
- Easier to update/replace a specific model's evaluation

### Why Inductive Misconception Approach?

**Inductive** (chosen):
- LLM identifies misconceptions directly from student code
- More flexible; discovers unexpected patterns
- Better for research (what misconceptions do models find?)

**Deductive** (alternative):
- Predefined list of misconceptions to check for
- More consistent across evaluations
- Easier to aggregate statistics

We chose inductive because this is a research tool focused on discovery.

### Why No Database Yet?

Current state: JSON files only
Planned: SQLite database

**Rationale**:
- Schema must be stable first (now achieved with v1.0.0)
- JSON files are human-readable for debugging
- Easier to share results (single JSON file vs database export)
- Database adds complexity; defer until needed

**When database comes**:
- Store evaluations for historical analysis
- Query across students/assignments/models
- Track changes over time
- Generate cohort-level statistics

### Future: CLI vs Library?

Current: Library (import and use in scripts)
Future: Both

**Plan**:
1. Keep library interface for programmatic use
2. Add CLI for common workflows
3. CLI wraps library functions (Don't Repeat Yourself)

```bash
# Future CLI usage
ensemble-eval grade --student sergio --models gemini,claude,gpt4
ensemble-eval compare --evaluation sergio_eval.json
ensemble-eval analyze --cohort cs101_a1 --metric icc
```

---

## Performance Considerations

### LLM Query Parallelization

Current: Sequential queries (one model after another)
Future: Parallel queries

```python
# Current (sequential)
for model_name in models:
    response = get_structured_response(messages, LLMEvaluationResponse, model_name)

# Future (parallel)
import asyncio

async def evaluate_with_model(model_name):
    return await async_get_structured_response(messages, LLMEvaluationResponse, model_name)

responses = await asyncio.gather(*[evaluate_with_model(m) for m in models])
```

### Caching Strategy

For repeated evaluations (e.g., testing prompt changes):
- Cache LLM responses keyed by (model, prompt_hash, code_hash)
- Avoid expensive re-queries when inputs haven't changed
- Store cache in JSON or SQLite

### Memory Management

Current evaluation size:
- ~50KB per student evaluation (with 2 models)
- ~5MB for 100 students

For large cohorts (1000+ students):
- Stream processing (don't load all evaluations in memory)
- Database queries with pagination
- Lazy loading of comparison metrics

---

## Security & Privacy

### API Key Management

- Stored in `.env` file (never committed)
- Loaded via `python-dotenv`
- Environment variables preferred over hardcoded keys

### Student Data

- No PII in example files (anonymized student IDs)
- Instructors responsible for data handling policies
- Consider: encrypt student_evals/ directory for sensitive data

### LLM Provider Considerations

- Data sent to third-party APIs (OpenAI, OpenRouter)
- Check provider data retention policies
- For sensitive code: consider self-hosted models (Ollama, vLLM)

---

## Testing Strategy

### Current Tests

- `tests/simple_openai_test.py`: Basic OpenAI client test

### Needed Tests

1. **Unit Tests**
   - Pydantic model validation
   - Prompt builders
   - Utility functions

2. **Integration Tests**
   - Full evaluation workflow
   - Multi-model evaluation
   - JSON serialization round-trip

3. **Mocking LLM Responses**
   ```python
   from unittest.mock import patch

   def test_evaluation_with_mock():
       mock_response = LLMEvaluationResponse(...)

       with patch('utils.openrouter_sdk.get_structured_response', return_value=mock_response):
           result = grade_student(...)
           assert result.scores.percentage == 85.0
   ```

4. **Validation Tests**
   - Invalid schema versions
   - Out-of-range scores
   - Missing required fields

---

## Related Documentation

- [README.md](README.md) - User guide and quick start
- [API.md](API.md) - Complete API reference
- [pydantic_models/README.md](pydantic_models/README.md) - Model usage guide
