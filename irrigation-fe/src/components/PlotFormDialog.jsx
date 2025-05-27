import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";

export default function PlotFormDialog({ open, onOpenChange, onAddPlot, initialData }) {
  const [name, setName] = useState("");
  const [minMoisture, setMinMoisture] = useState("");
  const [maxMoisture, setMaxMoisture] = useState("");

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || "");
    } else {
      setName("");
    }
  }, [initialData, open]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const plot = {
      name,
    };

    const method = initialData ? "PUT" : "POST";
    const url = initialData
      ? `http://localhost:8080/plots/${initialData.id}`
      : "http://localhost:8080/plots";

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
          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Plot"}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}
