import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import ValveTable from "@/components/ValveTable";
import ValveFormDialog from "@/components/ValveFormDialog";
import axios from "axios";

export default function ValvesPage() {
  const [valves, setValves] = useState([]);
  const [open, setOpen] = useState(false);
  const [valveForEdit, setValveForEdit] = useState(null);

  useEffect(() => {
    axios
      .get("/api/valves")
      .then((res) => setValves(res.data))
      .catch((err) => console.error("Error fetching valves:", err));
  }, []);

  const handleAddValve = (newValve) => {
    setValves((prev) => {
      const index = prev.findIndex((v) => v.id === newValve.id);
      if (index !== -1) {
        const updated = [...prev];
        updated[index] = newValve;
        return updated;
      }
      return [...prev, newValve];
    });
    setValveForEdit(null);
  };

  const handleEdit = (valve) => {
    setValveForEdit(valve);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this valve?")) return;
    try {
      await axios.delete(`/api/valves/${id}`);
      setValves((prev) => prev.filter((v) => v.id !== id));
    } catch (err) {
      alert("Error deleting valve.");
    }
  };

  const handleToggle = (updatedValve) => {
    setValves((prev) =>
      prev.map((v) => (v.id === updatedValve.id ? updatedValve : v))
    );
  };

  return (
    <div className="p-6">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">Valves</h1>
        <Button
          onClick={() => {
            setValveForEdit(null);
            setOpen(true);
          }}
        >
          Add Valve
        </Button>
      </div>

      <ValveTable
        valves={valves}
        onEdit={handleEdit}
        onDelete={handleDelete}
        onToggle={handleToggle}
      />

      <ValveFormDialog
        open={open}
        onOpenChange={setOpen}
        onAddValve={handleAddValve}
        initialData={valveForEdit}
      />
    </div>
  );
}
