import sys
import os
from datetime import datetime, timezone
from pydantic_models.evaluation import EvaluationDocument
from pydantic_models.models import ModelEvaluation, Config, Misconception, Evidence
from utils.grading import create_evaluation_document

def test_models():
    print("Testing ModelEvaluation instantiation...")
    try:
        me = ModelEvaluation(
            model_name="test-model",
            provider="test-provider",
            run_id="test-run",
            config=Config(system_prompt_id="sys", rubric_prompt_id="rub"),
            misconceptions=[
                Misconception(
                    topic="Variables",
                    task="Task 1",
                    name="Test Misconception",
                    description="Test description",
                    confidence=0.9,
                    evidence=[
                        Evidence(
                            source="student_code",
                            file_path="Test.java",
                            language="java",
                            snippet="int x = 5;",
                            line_start=1,
                            line_end=1,
                            note="Test note"
                        )
                    ]
                )
            ]
        )
        print("ModelEvaluation instantiated successfully.")
    except Exception as e:
        print(f"Failed to instantiate ModelEvaluation: {e}")
        sys.exit(1)

    print("\nTesting create_evaluation_document...")
    try:
        # Mock data
        student_id = "Test_Student"
        student_name = "Test Student"
        question_text = "Test Question"
        filename = "Test.java"
        model_evals = {"test-model": me}
        
        doc = create_evaluation_document(
            student_id,
            student_name,
            question_text,
            filename,
            model_evals
        )
        print("create_evaluation_document succeeded.")
        print(f"Evaluation ID: {doc.evaluation_id}")
        
        # Verify fields are missing
        if hasattr(doc, "rubric"):
            print("ERROR: doc has 'rubric' field!")
        else:
            print("SUCCESS: doc does not have 'rubric' field.")
            
        if hasattr(doc, "comparison"):
            print("ERROR: doc has 'comparison' field!")
        else:
            print("SUCCESS: doc does not have 'comparison' field.")

    except Exception as e:
        print(f"Failed create_evaluation_document: {e}")
        sys.exit(1)

if __name__ == "__main__":
    test_models()
