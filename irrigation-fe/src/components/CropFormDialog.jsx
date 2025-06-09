import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";

export default function CropFormDialog({ open, onOpenChange, onAddCrop, initialData }) {
  const [name, setName] = useState("");
  const [minMoisture, setMinMoisture] = useState("");
  const [maxMoisture, setMaxMoisture] = useState("");

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || "");
      setMinMoisture(initialData.minMoisture?.toString() || "");
      setMaxMoisture(initialData.maxMoisture?.toString() || "");
    } else {
      setName("");
      setMinMoisture("");
      setMaxMoisture("");
    }
  }, [initialData, open]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const crop = {
      name,
      minMoisture: parseFloat(minMoisture),
      maxMoisture: parseFloat(maxMoisture),
    };

    const method = initialData ? "PUT" : "POST";
    const url = initialData
      ? `http://localhost:8080/api/crops/${initialData.id}`
      : "http://localhost:8080/api/crops";

    const response = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(crop),
    });

    if (response.ok) {
      const updatedCrop = await response.json();
      onAddCrop(updatedCrop);
      onOpenChange(false);
    } else {
      alert("Failed to save crop.");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{initialData ? "Edit Crop" : "Add New Crop"}</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div>
            <Label htmlFor="name">Name</Label>
            <Input id="name" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>
          <div>
            <Label htmlFor="minMoisture">Min Moisture (%)</Label>
            <Input
              id="minMoisture"
              type="number"
              step="0.1"
              value={minMoisture}
              onChange={(e) => setMinMoisture(e.target.value)}
              required
            />
          </div>
          <div>
            <Label htmlFor="maxMoisture">Max Moisture (%)</Label>
            <Input
              id="maxMoisture"
              type="number"
              step="0.1"
              value={maxMoisture}
              onChange={(e) => setMaxMoisture(e.target.value)}
              required
            />
          </div>
          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Crop"}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}
