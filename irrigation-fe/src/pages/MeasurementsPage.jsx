import { useEffect, useState } from "react";
import MeasurementTable from "../components/MeasurementTable";

export default function MeasurementsPage() {
  const [measurements, setMeasurements] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/measurements")
      .then((res) => res.json())
      .then(setMeasurements)
      .catch((err) => console.error("Error fetching measurements:", err));
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Measurements</h1>
      <MeasurementTable measurements={measurements} />
    </div>
  );
}
