import { parse } from "iso8601-duration";

export function formatDuration(durationString) {
  if (!durationString) return "";

  try {
    const parsed = parse(durationString); // { hours, minutes, seconds, ... }
    const parts = [];

    if (parsed.hours) {
      parts.push(`${parsed.hours} ${parsed.hours === 1 ? "hour" : "hours"}`);
    }

    if (parsed.minutes) {
      parts.push(`${parsed.minutes} ${parsed.minutes === 1 ? "minute" : "minutes"}`);
    }

    if (parsed.seconds) {
      parts.push(`${parsed.seconds} ${parsed.seconds === 1 ? "second" : "seconds"}`);
    }

    return parts.join(" ");
  } catch (err) {
    return durationString; // fallback if parsing fails
  }
}


export default function ActivityTable({ activities }) {
  if (!Array.isArray(activities) || activities.length === 0) {
    return <div className="text-gray-500">No activities found.</div>;
  }



  return (
    <table className="min-w-full bg-white border rounded-md">
      <thead>
        <tr className="bg-gray-100">
          <th className="text-left p-2 border-b">Started at</th>
          <th className="text-left p-2 border-b">Plot name</th>
          <th className="text-left p-2 border-b">Status</th>
          <th className="text-left p-2 border-b">Duration</th>
          <th className="text-left p-2 border-b">Triggered by</th>
        </tr>
      </thead>
      <tbody>
        {activities.map((m) => (
          <tr key={m.id}>
            <td className="p-2 border-b">{new Date(m.startTime).toLocaleString()}</td>
            <td className="p-2 border-b">{m.plotName}</td>
            <td className="p-2 border-b">{m.status}</td>
            <td className="p-2 border-b">{formatDuration(m.duration)}</td>
            <td className="p-2 border-b">{m.type}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
