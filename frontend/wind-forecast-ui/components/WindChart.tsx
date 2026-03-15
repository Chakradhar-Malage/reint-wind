"use client"

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
  data: WindData[]
}

export default function WindChart({ data }: Props) {

  const formatted = data.map(d => ({
    ...d,
    time: new Date(d.time).toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit"
    })
  }))

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart data={formatted}>
        <CartesianGrid strokeDasharray="3 3" />

        <XAxis dataKey="time" />

        <YAxis />

        <Tooltip />

        <Legend />

        <Line
          type="monotone"
          dataKey="actual"
          stroke="#2563eb"
          strokeWidth={2}
          name="Actual Generation"
        />

        <Line
          type="monotone"
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