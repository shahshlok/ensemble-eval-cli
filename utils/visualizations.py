import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
from pathlib import Path
import json
from collections import defaultdict

# Set style
plt.style.use('seaborn-v0_8-whitegrid')
sns.set_palette("viridis")

OUTPUT_DIR = Path("reports/figures")
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

def load_data():
    # Load analytics data (simulated loading for now based on report data structure)
    # Ideally this should import from utils.analytics but we want standalone generation
    
    strategies = ["Minimal", "Baseline", "Socratic", "Rubric_Only"]
    metrics = {
        "Precision": [54.5, 55.6, 55.0, 55.6],
        "Recall": [92.3, 76.9, 84.6, 76.9],
        "F1 Score": [68.6, 64.5, 66.7, 64.5]
    }
    return strategies, metrics

def generate_strategy_comparison_chart():
    strategies, metrics = load_data()
    
    df = pd.DataFrame(metrics, index=strategies)
    df = df.reset_index().melt(id_vars="index", var_name="Metric", value_name="Score")
    df.rename(columns={"index": "Strategy"}, inplace=True)
    
    plt.figure(figsize=(10, 6))
    ax = sns.barplot(data=df, x="Strategy", y="Score", hue="Metric")
    
    plt.title("Performance Comparison by Prompt Strategy", fontsize=14, pad=20)
    plt.ylim(0, 100)
    plt.ylabel("Score (%)")
    plt.xlabel("")
    plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
    
    # Add values on bars
    for container in ax.containers:
        ax.bar_label(container, fmt='%.1f')
        
    plt.tight_layout()
    plt.savefig(OUTPUT_DIR / "strategy_comparison.png", dpi=300)
    print(f"Saved {OUTPUT_DIR}/strategy_comparison.png")

def generate_misconception_heatmap():
    # Data from the report (Detection Rates)
    data = {
        "VAR001 (Operator Prec.)": [100, 100, 100, 100],
        "OTHER002 (Hardcoded)": [100, 100, 100, 100],
        "DT002 (Int Division)": [100, 50, 100, 100],
        "INPUT002 (Scanner Read)": [100, 50, 50, 50],
        "CONST002 (Missing Sqrt)": [50, 100, 50, 50],
        "CONST001 (Caret Power)": [50, 50, 50, 50],
        "OTHER001 (Wrong Qty)": [50, 50, 50, 50],
        "INPUT003 (Scanner Close)": [50, 50, 50, 50],
        "DT003 (Type Mismatch)": [25, 25, 50, 25],
        "INPUT001 (Missing Import)": [50, 0, 0, 0]
    }
    
    strategies = ["Minimal", "Baseline", "Socratic", "Rubric_Only"]
    
    # Transpose for Heatmap (Rows=Misconceptions, Cols=Strategies)
    heatmap_data = pd.DataFrame(data).T
    heatmap_data.columns = strategies
    
    plt.figure(figsize=(8, 10))
    sns.heatmap(heatmap_data, annot=True, fmt="d", cmap="RdYlGn", vmin=0, vmax=100, 
                cbar_kws={'label': 'Detection Rate (%)'})
    
    plt.title("Misconception Detection Rate by Strategy", fontsize=14, pad=20)
    plt.tight_layout()
    plt.savefig(OUTPUT_DIR / "misconception_heatmap.png", dpi=300)
    print(f"Saved {OUTPUT_DIR}/misconception_heatmap.png")

def generate_model_agreement_heatmap():
    # Cohen's Kappa data
    data = [
        [1.0, 0.75, 0.63, 0.62, 0.12], # Dummy diagonal + values
        [0.75, 1.0, 0.0, 0.0, 0.0],
    ]
    # This is harder to visualize without full matrix. 
    # Let's do a simple bar chart for Agreement (Kappa) per Strategy instead.
    
    strategies = ["Minimal", "Baseline", "Socratic", "Rubric_Only"]
    kappas = [0.7512, 0.6277, 0.6173, 0.1209]
    
    plt.figure(figsize=(8, 5))
    bars = plt.bar(strategies, kappas, color=['#2ecc71', '#f1c40f', '#f1c40f', '#e74c3c'])
    
    plt.axhline(y=0.6, color='gray', linestyle='--', alpha=0.5, label='Substantial Agreement (0.6)')
    plt.axhline(y=0.8, color='gray', linestyle=':', alpha=0.5, label='Perfect Agreement (0.8)')
    
    plt.title("Inter-Model Agreement (Cohen's Kappa)", fontsize=14, pad=20)
    plt.ylabel("Kappa Score")
    plt.ylim(0, 1.0)
    
    # Add value labels
    for bar in bars:
        height = bar.get_height()
        plt.text(bar.get_x() + bar.get_width()/2., height,
                 f'{height:.2f}',
                 ha='center', va='bottom')

    plt.legend()
    plt.tight_layout()
    plt.savefig(OUTPUT_DIR / "model_agreement.png", dpi=300)
    print(f"Saved {OUTPUT_DIR}/model_agreement.png")

if __name__ == "__main__":
    print("Generating visualizations...")
    generate_strategy_comparison_chart()
    generate_misconception_heatmap()
    generate_model_agreement_heatmap()
    print("Done.")
