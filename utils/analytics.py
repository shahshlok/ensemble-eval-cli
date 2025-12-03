"""
Statistical Analytics Engine for LLM Misconception Detection Evaluation.

Provides rigorous statistical comparison of prompt strategies with:
- Cochran's Q Test for omnibus comparisons
- McNemar's Test for pairwise comparisons (strategies AND models)
- Cohen's Kappa and Krippendorff's Alpha for inter-rater agreement
- Per-misconception detection analysis
- Per-model comparison (GPT-5.1 vs Gemini 2.5 Flash)
- Integrated visualization generation
"""

import json
from collections import defaultdict
from dataclasses import dataclass, field
from itertools import combinations
from pathlib import Path
from typing import Any

import numpy as np
from scipy import stats

# Visualization imports (optional - gracefully degrade if not available)
try:
    import matplotlib.pyplot as plt
    import pandas as pd
    import seaborn as sns

    HAS_PLOTTING = True
except ImportError:
    HAS_PLOTTING = False

# Model configuration for GPT-5.1 and Gemini 2.5 Flash
MODEL_CONFIG = {
    "openai/gpt-5.1": {
        "short_name": "GPT-5.1",
        "color": "#10a37f",  # OpenAI green
        "display_order": 1,
    },
    "google/gemini-2.5-flash-preview-09-2025": {
        "short_name": "Gemini-2.5-Flash",
        "color": "#4285f4",  # Google blue
        "display_order": 2,
    },
}


@dataclass
class GroundTruth:
    """Ground truth for a student-question pair."""

    student_id: str
    question_id: str
    misconception_id: str | None
    is_correct: bool


@dataclass
class PredictionRecord:
    """A single prediction from a model for a student-question pair."""

    student_id: str
    question_id: str
    strategy: str
    model: str
    has_misconception: bool  # Binary: did the model detect any misconception?
    detected_ids: list[str] = field(default_factory=list)  # Mapped misconception IDs


@dataclass
class StrategyMetrics:
    """Metrics for a single strategy."""

    strategy: str
    tp: int = 0
    fp: int = 0
    fn: int = 0
    tn: int = 0
    precision: float = 0.0
    recall: float = 0.0
    f1: float = 0.0
    accuracy: float = 0.0

    def calculate(self):
        """Compute derived metrics."""
        if self.tp + self.fp > 0:
            self.precision = self.tp / (self.tp + self.fp)
        if self.tp + self.fn > 0:
            self.recall = self.tp / (self.tp + self.fn)
        if self.precision + self.recall > 0:
            self.f1 = 2 * (self.precision * self.recall) / (self.precision + self.recall)
        total = self.tp + self.fp + self.fn + self.tn
        if total > 0:
            self.accuracy = (self.tp + self.tn) / total


