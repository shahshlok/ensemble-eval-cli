import os
import sys
from collections import defaultdict

# Add current directory to path
sys.path.append(os.getcwd())

from utils.misconceptions.analyzer import MisconceptionAnalyzer, normalize_topic


def debug_variables_misconceptions():
    analyzer = MisconceptionAnalyzer()
    count = analyzer.load_evaluations()
    print(f"Loaded {count} evaluations")

    analyzer.extract_misconceptions()

    variables_misconceptions = []
    original_topics = []
    for record in analyzer.misconception_records:
        if record.topic == "Variables":
            variables_misconceptions.append(record.name)
            # We need to see what the raw topic was, but record.topic is already normalized.
            # We might need to look at the raw data or infer it if we can't access it easily.
            # Actually, MisconceptionRecord stores the normalized topic.
            # The raw topic is lost during extraction in extract_misconceptions.
            pass

    # To debug the normalization, we need to look at the raw evaluations again.
    print(f"\nTotal 'Variables' misconceptions: {len(variables_misconceptions)}")

    # Let's check the raw topics from the loaded evaluations directly
    print("\n--- Raw Topic Analysis for 'Variables' ---")
    raw_topic_counts = defaultdict(int)
    for eval_doc in analyzer.evaluations:
        for model_name, model_eval in eval_doc.models.items():
            for misconception in model_eval.misconceptions:
                normalized = normalize_topic(misconception.topic)
                if normalized == "Variables":
                    raw_topic_counts[misconception.topic] += 1

    for topic, count in sorted(raw_topic_counts.items(), key=lambda x: -x[1]):
        print(f"{count}: '{topic}' -> Variables")

    # Test clustering
    print("\n--- Testing Clustering ---")
    clusters = analyzer.cluster_misconceptions(threshold=0.8)

    clustered_variables = []
    for name in variables_misconceptions:
        clustered_variables.append(clusters.get(name, name))

    from collections import Counter

    counts = Counter(clustered_variables)

    print("\nCounts by Clustered Misconception Name:")
    for name, count in counts.most_common():
        print(f"{count}: {name}")

    # Show what was merged
    print("\nMerge Details:")
    reverse_clusters = defaultdict(list)
    for original, canonical in clusters.items():
        if original != canonical:
            reverse_clusters[canonical].append(original)

    for canonical, originals in reverse_clusters.items():
        if originals:
            print(f"\n'{canonical}' absorbed:")
            for orig in originals:
                print(f"  - {orig}")


if __name__ == "__main__":
    debug_variables_misconceptions()
