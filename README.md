# Ensemble Model Evaluation (EME) Framework

A research tool for grading student code submissions using multiple LLM models, discovering misconception patterns, and evaluating ensemble grading strategies through comprehensive cross-model analytics.

## Status: Schema Complete, Implementation In Progress (v1.0.0)

The evaluation schema has been completely redesigned and finalized to support research-grade ensemble analysis and misconception pattern discovery. The schema is implemented using Pydantic models with comprehensive validation.

### What's Implemented ✅
- **Complete Pydantic schema**: All data models for evaluations, submissions, rubrics, and comparisons
- **Multi-model evaluation support**: OpenAI and OpenRouter SDK integration with Instructor
- **Structured LLM outputs**: Enforced JSON schema validation for all model responses
- **Misconception tracking**: Inductive approach with evidence linking and model attribution
- **Comprehensive comparison models**: Score analysis, reliability metrics, ensemble decisions, and quality assessment
- **Three grading strategies**: Direct, Reverse, and Ensemble Method Evaluation (EME) prompts

### In Development 🚧
- **Comparison computation engine**: Logic to calculate score statistics, ICC, Krippendorff's alpha, and ensemble decisions
- **Database persistence**: SQLite integration for historical evaluation storage
- **CLI interface**: Interactive menu system for running evaluations and analysis
- **Visualization tools**: Dashboards for instructors to review ensemble results

### Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    EVALUATION PIPELINE                       │
└─────────────────────────────────────────────────────────────┘

Input (Question + Rubric + Student Code)
    │
    ▼
Prompt Generation (Direct/Reverse/EME)
    │
    ▼
Multi-Model LLM Evaluation (OpenAI/OpenRouter)
    │
    ▼
Structured Response Parsing (Pydantic Models)
    │
    ▼
EvaluationDocument Assembly
    │
    ├──> Context (Course/Assignment/Question)
    ├──> Submission (Student Files)
    ├──> Rubric (Categories & Criteria)
    └──> Models (Per-Model Evaluations)
    │
    ▼
