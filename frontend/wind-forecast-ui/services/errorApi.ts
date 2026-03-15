export async function fetchWindError(horizon:number){

  const res = await fetch(
    `http://localhost:8081/api/wind-error?horizon=${horizon}`
  )

  if(!res.ok){
    throw new Error("Failed to fetch error data")
  }

  return res.json()
}