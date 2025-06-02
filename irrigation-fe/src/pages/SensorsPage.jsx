import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import SensorTable from "../components/SensorTable";
import SensorFormDialog from "../components/SensorFormDialog";

export default function SensorsPage() {
  const [sensors, setSensors] = useState([]);
  const [open, setOpen] = useState(false);
  const [sensorForEdit, setSensorForEdit] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/sensors")
      .then((res) => res.json())
      .then(setSensors)
      .catch((err) => console.error("Error fetching sensors:", err));
  }, []);

  const handleAddSensor = (newSensor) => {
    setSensors((prev) => {
      const index = prev.findIndex((p) => p.id === newSensor.id);
      if (index !== -1) {
        const updated = [...prev];
        updated[index] = newSensor;
        return updated;
      }
      return [...prev, newSensor];
    });
    setSensorForEdit(null);
  };

  const handleEdit = (sensor) => {
    setSensorForEdit(sensor);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this sensor?")) return;
    try {
      await fetch(`http://localhost:8080/sensors/${id}`, {
        method: "DELETE",
      });
      setSensors((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      alert("Error deleting sensor.");
    }
  };

  return (
    <div className="p-6">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">Sensors</h1>
        <Button onClick={() => { setSensorForEdit(null); setOpen(true); }}>
          Add Sensor
        </Button>
      </div>

      <SensorTable sensors={sensors} onEdit={handleEdit} onDelete={handleDelete} />

      <SensorFormDialog
        open={open}
        onOpenChange={setOpen}
        onAddSensor={handleAddSensor}
        initialData={sensorForEdit}
      />
    </div>
  );
}
