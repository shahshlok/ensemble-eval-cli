"""Models for individual model evaluation results.

Top‑down structure of the final model:

    ModelEvaluation
    ├─ model_name: str
    ├─ provider: str
    ├─ run_id: str
    ├─ config: Config
    │   ├─ system_prompt_id: str
    │   └─ rubric_prompt_id: str
--- Everything from here is from the LLMEvaluationResponse model ---
    ├─ scores: Scores
    │   ├─ total_points_awarded: float
    │   ├─ max_points: float
    │   └─ percentage: float
    ├─ category_scores: list[CategoryScore]
    │   └─ CategoryScore
    │       ├─ task: str
    │       ├─ points_awarded: float
    │       ├─ max_points: float
    │       ├─ reasoning: str
    │       ├─ confidence: float
    │       └─ reasoning_tokens: int
    ├─ feedback: Feedback
    │   ├─ overall_comment: str
    │   ├─ strengths: list[str]
    │   └─ areas_for_improvement: list[str]
    └─ misconceptions: list[Misconception]
        └─ Misconception
            ├─ topic: str
            ├─ task: str
            ├─ name: str
            ├─ description: str
            ├─ confidence: float
            ├─ evidence: list[Evidence]
            │   └─ Evidence
            │       ├─ source: str
            │       ├─ file_path: str
            │       ├─ language: str
            │       ├─ snippet: str
            │       ├─ line_start: int
            │       ├─ line_end: int
            │       └─ note: str
            └─ validated_by: str | None
"""

from enum import Enum

from pydantic import BaseModel, ConfigDict, Field, model_validator

# Canonical topics for misconception classification (from Assignment 2 rubric)
# These are the actual learning objectives the assignment tests
CANONICAL_TOPICS = [
    "Variables",  # Declaring, assigning, using in expressions
    "Data Types",  # int vs double, type conversions
    "Constants",  # Math library (Math.pow, Math.sqrt)
    "Reading input from the keyboard",  # Scanner usage, prompts
    "Other",  # Catch-all for things that don't fit above
]


class Config(BaseModel):
    """Configuration settings for model evaluation."""

    # extra="forbid" means that bad keys trigger validation errors instead of being dropped silently
    model_config = ConfigDict(extra="forbid")

    system_prompt_id: str = Field(..., description="ID of system prompt template used")
    rubric_prompt_id: str = Field(
        ..., description="ID of grading prompt template specific to this rubric/question"
    )


class Scores(BaseModel):
    """Overall scoring information."""

    model_config = ConfigDict(extra="forbid")

    total_points_awarded: float = Field(
        ..., description="Sum of all category_scores[*].points_awarded"
    )
    max_points: float = Field(
        ..., description="Maximum possible points (should match rubric.total_points)"
    )
    percentage: float = Field(
        ..., description="Percentage score (total_points_awarded / max_points * 100)"
    )

    @model_validator(mode="after")
    def validate_percentage(self) -> "Scores":
        if self.max_points <= 0:
            return self
        expected = (self.total_points_awarded / self.max_points) * 100
        # Allow small floating point slack
        if abs(self.percentage - expected) > 1e-6:
            raise ValueError(
                f"percentage {self.percentage} does not match "
                f"total_points_awarded / max_points * 100 ({expected})"
            )
        return self


class CategoryScore(BaseModel):
    """Scoring information for a specific rubric category."""

    model_config = ConfigDict(extra="forbid")

    task: str = Field(..., description="The task name from the rubric")
    points_awarded: float = Field(..., description="Points this model gave in this category")
    max_points: float = Field(..., description="Maximum possible points in this category")
    reasoning: str = Field(..., description="Category-specific reasoning for the score")
    confidence: float = Field(
        ..., ge=0.0, le=1.0, description="Model's confidence in this category score (0-1)"
    )
    reasoning_tokens: int = Field(
        ..., description="Number of tokens in reasoning (proxy for explanation depth)"
    )


class Feedback(BaseModel):
    """Human-readable feedback bundle for a submission."""

    model_config = ConfigDict(extra="forbid")

    overall_comment: str = Field(
        ...,
        description="Holistic comment on the submission (descriptive, what you'd show a student)",
    )
    strengths: list[str] = Field(
        ..., description="Bullet-style list of strengths in the submission"
    )
    areas_for_improvement: list[str] = Field(
        ..., description="Bullet-style list of weaknesses/next steps for the student"
    )


class Evidence(BaseModel):
    """Evidence supporting a misconception finding."""

    model_config = ConfigDict(extra="forbid")

    source: str = Field(
        ..., description="Where the snippet comes from (e.g., student_code, tests, text_answer)"
    )
    file_path: str = Field(..., description="Which file the snippet lives in")
    language: str = Field(
        ...,
        description="Language of the snippet (could also be 'text' if it's a written explanation)",
    )
    snippet: str = Field(..., description="Concrete code/text that demonstrates the issue")
    line_start: int = Field(
        ..., description="Approximate starting line number in that file (for UI highlighting)"
    )
    line_end: int = Field(
        ..., description="Approximate ending line number in that file (for UI highlighting)"
    )
    note: str = Field(
        ..., description="Concise explanation of why this snippet is considered evidence"
    )


