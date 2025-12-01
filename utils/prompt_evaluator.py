import json
import os
import glob
from collections import defaultdict
from typing import List, Dict, Any, Set, Tuple

class PromptEvaluator:
    def __init__(self, base_dir: str = "."):
        self.base_dir = base_dir
        self.manifest_path = os.path.join(base_dir, "authentic_seeded", "manifest.json")
        self.catalog_path = os.path.join(base_dir, "data", "misconception_catalog.json")
        self.evals_dir = os.path.join(base_dir, "student_evals")
        
        self.manifest = self._load_json(self.manifest_path)
        self.catalog = self._load_json(self.catalog_path)
        self.misconception_map = self._build_misconception_map()
        
        # Build a quick lookup for ground truth: (student_id_base, question_id) -> misconception_id
        self.gt_lookup = {}
        for entry in self.manifest:
            # entry: {student_id, question_id, misconception_id, is_correct}
            key = (entry["student_id"], entry["question_id"])
            self.gt_lookup[key] = entry["misconception_id"]

    def _load_json(self, path: str) -> Any:
        try:
            with open(path, "r") as f:
                return json.load(f)
        except Exception as e:
            print(f"Error loading {path}: {e}")
            return []

    def _build_misconception_map(self) -> Dict[str, str]:
        """
        Builds a mapping from keywords/names to misconception IDs.
        """
        mapping = {}
        if "misconceptions" not in self.catalog:
            return mapping
            
        for m in self.catalog["misconceptions"]:
            m_id = m["id"]
            # Map the exact name
            mapping[m["name"].lower()] = m_id
            # Map keywords
            for kw in m.get("detection_keywords", []):
                mapping[kw.lower()] = m_id
        return mapping

    def _map_prediction_to_id(self, pred_name: str, pred_desc: str) -> str:
        """
        Tries to map a predicted misconception name/description to a canonical ID.
        Returns None if no match found.
        """
        text = (str(pred_name) + " " + str(pred_desc)).lower()
        
        # 1. Check for exact name match (highest priority)
        if str(pred_name).lower() in self.misconception_map:
             return self.misconception_map[str(pred_name).lower()]

        # 2. Check for keyword matches
        best_match_id = None
        max_matches = 0
        
        if "misconceptions" in self.catalog:
            for m in self.catalog["misconceptions"]:
                m_id = m["id"]
                keywords = m.get("detection_keywords", [])
                # Count how many keywords appear in the text
                matches = sum(1 for kw in keywords if kw.lower() in text)
                
                if matches > max_matches:
                    max_matches = matches
                    best_match_id = m_id
        
        return best_match_id

    def evaluate_strategy(self, strategy: str) -> Dict[str, Dict[str, int]]:
        strategy_dir = os.path.join(self.evals_dir, strategy)
        if not os.path.exists(strategy_dir):
            print(f"Strategy directory not found: {strategy_dir}")
            return {}

        eval_files = glob.glob(os.path.join(strategy_dir, "*.json"))
        
        # Per-model results
        model_results = defaultdict(lambda: {"tp": 0, "fp": 0, "fn": 0, "tn": 0})

        for eval_file in eval_files:
            data = self._load_json(eval_file)
            if not data: continue
            
            # Extract student info
            if "submission" not in data or "context" not in data:
                continue
                
            full_student_id = data["submission"].get("student_id", "")
            question_id = data["context"].get("question_id", "")
            
            # Try to find GT
            gt_id = self.gt_lookup.get((full_student_id, question_id))
            
            if gt_id is None:
                # Try stripping the last part (misconception suffix)
                # E.g. Anderson_Noah_200113_DT003 -> Anderson_Noah_200113
                parts = full_student_id.split("_")
                # Heuristic: The base ID usually has 3 parts: Name_Name_ID
                # But sometimes the misconception ID is appended.
                # Let's try removing the last part if it looks like a misconception ID (uppercase + numbers)
                if len(parts) > 1:
                    base_id = "_".join(parts[:-1])
                    gt_id = self.gt_lookup.get((base_id, question_id))
            
            # If still None, it might be that the student is not in the manifest or I'm parsing wrong.
            # However, if the student IS in the manifest but has NO misconception (is_correct=True),
            # gt_id will be None (from the manifest).
            # We need to distinguish "Not in manifest" vs "Correct in manifest".
            
            # Let's check if the key exists in lookup
            is_in_manifest = (full_student_id, question_id) in self.gt_lookup
            if not is_in_manifest:
                 parts = full_student_id.split("_")
                 if len(parts) > 1:
                    base_id = "_".join(parts[:-1])
                    if (base_id, question_id) in self.gt_lookup:
                        is_in_manifest = True
                    else:
                        # Skip if not in manifest at all
                        # print(f"Skipping {full_student_id} - not in manifest")
                        continue
            
            # If we are here, we have a valid student. gt_id is either a string or None.

            # For each model in the evaluation
            if "models" not in data:
                continue
                
            for model_name, model_data in data["models"].items():
                preds = model_data.get("misconceptions", [])
                
                # Map predictions to IDs
                pred_ids = set()
                for p in preds:
                    mapped_id = self._map_prediction_to_id(p.get("name", ""), p.get("description", ""))
                    if mapped_id:
                        pred_ids.add(mapped_id)
                
                # Compare
                if gt_id is None:
                    # Ground Truth: Student is Correct
                    if not pred_ids:
                        model_results[model_name]["tn"] += 1
                    else:
                        model_results[model_name]["fp"] += 1 
                else:
                    # Ground Truth: Student has Misconception gt_id
                    if gt_id in pred_ids:
                        model_results[model_name]["tp"] += 1
                        # If they detected OTHER things too, strictly that's FP, but usually we care about recall first.
                        # For F1, we should penalize extra detections.
                        if len(pred_ids) > 1:
                             model_results[model_name]["fp"] += (len(pred_ids) - 1)
                    else:
                        model_results[model_name]["fn"] += 1
                        # If they detected something else instead
                        if pred_ids:
                            model_results[model_name]["fp"] += len(pred_ids)
        
        return model_results

    def run_all(self):
        strategies = ["baseline", "minimal", "socratic", "rubric_only"]
        
        # Header
        print(f"{'Strategy':<15} | {'Model':<30} | {'Prec':<8} | {'Rec':<8} | {'F1':<8} | {'Acc':<8} | {'N':<4}")
        print("-" * 100)
        
        for strat in strategies:
            res = self.evaluate_strategy(strat)
            if not res: continue
            
            for model, metrics in res.items():
                tp, fp, fn, tn = metrics["tp"], metrics["fp"], metrics["fn"], metrics["tn"]
                total = tp + fp + fn + tn
                
                precision = tp / (tp + fp) if (tp + fp) > 0 else 0.0
                recall = tp / (tp + fn) if (tp + fn) > 0 else 0.0
                f1 = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0.0
                accuracy = (tp + tn) / total if total > 0 else 0.0
                
                # Clean model name
                model_short = model.split("/")[-1]
                
                print(f"{strat:<15} | {model_short:<30} | {precision:.1%}   | {recall:.1%}   | {f1:.1%}   | {accuracy:.1%}   | {total:<4}")

if __name__ == "__main__":
    evaluator = PromptEvaluator()
    evaluator.run_all()
