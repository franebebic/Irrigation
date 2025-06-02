import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@/components/ui/select";
import { useEffect, useState } from "react";

export default function SensorFormDialog({ open, onOpenChange, onAddSensor, initialData }) {
  const [plotId, setPlotId] = useState("");
  const [plots, setPlots] = useState([]);
  const [name, setName] = useState("");


  useEffect(() => {
    if (initialData) {
      setPlotId(initialData.plotId?.toString() || "");
      setName(initialData.name || "");
    } else {
      setPlotId("");
      setName("");
    }

    if (open) {
      fetch("http://localhost:8080/plots").then(res => res.json()).then(setPlots);
    }
  }, [initialData, open]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const sensor = {
      name:name.trim(),
      plotId: plotId ? parseInt(plotId) : null,
    };

    const method = initialData ? "PUT" : "POST";
    const url = initialData
      ? `http://localhost:8080/sensors/${initialData.id}`
      : "http://localhost:8080/sensors";

    const response = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(sensor),
    });

    if (response.ok) {
      const updated = await response.json();
      onAddSensor(updated);
      onOpenChange(false);
    } else {
      alert("Failed to save sensor.");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>{initialData ? "Edit Sensor" : "Add New Sensor"}</DialogTitle>
          <DialogDescription>
            {initialData ? "Update the sensor's details below." : "Fill in the form to add a new sensor."}
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div>
            <Label>Name</Label>
            <Input
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
                placeholder="Enter sensor name"
            />
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
          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Sensor"}
          </Button>
        </form>
      </DialogContent>

    </Dialog>
  );
}
