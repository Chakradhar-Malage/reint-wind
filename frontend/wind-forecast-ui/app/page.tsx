"use client"

import { useEffect, useState } from "react"
import WindChart from "../components/WindChart"
import { fetchWindData } from "../services/api"
import { WindData } from "../types/wind"

export default function Home() {

  const [data, setData] = useState<WindData[]>([])
  const [horizon, setHorizon] = useState(4)
  const [loading, setLoading] = useState(true)
  useEffect(() => {
    loadData()
  }, [horizon])

  const loadData = async () => {
  setLoading(true)
  const result = await fetchWindData(horizon)
  setData(result)
  setLoading(false)
}

console.log(data)
  return (
    <main
      style={{
        maxWidth: "1200px",
        margin: "auto",
        padding: "20px"
      }}
    >

      <h1 style={{ marginBottom: "20px" }}>
        Wind Forecast Dashboard
      </h1>

      <div
        style={{
          marginBottom: "20px",
          display: "flex",
          gap: "10px",
          flexWrap: "wrap"
        }}
      >

        <label>Forecast Horizon</label>

        <select
          value={horizon}
          onChange={(e) => setHorizon(Number(e.target.value))}
        >
          <option value={2}>2 Hours</option>
          <option value={4}>4 Hours</option>
          <option value={6}>6 Hours</option>
          <option value={12}>12 Hours</option>
        </select>

      </div>
        
      <div
        style={{
          width: "100%",
          height: "420px",
          background: "#ffffff",
          borderRadius: "10px",
          padding: "20px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
        }}
      >
        {loading ? <p>Loading chart...</p> : <WindChart data={data} />}
      </div>

    </main>
  )
}