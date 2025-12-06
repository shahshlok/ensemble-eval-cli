"""Verification Harness for Differential Execution.

Compiles and runs Java files, caching clean references per persona to
detect whether seeded misconceptions produce observable output differences.
"""

from __future__ import annotations

import asyncio
import hashlib
import json
import os
import shutil
import subprocess
import tempfile
from pathlib import Path
from typing import Any

from openai import AsyncOpenAI

# Cache directory for compiled reference outputs
CACHE_DIR = Path(".cache/verification")
INPUTS_PATH = Path("data/inputs.json")


def load_inputs() -> dict[str, dict[str, str]]:
    """Load standard inputs for each assignment/question."""
    if not INPUTS_PATH.exists():
        raise FileNotFoundError(f"Missing inputs file: {INPUTS_PATH}")
    return json.loads(INPUTS_PATH.read_text(encoding="utf-8"))


def persona_hash(persona: str) -> str:
    """Generate a short hash for a persona string."""
    return hashlib.sha256(persona.encode()).hexdigest()[:12]


def compile_java(java_file: Path, work_dir: Path) -> tuple[bool, str]:
    """Compile a Java file. Returns (success, error_message)."""
    try:
        result = subprocess.run(
            ["javac", str(java_file)],
            cwd=work_dir,
            capture_output=True,
            text=True,
            timeout=30,
        )
        if result.returncode != 0:
            return False, result.stderr
        return True, ""
    except subprocess.TimeoutExpired:
        return False, "Compilation timed out"
    except FileNotFoundError:
        return False, "javac not found in PATH"


def run_java(class_name: str, work_dir: Path, stdin_input: str, timeout: int = 10) -> tuple[bool, str]:
    """Run a compiled Java class. Returns (success, stdout_or_error)."""
    try:
        result = subprocess.run(
            ["java", class_name],
            cwd=work_dir,
            input=stdin_input,
            capture_output=True,
            text=True,
            timeout=timeout,
        )
        # Even if exit code is non-zero, return stdout if present
        return True, result.stdout
    except subprocess.TimeoutExpired:
        return False, "TIMEOUT"
    except FileNotFoundError:
        return False, "java not found in PATH"


def extract_class_name(java_content: str) -> str | None:
    """Extract the public class name from Java source."""
    import re
    match = re.search(r"public\s+class\s+(\w+)", java_content)
    if match:
        return match.group(1)
    # Fallback: any class
    match = re.search(r"class\s+(\w+)", java_content)
    return match.group(1) if match else None


def run_safely(java_content: str, stdin_input: str, timeout: int = 10) -> tuple[bool, str]:
    """Compile and run Java code in an isolated temp directory.
    
    Returns (success, output_or_error).
    """
    class_name = extract_class_name(java_content)
    if not class_name:
        return False, "Could not extract class name"
    
    with tempfile.TemporaryDirectory() as tmp:
        tmp_path = Path(tmp)
        java_file = tmp_path / f"{class_name}.java"
        java_file.write_text(java_content, encoding="utf-8")
        
        # Compile
        success, error = compile_java(java_file, tmp_path)
        if not success:
            return False, f"COMPILE_ERROR: {error}"
        
        # Run
        return run_java(class_name, tmp_path, stdin_input, timeout)


class VerificationHarness:
    """Manages reference outputs and differential verification."""
    
    def __init__(
        self,
        client: AsyncOpenAI,
        model: str,
        assignment: str,
        question_texts: dict[str, str],
        question_briefs: dict[str, str],
    ):
        self.client = client
        self.model = model
        self.assignment = assignment
        self.question_texts = question_texts
        self.question_briefs = question_briefs
        self._inputs = load_inputs()
        self._reference_cache: dict[str, str] = {}  # (persona_hash, question) -> output
        
        CACHE_DIR.mkdir(parents=True, exist_ok=True)
    
    def _get_input(self, question: str) -> str:
        """Get the standard input for a question."""
        return self._inputs.get(self.assignment, {}).get(question, "")
    
    def _cache_key(self, persona: str, question: str) -> str:
        return f"{persona_hash(persona)}_{question}"
    
    async def _generate_clean_code(self, persona: str, question: str) -> str:
        """Generate a clean (correct) implementation for a persona/question."""
        from utils.generators.dataset_generator import build_messages, extract_text_from_response, strip_code_fences
        
        file_entry = {"type": "CLEAN", "instruction": None}
        messages = build_messages(
            persona=persona,
            question=question,
            question_text=self.question_texts[question],
            brief=self.question_briefs[question],
            file_entry=file_entry,
        )
        response = await self.client.responses.create(
            model=self.model,
            input=messages,
            max_output_tokens=800,
        )
        return strip_code_fences(extract_text_from_response(response))
    
    async def ensure_reference(self, persona: str, question: str) -> str | None:
        """Get or generate the reference output for a persona/question.
        
        Returns the expected stdout, or None if generation/compilation fails.
        """
        key = self._cache_key(persona, question)
        
        # Check in-memory cache
        if key in self._reference_cache:
            return self._reference_cache[key]
        
        # Check disk cache
        cache_file = CACHE_DIR / f"{key}.txt"
        if cache_file.exists():
            output = cache_file.read_text(encoding="utf-8")
            self._reference_cache[key] = output
            return output
        
        # Generate clean code
        clean_code = await self._generate_clean_code(persona, question)
        stdin_input = self._get_input(question)
        
        # Run it
        success, output = run_safely(clean_code, stdin_input)
        if not success:
            # Could not compile/run clean code - this might mean the LLM
            # generated bad code. Return None to skip verification.
            return None
        
        # Cache it
        self._reference_cache[key] = output
        cache_file.write_text(output, encoding="utf-8")
        return output
    
    async def verify_difference(
        self,
        seeded_code: str,
        persona: str,
        question: str,
    ) -> tuple[bool, str]:
        """Check if seeded code produces different output than the reference.
        
        Returns (is_different, reason).
        - (True, "OK") = Outputs differ, misconception is observable
        - (False, "MATCH") = Outputs match, bug is silent
        - (False, "COMPILE_ERROR") = Seeded code doesn't compile
        - (False, "NO_REFERENCE") = Could not generate reference
        """
        reference_output = await self.ensure_reference(persona, question)
        if reference_output is None:
            return False, "NO_REFERENCE"
        
        stdin_input = self._get_input(question)
        success, seeded_output = run_safely(seeded_code, stdin_input)
        
        if not success:
            # Compile error or timeout - this IS a difference!
            return True, "SEEDED_COMPILE_ERROR"
        
        if seeded_output.strip() == reference_output.strip():
            return False, "MATCH"
        
        return True, "OK"