class AnalyticsEngine:
    """
    Core analytics engine for loading data and computing confusion matrices.

    Loads evaluation data from multiple strategies and aligns with ground truth
    to compute per-strategy and per-model performance metrics.
    """

    STRATEGIES = ["minimal", "baseline", "socratic", "rubric_only"]

    MISCONCEPTION_PATTERNS = {
        "DT001": [
            "int instead of double",
            "using int.*decimal",
            "integer.*when.*double",
            "nextInt.*instead.*nextDouble",
        ],
        "DT002": ["integer division", "truncat", "int.*division"],
        "DT003": ["type mismatch", "nextInt.*nextDouble", "scanner.*wrong.*type"],
        "VAR001": ["operator precedence", "order of operations", "parenthes", "missing.*parenthes"],
        "VAR002": ["addition instead.*subtraction", "wrong.*operator", r"\+ instead.*-"],
        "VAR003": ["fuel.*formula", "cost.*calculation.*wrong", "multipl.*instead.*divid"],
        "VAR004": ["semi-?perimeter", "intermediate.*variable"],
        "CONST001": [r"\^.*instead.*pow", "caret.*xor", "exponent.*wrong"],
        "CONST002": ["missing.*sqrt", "no.*sqrt", "square root.*missing"],
        "CONST003": ["pow.*argument.*order", "swap.*base.*exponent"],
        "INPUT001": ["missing.*import", "no.*import.*Scanner"],
        "INPUT002": ["wrong.*number.*input", "missing.*input", "not.*reading.*all"],
        "INPUT003": [r"not.*clos.*scanner", r"resource.*leak", r"scanner\.close"],
        "OTHER001": ["wrong.*problem", "different.*problem", "misunderstand.*question"],
        "OTHER002": ["hardcod", "literal.*instead.*input"],
    }

    def __init__(
        self,
        manifest_path: str = "authentic_seeded/manifest.json",
        evals_dir: str = "student_evals",
    ):
        self.manifest_path = Path(manifest_path)
        self.evals_dir = Path(evals_dir)

        self.ground_truth: dict[tuple[str, str], GroundTruth] = {}
        self.predictions: dict[str, list[PredictionRecord]] = {s: [] for s in self.STRATEGIES}
        self.strategy_metrics: dict[str, StrategyMetrics] = {}
        self.model_metrics: dict[
            str, dict[str, StrategyMetrics]
        ] = {}  # model -> strategy -> metrics

    def load_ground_truth(self) -> int:
        """Load ground truth from manifest.json."""
        with open(self.manifest_path) as f:
            manifest = json.load(f)

        for entry in manifest:
            gt = GroundTruth(
                student_id=entry["student_id"],
                question_id=entry["question_id"],
                misconception_id=entry.get("misconception_id"),
                is_correct=entry.get("is_correct", entry.get("misconception_id") is None),
            )
            self.ground_truth[(gt.student_id, gt.question_id)] = gt

        return len(self.ground_truth)

    def _extract_base_student_id(self, full_id: str) -> str:
        """Extract base student ID without suffix."""
        parts = full_id.rsplit("_", 1)
        if len(parts) == 2 and (
            parts[1] in ("Correct", "Mixed")
            or any(parts[1].startswith(p) for p in ("DT", "VAR", "CONST", "INPUT", "OTHER"))
        ):
            return parts[0]
        return full_id

    def _map_to_misconception_id(self, name: str, description: str) -> str | None:
        """Map detected misconception to ground truth ID using patterns."""
        import re

        text = f"{name} {description}".lower()

        for gt_id, patterns in self.MISCONCEPTION_PATTERNS.items():
            for pattern in patterns:
                if re.search(pattern, text, re.IGNORECASE):
                    return gt_id
        return None

    def load_evaluations(self, strategy: str) -> int:
        """Load evaluation documents for a strategy."""
        strategy_dir = self.evals_dir / strategy
        if not strategy_dir.exists():
            return 0

        count = 0
        for eval_file in strategy_dir.glob("*_eval.json"):
            with open(eval_file) as f:
                data = json.load(f)

            full_student_id = data["submission"]["student_id"]
            base_student_id = self._extract_base_student_id(full_student_id)
            question_id = data["context"]["question_id"]

            for model_name, model_eval in data["models"].items():
                misconceptions = model_eval.get("misconceptions", [])
                has_misconception = len(misconceptions) > 0

                detected_ids = []
                for misc in misconceptions:
                    mapped_id = self._map_to_misconception_id(
                        misc.get("name", ""), misc.get("description", "")
                    )
                    if mapped_id:
                        detected_ids.append(mapped_id)

                record = PredictionRecord(
                    student_id=base_student_id,
                    question_id=question_id,
                    strategy=strategy,
                    model=model_name,
                    has_misconception=has_misconception,
                    detected_ids=detected_ids,
                )
                self.predictions[strategy].append(record)

            count += 1

        return count

    def load_all_strategies(self) -> dict[str, int]:
        """Load evaluations for all strategies."""
        counts = {}
        for strategy in self.STRATEGIES:
            counts[strategy] = self.load_evaluations(strategy)
        return counts

    def compute_strategy_metrics(self) -> dict[str, StrategyMetrics]:
        """Compute confusion matrix metrics for each strategy."""
        for strategy in self.STRATEGIES:
            metrics = StrategyMetrics(strategy=strategy)

            # Group predictions by (student_id, question_id)
            pred_by_submission: dict[tuple[str, str], list[PredictionRecord]] = defaultdict(list)
            for pred in self.predictions[strategy]:
                pred_by_submission[(pred.student_id, pred.question_id)].append(pred)

            for (student_id, question_id), preds in pred_by_submission.items():
                gt = self.ground_truth.get((student_id, question_id))
                if not gt:
                    continue

                # Ensemble: any model detected = detected
                any_detected = any(p.has_misconception for p in preds)

                if gt.is_correct:
                    if any_detected:
                        metrics.fp += 1
                    else:
                        metrics.tn += 1
                else:
                    if any_detected:
                        metrics.tp += 1
                    else:
                        metrics.fn += 1

            metrics.calculate()
            self.strategy_metrics[strategy] = metrics

        return self.strategy_metrics

    def compute_per_model_metrics(self) -> dict[str, dict[str, StrategyMetrics]]:
        """Compute metrics broken down by model and strategy."""
        for strategy in self.STRATEGIES:
            # Group by model
            preds_by_model: dict[str, dict[tuple[str, str], PredictionRecord]] = defaultdict(dict)
            for pred in self.predictions[strategy]:
                preds_by_model[pred.model][(pred.student_id, pred.question_id)] = pred

            for model_name, model_preds in preds_by_model.items():
                if model_name not in self.model_metrics:
                    self.model_metrics[model_name] = {}

                metrics = StrategyMetrics(strategy=strategy)

                for (student_id, question_id), pred in model_preds.items():
                    gt = self.ground_truth.get((student_id, question_id))
                    if not gt:
                        continue

                    if gt.is_correct:
                        if pred.has_misconception:
                            metrics.fp += 1
                        else:
                            metrics.tn += 1
                    else:
                        if pred.has_misconception:
                            metrics.tp += 1
                        else:
                            metrics.fn += 1

                metrics.calculate()
                self.model_metrics[model_name][strategy] = metrics

        return self.model_metrics

    def get_binary_outcomes(self, strategy: str) -> dict[tuple[str, str], int]:
        """
        Get binary detection outcomes for a strategy.
        Returns dict of (student_id, question_id) -> 1 if correct detection, 0 otherwise.
        """
        outcomes = {}

        pred_by_submission: dict[tuple[str, str], list[PredictionRecord]] = defaultdict(list)
        for pred in self.predictions[strategy]:
            pred_by_submission[(pred.student_id, pred.question_id)].append(pred)

        for (student_id, question_id), preds in pred_by_submission.items():
            gt = self.ground_truth.get((student_id, question_id))
            if not gt:
                continue

            any_detected = any(p.has_misconception for p in preds)

            # Correct if: (error exists and detected) or (no error and not detected)
            correct = (not gt.is_correct and any_detected) or (gt.is_correct and not any_detected)
            outcomes[(student_id, question_id)] = 1 if correct else 0

        return outcomes

    def get_misconception_detection_rates(self) -> dict[str, dict[str, dict[str, Any]]]:
        """
        Compute per-misconception detection rates for each strategy.
        Returns: strategy -> misconception_id -> {total, detected, rate}
        """
        rates: dict[str, dict[str, dict[str, Any]]] = {}

        for strategy in self.STRATEGIES:
            rates[strategy] = {}

            # Count total occurrences and detections per misconception
            total_by_misc: dict[str, int] = defaultdict(int)
            detected_by_misc: dict[str, int] = defaultdict(int)

            pred_by_submission: dict[tuple[str, str], list[PredictionRecord]] = defaultdict(list)
            for pred in self.predictions[strategy]:
                pred_by_submission[(pred.student_id, pred.question_id)].append(pred)

            for (student_id, question_id), preds in pred_by_submission.items():
                gt = self.ground_truth.get((student_id, question_id))
                if not gt or gt.is_correct or not gt.misconception_id:
                    continue

                total_by_misc[gt.misconception_id] += 1

                # Check if any model detected this specific misconception
                all_detected_ids = set()
                for p in preds:
                    all_detected_ids.update(p.detected_ids)

                if gt.misconception_id in all_detected_ids:
                    detected_by_misc[gt.misconception_id] += 1
                elif any(p.has_misconception for p in preds):
                    # Detected something, but not mapped to correct ID
                    # Still count as partial detection
                    detected_by_misc[gt.misconception_id] += 0.5

            for misc_id, total in total_by_misc.items():
                detected = detected_by_misc.get(misc_id, 0)
                rates[strategy][misc_id] = {
                    "total": total,
                    "detected": detected,
                    "rate": detected / total if total > 0 else 0.0,
                }

        return rates

    def get_llm_vs_ground_truth_distribution(self) -> dict[str, dict[str, Any]]:
        """
        Compare LLM-detected misconception distribution vs ground truth.

        Returns: misconception_id -> {
            ground_truth_count: int,
            llm_detected_count: int,
            delta: int (positive = over-detection, negative = under-detection),
            ratio: float (llm/gt, >1 = over-detection)
        }
        """
        # Count ground truth occurrences
        gt_counts: dict[str, int] = defaultdict(int)
        for gt in self.ground_truth.values():
            if gt.misconception_id:
                gt_counts[gt.misconception_id] += 1

        # Count LLM detections across all strategies (deduplicated per submission)
        llm_counts: dict[str, int] = defaultdict(int)

        # Track unique detections per (student, question) to avoid double-counting
        seen_detections: dict[tuple[str, str], set[str]] = defaultdict(set)

        for strategy in self.STRATEGIES:
            for pred in self.predictions[strategy]:
                key = (pred.student_id, pred.question_id)
                for misc_id in pred.detected_ids:
                    if misc_id not in seen_detections[key]:
                        seen_detections[key].add(misc_id)
                        llm_counts[misc_id] += 1

        # Combine into comparison
        all_ids = set(gt_counts.keys()) | set(llm_counts.keys())
        result = {}

        for misc_id in all_ids:
            gt = gt_counts.get(misc_id, 0)
            llm = llm_counts.get(misc_id, 0)
            delta = llm - gt
            ratio = llm / gt if gt > 0 else float("inf") if llm > 0 else 0

            result[misc_id] = {
                "ground_truth_count": gt,
                "llm_detected_count": llm,
                "delta": delta,
                "ratio": ratio,
                "assessment": self._assess_detection(delta, gt, llm),
            }

        return result

    def _assess_detection(self, delta: int, gt: int, llm: int) -> str:
        """Provide human-readable assessment of detection accuracy."""
        if gt == 0 and llm > 0:
            return "Hallucinated (not in ground truth)"
        if gt > 0 and llm == 0:
            return "Completely missed"
        if delta == 0:
            return "Exact match"
        if delta > 0:
            pct = (delta / gt) * 100 if gt > 0 else 100
            if pct > 50:
                return f"Over-detected (+{pct:.0f}%)"
            return f"Slightly over (+{pct:.0f}%)"
        else:
            pct = (abs(delta) / gt) * 100
            if pct > 50:
                return f"Under-detected ({pct:.0f}% missed)"
            return f"Slightly under ({pct:.0f}% missed)"

    def get_model_binary_outcomes(self, model: str, strategy: str) -> dict[tuple[str, str], int]:
        """
        Get binary detection outcomes for a specific model on a strategy.
        Returns dict of (student_id, question_id) -> 1 if correct detection, 0 otherwise.
        """
        outcomes = {}

        for pred in self.predictions[strategy]:
            if pred.model != model:
                continue

            key = (pred.student_id, pred.question_id)
            gt = self.ground_truth.get(key)
            if not gt:
                continue

            # Correct if: (error exists and detected) or (no error and not detected)
            correct = (not gt.is_correct and pred.has_misconception) or (
                gt.is_correct and not pred.has_misconception
            )
            outcomes[key] = 1 if correct else 0

        return outcomes

    def get_per_model_misconception_rates(self) -> dict[str, dict[str, dict[str, Any]]]:
        """
        Compute per-misconception detection rates broken down by model.
        Returns: model -> misconception_id -> {total, detected, rate}
        """
        rates: dict[str, dict[str, dict[str, Any]]] = {}

        # Get all models
        models = set()
        for strategy in self.STRATEGIES:
            for pred in self.predictions[strategy]:
                models.add(pred.model)

        for model in models:
            rates[model] = {}
            total_by_misc: dict[str, int] = defaultdict(int)
            detected_by_misc: dict[str, float] = defaultdict(float)

            # Aggregate across all strategies for this model
            seen_submissions: dict[str, set[tuple[str, str]]] = defaultdict(set)

            for strategy in self.STRATEGIES:
                for pred in self.predictions[strategy]:
                    if pred.model != model:
                        continue

                    key = (pred.student_id, pred.question_id)
                    gt = self.ground_truth.get(key)
                    if not gt or gt.is_correct or not gt.misconception_id:
                        continue

                    misc_id = gt.misconception_id

                    # Only count each submission once per misconception
                    if key not in seen_submissions[misc_id]:
                        seen_submissions[misc_id].add(key)
                        total_by_misc[misc_id] += 1

                        # Check detection
                        if misc_id in pred.detected_ids:
                            detected_by_misc[misc_id] += 1
                        elif pred.has_misconception:
                            detected_by_misc[misc_id] += 0.5  # Partial credit

            for misc_id, total in total_by_misc.items():
                detected = detected_by_misc.get(misc_id, 0)
                rates[model][misc_id] = {
                    "total": total,
                    "detected": detected,
                    "rate": detected / total if total > 0 else 0.0,
                }

        return rates

    def get_per_question_metrics(self) -> dict[str, dict[str, dict[str, Any]]]:
        """
        Compute metrics broken down by question.
        Returns: question_id -> strategy -> metrics
        """
        results: dict[str, dict[str, dict[str, Any]]] = defaultdict(dict)

        for strategy in self.STRATEGIES:
            by_question: dict[str, dict[str, int]] = defaultdict(
                lambda: {"tp": 0, "fp": 0, "fn": 0, "tn": 0}
            )

            pred_by_submission: dict[tuple[str, str], list[PredictionRecord]] = defaultdict(list)
            for pred in self.predictions[strategy]:
                pred_by_submission[(pred.student_id, pred.question_id)].append(pred)

            for (student_id, question_id), preds in pred_by_submission.items():
                gt = self.ground_truth.get((student_id, question_id))
                if not gt:
                    continue

                any_detected = any(p.has_misconception for p in preds)

                if gt.is_correct:
                    if any_detected:
                        by_question[question_id]["fp"] += 1
                    else:
                        by_question[question_id]["tn"] += 1
                else:
                    if any_detected:
                        by_question[question_id]["tp"] += 1
                    else:
                        by_question[question_id]["fn"] += 1

            for qid, counts in by_question.items():
                tp, fp, fn, tn = counts["tp"], counts["fp"], counts["fn"], counts["tn"]
                precision = tp / (tp + fp) if (tp + fp) > 0 else 0
                recall = tp / (tp + fn) if (tp + fn) > 0 else 0
                f1 = (
                    2 * precision * recall / (precision + recall) if (precision + recall) > 0 else 0
                )
                accuracy = (tp + tn) / (tp + fp + fn + tn) if (tp + fp + fn + tn) > 0 else 0

                results[qid][strategy] = {
                    "tp": tp,
                    "fp": fp,
                    "fn": fn,
                    "tn": tn,
                    "precision": precision,
                    "recall": recall,
                    "f1": f1,
                    "accuracy": accuracy,
                }

        return dict(results)

    def get_model_comparison_summary(self) -> dict[str, Any]:
        """
        Generate a head-to-head comparison summary between models.
        Returns detailed comparison metrics.
        """
        models = list(MODEL_CONFIG.keys())
        if len(models) < 2:
            return {"error": "Need at least 2 models for comparison"}

        model_metrics = self.compute_per_model_metrics()
        comparison = {
            "models": {},
            "head_to_head": {},
            "agreement": {},
        }

        # Per-model aggregate metrics
        for model in models:
            if model not in model_metrics:
                continue
            short_name = MODEL_CONFIG.get(model, {}).get("short_name", model.split("/")[-1])

            # Average across strategies
            strategies_data = model_metrics[model]
            avg_precision = np.mean([m.precision for m in strategies_data.values()])
            avg_recall = np.mean([m.recall for m in strategies_data.values()])
            avg_f1 = np.mean([m.f1 for m in strategies_data.values()])

            comparison["models"][short_name] = {
                "full_name": model,
                "avg_precision": round(avg_precision, 4),
                "avg_recall": round(avg_recall, 4),
                "avg_f1": round(avg_f1, 4),
                "by_strategy": {
                    s: {
                        "precision": round(m.precision, 4),
                        "recall": round(m.recall, 4),
                        "f1": round(m.f1, 4),
                    }
                    for s, m in strategies_data.items()
                },
            }

        return comparison


