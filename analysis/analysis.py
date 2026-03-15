import requests
import pandas as pd
import numpy as np

# Backend API
API_URL = "http://localhost:8081/api/wind-data?horizon=4"

# Fetch data
response = requests.get(API_URL)
data = response.json()

# Convert to DataFrame
df = pd.DataFrame(data)

# Convert time column
df["time"] = pd.to_datetime(df["time"])

# Calculate errors
df["error"] = df["actual"] - df["forecast"]
df["abs_error"] = np.abs(df["error"])
df["squared_error"] = df["error"] ** 2

# Metrics
MAE = df["abs_error"].mean()
RMSE = np.sqrt(df["squared_error"].mean())

print("Wind Forecast Accuracy Metrics")
print("--------------------------------")
print(f"MAE  (Mean Absolute Error): {MAE:.2f} MW")
print(f"RMSE (Root Mean Squared Error): {RMSE:.2f} MW")

# Show sample rows
print("\nSample Data:")
print(df.head())