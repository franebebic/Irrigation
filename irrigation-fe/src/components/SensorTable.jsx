import { Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";

export default function SensorTable({ sensors, onEdit, onDelete }) {
  if (!sensors.length) {
    return <div className="text-gray-500">No sensors found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">ID</th>
          <th className="text-left p-2 border-b">Name</th>
          <th className="text-left p-2 border-b">Plot</th>
          <th className="text-left p-2 border-b">Actions</th>
        </tr>
      </thead>
      <tbody>
        {sensors.map((sensor) => (
          <tr key={sensor.id}>
            <td className="p-2 border-b">{sensor.id}</td>
            <td className="p-2 border-b">{sensor.name}</td>
            <td className="p-2 border-b">{sensor.plotName}</td>
            <td className="p-2 border-b">
              <div className="flex gap-2">
                <Button variant="ghost" size="icon" onClick={() => onEdit(sensor)} title="Edit">
                  <Pencil className="w-4 h-4" />
                </Button>
                <Button variant="ghost" size="icon" onClick={() => onDelete(sensor.id)} title="Delete">
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
