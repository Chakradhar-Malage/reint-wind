# System Architecture

This project is a full-stack wind power forecasting analysis system.

It fetches wind generation data from the Elexon BMRS API, processes forecast data, and visualizes the results using an interactive dashboard.

## Architecture Overview

Frontend (Next.js + Recharts)
        │
        ▼
Backend API (Spring Boot WebFlux)
        │
        ▼
Elexon BMRS Data API

## Components

### Frontend
- Built using **Next.js**
- Data visualization with **Recharts**
- Responsive dashboard UI
- Forecast horizon slider
- Error analysis chart

### Backend
- Built with **Spring Boot WebFlux**
- Reactive API design
- Fetches wind generation data from Elexon API
- Implements forecast horizon logic
- Provides REST APIs for dashboard

### Data Analysis
- Python script calculates forecast accuracy metrics:
  - MAE (Mean Absolute Error)
  - RMSE (Root Mean Square Error)
  - MAPE (Mean Absolute Percentage Error)

## Data Flow

1. Backend fetches actual and forecast wind generation from Elexon API.
2. ForecastProcessingService selects the best forecast based on horizon.
3. APIs return processed wind data.
4. Frontend dashboard visualizes the results.
5. Python script analyzes forecast accuracy.