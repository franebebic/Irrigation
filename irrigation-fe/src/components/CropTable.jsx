import { Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";

export default function CropTable({ crops, onEdit, onDelete }) {
  if (!crops.length) {
    return <div className="text-gray-500">No crops found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">ID</th>
          <th className="text-left p-2 border-b">Name</th>
          <th className="text-left p-2 border-b">Min Moisture (%)</th>
          <th className="text-left p-2 border-b">Max Moisture (%)</th>
          <th className="text-left p-2 border-b">Actions</th>
        </tr>
      </thead>
      <tbody>
        {crops.map((crop) => (
          <tr key={crop.id}>
            <td className="p-2 border-b">{crop.id}</td>
            <td className="p-2 border-b">{crop.name}</td>
            <td className="text-center p-2 border-b">{crop.minMoisture}</td>
            <td className="text-center p-2 border-b">{crop.maxMoisture}</td>
            <td className="p-2 border-b">
              <div className="flex gap-2">
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onEdit(crop)}
                  title="Edit"
                >
                  <Pencil className="w-4 h-4" />
                </Button>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onDelete(crop.id)}
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
