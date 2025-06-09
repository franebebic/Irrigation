import { Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Switch } from "@/components/ui/switch";
import axios from "axios";
import { useState } from "react";

export default function ValveTable({ valves, onEdit, onDelete, onToggle }) {
  const [loadingIds, setLoadingIds] = useState([]);

  const handleToggle = async (valve) => {
    const id = valve.id;
    setLoadingIds((prev) => [...prev, id]);
    try {
      const res = await axios.put(`/api/valves/${id}/toggle`);
      onToggle(res.data); // ažurirani ValveDTO
    } catch (err) {
      alert("Error toggling valve status.");
    } finally {
      setLoadingIds((prev) => prev.filter((i) => i !== id));
    }
  };

  if (!valves || !valves.length) {
    return <div className="text-gray-500">No valves found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">ID</th>
          <th className="text-left p-2 border-b">Name</th>
          <th className="text-left p-2 border-b">Plot</th>
          <th className="text-left p-2 border-b">Status</th>
          <th className="text-left p-2 border-b">Actions</th>
        </tr>
      </thead>
      <tbody>
        {valves.map((valve) => (
          <tr key={valve.id}>
            <td className="p-2 border-b">{valve.id}</td>
            <td className="p-2 border-b">{valve.name}</td>
            <td className="p-2 border-b">{valve.plotName}</td>
            <td className="p-2 border-b">
              <Switch
                checked={valve.status === "ON"}
                onCheckedChange={() => handleToggle(valve)}
                disabled={loadingIds.includes(valve.id)}
              />
            </td>
            <td className="p-2 border-b">
              <div className="flex gap-2">
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onEdit(valve)}
                  title="Edit"
                >
                  <Pencil className="w-4 h-4" />
                </Button>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onDelete(valve.id)}
                  title="Delete"
                >
                  <Trash2 className="w-4 h-4" />
                </Button>
              </div>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
