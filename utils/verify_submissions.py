import json
import re
from collections import defaultdict
from pathlib import Path


def analyze_submission(file_path):
    try:
        with open(file_path, encoding="utf-8") as f:
            content = f.read()
    except Exception as e:
        return {"error": str(e)}

    lines = content.splitlines()

    # Indentation analysis
    indentation_types = set()
    for line in lines:
        if line.startswith("\t"):
            indentation_types.add("tab")
        elif line.startswith(" "):
            indentation_types.add("space")

    # Variable naming heuristic
    var_decls = re.findall(r"(int|double|String|boolean)\s+([a-zA-Z0-9_]+)\s*=", content)
    var_names = [v[1] for v in var_decls]
    avg_var_len = sum(len(v) for v in var_names) / len(var_names) if var_names else 0

    stats = {
        "size": len(content),
        "lines": len(lines),
        "has_main": "public static void main" in content,
        "has_scanner": "Scanner" in content,
        "has_comments": "//" in content or "/*" in content,
        "class_name": None,
        "imports": len(re.findall(r"^import\s+", content, re.MULTILINE)),
        "todos": "TODO" in content,
        "empty": len(content.strip()) == 0,
        "indentation": list(indentation_types),
        "avg_var_len": avg_var_len,
        "content_preview": content[:100].replace("\n", "\\n"),
    }

    # Extract class name
    match = re.search(r"class\s+(\w+)", content)
    if match:
        stats["class_name"] = match.group(1)

    return stats


def verify_corpus(base_path):
    base_dir = Path(base_path)
    manifest_path = base_dir / "manifest.json"

    # Load Manifest
    with open(manifest_path) as f:
        manifest = json.load(f)

    # Index manifest by student_id
    manifest_data = defaultdict(dict)
    for entry in manifest:
        manifest_data[entry["student_id"]][entry["question_id"]] = entry

    students = [d for d in base_dir.iterdir() if d.is_dir()]
    print(f"Found {len(students)} student directories.")

    results = defaultdict(dict)
    missing_files = []
    anomalies = []

    questions = ["Q1", "Q2", "Q3", "Q4"]

    for student_dir in sorted(students):
        dir_name = student_dir.name
        if dir_name.startswith("."):
            continue

        # Match directory name to student ID in manifest
        # Directory format: Name_ID_Archetype (e.g., Anderson_Noah_200113_DT003)
        # Manifest ID: Name_ID (e.g., Anderson_Noah_200113)
        matched_sid = None
        for sid in manifest_data:
            if dir_name.startswith(sid):
                matched_sid = sid
                break

        if not matched_sid:
            anomalies.append(f"Directory {dir_name} has no matching manifest entry")
            continue

        for q_name in questions:
            file_name = f"{q_name}.java"
            q_path = student_dir / file_name
            q_key = q_name.lower()  # manifest uses 'q1', 'q2'...

            if not q_path.exists():
                missing_files.append(f"{dir_name}/{file_name}")
                results[dir_name][q_name] = {"status": "MISSING"}
                continue

            stats = analyze_submission(q_path)

            # Cross-reference with manifest
            manifest_entry = manifest_data[matched_sid].get(q_key)
            if manifest_entry:
                stats["expected_correct"] = manifest_entry["is_correct"]
                stats["expected_misconception"] = manifest_entry["misconception_id"]

            results[dir_name][q_name] = stats

            # Anomaly Checks
            if stats.get("empty"):
                anomalies.append(f"{dir_name}/{file_name} is EMPTY")
            if not stats.get("has_main") and not stats.get("error"):
                anomalies.append(f"{dir_name}/{file_name} missing main method")

            # Class name check (should match filename usually, but students make mistakes)
            if stats.get("class_name") and stats.get("class_name") != q_name:
                # This is a common student error, so it's "authentic" but worth tracking
                pass

    # --- REPORT GENERATION ---
    print("\n" + "=" * 60)
    print("VERIFICATION REPORT")
    print("=" * 60)

    print(f"Total Student Directories: {len(students)}")
    print(f"Total Manifest Entries: {len(manifest_data)} students")

    if missing_files:
        print(f"\n[CRITICAL] MISSING FILES ({len(missing_files)}):")
        for f in missing_files:
            print(f"  - {f}")
    else:
        print("\n[OK] All expected files are present.")

    if anomalies:
        print(f"\n[WARNING] ANOMALIES FOUND ({len(anomalies)}):")
        for a in anomalies:
            print(f"  - {a}")
    else:
        print("\n[OK] No structural anomalies found.")

    # Diversity & Authenticity Metrics
    print("\n" + "-" * 30)
    print("AUTHENTICITY METRICS")
    print("-" * 30)

    total_files = 0
    with_comments = 0
    with_todos = 0
    mixed_indent = 0
    short_vars = 0
    long_vars = 0

    for s, qs in results.items():
        for q, data in qs.items():
            if data.get("status") == "MISSING" or "error" in data:
                continue
            total_files += 1

            if data.get("has_comments"):
                with_comments += 1
            if data.get("todos"):
                with_todos += 1

            indents = data.get("indentation", [])
            if "space" in indents and "tab" in indents:
                mixed_indent += 1

            avg_len = data.get("avg_var_len", 0)
            if avg_len > 0 and avg_len < 3:
                short_vars += 1
            if avg_len > 10:
                long_vars += 1

    print(f"Total Files Analyzed: {total_files}")
    print(f"Files with Comments: {with_comments} ({with_comments / total_files * 100:.1f}%)")
    print(f"Files with TODOs: {with_todos} ({with_todos / total_files * 100:.1f}%)")
    print(f"Files with Mixed Indentation: {mixed_indent} ({mixed_indent / total_files * 100:.1f}%)")
    print(
        f"Files with Short Variable Names (<3 chars): {short_vars} ({short_vars / total_files * 100:.1f}%)"
    )
    print(
        f"Files with Long Variable Names (>10 chars): {long_vars} ({long_vars / total_files * 100:.1f}%)"
    )

    # Correctness Distribution Check (based on manifest)
    correct_count = 0
    incorrect_count = 0
    for sid, qs in manifest_data.items():
        for qid, entry in qs.items():
            if entry["is_correct"]:
                correct_count += 1
            else:
                incorrect_count += 1

    print("\n" + "-" * 30)
    print("DATASET BALANCE (Manifest)")
    print("-" * 30)
    print(f"Correct Submissions: {correct_count}")
    print(f"Incorrect Submissions: {incorrect_count}")
    print(f"Ratio: {correct_count / (correct_count + incorrect_count):.2f}")


if __name__ == "__main__":
    verify_corpus("/Users/Shlok/Seventh Term/Honours/ensemble-eval-cli/authentic_seeded")
