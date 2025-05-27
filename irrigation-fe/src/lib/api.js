/*export async function fetchCrops() {
  return [
    { id: 1, name: "Tomato", type: "Vegetable" },
    { id: 2, name: "Papar", type: "Grain" },
  ];
}*/

export async function fetchCrops() {
  const response = await fetch("http://localhost:8080/crops");
  if (!response.ok) {
    throw new Error("Failed to fetch crops");
  }
  return response.json();
}