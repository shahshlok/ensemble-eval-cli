"""Rich terminal output for EME results."""
from __future__ import annotations

from typing import Any, Dict, List

from rich import box
from rich.console import Console
from rich.table import Table

console = Console()


def display_results(rows: List[Dict[str, Any]], summary: Dict[str, Any]) -> None:
    """Render a colorized comparison table and summary stats."""
    # Black-and-white table (no color styles)
    table = Table(title="Ensembling Method Evaluation", box=box.SIMPLE_HEAVY, highlight=False)
    table.add_column("Student")
    table.add_column("GPT-5", justify="right")
    table.add_column("EduAI", justify="right")
    table.add_column("Avg", justify="right")
    table.add_column("Diff %", justify="right")
    table.add_column("Flag")
    table.add_column("Comment")

    for row in rows:
        metrics = row.get("metrics", {})
        flag = metrics.get("flag", "")
        table.add_row(
            row.get("student", "Unknown"),
            _fmt_score(metrics.get("gpt5", {})),
            _fmt_score(metrics.get("eduai", {})),
            _fmt_pct(metrics.get("avg_pct")),
            _fmt_pct(metrics.get("diff_pct")),
            flag or "",
            metrics.get("comment", ""),
        )

    console.print(table)

    summary_msg = (
        f"Mean diff: {_fmt_pct(summary.get('mean_diff_pct'))} | "
        f"Flags: {summary.get('flagged_count', 0)}/{summary.get('total', 0)}"
    )
    console.print(summary_msg)


def _fmt_score(payload: Dict[str, Any]) -> str:
    total = payload.get("total")
    max_score = payload.get("max")
    pct = payload.get("pct")
    if total is None:
        return "—"
    total_str = f"{total:.1f}" if isinstance(total, float) else str(total)
    if max_score:
        max_str = f"{max_score:.1f}" if isinstance(max_score, float) else str(max_score)
        total_str = f"{total_str}/{max_str}"
    if pct is not None:
        total_str += f" ({pct:.1f}%)"
    return total_str


def _fmt_pct(value: Any) -> str:
    if value is None:
        return "—"
    try:
        return f"{float(value):.1f}%"
    except (TypeError, ValueError):
        return "—"
