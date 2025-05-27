import { Pencil, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";

export default function PlotTable({ plots, onEdit, onDelete }) {
  if (!plots.length) {
    return <div className="text-gray-500">No plots found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">ID</th>
          <th className="text-left p-2 border-b">Name</th>
          <th className="text-left p-2 border-b">Actions</th>
        </tr>
      </thead>
      <tbody>
        {plots.map((plot) => (
          <tr key={plot.id}>
            <td className="p-2 border-b">{plot.id}</td>
            <td className="p-2 border-b">{plot.name}</td>
            <td className="p-2 border-b">
              <div className="flex gap-2">
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onEdit(plot)}
                  title="Edit"
                >
                  <Pencil className="w-4 h-4" />
                </Button>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => onDelete(plot.id)}
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
