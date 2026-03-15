"use client"

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer
} from "recharts"

export default function ErrorChart({data}:{data:any[]}){

  const formatted = data.map(d => ({
    ...d,
    time: new Date(d.time).toLocaleTimeString([],{
      hour:"2-digit",
      minute:"2-digit"
    })
  }))

  return(

    <ResponsiveContainer width="100%" height={260}>

      <LineChart data={formatted}>

        <CartesianGrid strokeDasharray="3 3"/>

        <XAxis
          dataKey="time"
          tick={{fontSize:11}}
          interval="preserveStartEnd"
        />

        <YAxis/>

        <Tooltip/>

        <Line
          type="natural"
          dataKey="error"
          stroke="#ef4444"
          strokeWidth={2}
          name="Forecast Error"
        />

      </LineChart>

    </ResponsiveContainer>

  )
}