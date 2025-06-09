import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@/components/ui/select";
import { useEffect, useState } from "react";
import axios from "axios";

export default function ValveFormDialog({ open, onOpenChange, onAddValve, initialData }) {
  const [name, setName] = useState("");
  const [plotId, setPlotId] = useState("");
  const [plots, setPlots] = useState([]);

  // 1. Učitaj plots
  useEffect(() => {
    if (open) {
      axios.get("/api/plots")
        .then(res => setPlots(res.data))
        .catch(err => console.error("Failed to load plots", err));
    }
  }, [open]);

  // 2. Postavi formu kad su podaci dostupni
  useEffect(() => {
    if (initialData && plots.length > 0) {
      setName(initialData.name || "");
      setPlotId(initialData.plotId?.toString() || "");
    } else if (!initialData && plots.length > 0) {
      setName("");
      setPlotId("");
    }
  }, [initialData, plots]);

  // 3. Resetiraj formu kad se zatvori dijalog
  useEffect(() => {
    if (!open) {
      setName("");
      setPlotId("");
    }
  }, [open]);


const handleSubmit = async (e) => {
  e.preventDefault();

  const valve = {
    name: name.trim(),
    plotId: plotId ? parseInt(plotId) : null,
  };

  const method = initialData ? "put" : "post";
  const url = initialData ? `/api/valves/${initialData.id}` : "/api/valves";

  try {
    const res = await axios[method](url, valve);
    onAddValve(res.data);
    onOpenChange(false);
  } catch (err) {
    const errorMessage =
      typeof err?.response?.data === "string"
        ? err.response.data
        : err?.response?.data?.error || "Failed to save valve.";

    alert(errorMessage);
  }
};


  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>{initialData ? "Edit Valve" : "Add New Valve"}</DialogTitle>
          <DialogDescription>
            {initialData
              ? "Update the valve's details below."
              : "Fill in the form to add a new valve."}
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4 mt-4">
          <div>
            <Label>Name</Label>
            <Input
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              placeholder="Enter valve name"
            />
          </div>
          <div>
            <Label>Plot</Label>
            <Select value={plotId} onValueChange={setPlotId} required>
              <SelectTrigger>
                <SelectValue placeholder="Select plot" />
              </SelectTrigger>
              <SelectContent>
                {plots.map((p) => (
                  <SelectItem key={p.id} value={p.id.toString()}>
                    {p.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <Button type="submit" className="w-full">
            {initialData ? "Save Changes" : "Add Valve"}
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
}
