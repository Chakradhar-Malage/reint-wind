import axios from "axios"
import { WindData } from "../types/wind"

const API_BASE = "http://localhost:8081/api"

export const fetchWindData = async (horizon: number): Promise<WindData[]> => {
  const response = await axios.get(`${API_BASE}/wind-data?horizon=${horizon}`)
  return response.data
}