class MisconceptionSeverity(str, Enum):
    """Severity level of a misconception."""

    CRITICAL = "critical"  # Fundamentally breaks the solution
    MAJOR = "major"  # Significant impact on correctness
    MINOR = "minor"  # Small issue, partial understanding
    SURFACE = "surface"  # Superficial, may self-correct


class MisconceptionCategory(str, Enum):
    """Category of misconception by nature."""

    CONCEPTUAL = "conceptual"  # Flawed mental model of how something works
    PROCEDURAL = "procedural"  # Wrong steps/algorithm, right concept
    SYNTACTIC = "syntactic"  # Language-specific syntax confusion (not typos)
    SEMANTIC = "semantic"  # Misunderstanding what code does vs intended


class Misconception(BaseModel):
    """Identified misconception in student's work."""

    model_config = ConfigDict(extra="forbid")

    # === Core identification ===
    topic: str = Field(
        ...,
        description="The topic or concept associated with this misconception. Should be one of: Variables, Data Types, Constants, Reading input from the keyboard, or Other (if doesn't fit). Do NOT report syntax errors (missing semicolons, typos) as misconceptions.",
    )
    task: str = Field(
        ..., description="The task name from the rubric category where this misconception appears"
    )
    name: str = Field(
        ...,
        description="Human-readable label for this misconception (e.g., 'Integer division truncation')",
    )
    description: str = Field(
        ..., description="Description of what behavior/understanding this misconception reflects"
    )

    # === Classification ===
    severity: MisconceptionSeverity = Field(
        default=MisconceptionSeverity.MAJOR,
        description="How severe is this misconception? critical=breaks solution, major=significant impact, minor=partial understanding, surface=likely self-correcting",
    )
    category: MisconceptionCategory = Field(
        default=MisconceptionCategory.CONCEPTUAL,
        description="Type of misconception: conceptual=flawed mental model, procedural=wrong algorithm, syntactic=language confusion, semantic=misunderstanding code behavior",
    )

    # === Student's mental model ===
    student_belief: str = Field(
        default="",
        description="What does the student appear to believe? State it from their perspective. E.g., 'The student believes that ^ is the exponentiation operator in Java'",
    )
    correct_understanding: str = Field(
        default="",
        description="What is the correct understanding? E.g., 'In Java, ^ is the XOR operator. Use Math.pow(base, exponent) for exponentiation.'",
    )

    # === Impact analysis ===
    symptoms: list[str] = Field(
        default_factory=list,
        description="Observable symptoms in the code/output. E.g., ['Result is always 0 or 1', 'Output doesn't match expected values']",
    )
    root_cause: str = Field(
        default="",
        description="The underlying reason for this misconception. E.g., 'Confusion from mathematical notation where ^ means power'",
    )

    # === Remediation ===
    remediation_hint: str = Field(
        default="",
        description="A hint for how the student could fix this. E.g., 'Replace x^2 with Math.pow(x, 2) or x*x'",
    )
    related_concepts: list[str] = Field(
        default_factory=list,
        description="Related concepts the student should review. E.g., ['Java Math library', 'Operator precedence', 'Type casting']",
    )

    # === Confidence & evidence ===
    confidence: float = Field(
        ...,
        ge=0.0,
        le=1.0,
        description="Model's confidence (0-1) that this misconception truly applies",
    )
    confidence_rationale: str = Field(
        default="",
        description="Why this confidence level? E.g., 'High confidence because the pattern x^2 appears multiple times consistently'",
    )

    # Check the Evidence Pydantic Model
    evidence: list[Evidence] = Field(
        ...,
        description="Evidence showing exactly where this misconception appears in the submission",
    )

    # === Validation ===
    is_recurring: bool = Field(
        default=False,
        description="Does this misconception appear multiple times in the submission?",
    )
    affects_output: bool = Field(
        default=True, description="Does this misconception affect the program output/correctness?"
    )
    validated_by: str | None = Field(
        None, description="Optional human rater ID when a TA confirms this annotation"
    )


class LLMEvaluationResponse(BaseModel):
    """
    Response from LLM containing only the evaluation content (no metadata).

    This is what the LLM fills out. Metadata (model_name, provider, run_id, config)
    is added by the developer to create the full ModelEvaluation.
    """

    scores: Scores = Field(..., description="Aggregate score for this model")
    category_scores: list[CategoryScore] = Field(
        ..., description="Per-category rubric scores with justification"
    )
    feedback: Feedback = Field(..., description="Human-readable feedback bundle for this model")

    # Misconceptions has the Evidence model nested in it
    misconceptions: list[Misconception] = Field(
        ..., description="Misconceptions for this submission according to this model"
    )


class ModelEvaluation(LLMEvaluationResponse):
    """
    Complete evaluation result from a single grading model.

    See the module docstring at the top of this file for
    a full ASCII diagram of the JSON structure.
    """

    model_config = ConfigDict(extra="forbid")

    model_name: str = Field(
        ..., description="Human-readable model name with version (e.g., gpt-5-nano-2025-08-07)"
    )
    provider: str = Field(..., description="Who provides this model (for analysis across vendors)")
    run_id: str = Field(..., description="ID of this model invocation for traceability in logs")
    config: Config = Field(..., description="Configuration settings for this model run")
