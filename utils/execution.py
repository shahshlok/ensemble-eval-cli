"""Execution utilities for Java code verification.

This module provides functions to compile and run Java code,
used for verifying generated student submissions.
"""

from __future__ import annotations

import re
import shutil
import subprocess
import tempfile
from dataclasses import dataclass
from pathlib import Path


@dataclass
class CompileResult:
    """Result of a Java compilation attempt."""

    success: bool
    return_code: int
    stdout: str
    stderr: str
    file_path: Path
    renamed_to: Path | None = None  # If file was renamed for compilation


def extract_public_class_name(java_source: str) -> str | None:
    """
    Extract the public class name from Java source code.

    Args:
        java_source: The Java source code as a string.

    Returns:
        The name of the public class, or None if not found.
    """
    # Match: public class ClassName (with optional modifiers before 'class')
    # Handles: public class Foo, public final class Foo, etc.
    pattern = r'\bpublic\s+(?:final\s+|abstract\s+|strictfp\s+)*class\s+(\w+)'
    match = re.search(pattern, java_source)
    if match:
        return match.group(1)
    return None


def compile_java(file_path: Path | str, timeout: float = 30.0) -> CompileResult:
    """
    Compile a Java file using javac.

    Args:
        file_path: Path to the .java file to compile.
        timeout: Maximum seconds to wait for compilation.

    Returns:
        CompileResult with success status and output details.
    """
    file_path = Path(file_path)

    if not file_path.exists():
        return CompileResult(
            success=False,
            return_code=-1,
            stdout="",
            stderr=f"File not found: {file_path}",
            file_path=file_path,
        )

    if not file_path.suffix == ".java":
        return CompileResult(
            success=False,
            return_code=-1,
            stdout="",
            stderr=f"Not a Java file: {file_path}",
            file_path=file_path,
        )

    try:
        # Resolve to absolute path to avoid path issues
        abs_path = file_path.resolve()

        result = subprocess.run(
            ["javac", str(abs_path)],
            capture_output=True,
            text=True,
            timeout=timeout,
            cwd=abs_path.parent,  # Run in file's directory
        )

        return CompileResult(
            success=(result.returncode == 0),
            return_code=result.returncode,
            stdout=result.stdout,
            stderr=result.stderr,
            file_path=file_path,
        )

    except subprocess.TimeoutExpired:
        return CompileResult(
            success=False,
            return_code=-2,
            stdout="",
            stderr=f"Compilation timed out after {timeout}s",
            file_path=file_path,
        )

    except FileNotFoundError:
        return CompileResult(
            success=False,
            return_code=-3,
            stdout="",
            stderr="javac not found. Is Java installed?",
            file_path=file_path,
        )


def compile_java_with_rename(
    file_path: Path | str,
    timeout: float = 30.0,
    use_temp_dir: bool = True,
) -> CompileResult:
    """
    Compile a Java file, automatically renaming it to match the public class name.

    This handles the common case where the file is named 'Q1.java' but contains
    'public class RoadTripCost'. The file is copied to a temp directory with the
    correct name before compilation.

    Args:
        file_path: Path to the .java file to compile.
        timeout: Maximum seconds to wait for compilation.
        use_temp_dir: If True, copy to temp dir. If False, rename in place (dangerous).

    Returns:
        CompileResult with success status and output details.
    """
    file_path = Path(file_path).resolve()

    if not file_path.exists():
        return CompileResult(
            success=False,
            return_code=-1,
            stdout="",
            stderr=f"File not found: {file_path}",
            file_path=file_path,
        )

    # Read the source and extract the class name
    try:
        source = file_path.read_text(encoding="utf-8")
    except Exception as e:
        return CompileResult(
            success=False,
            return_code=-1,
            stdout="",
            stderr=f"Failed to read file: {e}",
            file_path=file_path,
        )

    class_name = extract_public_class_name(source)
    expected_filename = file_path.stem  # e.g., "Q1"

    # If class name matches filename, just compile normally
    if class_name is None or class_name == expected_filename:
        return compile_java(file_path, timeout)

    # Class name differs from filename - need to rename
    if use_temp_dir:
        # Create a temp directory and copy the file with the correct name
        import tempfile as tmp
        temp_dir = Path(tmp.mkdtemp(prefix="javac_"))
        renamed_path = temp_dir / f"{class_name}.java"
        shutil.copy2(file_path, renamed_path)
    else:
        # Rename in the same directory (not recommended)
        renamed_path = file_path.parent / f"{class_name}.java"
        shutil.copy2(file_path, renamed_path)

    try:
        result = subprocess.run(
            ["javac", str(renamed_path)],
            capture_output=True,
            text=True,
            timeout=timeout,
            cwd=renamed_path.parent,
        )

        return CompileResult(
            success=(result.returncode == 0),
            return_code=result.returncode,
            stdout=result.stdout,
            stderr=result.stderr,
            file_path=file_path,
            renamed_to=renamed_path,
        )

    except subprocess.TimeoutExpired:
        return CompileResult(
            success=False,
            return_code=-2,
            stdout="",
            stderr=f"Compilation timed out after {timeout}s",
            file_path=file_path,
            renamed_to=renamed_path,
        )

    except FileNotFoundError:
        return CompileResult(
            success=False,
            return_code=-3,
            stdout="",
            stderr="javac not found. Is Java installed?",
            file_path=file_path,
        )

    finally:
        # Clean up temp directory if we created one
        if use_temp_dir and temp_dir.exists():
            shutil.rmtree(temp_dir, ignore_errors=True)


def compile_all_in_directory(
    directory: Path | str,
    pattern: str = "*.java",
) -> list[CompileResult]:
    """
    Compile all Java files in a directory.

    Args:
        directory: Directory containing Java files.
        pattern: Glob pattern for finding files.

    Returns:
        List of CompileResult for each file.
    """
    directory = Path(directory)
    results = []

    for java_file in directory.glob(pattern):
        results.append(compile_java(java_file))

    return results


# Placeholder for Phase 2
@dataclass
class RunResult:
    """Result of running a Java program."""

    success: bool
    return_code: int
    stdout: str
    stderr: str
    timed_out: bool


def run_java(
    class_name: str,
    working_dir: Path | str,
    stdin_input: str = "",
    timeout: float = 10.0,
) -> RunResult:
    """
    Run a compiled Java class.

    Args:
        class_name: Name of the class to run (without .class extension).
        working_dir: Directory containing the .class file.
        stdin_input: Input to provide via stdin.
        timeout: Maximum seconds to wait for execution.

    Returns:
        RunResult with execution details.

    Note:
        This is a placeholder for Phase 2 (Differential Execution).
    """
    working_dir = Path(working_dir)

    try:
        result = subprocess.run(
            ["java", class_name],
            capture_output=True,
            text=True,
            timeout=timeout,
            cwd=working_dir,
            input=stdin_input,
        )

        return RunResult(
            success=(result.returncode == 0),
            return_code=result.returncode,
            stdout=result.stdout,
            stderr=result.stderr,
            timed_out=False,
        )

    except subprocess.TimeoutExpired:
        return RunResult(
            success=False,
            return_code=-1,
            stdout="",
            stderr=f"Execution timed out after {timeout}s",
            timed_out=True,
        )

    except FileNotFoundError:
        return RunResult(
            success=False,
            return_code=-2,
            stdout="",
            stderr="java not found. Is Java installed?",
            timed_out=False,
        )
