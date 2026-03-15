"use client";

import { useEffect, useState } from "react";
import WindChart from "../components/WindChart";
import ErrorChart from "../components/ErrorChart";
import { fetchWindData } from "../services/api";
import { fetchWindError } from "../services/errorApi";
import { WindData } from "../types/wind";

interface ErrorData {
  time: string;
  error: number;
}

export default function Home() {
  const [data, setData] = useState<WindData[]>([]);
  const [errorData, setErrorData] = useState<ErrorData[]>([]);
  const [horizon, setHorizon] = useState(4);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, [horizon]);

  const loadData = async () => {
    try {
      setLoading(true);

      const wind = await fetchWindData(horizon);
      const error = await fetchWindError(horizon);

      setData(wind);
      setErrorData(error);
    } catch (err) {
      console.error("Failed to load data:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main
      style={{
        maxWidth: "1200px",
        margin: "auto",
        padding: "16px",
      }}
    >
      <h1
        style={{
          marginBottom: "20px",
          textAlign: "center",
        }}
      >
        Wind Forecast Dashboard
      </h1>

      {/* Forecast Horizon Slider */}
      <div
        style={{
          marginBottom: "30px",
          background: "#fff",
          padding: "16px",
          borderRadius: "10px",
          boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
        }}
      >
        <label style={{ fontWeight: 600 }}>
          Forecast Horizon: {horizon} Hours
        </label>

        <input
          type="range"
          min="2"
          max="12"
          step="2"
          value={horizon}
          onChange={(e) => setHorizon(Number(e.target.value))}
          style={{
            width: "100%",
            marginTop: "12px",
          }}
        />
      </div>

      {/* Stats Cards */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit,minmax(160px,1fr))",
          gap: "16px",
          marginBottom: "30px",
        }}
      >
        <div
          style={{
            background: "white",
            padding: "16px",
            borderRadius: "10px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
          }}
        >
          <h4>Latest Actual</h4>
          <p style={{ fontSize: "20px", fontWeight: 600 }}>
            {data.length ? data.at(-1)?.actual : "-"} MW
          </p>
        </div>

        <div
          style={{
            background: "white",
            padding: "16px",
            borderRadius: "10px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
          }}
        >
          <h4>Latest Forecast</h4>
          <p style={{ fontSize: "20px", fontWeight: 600 }}>
            {data.length ? data.at(-1)?.forecast : "-"} MW
          </p>
        </div>

        <div
          style={{
            background: "white",
            padding: "16px",
            borderRadius: "10px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
          }}
        >
          <h4>Forecast Error</h4>
          <p style={{ fontSize: "20px", fontWeight: 600 }}>
            {data.length
              ? Math.abs(data.at(-1)!.actual - data.at(-1)!.forecast)
              : "-"}{" "}
            MW
          </p>
        </div>
      </div>

      {/* Wind Chart */}
      <div
        style={{
          width: "100%",
          minHeight: "420px",
          background: "#ffffff",
          borderRadius: "10px",
          padding: "20px",
          marginBottom: "40px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
        }}
      >
        {loading ? (
          <p style={{ textAlign: "center" }}>Loading chart...</p>
        ) : (
          <WindChart data={data} />
        )}
      </div>

      {/* Error Chart */}
      <div
        style={{
          background: "white",
          borderRadius: "10px",
          padding: "20px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
        }}
      >
        <h3 style={{ marginBottom: "15px" }}>Forecast Error Analysis</h3>

        {loading ? (
          <p style={{ textAlign: "center" }}>Loading error chart...</p>
        ) : (
          <ErrorChart data={errorData} />
        )}
      </div>
    </main>
  );
}