[Future] Comparison Analysis
    │
    ├──> Score Statistics & Agreement
    ├──> Misconception Overlap Analysis
    ├──> Confidence Patterns
    ├──> Reliability Metrics (ICC, Pearson, Spearman, Krippendorff's α)
    └──> Ensemble Decision & Quality Assessment
    │
    ▼
JSON Output (student_evals/*.json)
```

---

## Quick Start

### Installation

```bash
# Clone repository
git clone https://github.com/yourusername/ensemble-eval-cli
cd ensemble-eval-cli

# Install dependencies using uv (recommended)
uv sync

# Or using pip
pip install -e .
```

### Environment Setup

Create a `.env` file in the project root:

```env
# Required: API keys for LLM providers
OPENAI_API_KEY=sk-...
OPENROUTER_API_KEY=sk-or-...

# Optional: OpenRouter analytics (leave blank if not needed)
# OPENROUTER_SITE_URL=https://your-app-url.com
# OPENROUTER_SITE_NAME=Your App Name
```

### Run Your First Evaluation

```bash
# Run the example evaluation script for student Sergio
uv run python grade_sergio.py

# Output will be saved to: student_evals/sergio_eval.json
```

This will:
1. Load the Cuboid assignment question and rubric
2. Load Sergio's Java submission
3. Evaluate using multiple LLM models (Gemini 2.5 Flash Lite, Kimi K2)
4. Generate a complete evaluation document with scores, feedback, and misconceptions
5. Save the structured JSON output

---

## Features

### Evaluation Capabilities

✅ **Multi-Model Grading**
- Support for OpenAI models (GPT-4, GPT-4o, etc.)
- Support for OpenRouter providers (Gemini, Claude, Kimi, Llama, etc.)
- Parallel evaluation across multiple models for ensemble analysis
- Configurable model selection and parameters

✅ **Three Grading Strategies**
- **Direct Grading** (`prompts/direct_prompt.py`): Grade student code directly against rubric
- **Reverse Grading** (`prompts/reverse_prompt.py`): Generate ideal solution first, then compare student work
- **Ensemble Method Evaluation** (`prompts/eme_prompt.py`): Multi-model ensemble approach with structured 100-point rubric enforcement

✅ **Structured Output Validation**
- Pydantic models enforce type safety and schema compliance
- Automatic validation of all LLM responses
- Field-level descriptions for comprehensive documentation
- Support for nested structures and complex validation rules

✅ **Rich Misconception Detection**
- Inductive approach: models identify misconceptions directly from student code
- Evidence linking: each misconception tied to specific code lines and snippets
- Confidence scoring: models rate their certainty in each identified misconception
- Cross-model comparison: track which models identify which misconceptions

### Data Models & Schema

✅ **Comprehensive Pydantic Models** (`pydantic_models/`)
- **Context**: Course, assignment, and question metadata
- **Submission**: Student files, submission time, programming language
- **Rubric**: Hierarchical grading criteria with category weights
- **ModelEvaluation**: Per-model scores, category breakdowns, feedback, and misconceptions
- **Comparison** (designed, not yet computed):
  - Score statistics and pairwise model differences
  - Category-level agreement analysis
  - Misconception overlap and patterns
  - Confidence analysis and model characteristics
  - Inter-rater reliability metrics (ICC, Pearson, Spearman, Krippendorff's alpha)
  - Ensemble decision strategies (mean, median, weighted)
  - Quality assessment and automated review flags

### Integration & Extensibility

✅ **Flexible LLM Integration**
- OpenAI structured outputs API via `utils/openai_client.py`
- OpenRouter SDK with Instructor library via `utils/openrouter_sdk.py`
- Generic interface for adding new providers
- Fallback handling and error recovery

✅ **Data Validation**
- Schema version tracking and validation
- Type checking across all models
- Business logic validation (score calculations, percentages)
- Graceful error handling with detailed error messages

---

## Project Structure

```text
ensemble-eval-cli/
├── grade_sergio.py              # Example: Complete evaluation workflow for one student
├── tests/
│   └── simple_openai_test.py   # Unit test for OpenAI client evaluation
│
├── prompts/                     # Grading prompt builders
│   ├── direct_prompt.py        # Direct grading against rubric
│   ├── reverse_prompt.py       # Generate ideal solution → compare
│   └── eme_prompt.py           # Ensemble method with 100-pt rubric
│
├── utils/                       # LLM integration utilities
│   ├── openai_client.py        # OpenAI structured outputs wrapper
│   └── openrouter_sdk.py       # OpenRouter + Instructor integration
│
├── pydantic_models/             # Complete schema (v1.0.0)
│   ├── evaluation.py           # Root EvaluationDocument model
│   ├── README.md               # Model usage documentation
│   ├── context/                # Course, assignment, question metadata
│   │   └── models.py
│   ├── submission/             # Student files and submission data
│   │   └── models.py
│   ├── rubric/                 # Grading criteria and categories
│   │   └── models.py
│   ├── models/                 # Per-model evaluation results
│   │   └── evaluation.py      # Scores, feedback, misconceptions
│   └── comparison/             # Multi-model analysis (schema complete)
│       ├── models.py           # Main Comparison model
│       ├── score_analysis.py  # Score statistics and pairwise comparisons
│       ├── misconception_analysis.py  # Misconception overlap
│       ├── confidence_analysis.py     # Confidence patterns
│       ├── reliability.py      # ICC, Pearson, Spearman, Krippendorff's α
│       └── ensemble.py         # Ensemble decisions and quality
│
├── student_submissions/         # Input: Student code organized by ID
│   └── Diaz_Sergio_100029/
│       └── Cuboid.java
│
├── student_evals/              # Output: Generated evaluation JSON files
│   └── sergio_eval.json
│
├── question_cuboid.md          # Example: Assignment specification
├── rubric_cuboid.json          # Example: Grading rubric (100 points)
├── pyproject.toml              # Project configuration and dependencies
└── .env                        # API keys (not in repo)
```

### Key Directories Explained

- **`pydantic_models/`**: Complete v1.0.0 schema implementation
  - All models use Pydantic v2 with comprehensive field descriptions
  - Enables type-safe LLM outputs and JSON validation
  - Models are fully documented in `pydantic_models/README.md`

- **`prompts/`**: Three distinct grading strategies
  - Each prompt builder returns a formatted string for LLM evaluation
  - Can be extended with additional strategies

- **`utils/`**: LLM provider integrations
  - `openai_client.py`: Uses OpenAI's `responses.parse()` API for structured outputs
  - `openrouter_sdk.py`: Uses Instructor library with `Mode.JSON` for multi-provider support

- **`student_submissions/`**: Organized by student ID
  - Each student gets a subdirectory with their code files
  - Currently supports single-file submissions (e.g., one `.java` file)

- **`student_evals/`**: Output directory for evaluation JSON
  - Each file represents a complete `EvaluationDocument`
  - Includes context, submission, rubric, and all model evaluations

---

## Usage Guide

### Basic Evaluation Workflow

The main evaluation script `grade_sergio.py` demonstrates the complete workflow:

```python
from pydantic_models import (
    Context, Submission, Rubric, EvaluationDocument,
    ModelEvaluation, Config, LLMEvaluationResponse
)
from utils.openrouter_sdk import get_structured_response

# 1. Load assignment materials
with open("question_cuboid.md") as f:
    question_text = f.read()

with open("rubric_cuboid.json") as f:
    rubric_data = json.load(f)

# 2. Load student submission
student_id = "Diaz_Sergio_100029"
with open(f"student_submissions/{student_id}/Cuboid.java") as f:
    student_code = f.read()

# 3. Construct evaluation prompt
prompt = f"""
You are an expert grader for a Computer Science assignment.

**Question:**
{question_text}

**Rubric:**
{json.dumps(rubric_data)}

**Student Submission:**
```java
{student_code}
```

Evaluate the student's submission based on the provided rubric.
"""

# 4. Get structured LLM responses from multiple models
models_to_test = ["google/gemini-2.5-flash-lite", "moonshotai/kimi-k2-0905"]
model_evals = {}

for model_name in models_to_test:
    # Get structured response matching LLMEvaluationResponse schema
    llm_response = get_structured_response(
        messages=[{"role": "user", "content": prompt}],
        response_model=LLMEvaluationResponse,
        model=model_name
    )

    # Wrap in ModelEvaluation with metadata
    model_eval = ModelEvaluation(
        model_name=model_name,
        provider="openrouter",
        run_id=f"run_{uuid.uuid4().hex[:8]}",
        config=Config(system_prompt_id="simple_direct_prompt", rubric_prompt_id="rubric_v1"),
        scores=llm_response.scores,
        category_scores=llm_response.category_scores,
        feedback=llm_response.feedback,
        misconceptions=llm_response.misconceptions,
    )
    model_evals[model_name.split("/")[-1]] = model_eval

# 5. Assemble complete evaluation document
eval_doc = EvaluationDocument(
    evaluation_id=f"eval_{uuid.uuid4()}",
    schema_version="1.0.0",
    created_at=datetime.now(timezone.utc),
    created_by="grade_sergio.py",
    context=Context(...),        # Course/assignment metadata
    submission=Submission(...),  # Student info and files
    rubric=Rubric(...),          # Grading criteria
    models=model_evals,          # All model evaluations
)

# 6. Save to JSON
with open("student_evals/sergio_eval.json", "w") as f:
    f.write(eval_doc.model_dump_json(indent=2))
```

### Working with Evaluation Results

```python
# Load evaluation from JSON
with open("student_evals/sergio_eval.json") as f:
    eval_data = json.load(f)

evaluation = EvaluationDocument(**eval_data)

# Access student info
print(f"Student: {evaluation.submission.student_name}")
print(f"Assignment: {evaluation.context.assignment_title}")

# Compare scores across models
for model_name, model_eval in evaluation.models.items():
    print(f"{model_name}: {model_eval.scores.percentage}%")
    print(f"  Confidence: {model_eval.scores.confidence}")

# Review misconceptions
for model_name, model_eval in evaluation.models.items():
    print(f"\n{model_name} identified {len(model_eval.misconceptions)} misconceptions:")
    for misc in model_eval.misconceptions:
        print(f"  - {misc.description} (confidence: {misc.confidence})")
        for evidence in misc.evidence:
            print(f"    Line {evidence.line_number}: {evidence.code_snippet}")
```

---

## Structured LLM Outputs

The framework uses **Pydantic models** to ensure reliable, schema-validated evaluations from language models. This eliminates parsing errors and guarantees consistent output structure.

### How It Works

1. **Define the schema** using Pydantic models (`pydantic_models/`)
2. **LLM generates response** matching the schema structure
3. **Automatic validation** ensures all fields are present and correctly typed
4. **Type-safe access** to all evaluation data

### Evaluation Schema

The `LLMEvaluationResponse` model captures everything a grading model provides:

```python
class LLMEvaluationResponse(BaseModel):
    scores: Scores  # Overall score, max points, percentage, confidence
    category_scores: list[CategoryScore]  # Per-rubric-category breakdown
    feedback: Feedback  # Strengths and areas for improvement
    misconceptions: list[Misconception]  # Identified misconceptions with evidence
```

#### Scores
- `total_points_awarded`: Sum across all categories
- `max_points`: Total possible points
- `percentage`: Calculated percentage score
- `confidence`: Model's confidence in the overall evaluation (0-1)

#### CategoryScore
- `category_id`: Links to rubric category
- `points_awarded`: Score for this category
- `max_points`: Maximum possible for this category
- `reasoning`: Explanation of the score
- `confidence`: Model's confidence in this category assessment

#### Feedback
- `strengths`: List of positive aspects in the student's submission
- `areas_for_improvement`: List of constructive feedback points

#### Misconception
- `description`: What the misconception is
- `confidence`: How confident the model is (0-1)
- `evidence`: List of Evidence objects with:
  - `code_snippet`: Exact code demonstrating the misconception
  - `line_number`: Where it occurs
  - `explanation`: Why this demonstrates the misconception
- `identified_by_model`: Which model identified this (for multi-model comparison)
- `validated`: Whether a human has confirmed this misconception

### Provider Integrations

**OpenAI** (`utils/openai_client.py`):
```python
from utils.openai_client import evaluation_with_openai

result = evaluation_with_openai(
    question="Assignment question text",
    rubric="Rubric as JSON string",
    student_code="Student's code",
    model="gpt-4o"
)
# Returns validated ModelEvaluation instance
```

**OpenRouter** (`utils/openrouter_sdk.py`):
```python
from utils.openrouter_sdk import get_structured_response
from pydantic_models import LLMEvaluationResponse

response = get_structured_response(
    messages=[{"role": "user", "content": prompt}],
    response_model=LLMEvaluationResponse,
    model="google/gemini-2.5-flash-lite"
)
# Returns validated LLMEvaluationResponse instance
```

### Validation Benefits

- **Type Safety**: IDE autocomplete and type checking
- **No Parsing Errors**: Pydantic handles all validation
- **Clear Error Messages**: Know exactly what's wrong if validation fails
- **Schema Versioning**: Track schema evolution over time
- **JSON Round-Trip**: Serialize to JSON and back without data loss

```python
# Validation example
try:
    eval_doc = EvaluationDocument(**json_data)
except ValidationError as e:
    print(f"Validation failed: {e}")
    # Detailed error messages show exactly which fields failed
```

---

## Testing

### Running Tests

```bash
# Run all tests
uv run pytest

# Run specific test file
uv run pytest tests/simple_openai_test.py

# Run with verbose output
uv run pytest -v
```

### Current Test Coverage

The project currently has minimal test coverage (~5-10%):

**Tested**:
- `tests/simple_openai_test.py`: Basic OpenAI client evaluation test
  - Tests `evaluation_with_openai` function
  - Validates ModelEvaluation structure
  - Skips if OPENAI_API_KEY not set

**Not Yet Tested**:
- OpenRouter integration
- Comparison and ensemble analysis logic (not yet implemented)
- Score calculation accuracy
- Misconception detection
- Prompt builders
- Error handling scenarios

### Contributing Tests

When adding features, please:
1. Write unit tests for new functions
2. Add integration tests for end-to-end workflows
3. Test validation logic with invalid data
4. Verify error handling edge cases

---

## Configuration

### Supported LLM Models

**OpenAI Models** (via `utils/openai_client.py`):
- GPT-4o
- GPT-4
- GPT-3.5-turbo
- Any model supporting OpenAI's structured outputs API

**OpenRouter Models** (via `utils/openrouter_sdk.py`):
- Google Gemini models (e.g., `google/gemini-2.5-flash-lite`)
- Anthropic Claude models (e.g., `anthropic/claude-3-opus`)
- Moonshot AI models (e.g., `moonshotai/kimi-k2-0905`)
- Meta Llama models
- Many more providers (see [OpenRouter documentation](https://openrouter.ai/docs))

### Customizing Evaluation

**Modify models in `grade_sergio.py`**:
```python
models_to_test = [
    "google/gemini-2.5-flash-lite",
    "anthropic/claude-3-sonnet",
    "openai/gpt-4o"
]
```

**Choose prompting strategy**:
- `prompts/direct_prompt.py`: Direct evaluation
- `prompts/reverse_prompt.py`: Generate ideal solution first
- `prompts/eme_prompt.py`: Ensemble method with enforced structure

**Customize rubric** (`rubric_cuboid.json`):
```json
{
  "totalPoints": 100,
  "categories": [
    {
      "name": "Correctness",
      "points": 40,
      "description": "Code produces correct output"
    },
    {
      "name": "Code Quality",
      "points": 30,
      "description": "Code is well-structured and readable"
    }
  ]
}
```

---

## Output Format

### Evaluation JSON Structure

Each evaluation file (e.g., `student_evals/sergio_eval.json`) is a complete `EvaluationDocument`:

```json
{
  "evaluation_id": "eval_a1b2c3d4",
  "schema_version": "1.0.0",
  "created_at": "2025-11-19T10:30:00Z",
  "created_by": "grade_sergio.py",
  "context": {
    "course_id": "CS101",
    "course_name": "Intro to CS",
    "assignment_id": 1,
    "assignment_title": "Cuboid",
    "question_id": "q1",
    "question_title": "Cuboid Class"
  },
  "submission": {
    "student_id": "Diaz_Sergio_100029",
    "student_name": "Sergio Diaz",
    "submitted_at": "2025-11-19T09:00:00Z",
    "programming_language": "Java",
    "files": [
      {"path": "Cuboid.java", "language": "Java"}
    ]
  },
  "rubric": {
    "rubric_id": "rubric_cuboid_v1",
    "title": "Cuboid Assignment Rubric",
    "total_points": 100,
    "categories": [...]
  },
  "models": {
    "gemini-2.5-flash-lite": {
      "model_name": "google/gemini-2.5-flash-lite",
      "provider": "openrouter",
      "run_id": "run_abc123",
      "scores": {
        "total_points_awarded": 85,
        "max_points": 100,
        "percentage": 85.0,
        "confidence": 0.9
      },
      "category_scores": [...],
      "feedback": {
        "strengths": ["Clear variable naming", "Proper encapsulation"],
        "areas_for_improvement": ["Missing input validation"]
      },
      "misconceptions": [...]
    },
    "kimi-k2-0905": {...}
  }
}
```

### JSON Schema

All models are self-documenting via Pydantic. To view the full JSON schema:

```python
from pydantic_models import EvaluationDocument

# Get JSON Schema
schema = EvaluationDocument.model_json_schema()
print(json.dumps(schema, indent=2))
```

---

## Development Roadmap

### Phase 1: Schema & Infrastructure ✅ **COMPLETE**
- [x] Finalize evaluation JSON schema (v1.0.0)
- [x] Define misconception structure (inductive approach)
- [x] Design comprehensive comparison metrics
- [x] Document all fields and interpretations
- [x] Implement complete Pydantic model hierarchy
- [x] Integrate OpenAI and OpenRouter SDKs
- [x] Create example evaluation workflow (`grade_sergio.py`)

### Phase 2: Comparison Engine **IN PROGRESS**
- [ ] Implement comparison computation logic
  - [ ] Score statistics calculation (mean, median, std dev, SEM)
  - [ ] Pairwise model comparisons
  - [ ] Category-level agreement analysis
  - [ ] Misconception overlap detection
  - [ ] Confidence pattern analysis
- [ ] Implement reliability metrics
  - [ ] Intraclass Correlation Coefficient (ICC)
  - [ ] Pearson correlation
  - [ ] Spearman rank correlation
  - [ ] Krippendorff's alpha
- [ ] Implement ensemble decision strategies
  - [ ] Mean/median score aggregation
  - [ ] Weighted ensemble (by confidence)
  - [ ] Quality assessment metrics

### Phase 3: CLI & Database
- [ ] Build interactive CLI (using Typer & Rich)
  - [ ] Batch evaluation interface
  - [ ] Model selection and configuration
  - [ ] Progress reporting
- [ ] Implement SQLite database persistence
  - [ ] Schema migration from JSON
  - [ ] Query interface for historical data
  - [ ] Export and analysis tools

### Phase 4: Analysis & Visualization
- [ ] LLM-powered misconception pattern analysis
- [ ] Statistical analysis scripts
- [ ] Instructor dashboard with visualizations
  - [ ] Score distributions
  - [ ] Model agreement heatmaps
  - [ ] Misconception frequency charts
  - [ ] Confidence vs accuracy plots

### Phase 5: Research Applications
- [ ] Ensemble strategy comparison studies
- [ ] Model reliability & calibration analysis
- [ ] Misconception clustering and insights
- [ ] Publication-ready data exports

---

## Current Limitations & Future Work

### Known Limitations
- **Single-file submissions**: Currently supports one code file per student
- **No CLI**: Evaluation requires running Python scripts directly
- **No database**: Evaluations stored as individual JSON files
- **Comparison logic not implemented**: Models defined but computation not yet built
- **Manual model configuration**: Must edit code to change models/parameters

### Planned Enhancements
1. **Multi-file submission support**
   - Handle projects with multiple files (main class, tests, utilities)
   - Concatenate files with clear delimiters for LLM context
   - Track individual file metadata
   - Support mixed file types (`.java`, `.py`, `.cpp`, etc.)

2. **Batch evaluation**
   - Process entire classrooms of students
   - Parallel model queries for efficiency
   - Progress tracking and resume capability

3. **Interactive CLI**
   - Menu-driven interface for non-programmers
   - Configuration wizards
   - Result browsing and filtering

4. **Advanced analytics**
   - Cohort-level statistics
   - Longitudinal tracking across assignments
   - Anomaly detection (unusual scoring patterns)
   - Model calibration studies

---

## Contributing

When adding features:

1. **Follow architectural patterns**:
   - Pydantic models in `pydantic_models/`
   - LLM integrations in `utils/`
   - Prompt templates in `prompts/`
   - Evaluation workflows as standalone scripts (like `grade_sergio.py`)

2. **Maintain schema integrity**:
   - All changes to data models must update Pydantic schemas
   - Preserve backward compatibility with schema versioning
   - Add comprehensive field descriptions
   - Include validation logic where appropriate

3. **Document thoroughly**:
   - Update README.md for user-facing changes
   - Add docstrings to all functions and classes
   - Include usage examples in docstrings
   - Update `pydantic_models/README.md` for schema changes

4. **Write tests**:
   - Unit tests for new functions
   - Integration tests for workflows
   - Validation tests for edge cases
   - Mock LLM responses for deterministic testing

---

## Research Context

**Project:** Honours Thesis Research - Ensemble Model Evaluation for Code Grading
**Institution:** University of British Columbia Okanagan (UBCO)
**Course:** COSC 499 - Honours Thesis
**Researcher:** Shlok Shah
**Academic Year:** 2024-2025

## Citation

If you use this schema or framework in your research, please cite:

```bibtex
@software{eme_framework_2025,
  author = {Shah, Shlok},
  title = {Ensemble Model Evaluation Framework for Code Grading},
  year = {2025},
  institution = {University of British Columbia Okanagan},
  note = {Honours Thesis Research Project}
}
```

## License

TBD (Academic Research Project)
