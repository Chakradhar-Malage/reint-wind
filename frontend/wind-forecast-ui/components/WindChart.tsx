"use client"

import { memo, useMemo } from "react"
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  Legend,
  ResponsiveContainer
} from "recharts"

import { WindData } from "../types/wind"

interface Props {
  data?: WindData[]
}

function WindChart({ data = [] }: Props) {

  const formatted = useMemo(() => {
    return data.map(d => ({
      ...d,
      time: new Date(d.time).toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit"
      })
    }))
  }, [data])

  return (
    <ResponsiveContainer width="100%" height={window.innerWidth < 768 ? 300 : 420}>
      <LineChart data={formatted}>
        <CartesianGrid strokeDasharray="3 3" />

        <XAxis
          dataKey="time"
          tick={{ fontSize: 11 }}
          interval="preserveStartEnd"
        />

        <YAxis />

        <Tooltip
          contentStyle={{
            borderRadius: "8px",
            border: "1px solid #ddd"
          }}
        />

        <Legend />

        <Line
          type="natural"
          dataKey="actual"
          stroke="#2563eb"
          strokeWidth={2}
          name="Actual Generation"
        />

        <Line
          type="natural"
          dataKey="forecast"
          stroke="#f97316"
          strokeDasharray="5 5"
          strokeWidth={2}
          name="Forecast Generation"
        />
      </LineChart>
    </ResponsiveContainer>
  )
}

export default memo(WindChart)