import { useEffect, useState } from "react";
import { fetchCrops } from "../lib/api";
import CropTable from "../components/CropTable";
import { Button } from "@/components/ui/button";
import CropFormDialog from "../components/CropFormDialog";

export default function CropsPage() {
  const [crops, setCrops] = useState([]);
  const [open, setOpen] = useState(false);
  const [cropForEdit, setCropForEdit] = useState(null);

  useEffect(() => {
    fetchCrops()
      .then(setCrops)
      .catch((err) => {
        console.error("Error fetching crops:", err);
      });
  }, []);

  const handleAddCrop = (newCrop) => {
    setCrops((prev) => {
      const existingIndex = prev.findIndex((c) => c.id === newCrop.id);
      if (existingIndex !== -1) {
        const updated = [...prev];
        updated[existingIndex] = newCrop;
        return updated;
      }
      return [...prev, newCrop];
    });
    setCropForEdit(null);
  };

  const handleEdit = (crop) => {
    setCropForEdit(crop);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm("Are you sure you want to delete this crop?");
    if (!confirmed) return;

    try {
      await fetch(`/api/crops/${id}`, {
        method: "DELETE",
      });
      setCrops((prev) => prev.filter((c) => c.id !== id));
    } catch (err) {
      alert("Error deleting crop.");
    }
  };

  return (
    <div className="p-6">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">Crops</h1>
        <Button onClick={() => { setCropForEdit(null); setOpen(true); }}>
          Add Crop
        </Button>
      </div>

      <CropTable crops={crops} onEdit={handleEdit} onDelete={handleDelete} />

      <CropFormDialog
        open={open}
        onOpenChange={setOpen}
        onAddCrop={handleAddCrop}
        initialData={cropForEdit}
      />
    </div>
  );
}