class StatisticalTester:
    """
    Statistical testing for comparing strategies.

    Implements:
    - Cochran's Q Test (omnibus test for k related samples)
    - McNemar's Test (pairwise comparison with Bonferroni correction)
    - Cohen's Kappa (inter-rater agreement)
    - Krippendorff's Alpha (reliability coefficient)
    """

    def __init__(self, engine: AnalyticsEngine):
        self.engine = engine

    def cochrans_q_test(self) -> dict[str, Any]:
        """
        Perform Cochran's Q test to check if strategies differ significantly.

        H0: All strategies have the same success rate.
        H1: At least one strategy differs.
        """
        strategies = self.engine.STRATEGIES

        # Build binary outcome matrix: rows = submissions, cols = strategies
        all_keys = set()
        outcomes_by_strategy = {}
        for strategy in strategies:
            outcomes = self.engine.get_binary_outcomes(strategy)
            outcomes_by_strategy[strategy] = outcomes
            all_keys.update(outcomes.keys())

        # Filter to common submissions only
        common_keys = all_keys.copy()
        for outcomes in outcomes_by_strategy.values():
            common_keys &= set(outcomes.keys())

        if len(common_keys) < 10:
            return {
                "test": "Cochran's Q",
                "statistic": None,
                "p_value": None,
                "df": None,
                "n_samples": len(common_keys),
                "error": "Insufficient common samples for test",
            }

        # Build matrix
        sorted_keys = sorted(common_keys)
        matrix = np.array(
            [[outcomes_by_strategy[s].get(k, 0) for s in strategies] for k in sorted_keys]
        )

        n, k = matrix.shape

        # Cochran's Q formula
        col_sums = matrix.sum(axis=0)  # T_j
        row_sums = matrix.sum(axis=1)  # L_i

        T = col_sums.sum()  # Total successes

        numerator = (k - 1) * (k * np.sum(col_sums**2) - T**2)
        denominator = k * T - np.sum(row_sums**2)

        if denominator == 0:
            return {
                "test": "Cochran's Q",
                "statistic": 0,
                "p_value": 1.0,
                "df": k - 1,
                "n_samples": n,
                "error": "No variation in outcomes",
            }

        Q = numerator / denominator
        df = k - 1
        p_value = 1 - stats.chi2.cdf(Q, df)

        return {
            "test": "Cochran's Q",
            "statistic": round(Q, 4),
            "p_value": round(p_value, 6),
            "df": df,
            "n_samples": n,
            "significant": p_value < 0.05,
            "interpretation": "Strategies differ significantly"
            if p_value < 0.05
            else "No significant difference",
        }

    def mcnemar_test(self, strategy1: str, strategy2: str) -> dict[str, Any]:
        """
        Perform McNemar's test for pairwise strategy comparison.

        Tests if the disagreement between two strategies is symmetric.
        """
        outcomes1 = self.engine.get_binary_outcomes(strategy1)
        outcomes2 = self.engine.get_binary_outcomes(strategy2)

        common_keys = set(outcomes1.keys()) & set(outcomes2.keys())

        # Build contingency: b = s1 correct & s2 wrong, c = s1 wrong & s2 correct
        b = sum(1 for k in common_keys if outcomes1[k] == 1 and outcomes2[k] == 0)
        c = sum(1 for k in common_keys if outcomes1[k] == 0 and outcomes2[k] == 1)

        # McNemar's test statistic (with continuity correction)
        if b + c == 0:
            return {
                "test": "McNemar",
                "strategies": (strategy1, strategy2),
                "b": b,
                "c": c,
                "statistic": 0,
                "p_value": 1.0,
                "error": "No discordant pairs",
            }

        chi2 = (abs(b - c) - 1) ** 2 / (b + c)
        p_value = 1 - stats.chi2.cdf(chi2, 1)

        return {
            "test": "McNemar",
            "strategies": (strategy1, strategy2),
            "b": b,
            "c": c,
            "statistic": round(chi2, 4),
            "p_value": round(p_value, 6),
            "significant": p_value < 0.05,
            "winner": strategy1 if b > c else strategy2 if c > b else "tie",
        }

    def mcnemar_all_pairs(self, alpha: float = 0.05) -> list[dict[str, Any]]:
        """
        Perform McNemar's test for all strategy pairs with Bonferroni correction.
        """
        pairs = list(combinations(self.engine.STRATEGIES, 2))
        n_tests = len(pairs)
        corrected_alpha = alpha / n_tests  # Bonferroni

        results = []
        for s1, s2 in pairs:
            result = self.mcnemar_test(s1, s2)
            result["bonferroni_alpha"] = round(corrected_alpha, 6)
            result["significant_corrected"] = result.get("p_value", 1) < corrected_alpha
            results.append(result)

        return results

    def mcnemar_models(self, model1: str, model2: str, strategy: str) -> dict[str, Any]:
        """
        Perform McNemar's test for pairwise MODEL comparison on a given strategy.
        Tests if the disagreement between two models is symmetric.
        """
        outcomes1 = self.engine.get_model_binary_outcomes(model1, strategy)
        outcomes2 = self.engine.get_model_binary_outcomes(model2, strategy)

        common_keys = set(outcomes1.keys()) & set(outcomes2.keys())

        if len(common_keys) < 10:
            return {
                "test": "McNemar (Models)",
                "models": (model1, model2),
                "strategy": strategy,
                "error": "Insufficient common samples",
            }

        # Build contingency: b = m1 correct & m2 wrong, c = m1 wrong & m2 correct
        b = sum(1 for k in common_keys if outcomes1[k] == 1 and outcomes2[k] == 0)
        c = sum(1 for k in common_keys if outcomes1[k] == 0 and outcomes2[k] == 1)

        if b + c == 0:
            return {
                "test": "McNemar (Models)",
                "models": (model1, model2),
                "strategy": strategy,
                "b": b,
                "c": c,
                "statistic": 0,
                "p_value": 1.0,
                "error": "No discordant pairs",
            }

        chi2 = (abs(b - c) - 1) ** 2 / (b + c)
        p_value = 1 - stats.chi2.cdf(chi2, 1)

        short1 = MODEL_CONFIG.get(model1, {}).get("short_name", model1.split("/")[-1])
        short2 = MODEL_CONFIG.get(model2, {}).get("short_name", model2.split("/")[-1])

        return {
            "test": "McNemar (Models)",
            "models": (model1, model2),
            "model_names": (short1, short2),
            "strategy": strategy,
            "b": b,
            "c": c,
            "statistic": round(chi2, 4),
            "p_value": round(p_value, 6),
            "significant": p_value < 0.05,
            "winner": short1 if b > c else short2 if c > b else "tie",
            "winner_full": model1 if b > c else model2 if c > b else "tie",
        }

    def mcnemar_all_model_pairs(self, alpha: float = 0.05) -> dict[str, list[dict[str, Any]]]:
        """
        Perform McNemar's test for all model pairs across all strategies.
        Returns: strategy -> list of pairwise results
        """
        # Get all models
        models = set()
        for strategy in self.engine.STRATEGIES:
            for pred in self.engine.predictions[strategy]:
                models.add(pred.model)

        models = sorted(models)
        pairs = list(combinations(models, 2))
        n_tests = len(pairs) * len(self.engine.STRATEGIES)
        corrected_alpha = alpha / n_tests if n_tests > 0 else alpha

        results_by_strategy = {}

        for strategy in self.engine.STRATEGIES:
            results = []
            for m1, m2 in pairs:
                result = self.mcnemar_models(m1, m2, strategy)
                result["bonferroni_alpha"] = round(corrected_alpha, 6)
                result["significant_corrected"] = result.get("p_value", 1) < corrected_alpha
                results.append(result)
            results_by_strategy[strategy] = results

        return results_by_strategy

    def get_model_agreement_matrix(self, strategy: str) -> dict[str, Any]:
        """
        Build a full agreement matrix between all models for a strategy.
        Returns both raw counts and Kappa values.
        """
        models = set()
        for pred in self.engine.predictions[strategy]:
            models.add(pred.model)

        models = sorted(models)
        n = len(models)

        kappa_matrix = np.zeros((n, n))
        agreement_matrix = np.zeros((n, n))

        for i, m1 in enumerate(models):
            for j, m2 in enumerate(models):
                if i == j:
                    kappa_matrix[i, j] = 1.0
                    agreement_matrix[i, j] = 1.0
                elif i < j:
                    result = self.cohens_kappa(m1, m2, strategy)
                    kappa = result.get("kappa", 0) or 0
                    agreement = result.get("observed_agreement", 0) or 0
                    kappa_matrix[i, j] = kappa
                    kappa_matrix[j, i] = kappa
                    agreement_matrix[i, j] = agreement
                    agreement_matrix[j, i] = agreement

        short_names = [MODEL_CONFIG.get(m, {}).get("short_name", m.split("/")[-1]) for m in models]

        return {
            "models": models,
            "short_names": short_names,
            "kappa_matrix": kappa_matrix.tolist(),
            "agreement_matrix": agreement_matrix.tolist(),
        }

    def cohens_kappa(self, model1: str, model2: str, strategy: str) -> dict[str, Any]:
        """
        Compute Cohen's Kappa for agreement between two models on a strategy.
        """
        # Get predictions for both models
        preds_by_model: dict[str, dict[tuple[str, str], bool]] = defaultdict(dict)

        for pred in self.engine.predictions[strategy]:
            preds_by_model[pred.model][(pred.student_id, pred.question_id)] = pred.has_misconception

        if model1 not in preds_by_model or model2 not in preds_by_model:
            return {
                "test": "Cohen's Kappa",
                "models": (model1, model2),
                "strategy": strategy,
                "kappa": None,
                "error": "Model not found",
            }

        common_keys = set(preds_by_model[model1].keys()) & set(preds_by_model[model2].keys())

        if len(common_keys) < 10:
            return {
                "test": "Cohen's Kappa",
                "models": (model1, model2),
                "strategy": strategy,
                "kappa": None,
                "error": "Insufficient samples",
            }

        # Build confusion matrix
        a = sum(1 for k in common_keys if preds_by_model[model1][k] and preds_by_model[model2][k])
        b = sum(
            1 for k in common_keys if preds_by_model[model1][k] and not preds_by_model[model2][k]
        )
        c = sum(
            1 for k in common_keys if not preds_by_model[model1][k] and preds_by_model[model2][k]
        )
        d = sum(
            1
            for k in common_keys
            if not preds_by_model[model1][k] and not preds_by_model[model2][k]
        )

        n = a + b + c + d
        p_o = (a + d) / n  # Observed agreement
        p_e = ((a + b) * (a + c) + (c + d) * (b + d)) / (n * n)  # Expected agreement

        if p_e == 1:
            kappa = 1.0
        else:
            kappa = (p_o - p_e) / (1 - p_e)

        # Interpretation
        if kappa < 0:
            interpretation = "Poor (less than chance)"
        elif kappa < 0.2:
            interpretation = "Slight"
        elif kappa < 0.4:
            interpretation = "Fair"
        elif kappa < 0.6:
            interpretation = "Moderate"
        elif kappa < 0.8:
            interpretation = "Substantial"
        else:
            interpretation = "Almost Perfect"

        return {
            "test": "Cohen's Kappa",
            "models": (model1, model2),
            "strategy": strategy,
            "kappa": round(kappa, 4),
            "observed_agreement": round(p_o, 4),
            "expected_agreement": round(p_e, 4),
            "n_samples": n,
            "interpretation": interpretation,
            "contingency": {"both_yes": a, "m1_yes_m2_no": b, "m1_no_m2_yes": c, "both_no": d},
        }

    def krippendorff_alpha(self, strategy: str) -> dict[str, Any]:
        """
        Compute Krippendorff's Alpha for all models on a strategy.

        More robust than Kappa for multiple raters.
        """
        # Group predictions by submission
        preds_by_submission: dict[tuple[str, str], dict[str, int]] = defaultdict(dict)
        models_seen = set()

        for pred in self.engine.predictions[strategy]:
            key = (pred.student_id, pred.question_id)
            preds_by_submission[key][pred.model] = 1 if pred.has_misconception else 0
            models_seen.add(pred.model)

        if len(models_seen) < 2:
            return {
                "test": "Krippendorff's Alpha",
                "strategy": strategy,
                "alpha": None,
                "error": "Need at least 2 raters",
            }

        models = sorted(models_seen)

        # Build reliability data matrix
        # Filter to submissions with all models
        complete_submissions = [
            key for key, ratings in preds_by_submission.items() if len(ratings) == len(models)
        ]

        if len(complete_submissions) < 10:
            return {
                "test": "Krippendorff's Alpha",
                "strategy": strategy,
                "alpha": None,
                "error": "Insufficient complete samples",
            }

        # Compute alpha using nominal metric (binary data)
        n_units = len(complete_submissions)
        n_raters = len(models)

        # Count coincidences
        o_same = 0  # Observed same
        o_diff = 0  # Observed different

        for key in complete_submissions:
            ratings = [preds_by_submission[key][m] for m in models]
            for i in range(n_raters):
                for j in range(i + 1, n_raters):
                    if ratings[i] == ratings[j]:
                        o_same += 1
                    else:
                        o_diff += 1

        # Expected by chance
        all_ratings = []
        for key in complete_submissions:
            for m in models:
                all_ratings.append(preds_by_submission[key][m])

        p_1 = sum(all_ratings) / len(all_ratings)
        p_0 = 1 - p_1
        e_same = p_0 * p_0 + p_1 * p_1

        total_pairs = o_same + o_diff
        if total_pairs == 0:
            return {
                "test": "Krippendorff's Alpha",
                "strategy": strategy,
                "alpha": 1.0,
                "n_units": n_units,
                "n_raters": n_raters,
            }

        D_o = o_diff / total_pairs  # Observed disagreement
        D_e = 1 - e_same  # Expected disagreement

        if D_e == 0:
            alpha = 1.0
        else:
            alpha = 1 - D_o / D_e

        return {
            "test": "Krippendorff's Alpha",
            "strategy": strategy,
            "alpha": round(alpha, 4),
            "n_units": n_units,
            "n_raters": n_raters,
            "models": models,
            "interpretation": self._interpret_alpha(alpha),
        }

    def _interpret_alpha(self, alpha: float) -> str:
        if alpha < 0.667:
            return "Unacceptable for conclusions"
        elif alpha < 0.8:
            return "Acceptable for tentative conclusions"
        else:
            return "Good reliability"


