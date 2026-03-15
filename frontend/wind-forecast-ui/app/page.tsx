"use client";

import { useEffect, useState } from "react";
import WindChart from "../components/WindChart";
import { fetchWindData } from "../services/api";
import { WindData } from "../types/wind";

export default function Home() {
  const [data, setData] = useState<WindData[]>([]);
  const [horizon, setHorizon] = useState(4);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    loadData();
  }, [horizon]);

  const loadData = async () => {
    setLoading(true);
    const result = await fetchWindData(horizon);
    setData(result);
    setLoading(false);
  };

  console.log(data);
  return (
    <main
      style={{
        maxWidth: "1200px",
        margin: "auto",
        padding: "20px",
      }}
    >
      <h1 style={{ marginBottom: "20px" }}>Wind Forecast Dashboard</h1>

      <div style={{ marginBottom: "25px" }}>
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
            marginTop: "10px",
          }}
        />
      </div>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit,minmax(150px,1fr))",
          gap: "15px",
          marginBottom: "25px",
        }}
      >
        <div className="card">
          <h4>Latest Actual</h4>
          <p>{data.at(-1)?.actual ?? "-"} MW</p>
        </div>

        <div className="card">
          <h4>Latest Forecast</h4>
          <p>{data.at(-1)?.forecast ?? "-"} MW</p>
        </div>

        <div className="card">
          <h4>Forecast Error</h4>
          <p>
            {data.at(-1)
              ? Math.abs(data.at(-1)!.actual - data.at(-1)!.forecast)
              : "-"}{" "}
            MW
          </p>
        </div>
      </div>

      <div
        style={{
          width: "100%",
          height: "420px",
          background: "#ffffff",
          borderRadius: "10px",
          padding: "20px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
        }}
      >
        {loading ? <p>Loading chart...</p> : <WindChart data={data} />}
      </div>
    </main>
  );
}
