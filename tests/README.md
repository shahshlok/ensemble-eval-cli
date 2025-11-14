# EME Testing Suite Documentation

## Overview

This comprehensive testing suite provides thorough validation of the EME (Ensemble Method Evaluation) testing codebase, which benchmarks AI grading systems comparing GPT-5 Nano and GPT-OSS 120B models.

## Test Structure

```
tests/
├── conftest.py                    # Pytest configuration and shared fixtures
├── test_utils/                    # Core utility module tests
│   ├── test_validation.py         # JSON schema validation (31 tests)
│   ├── test_evaluator.py          # Metrics computation (29 tests)
│   └── test_ai_clients.py        # API client handling (45+ tests)
├── test_db/                      # Database layer tests
│   └── test_manager.py            # SQLite operations (26 tests)
├── test_modes/                    # Grading strategy tests (direct/eme/reverse)
├── test_integration/              # CLI and workflow tests
├── test_performance/              # Semaphore and concurrency smoke tests
└── fixtures/                     # Test data and samples
    ├── sample_evaluations.json    # Valid evaluation data
    ├── invalid_evaluations.json   # Invalid data for negative testing
    └── sample_submissions/        # Mock Java code files
```

## Running Tests

### Quick Start
```bash
# Run all tests
uv run pytest

# Run with coverage
uv run pytest --cov=utils --cov=db --cov=modes --cov-report=html

# Run specific test modules
uv run pytest tests/test_utils/
uv run pytest tests/test_db/

# Run with verbose output
uv run pytest -v
```

### Test Categories

#### Unit Tests (High Priority)
- **Validation Tests** (`tests/test_utils/test_validation.py`)
  - JSON schema validation
  - Business rule validation
  - Error handling and edge cases
  - 31 comprehensive test cases

- **Evaluator Tests** (`tests/test_utils/test_evaluator.py`)
  - Metrics computation algorithms
  - Score extraction and validation
  - Async evaluation workflows
  - 29 test cases covering all scenarios

- **Database Tests** (`tests/test_db/test_manager.py`)
  - SQLite schema operations
  - Data ingestion and restoration
  - Constraint validation
  - 26 test cases for database integrity

- **AI Client Tests** (`tests/test_utils/test_ai_clients.py`)
  - OpenAI API response parsing
  - EduAI endpoint communication
  - Error handling and retry logic
  - 45+ test cases for API interactions

#### Integration Tests (Medium Priority)
- **Grading Mode Tests** - Direct, Reverse, and EME strategies (see `tests/test_modes/`)
- **CLI Tests** - Command-line interface and helper workflows (`tests/test_integration/`)
- **End-to-End Tests** - Currently covered by light-weight async pipeline tests

#### Performance Tests (Low Priority)
- **Concurrency Tests** - Semaphore wiring and basic parallel execution (`tests/test_performance/`)
- **Stress Tests** - Not yet implemented (future work)

## Test Data Strategy

### Trivial Test Cases
- Perfect scoring submissions (100%)
- Complete failures (0%)
- Empty/null responses
- Valid JSON with minimal data

### Non-Trivial Test Cases
- Edge case scores (boundary values)
- Malformed API responses
- Database corruption scenarios
- Network timeout handling
- Concurrent access conflicts
- Mixed valid/invalid evaluation batches

### Sample Data
```json
{
  "student": "Perfect_Student_100",
  "gpt5_nano_result": {
    "total_score": 100,
    "max_possible_score": 100,
    "overall_feedback": "Perfect implementation"
  },
  "gpt_oss_120b_result": {
    "total_score": 100,
    "max_possible_score": 100,
    "overall_feedback": "Outstanding work"
  },
  "metrics": {
    "gpt5_nano": {"total": 100.0, "max": 100.0, "pct": 100.0},
    "gpt_oss_120b": {"total": 100.0, "max": 100.0, "pct": 100.0},
    "avg_pct": 100.0,
    "diff_pct": 0.0,
    "flag": "✅",
    "comment": "Models agree within tolerance"
  }
}
```

## Key Testing Features

### Mocking Strategy
- **API Calls**: All external API calls are mocked using `unittest.mock`
- **Database**: Uses in-memory SQLite for isolated testing
- **File System**: Temporary directories for test isolation
- **Environment Variables**: Controlled via `patch.dict`

### Async Testing
- Full support for async/await patterns
- Proper semaphore testing
- Concurrent evaluation simulation
- Uses `pytest-asyncio` plugin

### Coverage Goals
- **Target**: 90%+ code coverage
- **Critical Paths**: 100% coverage for core validation and evaluation logic
- **Error Paths**: Comprehensive testing of failure modes

## Test Fixtures

### Core Fixtures
- `temp_dir`: Temporary directory for test files
- `temp_db_file`: Temporary SQLite database
- `sample_evaluation`: Valid evaluation data
- `sample_evaluation_list`: Multiple evaluation records
- `invalid_evaluation`: Invalid data for negative testing

### Domain-Specific Fixtures
- `sample_java_code`: Mock student submission
- `sample_rubric`: Grading criteria
- `sample_question`: Programming problem statement
- `mock_api_responses`: Simulated API responses

## Continuous Integration

The suite is designed to play nicely with CI (e.g. GitHub Actions),
but no workflows are shipped in this repository yet. A typical setup
would:

- Install dependencies with `uv`
- Run `uv run pytest`
- Optionally collect coverage with `uv run pytest --cov`

### Pre-commit Hooks (Optional)

You can wire this test suite into pre-commit locally. As a simple
starting point, run:

```bash
uv run pytest
uv run black --check .
uv run ruff check .
```

Type checking (e.g. `mypy`) is currently optional and not configured.

## Debugging Tests

### Running Individual Tests
```bash
# Run specific test class
uv run pytest tests/test_utils/test_validation.py::TestValidationResult

# Run specific test method
uv run pytest tests/test_utils/test_validation.py::TestValidationResult::test_valid_result_creation

# Run with debugging
uv run pytest -s -vv tests/test_utils/test_validation.py
```

### Test Output Analysis
- Use `-v` for verbose output
- Use `-s` to see print statements
- Use `--tb=short` for concise tracebacks
- Use `--pdb` for post-mortem debugging

## Contributing

### Adding New Tests
1. Follow existing naming conventions (`test_*`)
2. Use appropriate fixtures for setup
3. Test both success and failure cases
4. Include docstrings explaining test purpose
5. Add to relevant test class or create new one

### Test Quality Standards
- **Isolation**: Tests should not depend on each other
- **Determinism**: Tests should produce consistent results
- **Speed**: Tests should run quickly (target < 1s per test)
- **Clarity**: Test names should describe what is being tested

## Coverage Report

After running tests with coverage:
```bash
uv run pytest --cov=. --cov-report=html
open htmlcov/index.html
```

Target coverage areas:
- **utils.validation**: 100% (critical for data integrity)
- **utils.evaluator**: 95%+ (core business logic)
- **db.manager**: 90%+ (data persistence)
- **utils.ai_clients**: 85%+ (external integrations)

This comprehensive testing suite ensures robust validation of the AI grading system, covering everything from individual function correctness to complete workflow reliability.