class ReportGenerator:
    """
    Generates markdown reports from analytics results.
    """

    def __init__(self, engine: AnalyticsEngine, tester: StatisticalTester):
        self.engine = engine
        self.tester = tester
        self.catalog = self._load_catalog()

    def _load_catalog(self) -> dict[str, str]:
        """Load misconception names from catalog."""
        try:
            with open("data/misconception_catalog.json") as f:
                data = json.load(f)
                return {m["id"]: m["name"] for m in data.get("misconceptions", [])}
        except (FileNotFoundError, json.JSONDecodeError):
            return {}

    def generate_full_report(self, include_figures: bool = True) -> str:
        """Generate the complete research evidence report with optional figures."""
        lines = [
            "# Research Evidence Report: LLM Misconception Detection",
            "",
            "Statistical analysis of prompt strategies for automated misconception detection.",
            "",
            "**Models:** GPT-5.1 vs Gemini 2.5 Flash",
            "",
            "---",
            "",
        ]

        # Section 1: Executive Summary
        lines.extend(self._section_executive_summary(include_figures))

        # Section 2: Strategy Comparison
        lines.extend(self._section_strategy_comparison(include_figures))

        # Section 3: Model Comparison (NEW)
        lines.extend(self._section_model_comparison(include_figures))

        # Section 4: Inter-Rater Reliability
        lines.extend(self._section_interrater_reliability(include_figures))

        # Section 5: Misconception Deep Dive
        lines.extend(self._section_misconception_analysis(include_figures))

        # Section 6: Appendix
        lines.extend(self._section_appendix())

        return "\n".join(lines)

    def _section_executive_summary(self, include_figures: bool = True) -> list[str]:
        """Generate executive summary section."""
        lines = [
            "## 1. Executive Statistical Summary",
            "",
            "### Understanding the Metrics",
            "",
            "Before diving into the results, here's what each metric means:",
            "",
            "| Metric | What It Measures | Plain English |",
            "|--------|------------------|---------------|",
            "| **Precision** | TP / (TP + FP) | When the system flags an error, how often is it actually an error? High precision = few false alarms. |",
            "| **Recall** | TP / (TP + FN) | Of all the actual errors, how many did the system catch? High recall = few missed errors. |",
            "| **F1 Score** | Harmonic mean of P & R | A balanced measure combining precision and recall. Best single metric for overall performance. |",
            "| **Accuracy** | (TP + TN) / Total | Overall correctness rate. Can be misleading if classes are imbalanced. |",
            "",
            "**Confusion Matrix Terms:**",
            "- **TP (True Positive):** Correctly identified an error that exists",
            "- **FP (False Positive):** Flagged an error when code was actually correct (false alarm)",
            "- **FN (False Negative):** Missed an actual error (the dangerous one!)",
            "- **TN (True Negative):** Correctly identified correct code as correct",
            "",
            "---",
            "",
        ]

        # Strategy comparison table
        lines.append("### Strategy Performance Overview")
        lines.append("")
        lines.append("| Strategy | Precision | Recall | F1 Score | Accuracy | TP | FP | FN | TN |")
        lines.append("|----------|:---------:|:------:|:--------:|:--------:|:--:|:--:|:--:|:--:|")

        metrics = self.engine.compute_strategy_metrics()
        best_f1 = max(m.f1 for m in metrics.values())

        for strategy in self.engine.STRATEGIES:
            m = metrics[strategy]
            marker = " **" if m.f1 == best_f1 else ""
            lines.append(
                f"| {strategy.capitalize()}{marker} | {m.precision:.1%} | {m.recall:.1%} | "
                f"{m.f1:.1%} | {m.accuracy:.1%} | {m.tp} | {m.fp} | {m.fn} | {m.tn} |"
            )

        lines.append("")
        lines.append("*Strategy with highest F1 is marked with **")
        lines.append("")

        if include_figures:
            lines.append("![Strategy Performance Comparison](figures/strategy_comparison.png)")
            lines.append("")

        # Cochran's Q
        cochran = self.tester.cochrans_q_test()
        lines.append("### Omnibus Test: Cochran's Q")
        lines.append("")
        lines.append("<details>")
        lines.append(
            "<summary><strong>What is Cochran's Q Test and why do we use it?</strong></summary>"
        )
        lines.append("")
        lines.append(
            "Cochran's Q test is a statistical test used when you want to compare **more than two related groups** "
        )
        lines.append(
            "on a binary outcome (success/failure). Think of it as an extension of McNemar's test for multiple groups."
        )
        lines.append("")
        lines.append(
            "**Why we use it here:** We have 4 different prompt strategies, and we want to know if there's ANY "
        )
        lines.append(
            "significant difference between them before doing pairwise comparisons. This prevents us from "
        )
        lines.append(
            "'p-hacking' by running many pairwise tests without first checking if differences exist at all."
        )
        lines.append("")
        lines.append("**How to interpret:**")
        lines.append(
            "- **p < 0.05:** At least one strategy performs significantly differently from the others. We should proceed with pairwise tests."
        )
        lines.append(
            "- **p >= 0.05:** No significant difference between strategies. Any observed differences are likely due to random chance."
        )
        lines.append("")
        lines.append("</details>")
        lines.append("")
        lines.append("**Results:**")
        lines.append("")
        lines.append(f"- **Q Statistic:** {cochran.get('statistic', 'N/A')}")
        lines.append(f"- **p-value:** {cochran.get('p_value', 'N/A')}")
        lines.append(
            f"- **Degrees of Freedom:** {cochran.get('df', 'N/A')} (number of strategies - 1)"
        )
        lines.append(f"- **N samples:** {cochran.get('n_samples', 'N/A')}")
        lines.append("")

        if cochran.get("significant"):
            lines.append(
                "> **Result:** Significant difference found (p < 0.05). The strategies do NOT perform equally. "
            )
            lines.append(
                "> Post-hoc pairwise tests (McNemar's) are warranted to identify which specific strategies differ."
            )
        else:
            lines.append("> **Result:** No significant difference between strategies (p >= 0.05). ")
            lines.append(
                "> The observed performance differences are likely due to random variation, not true differences in strategy effectiveness."
            )

        lines.append("")
        lines.append("---")
        lines.append("")

        return lines

    def _section_strategy_comparison(self, include_figures: bool = True) -> list[str]:
        """Generate strategy comparison section with pairwise tests."""
        lines = [
            "## 2. Strategy Comparison (Pairwise Analysis)",
            "",
            "<details>",
            "<summary><strong>What is McNemar's Test and why do we use it?</strong></summary>",
            "",
            "McNemar's test is used to compare **two related groups** on a binary outcome. It's specifically designed ",
            "for **paired data** where the same subjects are measured under two different conditions.",
            "",
            "**Why we use it here:** Each student submission is evaluated by both Strategy A and Strategy B. ",
            "We want to know if one strategy is significantly better than the other at correctly identifying misconceptions.",
            "",
            "**The key insight:** McNemar's test focuses on the **discordant pairs** - cases where the two strategies disagree:",
            "- **b:** Strategy 1 was correct, Strategy 2 was wrong",
            "- **c:** Strategy 1 was wrong, Strategy 2 was correct",
            "",
            "If b and c are roughly equal, the strategies perform similarly. If one is much larger, that strategy is worse.",
            "",
            "**Bonferroni Correction:** When running multiple comparisons (6 pairs for 4 strategies), we increase the risk ",
            "of false positives. Bonferroni correction divides the significance threshold (0.05) by the number of tests ",
            "to maintain overall reliability. Here: 0.05 / 6 = 0.0083.",
            "",
            "**How to interpret:**",
            "- **p < Bonferroni alpha:** The difference is statistically significant even after correction",
            "- **p >= Bonferroni alpha:** No significant difference; observed variation is likely random",
            "- **Winner:** The strategy that was correct more often when the two disagreed",
            "",
            "</details>",
            "",
        ]

        mcnemar_results = self.tester.mcnemar_all_pairs()

        lines.append(
            "| Comparison | Chi-squared | p-value | Bonferroni alpha | Significant | Winner |"
        )
        lines.append(
            "|------------|:-----------:|:-------:|:----------------:|:-----------:|:------:|"
        )

        for result in mcnemar_results:
            s1, s2 = result["strategies"]
            chi2 = result.get("statistic", "N/A")
            p = result.get("p_value", "N/A")
            b_alpha = result.get("bonferroni_alpha", "N/A")
            sig = "Yes*" if result.get("significant_corrected") else "No"
            winner = result.get("winner", "N/A").capitalize()

            lines.append(
                f"| {s1.capitalize()} vs {s2.capitalize()} | {chi2} | {p} | {b_alpha} | {sig} | {winner} |"
            )

        lines.append("")
        lines.append("*Significant after Bonferroni correction")
        lines.append("")

        # Per-model breakdown
        lines.append("### Per-Model Performance by Strategy")
        lines.append("")

        model_metrics = self.engine.compute_per_model_metrics()

        for model_name in sorted(model_metrics.keys()):
            short_name = model_name.split("/")[-1]
            lines.append(f"#### {short_name}")
            lines.append("")
            lines.append("| Strategy | Precision | Recall | F1 |")
            lines.append("|----------|:---------:|:------:|:--:|")

            for strategy in self.engine.STRATEGIES:
                m = model_metrics[model_name].get(strategy)
                if m:
                    lines.append(
                        f"| {strategy.capitalize()} | {m.precision:.1%} | {m.recall:.1%} | {m.f1:.1%} |"
                    )

            lines.append("")

        if include_figures:
            lines.append("![Confusion Matrices](figures/confusion_matrices.png)")
            lines.append("")

        lines.append("---")
        lines.append("")

        return lines

    def _section_model_comparison(self, include_figures: bool = True) -> list[str]:
        """Generate model comparison section (GPT-5.1 vs Gemini 2.5 Flash)."""
        lines = [
            "## 3. Model Comparison: GPT-5.1 vs Gemini 2.5 Flash",
            "",
            "This section provides a head-to-head comparison of the two LLM models used in this evaluation.",
            "",
        ]

        model_metrics = self.engine.compute_per_model_metrics()

        # Summary table
        lines.append("### Overall Performance Summary")
        lines.append("")
        lines.append("| Model | Avg Precision | Avg Recall | Avg F1 |")
        lines.append("|-------|:-------------:|:----------:|:------:|")

        for model_full, strategies_data in sorted(model_metrics.items()):
            short = MODEL_CONFIG.get(model_full, {}).get("short_name", model_full.split("/")[-1])
            avg_p = np.mean([m.precision for m in strategies_data.values()])
            avg_r = np.mean([m.recall for m in strategies_data.values()])
            avg_f1 = np.mean([m.f1 for m in strategies_data.values()])
            lines.append(f"| {short} | {avg_p:.1%} | {avg_r:.1%} | {avg_f1:.1%} |")

        lines.append("")

        if include_figures:
            lines.append("![Model Comparison](figures/model_comparison.png)")
            lines.append("")

        # McNemar tests between models
        lines.append("### Statistical Comparison (McNemar's Test)")
        lines.append("")
        lines.append(
            "Testing whether the performance difference between models is statistically significant:"
        )
        lines.append("")

        mcnemar_results = self.tester.mcnemar_all_model_pairs()

        for strategy, results in mcnemar_results.items():
            lines.append(f"**{strategy.capitalize()}:**")
            for r in results:
                if "error" in r:
                    lines.append(f"- {r.get('error', 'N/A')}")
                else:
                    names = r.get("model_names", ("Model1", "Model2"))
                    p_val = r.get("p_value", "N/A")
                    sig = "Yes" if r.get("significant") else "No"
                    winner = r.get("winner", "tie")
                    lines.append(
                        f"- {names[0]} vs {names[1]}: p={p_val}, Significant={sig}, Winner={winner}"
                    )
            lines.append("")

        lines.append("---")
        lines.append("")

        return lines

    def _section_interrater_reliability(self, include_figures: bool = True) -> list[str]:
        """Generate inter-rater reliability section."""
        lines = [
            "## 4. Inter-Rater Reliability",
            "",
            "This section answers: **Do the LLM models agree with each other?** If models frequently disagree, ",
            "we can't trust their judgments. High agreement suggests the detection task is well-defined and models are reliable.",
            "",
            "<details>",
            "<summary><strong>What are Kappa and Alpha, and why do they matter?</strong></summary>",
            "",
            "### Cohen's Kappa",
            "Cohen's Kappa measures agreement between **exactly two raters** while accounting for agreement that would ",
            "happen by random chance. A kappa of 0 means agreement is no better than random; 1 means perfect agreement.",
            "",
            "**Interpretation scale:**",
            "| Kappa | Interpretation |",
            "|-------|----------------|",
            "| < 0 | Poor (worse than chance) |",
            "| 0.00 - 0.20 | Slight |",
            "| 0.21 - 0.40 | Fair |",
            "| 0.41 - 0.60 | Moderate |",
            "| 0.61 - 0.80 | Substantial |",
            "| 0.81 - 1.00 | Almost Perfect |",
            "",
            "### Krippendorff's Alpha",
            "Krippendorff's Alpha is more flexible than Kappa - it works with **any number of raters** and handles ",
            "missing data. It's the standard metric for content analysis reliability studies.",
            "",
            "**Interpretation thresholds:**",
            "- **alpha >= 0.80:** Good reliability - conclusions are trustworthy",
            "- **0.667 <= alpha < 0.80:** Acceptable for tentative conclusions",
            "- **alpha < 0.667:** Unacceptable - too much disagreement to draw conclusions",
            "",
            "**Why this matters for the thesis:** If models strongly agree (high Kappa/Alpha), it suggests that ",
            "misconception detection is a well-defined task that LLMs can perform reliably. If they disagree, ",
            "either the task is inherently ambiguous or the models have different 'opinions' about what constitutes an error.",
            "",
            "</details>",
            "",
        ]

        # Get all models
        models = set()
        for strategy in self.engine.STRATEGIES:
            for pred in self.engine.predictions[strategy]:
                models.add(pred.model)

        models = sorted(models)

        # Krippendorff's Alpha per strategy
        lines.append("### Krippendorff's Alpha (Multi-Rater Agreement)")
        lines.append("")
        lines.append("| Strategy | Alpha | Interpretation |")
        lines.append("|----------|:-----:|----------------|")

        for strategy in self.engine.STRATEGIES:
            result = self.tester.krippendorff_alpha(strategy)
            alpha = result.get("alpha", "N/A")
            interp = result.get("interpretation", result.get("error", "N/A"))
            lines.append(f"| {strategy.capitalize()} | {alpha} | {interp} |")

        lines.append("")

        # Cohen's Kappa for each model pair
        if len(models) >= 2:
            lines.append("### Cohen's Kappa (Pairwise Model Agreement)")
            lines.append("")

            for strategy in self.engine.STRATEGIES:
                lines.append(f"#### {strategy.capitalize()}")
                lines.append("")

                for i, m1 in enumerate(models):
                    for m2 in models[i + 1 :]:
                        result = self.tester.cohens_kappa(m1, m2, strategy)
                        if result.get("kappa") is not None:
                            short1 = m1.split("/")[-1]
                            short2 = m2.split("/")[-1]
                            lines.append(
                                f"- **{short1}** vs **{short2}**: kappa={result['kappa']} ({result['interpretation']})"
                            )

                lines.append("")

        if include_figures:
            lines.append("![Model Agreement](figures/model_agreement.png)")
            lines.append("")

        lines.append("---")
        lines.append("")

        return lines

    def _section_misconception_analysis(self, include_figures: bool = True) -> list[str]:
        """Generate per-misconception detection analysis."""
        lines = [
            "## 5. Misconception Detection Analysis",
            "",
            "This section answers: **Which types of errors are easy vs. hard to detect?**",
            "",
            "Understanding this helps identify:",
            "- Which misconceptions need better prompting strategies",
            "- Which error types might require specialized detection approaches",
            "- Patterns in what LLMs miss (e.g., subtle vs. obvious errors)",
            "",
            "**Detection Rate:** The percentage of times a misconception was correctly identified when it was present ",
            "in the ground truth. A rate of 100% means the LLMs caught every instance; 0% means they missed all of them.",
            "",
        ]

        rates = self.engine.get_misconception_detection_rates()

        # Aggregate across strategies
        all_miscs = set()
        for strategy_rates in rates.values():
            all_miscs.update(strategy_rates.keys())

        lines.append("### Detection Rates by Misconception Type")
        lines.append("")

        header = (
            "| ID | Name | "
            + " | ".join(s.capitalize() for s in self.engine.STRATEGIES)
            + " | Avg |"
        )
        lines.append(header)
        lines.append("|" + ":---|" * 2 + ":---:|" * (len(self.engine.STRATEGIES) + 1))

        misc_avg_rates = []
        for misc_id in sorted(all_miscs):
            name = self.catalog.get(misc_id, "Unknown")[:35]
            row_rates = []
            for strategy in self.engine.STRATEGIES:
                r = rates[strategy].get(misc_id, {}).get("rate", 0)
                row_rates.append(r)

            avg_rate = sum(row_rates) / len(row_rates) if row_rates else 0
            misc_avg_rates.append((misc_id, avg_rate))

            rate_strs = [f"{r:.0%}" for r in row_rates]
            lines.append(
                f"| {misc_id} | {name} | " + " | ".join(rate_strs) + f" | {avg_rate:.0%} |"
            )

        lines.append("")

        if include_figures:
            lines.append("![Misconception Detection Heatmap](figures/misconception_heatmap.png)")
            lines.append("")
            lines.append("![Misconception by Model](figures/misconception_by_model.png)")
            lines.append("")

        # Hardest to detect
        lines.append("### Hardest-to-Detect Misconceptions")
        lines.append("")
        misc_avg_rates.sort(key=lambda x: x[1])

        for misc_id, rate in misc_avg_rates[:5]:
            name = self.catalog.get(misc_id, "Unknown")
            lines.append(f"1. **{misc_id}** ({name}): {rate:.0%} average detection rate")

        lines.append("")

        # Easiest to detect
        lines.append("### Easiest-to-Detect Misconceptions")
        lines.append("")

        for misc_id, rate in misc_avg_rates[-5:][::-1]:
            name = self.catalog.get(misc_id, "Unknown")
            lines.append(f"1. **{misc_id}** ({name}): {rate:.0%} average detection rate")

        lines.append("")

        # Per-question breakdown
        if include_figures:
            lines.append("### Per-Question Performance")
            lines.append("")
            lines.append("![Per-Question Breakdown](figures/per_question_breakdown.png)")
            lines.append("")

        # NEW: LLM vs Ground Truth Distribution comparison
        lines.extend(self._subsection_llm_vs_ground_truth())

        lines.append("---")
        lines.append("")

        return lines

    def _subsection_llm_vs_ground_truth(self) -> list[str]:
        """Generate LLM detection vs Ground Truth comparison per model."""
        lines = [
            "### LLM Detection vs Ground Truth: Per-Model Breakdown",
            "",
            "This table breaks down detection performance by individual models across all strategies.",
            "It helps identify if specific models are better at detecting certain misconceptions.",
            "",
        ]

        # Count Ground Truth
        gt_counts: dict[str, int] = defaultdict(int)
        for gt in self.engine.ground_truth.values():
            if gt.misconception_id:
                gt_counts[gt.misconception_id] += 1

        # Count LLM detections per model
        # model_name -> misconception_id -> count
        llm_counts: dict[str, dict[str, int]] = defaultdict(lambda: defaultdict(int))

        # Track unique detections per (student, question, model) to avoid double-counting
        # if a model detects the same misconception multiple times in one submission
        seen_detections: dict[tuple[str, str, str], set[str]] = defaultdict(set)

        for strategy in self.engine.STRATEGIES:
            for pred in self.engine.predictions[strategy]:
                # Key includes model now
                key = (pred.student_id, pred.question_id, pred.model)
                for misc_id in pred.detected_ids:
                    if misc_id not in seen_detections[key]:
                        seen_detections[key].add(misc_id)
                        llm_counts[pred.model][misc_id] += 1

        all_ids = sorted(list(gt_counts.keys()))
        model_names = sorted(list(llm_counts.keys()))

        for model_name in model_names:
            short_name = model_name.split("/")[-1]
            lines.append(f"#### Model: {short_name}")
            lines.append("")
            lines.append("| ID | Misconception | Ground Truth | Detected | Delta | Assessment |")
            lines.append("|:---|:--------------|:------------:|:--------:|:-----:|:-----------|")

            # Sort by GT count
            sorted_ids = sorted(all_ids, key=lambda x: gt_counts[x], reverse=True)

            for misc_id in sorted_ids:
                name = self.catalog.get(misc_id, "Unknown")[:30]
                gt = gt_counts[misc_id]

                # Detection count for THIS model across ALL strategies
                # Note: This sums up detections from 4 strategies, so max possible is 4 * GT
                # To make it comparable, we should average it or just show raw?
                # Showing raw sums across 4 strategies is confusing because GT is fixed.
                # Let's show the count from the BEST performing strategy for this model/misconception?
                # Or just sum them and note it?
                # Actually, standard practice is to evaluate the 'system'.
                # If we have 4 strategies, we have 4 chances to detect.
                # Let's calculate the 'Average Detection Rate' across the 4 strategies for this model.

                detections_across_strategies = llm_counts[model_name].get(misc_id, 0)
                # Divide by 4 to get average detections per single run
                avg_detected = detections_across_strategies / 4.0

                delta = avg_detected - gt

                # Assessment based on average
                if gt == 0 and avg_detected > 0:
                    assessment = "Hallucinated"
                elif gt > 0 and avg_detected == 0:
                    assessment = "Missed"
                elif delta > 0:
                    pct = (delta / gt) * 100
                    assessment = f"Over (+{pct:.0f}%)"
                else:
                    pct = (abs(delta) / gt) * 100
                    assessment = f"Under (-{pct:.0f}%)"

                delta_str = f"{delta:+.1f}"

                lines.append(
                    f"| {misc_id} | {name} | {gt} | {avg_detected:.1f} | {delta_str} | {assessment} |"
                )

            lines.append("")

            # Add model summary
            total_detected = sum(llm_counts[model_name].values()) / 4.0
            total_gt = sum(gt_counts.values())
            lines.append(f"**Summary for {short_name}:**")
            lines.append(
                f"- Average Total Detections: {total_detected:.1f} (vs {total_gt} real errors)"
            )
            lines.append("")

        return lines

    def _section_appendix(self) -> list[str]:
        """Generate appendix with raw data."""
        lines = [
            "## 6. Appendix: Raw Data",
            "",
            "### Ground Truth Summary",
            "",
        ]

        # Count by question
        by_question: dict[str, dict[str, int]] = defaultdict(lambda: {"correct": 0, "errors": 0})
        for (_, qid), gt in self.engine.ground_truth.items():
            if gt.is_correct:
                by_question[qid]["correct"] += 1
            else:
                by_question[qid]["errors"] += 1

        lines.append("| Question | Correct | With Errors | Error Rate |")
        lines.append("|----------|:-------:|:-----------:|:----------:|")

        for qid in sorted(by_question.keys()):
            c = by_question[qid]["correct"]
            e = by_question[qid]["errors"]
            rate = e / (c + e) if (c + e) > 0 else 0
            lines.append(f"| {qid} | {c} | {e} | {rate:.0%} |")

        lines.append("")

        # Misconception distribution
        lines.append("### Misconception Distribution in Ground Truth")
        lines.append("")

        misc_counts: dict[str, int] = defaultdict(int)
        for gt in self.engine.ground_truth.values():
            if gt.misconception_id:
                misc_counts[gt.misconception_id] += 1

        lines.append("| ID | Name | Count |")
        lines.append("|:---|:-----|:-----:|")

        for misc_id, count in sorted(misc_counts.items(), key=lambda x: -x[1]):
            name = self.catalog.get(misc_id, "Unknown")[:40]
            lines.append(f"| {misc_id} | {name} | {count} |")

        lines.append("")
        lines.append("---")
        lines.append("")
        lines.append("*Report generated by utils/analytics.py*")

        return lines


