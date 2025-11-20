# API Documentation

Complete reference for the Ensemble Model Evaluation Framework's data models, utilities, and integration points.

## Table of Contents

- [Pydantic Models](#pydantic-models)
- [Utility Functions](#utility-functions)
- [Prompt Builders](#prompt-builders)
- [LLM Integration](#llm-integration)
- [Usage Examples](#usage-examples)

---

## Pydantic Models

All models are located in `pydantic_models/` and use Pydantic v2 for validation.

### Root Model

#### `EvaluationDocument`

**Location**: `pydantic_models/evaluation.py`

Complete evaluation document representing one student's submission graded by multiple models.

**Fields**:
- `evaluation_id: str` - Unique ID for this evaluation (student + question + run)
- `schema_version: str` - Schema version (currently "1.0.0")
- `created_at: datetime` - When the evaluation was generated
- `created_by: str` - Pipeline/script that produced this
- `context: Context` - Educational context metadata
- `submission: Submission` - Student submission data
- `rubric: Rubric` - Grading criteria
- `models: dict[str, ModelEvaluation]` - Per-model evaluation results

**Validation**:
- Schema version must match library version
- All nested models validated recursively

**Example**:
```python
from pydantic_models import EvaluationDocument

eval_doc = EvaluationDocument(
    evaluation_id="eval_abc123",
    schema_version="1.0.0",
    created_at=datetime.now(timezone.utc),
    created_by="my_script.py",
    context=context,
    submission=submission,
    rubric=rubric,
    models={"gpt-4o": model_eval_1, "gemini": model_eval_2}
)

# Serialize to JSON
json_str = eval_doc.model_dump_json(indent=2)

# Load from JSON
eval_doc = EvaluationDocument(**json.loads(json_str))
```

---

### Context Models

#### `Context`

**Location**: `pydantic_models/context/models.py`

Educational context for the evaluation (course, assignment, question).

**Fields**:
- `course_id: str` - Course identifier
- `course_name: str` - Human-readable course name
- `assignment_id: int` - Assignment number
- `assignment_title: str` - Assignment title
- `question_source_path: str` - Path to question specification file
- `question_id: str` - Question identifier
- `question_title: str` - Question title
- `rubric_source_path: str` - Path to rubric file

**Example**:
```python
context = Context(
    course_id="CS101",
    course_name="Introduction to Computer Science",
    assignment_id=1,
    assignment_title="Object-Oriented Programming Basics",
    question_source_path="question_cuboid.md",
    question_id="q1",
    question_title="Cuboid Class Implementation",
    rubric_source_path="rubric_cuboid.json"
)
```

---

### Submission Models

#### `Submission`

**Location**: `pydantic_models/submission/models.py`

Student submission metadata and files.

**Fields**:
- `student_id: str` - Student identifier (e.g., "Diaz_Sergio_100029")
- `student_name: str` - Human-readable student name
- `submitted_at: datetime` - Submission timestamp
- `programming_language: str` - Primary language (e.g., "Java", "Python")
- `files: list[StudentFile]` - List of submitted files

**Example**:
```python
submission = Submission(
    student_id="Diaz_Sergio_100029",
    student_name="Sergio Diaz",
    submitted_at=datetime(2025, 11, 19, 9, 0, 0, tzinfo=timezone.utc),
    programming_language="Java",
    files=[
        StudentFile(path="Cuboid.java", language="Java"),
        StudentFile(path="CuboidTest.java", language="Java")
    ]
)
```

#### `StudentFile`

**Location**: `pydantic_models/submission/models.py`

Individual submitted file.

**Fields**:
- `path: str` - Relative path to file
- `language: str` - Programming language

---

### Rubric Models

#### `Rubric`

**Location**: `pydantic_models/rubric/models.py`

Complete grading rubric with categories.

**Fields**:
- `rubric_id: str` - Rubric identifier
- `title: str` - Rubric title
- `total_points: int` - Sum of all category max points
- `categories: list[RubricCategory]` - Grading categories

**Example**:
```python
rubric = Rubric(
    rubric_id="rubric_cuboid_v1",
    title="Cuboid Assignment Rubric",
    total_points=100,
    categories=[
        RubricCategory(
            category_id="correctness",
            name="Correctness",
            max_points=40,
            description="Code produces correct outputs"
        ),
        RubricCategory(
            category_id="code_quality",
            name="Code Quality",
            max_points=30,
            description="Well-structured, readable code"
        )
    ]
)
```

#### `RubricCategory`

**Location**: `pydantic_models/rubric/models.py`

Individual rubric category.

**Fields**:
- `category_id: str` - Unique category identifier
- `name: str` - Display name
- `max_points: int` - Maximum points for this category
- `description: str` - What this category evaluates

---

### Model Evaluation

#### `ModelEvaluation`

**Location**: `pydantic_models/models/evaluation.py`

Complete evaluation from a single LLM model.

**Fields**:
- `model_name: str` - Full model identifier (e.g., "google/gemini-2.5-flash-lite")
- `provider: str` - LLM provider (e.g., "openai", "openrouter")
- `model_version: str | None` - Specific model version if available
- `run_id: str` - Unique run identifier
- `config: Config` - Model configuration
- `scores: Scores` - Aggregate scores
- `category_scores: list[CategoryScore]` - Per-category breakdown
- `feedback: Feedback` - Overall feedback
- `misconceptions: list[Misconception]` - Identified misconceptions

**Example**:
```python
model_eval = ModelEvaluation(
    model_name="google/gemini-2.5-flash-lite",
    provider="openrouter",
    run_id="run_abc123",
    config=Config(
        system_prompt_id="direct_v1",
        rubric_prompt_id="rubric_v1"
    ),
    scores=scores,
    category_scores=category_scores,
    feedback=feedback,
    misconceptions=misconceptions
)
```

#### `LLMEvaluationResponse`

**Location**: `pydantic_models/models/evaluation.py`

Direct response from LLM (before wrapping in ModelEvaluation).

**Fields**:
- `scores: Scores`
- `category_scores: list[CategoryScore]`
- `feedback: Feedback`
- `misconceptions: list[Misconception]`

**Usage**: This is the schema that LLMs are asked to produce.

#### `Config`

**Location**: `pydantic_models/models/evaluation.py`

Model configuration metadata.

**Fields**:
- `system_prompt_id: str` - Which system prompt was used
- `rubric_prompt_id: str` - Which rubric template was used
- `temperature: float | None` - Sampling temperature
- `max_tokens: int | None` - Max response tokens

#### `Scores`

**Location**: `pydantic_models/models/evaluation.py`

Aggregate score information.

**Fields**:
- `total_points_awarded: float` - Sum across all categories
- `max_points: int` - Total possible points
- `percentage: float` - Score as percentage (0-100)
- `confidence: float | None` - Model's overall confidence (0-1)

**Validation**:
- `percentage` must be between 0 and 100
- `confidence` must be between 0 and 1

#### `CategoryScore`

**Location**: `pydantic_models/models/evaluation.py`

Score for a single rubric category.

**Fields**:
- `category_id: str` - Links to rubric category
- `points_awarded: float` - Points given
- `max_points: int` - Max possible for this category
- `reasoning: str` - Explanation of the score
- `confidence: float | None` - Model's confidence in this assessment (0-1)

#### `Feedback`

**Location**: `pydantic_models/models/evaluation.py`

Overall feedback on submission.

**Fields**:
- `strengths: list[str]` - Positive aspects
- `areas_for_improvement: list[str]` - Constructive feedback

#### `Misconception`

**Location**: `pydantic_models/models/evaluation.py`

Identified misconception with evidence.

**Fields**:
- `description: str` - What the misconception is
- `confidence: float` - Model's confidence (0-1)
- `evidence: list[Evidence]` - Supporting evidence from code
- `identified_by_model: str | None` - Which model found this
- `validated: bool` - Whether a human confirmed this (default: False)

#### `Evidence`

**Location**: `pydantic_models/models/evaluation.py`

Evidence supporting a misconception.

**Fields**:
- `code_snippet: str` - Exact code demonstrating the issue
- `line_number: int | None` - Line number where it occurs
- `explanation: str` - Why this demonstrates the misconception

---

### Comparison Models

**Location**: `pydantic_models/comparison/`

**Note**: These models are fully defined but computation logic is not yet implemented.

#### `Comparison`

**Location**: `pydantic_models/comparison/models.py`

Complete multi-model comparison and ensemble analysis.

**Fields**:
- `score_summary: ScoreSummary` - Aggregate statistics
- `pairwise_differences: list[PairwiseComparison]` - Model-vs-model
- `category_agreement: list[CategoryAgreement]` - Per-category stats
- `category_insights: CategoryInsights` - High-level insights
- `misconception_summary: MisconceptionSummary` - Misconception overlap
- `confidence_analysis: ConfidenceAnalysis` - Confidence patterns
- `model_characteristics: ModelCharacteristics` - Grading tendencies
- `reliability_metrics: ReliabilityMetrics` - Inter-rater reliability
- `ensemble_decision: EnsembleDecision` - Final recommendation
- `ensemble_quality: EnsembleQuality` - Quality assessment
- `flags: Flags` - Automated review flags
- `metadata: ComparisonMetadata` - Tracking info

#### `ScoreSummary`

**Location**: `pydantic_models/comparison/score_analysis.py`

Aggregate score statistics across models.

**Fields**:
- `mean_score: float` - Average score across models
- `median_score: float` - Median score
- `std_dev: float` - Standard deviation
- `min_score: float` - Lowest score
- `max_score: float` - Highest score
- `range: float` - Max - min
- `standard_error_mean: float` - SEM for confidence intervals
- `coefficient_of_variation: float` - Normalized variability

#### `ReliabilityMetrics`

**Location**: `pydantic_models/comparison/reliability.py`

Inter-rater reliability statistics.

**Fields**:
- `intraclass_correlation_icc: float | None` - ICC score
- `pearson_correlation: float | None` - Pearson r
- `spearman_correlation: float | None` - Spearman ρ
- `krippendorff_alpha: float | None` - Krippendorff's α
- `cohens_kappa: float | None` - Cohen's κ (for categorical)
- `interpretation: str` - Human-readable interpretation

#### `EnsembleDecision`

**Location**: `pydantic_models/comparison/ensemble.py`

Final ensemble recommendation.

**Fields**:
- `recommended_score: float` - Final recommended score
- `strategy_used: str` - Which aggregation strategy (e.g., "mean", "median", "weighted")
- `justification: str` - Why this score was chosen
- `confidence: float` - Confidence in the ensemble decision

---

## Utility Functions

### OpenAI Integration

**Location**: `utils/openai_client.py`

#### `evaluation_with_openai`

Get structured evaluation from OpenAI models.

**Signature**:
```python
def evaluation_with_openai(
    question: str,
    rubric: str,
    student_code: str,
    model: str = "gpt-4o"
) -> ModelEvaluation
```

**Parameters**:
- `question` - Assignment question text
- `rubric` - Rubric as JSON string
- `student_code` - Student's code
- `model` - OpenAI model to use

**Returns**: Complete `ModelEvaluation` instance

**Example**:
```python
from utils.openai_client import evaluation_with_openai

result = evaluation_with_openai(
    question=question_text,
    rubric=json.dumps(rubric_data),
    student_code=student_code,
    model="gpt-4o"
)

print(f"Score: {result.scores.percentage}%")
```

---

### OpenRouter Integration

**Location**: `utils/openrouter_sdk.py`

#### `get_structured_response`

Get structured response from any OpenRouter-supported model.

**Signature**:
```python
def get_structured_response(
    messages: list[dict[str, str]],
    response_model: type[T],
    model: str = DEFAULT_MODEL
) -> T
```

**Parameters**:
- `messages` - Chat messages (list of `{"role": "user/assistant", "content": "..."}`)
- `response_model` - Pydantic model class to parse response into
- `model` - OpenRouter model identifier (default: "google/gemini-2.5-flash-lite")

**Returns**: Instance of `response_model` populated with LLM response

**Example**:
```python
from utils.openrouter_sdk import get_structured_response
from pydantic_models import LLMEvaluationResponse

messages = [{"role": "user", "content": prompt}]

response = get_structured_response(
    messages=messages,
    response_model=LLMEvaluationResponse,
    model="anthropic/claude-3-opus"
)

print(f"Total points: {response.scores.total_points_awarded}")
```

**Supported Models**:
- Google Gemini: `google/gemini-2.5-flash-lite`, `google/gemini-pro`
- Anthropic Claude: `anthropic/claude-3-opus`, `anthropic/claude-3-sonnet`
- Moonshot AI: `moonshotai/kimi-k2-0905`
- OpenAI: `openai/gpt-4o`, `openai/gpt-4`
- Many more (see [OpenRouter docs](https://openrouter.ai/docs))

---

## Prompt Builders

Prompt builders are located in `prompts/` and provide different grading strategies.

### Direct Prompt

**Location**: `prompts/direct_prompt.py`

Grades student code directly against the rubric without intermediate steps.

**Function**: `build_direct_prompt(question, rubric, student_code) -> str`

### Reverse Prompt

**Location**: `prompts/reverse_prompt.py`

Generates an ideal solution first, then compares student work against it.

**Function**: `build_reverse_prompt(question, rubric, student_code) -> str`

### EME Prompt

**Location**: `prompts/eme_prompt.py`

Ensemble Method Evaluation prompt enforcing structured 100-point rubric evaluation.

**Function**: `build_eme_prompt(question, rubric, student_code) -> str`

---

## Usage Examples

### Complete Evaluation Workflow

```python
import json
from datetime import datetime, timezone
from pydantic_models import (
    Context, Submission, Rubric, EvaluationDocument,
    ModelEvaluation, Config, LLMEvaluationResponse
)
from utils.openrouter_sdk import get_structured_response

# 1. Load materials
with open("question.md") as f:
    question = f.read()

with open("rubric.json") as f:
    rubric_data = json.load(f)

with open("student_code.java") as f:
    code = f.read()

# 2. Build prompt
prompt = f"""
You are an expert grader.

**Question**: {question}
**Rubric**: {json.dumps(rubric_data)}
**Student Code**:
```java
{code}
```

Evaluate based on the rubric.
"""

# 3. Get evaluations from multiple models
models = ["google/gemini-2.5-flash-lite", "anthropic/claude-3-sonnet"]
model_evals = {}

for model_name in models:
    llm_response = get_structured_response(
        messages=[{"role": "user", "content": prompt}],
        response_model=LLMEvaluationResponse,
        model=model_name
    )

    model_eval = ModelEvaluation(
        model_name=model_name,
        provider="openrouter",
        run_id=f"run_{uuid.uuid4().hex[:8]}",
        config=Config(system_prompt_id="direct", rubric_prompt_id="v1"),
        scores=llm_response.scores,
        category_scores=llm_response.category_scores,
        feedback=llm_response.feedback,
        misconceptions=llm_response.misconceptions
    )

    model_evals[model_name.split("/")[-1]] = model_eval

# 4. Build EvaluationDocument
eval_doc = EvaluationDocument(
    evaluation_id=f"eval_{uuid.uuid4()}",
    schema_version="1.0.0",
    created_at=datetime.now(timezone.utc),
    created_by="my_script.py",
    context=Context(...),
    submission=Submission(...),
    rubric=Rubric(...),
    models=model_evals
)

# 5. Save to JSON
with open("output.json", "w") as f:
    f.write(eval_doc.model_dump_json(indent=2))
```

### Loading and Analyzing Results

```python
# Load evaluation
with open("output.json") as f:
    eval_doc = EvaluationDocument(**json.load(f))

# Compare scores
print("Score Comparison:")
for model_name, model_eval in eval_doc.models.items():
    print(f"{model_name}: {model_eval.scores.percentage}% "
          f"(confidence: {model_eval.scores.confidence})")

# Review misconceptions
print("\nMisconception Analysis:")
for model_name, model_eval in eval_doc.models.items():
    print(f"\n{model_name}:")
    for misc in model_eval.misconceptions:
        print(f"  - {misc.description} (confidence: {misc.confidence})")
        for ev in misc.evidence:
            print(f"    Line {ev.line_number}: {ev.code_snippet}")

# Category breakdown
print("\nCategory Scores:")
for cat_score in eval_doc.models["gemini-2.5-flash-lite"].category_scores:
    print(f"{cat_score.category_id}: "
          f"{cat_score.points_awarded}/{cat_score.max_points}")
    print(f"  Reasoning: {cat_score.reasoning}")
```

---

## Error Handling

All Pydantic models raise `ValidationError` on invalid data:

```python
from pydantic import ValidationError

try:
    eval_doc = EvaluationDocument(**data)
except ValidationError as e:
    print(f"Validation failed: {e}")
    # e.errors() provides detailed error information
    for error in e.errors():
        print(f"Field: {error['loc']}, Error: {error['msg']}")
```

Common validation errors:
- Missing required fields
- Type mismatches (e.g., string instead of int)
- Value out of range (e.g., percentage > 100)
- Schema version mismatch
