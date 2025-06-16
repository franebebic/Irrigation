export async function fetchCrops() {
  const response = await fetch("/api/crops");
  if (!response.ok) {
    throw new Error("Failed to fetch crops");
  }
  return response.json();
}

export async function fetchPlots() {
  const response = await fetch("/api/plots");
  if (!response.ok) {
    throw new Error("Failed to fetch plots");
  }
  return response.json();
}