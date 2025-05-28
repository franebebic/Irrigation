import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { format } from "date-fns";
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@/components/ui/select";
import { useEffect, useState } from "react";

export default function PlantationFormDialog({ open, onOpenChange, onAddPlantation, initialData }) {
  const [cropId, setCropId] = useState("");
  const [plotId, setPlotId] = useState("");
  const [plantingDate, setPlantingDate] = useState(null);
  const [plantCount, setPlantCount] = useState("");
  const [crops, setCrops] = useState([]);
  const [plots, setPlots] = useState([]);

  useEffect(() => {
    if (initialData) {
      setCropId(initialData.cropId?.toString() || "");
      setPlotId(initialData.plotId?.toString() || "");
      setPlantingDate(new Date(initialData.plantingDate));
      setPlantCount(initialData.plantCount?.toString() || "");
    } else {
      setCropId(""); setPlotId(""); setPlantingDate(null); setPlantCount("");
    }

    if (open) {
      fetch("http://localhost:8080/crops").then(res => res.json()).then(setCrops);
      fetch("http://localhost:8080/plots").then(res => res.json()).then(setPlots);
    }
  }, [initialData, open]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const plantation = {
      cropId: parseInt(cropId),
      plotId: parseInt(plotId),
      plantingDate: plantingDate.toISOString(),
      plantCount: parseInt(plantCount),
    };

    const method = initialData ? "PUT" : "POST";
    const url = initialData
      ? `http://localhost:8080/plantations/${initialData.id}`
      : "http://localhost:8080/plantations";

    const response = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(plantation),
    });

    if (response.ok) {
      const updated = await response.json();
      onAddPlantation(updated);
      onOpenChange(false);
    } else {
      alert("Failed to save plantation.");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>{initialData ? "Edit Plantation" : "Add New Plantation"}</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div>
            <Label>Crop</Label>
            <Select value={cropId} onValueChange={setCropId} required>
              <SelectTrigger>
                <SelectValue placeholder="Select crop" />
              </SelectTrigger>
              <SelectContent>
                {crops.map(c => (
                  <SelectItem key={c.id} value={c.id.toString()}>
                    {c.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div>
            <Label>Plot</Label>
            <Select value={plotId} onValueChange={setPlotId} required>
              <SelectTrigger>
                <SelectValue placeholder="Select plot" />
              </SelectTrigger>
              <SelectContent>
                {plots.map(p => (
                  <SelectItem key={p.id} value={p.id.toString()}>
                    {p.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div>
            <Label>Planting Date</Label>
            <Calendar mode="single" selected={plantingDate} onSelect={setPlantingDate} required />
            {plantingDate && (
              <p className="text-sm text-muted-foreground">
                Selected: {format(plantingDate, "PPP")}
              </p>
            )}
          </div>

          <div>
            <Label>Plant Count</Label>
            <Input
              type="number"
              value={plantCount}
              onChange={(e) => setPlantCount(e.target.value)}
              required
            />
          </div>

          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Plantation"}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}
