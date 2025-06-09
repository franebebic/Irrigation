import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import PlantationTable from "../components/PlantationTable";
import PlantationFormDialog from "../components/PlantationFormDialog";

export default function PlantationsPage() {
  const [plantations, setPlantations] = useState([]);
  const [open, setOpen] = useState(false);
  const [plantationForEdit, setPlantationForEdit] = useState(null);

  useEffect(() => {
    fetch("/api/plantations")
      .then((res) => res.json())
      .then(setPlantations)
      .catch((err) => console.error("Error fetching plantations:", err));
  }, []);

  const handleAddPlantation = (newPlantation) => {
    setPlantations((prev) => {
      const index = prev.findIndex((p) => p.id === newPlantation.id);
      if (index !== -1) {
        const updated = [...prev];
        updated[index] = newPlantation;
        return updated;
      }
      return [...prev, newPlantation];
    });
    setPlantationForEdit(null);
  };

  const handleEdit = (plantation) => {
    setPlantationForEdit(plantation);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this plantation?")) return;
    try {
      await fetch(`/api/plantations/${id}`, {
        method: "DELETE",
      });
      setPlantations((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      alert("Error deleting plantation.");
    }
  };

  return (
    <div className="p-6">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">Plantations</h1>
        <Button onClick={() => { setPlantationForEdit(null); setOpen(true); }}>
          Add Plantation
        </Button>
      </div>

      <PlantationTable plantations={plantations} onEdit={handleEdit} onDelete={handleDelete} />

      <PlantationFormDialog
        open={open}
        onOpenChange={setOpen}
        onAddPlantation={handleAddPlantation}
        initialData={plantationForEdit}
      />
    </div>
  );
}
