# Open Questions for the Revamped Thesis Report

1. **Report artifacts**: Should the new report embed charts as static PNG/SVG under `docs/report_assets/` and link them from `thesis_report.md`, or remain table-only? Any color/branding or dark-mode preference?

Yes they should be embedded and you can create the report assets in the `docs/report_assets/` directory. No branding or dark mode preferences. Just make it red green colorblind friendly.

2. **Statistical rigor**: Are paired bootstrap CIs (precision/recall/F1), McNemar variants, and permutation tests acceptable defaults (α = 0.05)? Do you want effect sizes reported (e.g., Cohen’s g) alongside p-values?
Everything goes. More data is always better. More rigor is always better. More detail is always better.


3. **External models/embeddings**: May we call OpenAI embeddings or other APIs during analysis to power semantic matching and clustering, or should the pipeline stay offline/cached only?
Yes you can definitely use OpenAI embeddings or other APIs during analysis to power semantic matching and clustering. What other APIs do you want to use?

4. **Anonymity**: Should student identifiers remain visible in the report, or should we pseudonymize for publishable artifacts?
The student identifiers are anonymized. The authentic_seeded dataset is synthetic and does not have any real student data. 

5. **Scalability**: Should the report auto-scale beyond the current 60×4 submissions (e.g., more strategies/models) without manual changes?
That would be great but not a priority.


6. **Extra exports**: Besides `thesis_report.md`, do you want HTML/PDF and a machine-readable dump (`thesis_report.json`/`parquet`) for downstream analysis?
Yes, we can export the report in HTML and PDF format. 

1. **Runtime budget**: Are heavier computations (bootstraps, clustering, recalculating embeddings) acceptable on each run, or should we add caching and a “quick mode” switch?
Heavier computations are acceptable on each run but we can add caching and a “quick mode” switch just in case.