class VisualizationGenerator:
    """
    Generates publication-quality visualizations from analytics data.
    Integrates with AnalyticsEngine for dynamic, up-to-date figures.
    """

    OUTPUT_DIR = Path("reports/figures")

    def __init__(self, engine: AnalyticsEngine, tester: StatisticalTester):
        self.engine = engine
        self.tester = tester
        self.OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

        if HAS_PLOTTING:
            plt.style.use("seaborn-v0_8-whitegrid")
            sns.set_palette("viridis")

        self.catalog = self._load_catalog()

    def _load_catalog(self) -> dict[str, str]:
        """Load misconception names from catalog."""
        try:
            with open("data/misconception_catalog.json") as f:
                data = json.load(f)
                return {m["id"]: m["name"] for m in data.get("misconceptions", [])}
        except (FileNotFoundError, json.JSONDecodeError):
            return {}

    def generate_all(self) -> list[str]:
        """Generate all visualizations and return list of generated file paths."""
        if not HAS_PLOTTING:
            print("Warning: matplotlib/seaborn not available. Skipping visualizations.")
            return []

        generated = []
        generated.append(self.generate_strategy_comparison())
        generated.append(self.generate_misconception_heatmap())
        generated.append(self.generate_model_agreement())
        generated.append(self.generate_model_comparison())
        generated.append(self.generate_confusion_matrices())
        generated.append(self.generate_per_question_breakdown())
        generated.append(self.generate_misconception_by_model())
        return [g for g in generated if g]

    def generate_strategy_comparison(self) -> str | None:
        """Generate strategy performance comparison bar chart."""
        if not HAS_PLOTTING:
            return None

        metrics = self.engine.compute_strategy_metrics()
        strategies = [s.capitalize() for s in self.engine.STRATEGIES]

        data = {
            "Precision": [metrics[s].precision * 100 for s in self.engine.STRATEGIES],
            "Recall": [metrics[s].recall * 100 for s in self.engine.STRATEGIES],
            "F1 Score": [metrics[s].f1 * 100 for s in self.engine.STRATEGIES],
        }

        df = pd.DataFrame(data, index=strategies)
        df = df.reset_index().melt(id_vars="index", var_name="Metric", value_name="Score")
        df.rename(columns={"index": "Strategy"}, inplace=True)

        plt.figure(figsize=(10, 6))
        ax = sns.barplot(data=df, x="Strategy", y="Score", hue="Metric")

        plt.title("Performance Comparison by Prompt Strategy", fontsize=14, pad=20)
        plt.ylim(0, 100)
        plt.ylabel("Score (%)")
        plt.xlabel("")
        plt.legend(bbox_to_anchor=(1.05, 1), loc="upper left")

        for container in ax.containers:
            ax.bar_label(container, fmt="%.1f")

        plt.tight_layout()
        path = self.OUTPUT_DIR / "strategy_comparison.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_misconception_heatmap(self) -> str | None:
        """Generate misconception detection rate heatmap."""
        if not HAS_PLOTTING:
            return None

        rates = self.engine.get_misconception_detection_rates()
        all_miscs = set()
        for strategy_rates in rates.values():
            all_miscs.update(strategy_rates.keys())

        # Build data matrix
        data = {}
        for misc_id in sorted(all_miscs):
            short_name = self.catalog.get(misc_id, misc_id)[:20]
            label = f"{misc_id} ({short_name})"
            data[label] = [
                rates[s].get(misc_id, {}).get("rate", 0) * 100 for s in self.engine.STRATEGIES
            ]

        strategies = [s.capitalize() for s in self.engine.STRATEGIES]
        heatmap_data = pd.DataFrame(data).T
        heatmap_data.columns = strategies

        # Sort by average detection rate
        heatmap_data["avg"] = heatmap_data.mean(axis=1)
        heatmap_data = heatmap_data.sort_values("avg", ascending=False).drop("avg", axis=1)

        plt.figure(figsize=(8, max(6, len(heatmap_data) * 0.5)))
        sns.heatmap(
            heatmap_data,
            annot=True,
            fmt=".0f",
            cmap="RdYlGn",
            vmin=0,
            vmax=100,
            cbar_kws={"label": "Detection Rate (%)"},
        )

        plt.title("Misconception Detection Rate by Strategy", fontsize=14, pad=20)
        plt.tight_layout()
        path = self.OUTPUT_DIR / "misconception_heatmap.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_model_agreement(self) -> str | None:
        """Generate inter-model agreement bar chart (Kappa per strategy)."""
        if not HAS_PLOTTING:
            return None

        strategies = [s.capitalize() for s in self.engine.STRATEGIES]
        kappas = []

        for strategy in self.engine.STRATEGIES:
            result = self.tester.krippendorff_alpha(strategy)
            kappas.append(result.get("alpha", 0) or 0)

        # Color bars based on kappa value
        colors = []
        for k in kappas:
            if k >= 0.8:
                colors.append("#2ecc71")  # Green
            elif k >= 0.6:
                colors.append("#f1c40f")  # Yellow
            else:
                colors.append("#e74c3c")  # Red

        plt.figure(figsize=(8, 5))
        bars = plt.bar(strategies, kappas, color=colors)

        plt.axhline(y=0.6, color="gray", linestyle="--", alpha=0.5, label="Substantial (0.6)")
        plt.axhline(y=0.8, color="gray", linestyle=":", alpha=0.5, label="Perfect (0.8)")

        plt.title("Inter-Model Agreement (Krippendorff's Alpha)", fontsize=14, pad=20)
        plt.ylabel("Alpha Score")
        plt.ylim(0, 1.0)

        for bar in bars:
            height = bar.get_height()
            plt.text(
                bar.get_x() + bar.get_width() / 2.0,
                height,
                f"{height:.2f}",
                ha="center",
                va="bottom",
            )

        plt.legend()
        plt.tight_layout()
        path = self.OUTPUT_DIR / "model_agreement.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_model_comparison(self) -> str | None:
        """Generate GPT-5.1 vs Gemini 2.5 Flash comparison chart."""
        if not HAS_PLOTTING:
            return None

        model_metrics = self.engine.compute_per_model_metrics()
        if len(model_metrics) < 2:
            return None

        # Get model data
        model_data = {}
        for model_full, strategies_data in model_metrics.items():
            short = MODEL_CONFIG.get(model_full, {}).get("short_name", model_full.split("/")[-1])
            color = MODEL_CONFIG.get(model_full, {}).get("color", "#333333")

            # Handle missing strategies gracefully
            available_metrics = [m for m in strategies_data.values()]
            if not available_metrics:
                continue

            avg_f1 = np.mean([m.f1 for m in available_metrics])
            avg_precision = np.mean([m.precision for m in available_metrics])
            avg_recall = np.mean([m.recall for m in available_metrics])

            # Get F1 for each strategy (0 if missing)
            f1_list = [
                strategies_data.get(s, StrategyMetrics(strategy=s)).f1 * 100
                for s in self.engine.STRATEGIES
            ]
            precision_list = [
                strategies_data.get(s, StrategyMetrics(strategy=s)).precision * 100
                for s in self.engine.STRATEGIES
            ]
            recall_list = [
                strategies_data.get(s, StrategyMetrics(strategy=s)).recall * 100
                for s in self.engine.STRATEGIES
            ]

            model_data[short] = {
                "color": color,
                "f1": f1_list,
                "precision": precision_list,
                "recall": recall_list,
                "avg_f1": avg_f1 * 100,
                "avg_precision": avg_precision * 100,
                "avg_recall": avg_recall * 100,
            }

        # Create grouped bar chart
        fig, axes = plt.subplots(1, 2, figsize=(14, 5))

        # Left: F1 by strategy
        x = np.arange(len(self.engine.STRATEGIES))
        width = 0.35
        strategies = [s.capitalize() for s in self.engine.STRATEGIES]

        models = list(model_data.keys())
        for i, model in enumerate(models):
            offset = (i - 0.5) * width
            bars = axes[0].bar(
                x + offset,
                model_data[model]["f1"],
                width,
                label=model,
                color=model_data[model]["color"],
            )
            axes[0].bar_label(bars, fmt="%.1f", fontsize=8)

        axes[0].set_ylabel("F1 Score (%)")
        axes[0].set_title("F1 Score by Strategy", fontsize=12)
        axes[0].set_xticks(x)
        axes[0].set_xticklabels(strategies)
        axes[0].legend()
        axes[0].set_ylim(0, 100)

        # Right: Overall metrics comparison
        metrics = ["Precision", "Recall", "F1"]
        x2 = np.arange(len(metrics))

        for i, model in enumerate(models):
            offset = (i - 0.5) * width
            values = [
                model_data[model]["avg_precision"],
                model_data[model]["avg_recall"],
                model_data[model]["avg_f1"],
            ]
            bars = axes[1].bar(
                x2 + offset, values, width, label=model, color=model_data[model]["color"]
            )
            axes[1].bar_label(bars, fmt="%.1f", fontsize=8)

        axes[1].set_ylabel("Score (%)")
        axes[1].set_title("Overall Model Comparison", fontsize=12)
        axes[1].set_xticks(x2)
        axes[1].set_xticklabels(metrics)
        axes[1].legend()
        axes[1].set_ylim(0, 100)

        plt.suptitle("GPT-5.1 vs Gemini 2.5 Flash Performance", fontsize=14)
        plt.tight_layout()
        path = self.OUTPUT_DIR / "model_comparison.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_confusion_matrices(self) -> str | None:
        """Generate confusion matrix visualizations for each strategy."""
        if not HAS_PLOTTING:
            return None

        metrics = self.engine.compute_strategy_metrics()
        n_strategies = len(self.engine.STRATEGIES)

        fig, axes = plt.subplots(1, n_strategies, figsize=(4 * n_strategies, 4))
        if n_strategies == 1:
            axes = [axes]

        for idx, strategy in enumerate(self.engine.STRATEGIES):
            m = metrics[strategy]
            cm = np.array([[m.tn, m.fp], [m.fn, m.tp]])

            sns.heatmap(
                cm,
                annot=True,
                fmt="d",
                cmap="Blues",
                xticklabels=["Pred: No Error", "Pred: Error"],
                yticklabels=["True: No Error", "True: Error"],
                ax=axes[idx],
            )
            axes[idx].set_title(f"{strategy.capitalize()}\nF1={m.f1:.1%}")

        plt.suptitle("Confusion Matrices by Strategy", fontsize=14)
        plt.tight_layout()
        path = self.OUTPUT_DIR / "confusion_matrices.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_per_question_breakdown(self) -> str | None:
        """Generate per-question F1 score breakdown."""
        if not HAS_PLOTTING:
            return None

        question_metrics = self.engine.get_per_question_metrics()
        if not question_metrics:
            return None

        questions = sorted(question_metrics.keys())
        strategies = self.engine.STRATEGIES

        fig, ax = plt.subplots(figsize=(10, 6))

        x = np.arange(len(questions))
        width = 0.2

        for i, strategy in enumerate(strategies):
            offset = (i - len(strategies) / 2 + 0.5) * width
            f1_values = [
                question_metrics[q].get(strategy, {}).get("f1", 0) * 100 for q in questions
            ]
            ax.bar(x + offset, f1_values, width, label=strategy.capitalize())

        ax.set_ylabel("F1 Score (%)")
        ax.set_title("F1 Score by Question and Strategy", fontsize=14)
        ax.set_xticks(x)
        ax.set_xticklabels([q.upper() for q in questions])
        ax.legend()
        ax.set_ylim(0, 100)

        plt.tight_layout()
        path = self.OUTPUT_DIR / "per_question_breakdown.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)

    def generate_misconception_by_model(self) -> str | None:
        """Generate misconception detection rates by model."""
        if not HAS_PLOTTING:
            return None

        model_rates = self.engine.get_per_model_misconception_rates()
        if not model_rates:
            return None

        # Gather all misconceptions
        all_miscs = set()
        for rates in model_rates.values():
            all_miscs.update(rates.keys())

        if not all_miscs:
            return None

        # Build data for heatmap
        models = sorted(model_rates.keys())
        short_names = [MODEL_CONFIG.get(m, {}).get("short_name", m.split("/")[-1]) for m in models]

        sorted_miscs = sorted(all_miscs)
        misc_labels = [f"{m} ({self.catalog.get(m, 'Unknown')[:15]})" for m in sorted_miscs]

        data = []
        for misc_id in sorted_miscs:
            row = []
            for model in models:
                rate = model_rates[model].get(misc_id, {}).get("rate", 0) * 100
                row.append(rate)
            data.append(row)

        df = pd.DataFrame(data, index=misc_labels, columns=short_names)

        plt.figure(figsize=(8, max(6, len(df) * 0.4)))
        sns.heatmap(
            df,
            annot=True,
            fmt=".0f",
            cmap="RdYlGn",
            vmin=0,
            vmax=100,
            cbar_kws={"label": "Detection Rate (%)"},
        )

        plt.title("Misconception Detection Rate by Model", fontsize=14, pad=20)
        plt.xlabel("Model")
        plt.tight_layout()
        path = self.OUTPUT_DIR / "misconception_by_model.png"
        plt.savefig(path, dpi=300, bbox_inches="tight")
        plt.close()
        print(f"  Generated: {path}")
        return str(path)


