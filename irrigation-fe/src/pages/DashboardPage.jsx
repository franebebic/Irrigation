export default function DashboardPage() {
  const grafanaURL =
    "http://192.168.100.10:3000/d/a9ca4341-96a5-478e-ac8f-09196228b72c/moisture-monitoring" +
    "?orgId=1&from=now-6h&to=now&theme=light&kiosk=tv";

  return (
<div
  className="h-[90vh]"
  style={{ width: "calc(100vw - 300px)" }}>
    <iframe
    src={grafanaURL}
    title="Grafana"
    width="100%"
    height="100%"
    frameBorder="0"
    style={{ border: "none" }}
  ></iframe>
</div>
  );
}
