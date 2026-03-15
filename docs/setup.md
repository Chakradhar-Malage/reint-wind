# Project Setup Guide

## Prerequisites

- Java 17+
- Node.js 18+
- Python 3.9+
- npm
- pip

---

## 1 Run Backend

Navigate to backend folder:

cd backend

Run the application:

./mvnw spring-boot:run

Backend will start at:

http://localhost:8080

---

## 2 Run Frontend

Navigate to frontend folder:

cd frontend

Install dependencies:

npm install

Run development server:

npm run dev

Open browser:

http://localhost:3000

---

## 3 Run Python Analysis

Navigate to analysis folder:

cd analysis

Install dependencies:

pip install requests pandas numpy

Run analysis:

python forecast_analysis.py

This will calculate:

- MAE
- RMSE
- MAPE