def run_analysis(
    output_path: str = "reports/research_evidence_report.md", generate_figures: bool = True
) -> str:
    """
    Run the full analysis pipeline and generate report with figures.

    Args:
        output_path: Path for the markdown report
        generate_figures: Whether to generate visualization figures

    Returns the path to the generated report.
    """
    print("Initializing analytics engine...")
    engine = AnalyticsEngine()

    print("Loading ground truth...")
    gt_count = engine.load_ground_truth()
    print(f"  Loaded {gt_count} ground truth entries")

    print("Loading evaluations...")
    counts = engine.load_all_strategies()
    for strategy, count in counts.items():
        print(f"  {strategy}: {count} evaluations")

    print("\nComputing metrics...")
    engine.compute_strategy_metrics()
    engine.compute_per_model_metrics()

    print("Running statistical tests...")
    tester = StatisticalTester(engine)

    # Generate visualizations
    figures = []
    if generate_figures:
        print("\nGenerating visualizations...")
        viz = VisualizationGenerator(engine, tester)
        figures = viz.generate_all()

    print("\nGenerating report...")
    generator = ReportGenerator(engine, tester)
    report = generator.generate_full_report(include_figures=generate_figures)

    # Ensure output directory exists
    output_file = Path(output_path)
    output_file.parent.mkdir(parents=True, exist_ok=True)

    with open(output_file, "w") as f:
        f.write(report)

    print(f"\nReport saved to: {output_path}")
    if figures:
        print("Figures saved to: reports/figures/")

    # Print summary
    metrics = engine.strategy_metrics
    print("\n" + "=" * 60)
    print("STRATEGY COMPARISON SUMMARY")
    print("=" * 60)

    for strategy in engine.STRATEGIES:
        m = metrics[strategy]
        print(f"{strategy.upper():12} | P={m.precision:.1%} R={m.recall:.1%} F1={m.f1:.1%}")

    # Print model comparison
    model_metrics = engine.model_metrics
    if model_metrics:
        print("\n" + "=" * 60)
        print("MODEL COMPARISON SUMMARY (Average across strategies)")
        print("=" * 60)
        for model, strategies in model_metrics.items():
            short = MODEL_CONFIG.get(model, {}).get("short_name", model.split("/")[-1])
            avg_f1 = np.mean([m.f1 for m in strategies.values()])
            avg_p = np.mean([m.precision for m in strategies.values()])
            avg_r = np.mean([m.recall for m in strategies.values()])
            print(f"{short:20} | P={avg_p:.1%} R={avg_r:.1%} F1={avg_f1:.1%}")

    return output_path


if __name__ == "__main__":
    run_analysis()
