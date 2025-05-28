import { Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";

export default function PlantationTable({ plantations, onEdit, onDelete }) {
  if (!plantations.length) {
    return <div className="text-gray-500">No plantations found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">ID</th>
          <th className="text-left p-2 border-b">Crop</th>
          <th className="text-left p-2 border-b">Plot</th>
          <th className="text-left p-2 border-b">Planting Date</th>
          <th className="text-left p-2 border-b">Plant Count</th>
          <th className="text-left p-2 border-b">Actions</th>
        </tr>
      </thead>
      <tbody>
        {plantations.map((plantation) => (
          <tr key={plantation.id}>
            <td className="p-2 border-b">{plantation.id}</td>
            <td className="p-2 border-b">{plantation.cropName}</td>
            <td className="p-2 border-b">{plantation.plotName}</td>
            <td className="p-2 border-b">
              {new Date(plantation.plantingDate).toLocaleDateString()}
            </td>
            <td className="text-center p-2 border-b">{plantation.plantCount}</td>
            <td className="p-2 border-b">
              <div className="flex gap-2">
                <Button variant="ghost" size="icon" onClick={() => onEdit(plantation)} title="Edit">
                  <Pencil className="w-4 h-4" />
                </Button>
                <Button variant="ghost" size="icon" onClick={() => onDelete(plantation.id)} title="Delete">
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
