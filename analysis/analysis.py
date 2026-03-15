import requests
import pandas as pd
import numpy as np

API_URL = "http://localhost:8081/api/wind-error?horizon=4"


def fetch_data():
    print("Fetching wind error data from API...")

    response = requests.get(API_URL)

    if response.status_code != 200:
        raise Exception("Failed to fetch API data")

    data = response.json()

    df = pd.DataFrame(data)

    return df


def calculate_metrics(df):

    actual = df["actual"]
    forecast = df["forecast"]

    error = actual - forecast

    mae = np.mean(np.abs(error))

    rmse = np.sqrt(np.mean(error**2))

    mape = np.mean(np.abs(error / actual)) * 100

    return mae, rmse, mape


def main():

    df = fetch_data()

    mae, rmse, mape = calculate_metrics(df)

    print("\nForecast Accuracy Report")
    print("----------------------------")

    print(f"MAE  : {mae:.2f} MW")
    print(f"RMSE : {rmse:.2f} MW")
    print(f"MAPE : {mape:.2f} %")

    print("\nTotal Data Points:", len(df))

    df.to_csv("wind_forecast_analysis.csv", index=False)
if __name__ == "__main__":
    main()