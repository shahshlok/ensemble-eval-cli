import asyncio
import os
import random

from dotenv import load_dotenv
from rich.console import Console

from sandbox.utils.grading import (
    construct_prompt,
    create_evaluation_document,
    grade_with_model,
    load_question,
    load_rubric,
    load_student_submission,
)

load_dotenv()
console = Console()

MODELS = [
    "google/gemini-2.5-flash",
    "openai/gpt-5.1",
    "google/gemini-2.5-flash-lite",
    "openai/gpt-5-nano",
]


async def grade_single_student():
    # 1. Setup
    console.print("[bold blue]Experiment: Single Student Grading (Q1-Q4)[/bold blue]")

    submission_dir = "student_submissions"
    if not os.path.exists(submission_dir):
        console.print(f"[red]Submission directory '{submission_dir}' not found.[/red]")
        return

    students = [
        d for d in os.listdir(submission_dir) if os.path.isdir(os.path.join(submission_dir, d))
    ]
    if not students:
        console.print("[red]No students found.[/red]")
        return

    console.print("[yellow] Selecting random student...[/yellow]")
    student_id = random.choice(students)

    console.print(f"Selected Student: [cyan]{student_id}[/cyan]")

    # 2. Loop through Questions 1 to 4
    for q_num in range(1, 5):
        console.print(f"\n[bold]Processing Question {q_num}...[/bold]")
        
        question_file = f"data/a2/q{q_num}.md"
        rubric_file = f"data/a2/rubric_q{q_num}.md"
        
        # Load Resources
        try:
            question_text = load_question(question_file)
            rubric_data = load_rubric(rubric_file)
            
            # Load specific student file for this question (e.g., Q1.java)
            # We need to manually construct the path or modify load_student_submission
            # But load_student_submission currently finds *any* .java file.
            # Let's manually load the specific file here to be precise.
            student_dir = os.path.join(submission_dir, student_id)
            student_file_name = f"Q{q_num}.java"
            student_file_path = os.path.join(student_dir, student_file_name)
            
            if not os.path.exists(student_file_path):
                 console.print(f"[red]File {student_file_name} not found for student {student_id}. Skipping.[/red]")
                 continue
                 
            with open(student_file_path, "r") as f:
                student_code = f.read()
                
        except Exception as e:
            console.print(f"[red]Error loading resources for Q{q_num}: {e}[/red]")
            continue

        # 3. Grade
        prompt = construct_prompt(question_text, rubric_data, student_code)
        messages = [{"role": "user", "content": prompt}]

        model_evals = {}

        for model in MODELS:
            console.print(f"  Grading with [magenta]{model}[/magenta]...")
            try:
                eval_result = await grade_with_model(model, messages)
                model_evals[model] = eval_result
                console.print(f"  [green]Success ({model})[/green]")
            except Exception as e:
                console.print(f"  [red]Failed ({model}): {e}[/red]")

        if not model_evals:
            console.print(f"[red]All models failed for Q{q_num}.[/red]")
            continue

        # 4. Save
        student_name = student_id.replace("_", " ")
        # We need to pass the correct filename to create_evaluation_document
        eval_doc = create_evaluation_document(
            student_id,
            student_name,
            question_text,
            rubric_data,
            student_file_name,
            model_evals,
            question_source_path=question_file,
            rubric_source_path=rubric_file,
        )
        
        # Update context with correct question ID
        eval_doc.context.question_id = f"q{q_num}"
        eval_doc.context.question_title = f"Question {q_num}"

        output_dir = "sandbox/evals"
        os.makedirs(output_dir, exist_ok=True)
        output_file = f"{output_dir}/{student_id}_q{q_num}_eval.json"

        with open(output_file, "w") as f:
            f.write(eval_doc.model_dump_json(indent=2))

        console.print(f"[bold green]Saved evaluation for Q{q_num} to {output_file}[/bold green]")


def main():
    """Entry point for uv run experiment command."""
    asyncio.run(grade_single_student())


if __name__ == "__main__":
    main()
