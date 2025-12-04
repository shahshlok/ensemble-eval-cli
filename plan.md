# Plan: Thesis Report Revamp (Data-Rich, Statistical, Novel)

1. **Data assembly layer**
   - Build a tidy dataframe joining manifest, groundtruth, and every detection (model/strategy/question), including classification outcome, match scores, confidence, match method, and evidence snippet metadata.
   - Cache intermediate tables (e.g., parquet) for reuse and faster reruns.

2. **Hybrid matching upgrade**
   - Add a fusion matcher that combines fuzzy (token/sequence), multi-view semantic similarity (name/description/belief/evidence), and priors from question/topic to rerank matches (e.g., reciprocal-rank fusion).
   - Calibrate thresholds per strategy/model using manifest-aware validation; report gains vs. current matcher.

3. **Expanded metrics & statistical analysis**
   - Compute slices per strategy/model/question/topic/misconception and per-student coverage.
   - Add calibration metrics (ECE/Brier), confidence histograms, and agreement scores (Cohen’s κ) between models.
   - Add paired significance tests (McNemar variants, paired bootstrap CIs for precision/recall/F1) with effect sizes.
   - Error taxonomy: FP types (hallucination vs. near-miss) and FN breakdown by topic/misconception difficulty.

4. **Visualization pipeline**
   - Generate seaborn/matplotlib figures: heatmaps (topic × strategy/model), calibration curves, confidence histograms, agreement/confusion matrices, misconception difficulty bars, and hallucination frequency plots.
   - Save assets under `docs/report_assets/` (or configured path) and reference them from the markdown.

5. **Report generator rewrite**
   - Replace string-append writer with a templated generator that pulls from the dataframe and metrics, embeds figure links, and emits both `thesis_report.md` and `thesis_report.json`.
   - Include method section (dataset, matching, thresholds), key findings with CIs/p-values, limitations, and recommendations.

6. **CLI integration & configurability**
   - Expose flags for: asset output path, enabling hybrid matcher, bootstrap iterations, caching/quick mode, and section toggles.
   - Wire into `analyze`/`pipeline` commands and add smoke tests for analytics components.
