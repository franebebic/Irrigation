export default function MeasurementTable({ measurements }) {
  if (!Array.isArray(measurements) || measurements.length === 0) {
    return <div className="text-gray-500">No measurements found.</div>;
  }

  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">Time</th>
          <th className="text-left p-2 border-b">Type</th>
          <th className="text-left p-2 border-b">Sensor</th>
          <th className="text-left p-2 border-b">Plot</th>
          <th className="text-left p-2 border-b">Value</th>
        </tr>
      </thead>
      <tbody>
        {measurements.map((m) => (
          <tr key={m.id}>
            <td className="p-2 border-b">{new Date(m.measuredAt).toLocaleString()}</td>
            <td className="p-2 border-b">{m.type}</td>
            <td className="p-2 border-b">{m.sensorNameSnapshot}</td>
            <td className="p-2 border-b">{m.plotNameSnapshot}</td>
            <td className="p-2 border-b">{m.measuredValue}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
