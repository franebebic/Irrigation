export default function ActivityTable({ activities }) {
  if (!Array.isArray(activities) || activities.length === 0) {
    return <div className="text-gray-500">No activities found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">Type</th>
          <th className="text-left p-2 border-b">Valve</th>
          <th className="text-left p-2 border-b">Plot</th>
          <th className="text-left p-2 border-b">Status</th>
          <th className="text-left p-2 border-b">Start time</th>
          <th className="text-left p-2 border-b">End time</th>
        </tr>
      </thead>
      <tbody>
        {activities.map((m) => (
          <tr key={m.id}>
            <td className="p-2 border-b">{m.type}</td>
            <td className="p-2 border-b">{m.valveName}</td>
            <td className="p-2 border-b">{m.plotName}</td>
            <td className="p-2 border-b">{m.status}</td>
            <td className="p-2 border-b">{new Date(m.startTime).toLocaleString()}</td>
            <td className="p-2 border-b">{new Date(m.endTime).toLocaleString()}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
