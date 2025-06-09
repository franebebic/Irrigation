import { useEffect, useState } from "react";
import { fetchPlots } from "../lib/api";
import PlotTable from "../components/PlotTable";
import { Button } from "@/components/ui/button";
import PlotFormDialog from "../components/PlotFormDialog";

export default function PlotsPage() {
  const [plots, setPlots] = useState([]);
  const [open, setOpen] = useState(false);
  const [plotForEdit, setPlotForEdit] = useState(null);

  useEffect(() => {
    fetchPlots()
      .then(setPlots)
      .catch((err) => {
        console.error("Error fetching plots:", err);
      });
  }, []);

  const handleAddPlot = (newPlot) => {
    setPlots((prev) => {
      const existingIndex = prev.findIndex((c) => c.id === newPlot.id);
      if (existingIndex !== -1) {
        const updated = [...prev];
        updated[existingIndex] = newPlot;
        return updated;
      }
      return [...prev, newPlot];
    });
    setPlotForEdit(null);
  };

  const handleEdit = (plot) => {
    setPlotForEdit(plot);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm("Are you sure you want to delete this plot?");
    if (!confirmed) return;

    try {
      await fetch(`/api/plots/${id}`, {
        method: "DELETE",
      });
      setPlots((prev) => prev.filter((c) => c.id !== id));
    } catch (err) {
      alert("Error deleting plot.");
    }
  };

  return (
    <div className="p-6">
      <div className="mb-4">
        <h1 className="text-2xl font-bold mb-2">Plots</h1>
        <Button onClick={() => { setPlotForEdit(null); setOpen(true); }}>
          Add Plot
        </Button>
      </div>

      <PlotTable plots={plots} onEdit={handleEdit} onDelete={handleDelete} />

      <PlotFormDialog
        open={open}
        onOpenChange={setOpen}
        onAddPlot={handleAddPlot}
        initialData={plotForEdit}
      />
    </div>
  );
}
