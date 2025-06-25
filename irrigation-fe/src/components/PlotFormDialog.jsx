import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import PlotLocationMap from "./PlotLocationMap";
import { useEffect, useState } from "react";

export default function PlotFormDialog({ open, onOpenChange, onAddPlot, initialData }) {
  const [name, setName] = useState("");
  const [minMoisture, setMinMoisture] = useState("");
  const [maxMoisture, setMaxMoisture] = useState("");
  const [latitude, setLatitude] = useState(43.5081); // default Split
  const [longitude, setLongitude] = useState(16.4402);

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || "");
      setLatitude(initialData.latitude || 43.5081);
      setLongitude(initialData.longitude || 16.4402);
    } else {
      setName("");
      setLatitude(43.5081);
      setLongitude(16.4402);
    }
  }, [initialData, open]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const plot = {
        name,
        latitude,
        longitude,
    };

    const method = initialData ? "PUT" : "POST";
    const url = initialData
      ? `/api/plots/${initialData.id}`
      : "/api/plots";

    console.log("Submitting plot:", plot);

    const response = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(plot),
    });

    if (response.ok) {
      const updatedPlot = await response.json();
      onAddPlot(updatedPlot);
      onOpenChange(false);
    } else {
      alert("Failed to save plot.");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{initialData ? "Edit Plot" : "Add New Plot"}</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div>
            <Label htmlFor="name">Name</Label>
            <Input id="name" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>
          <div>
            <Label>Location</Label>
            <PlotLocationMap
              latitude={latitude}
              longitude={longitude}
              onChange={(lat, lon) => {
                setLatitude(lat);
                setLongitude(lon);
              }}
            />
          </div>
          <div className="flex justify-between text-sm text-gray-500">
            <span>Lat: {latitude.toFixed(5)}</span>
            <span>Lon: {longitude.toFixed(5)}</span>
          </div>
          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Plot"}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}
