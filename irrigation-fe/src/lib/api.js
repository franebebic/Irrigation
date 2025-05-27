export async function fetchCrops() {
  const response = await fetch("http://localhost:8080/crops");
  if (!response.ok) {
    throw new Error("Failed to fetch crops");
  }
  return response.json();
}

export async function fetchPlots() {
  const response = await fetch("http://localhost:8080/plots");
  if (!response.ok) {
    throw new Error("Failed to fetch plots");
  }
  return response.json();
}