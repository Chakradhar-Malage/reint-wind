# API Documentation

Base URL

http://localhost:8081/api

---

## 1 Wind Data Endpoint

Returns actual and forecast wind generation data.

GET /api/wind-data?horizon=4

### Parameters

| Parameter | Description |
|--------|--------|
| horizon | Forecast horizon in hours |

### Example Response

{
  "time": "2024-01-01T00:00:00",
  "actual": 10402.0,
  "forecast": 11453.0
}

---

## 2 Wind Error Endpoint

Returns forecast error data for analysis.

GET /api/wind-error?horizon=4

### Example Response

{
  "time": "2024-01-01T00:00:00",
  "actual": 10402.0,
  "forecast": 11453.0,
  "error": 1051.0
}