// src/pages/MeasurementsPage.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import MeasurementTable from "@/components/MeasurementTable";

export default function MeasurementsPage() {
  const [measurementsPage, setMeasurementsPage] = useState(null);
  const [sensorOptions, setSensorOptions] = useState([]);
  const [plotOptions, setPlotOptions] = useState([]);
  const [sensorName, setSensorName] = useState("");
  const [plotName, setPlotName] = useState("");
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(() => {
    const saved = localStorage.getItem("pageSize");
    return saved ? parseInt(saved, 10) : 50;
  });

  // Dohvati mjerenja (s pollingom)
  useEffect(() => {
    const fetchData = () => {
      axios
        .get("/measurements", {
          params: {
            page,
            size,
            ...(sensorName && { sensorName }),
            ...(plotName && { plotName }),
          },
        })
        .then((res) => setMeasurementsPage(res.data))
        .catch(console.error);
    };

    fetchData();
    const intervalId = setInterval(fetchData, 15000);
    return () => clearInterval(intervalId);
  }, [page, size, sensorName, plotName]);

  // Dohvati opcije za filtere (samo jednom)
  useEffect(() => {
    axios.get("/measurements/sensor-options").then((res) => setSensorOptions(res.data));
    axios.get("/measurements/plot-options").then((res) => setPlotOptions(res.data));
  }, []);

  if (!measurementsPage) return <div>Loading...</div>;

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Measurements</h2>

      {/* FILTERI */}
      <div className="mb-4 flex gap-4">
        <div>
          <label className="block text-sm font-medium mb-1">Sensor name</label>
          <input
            list="sensor-list"
            value={sensorName}
            onChange={(e) => {
              setSensorName(e.target.value);
              setPage(0);
            }}
            placeholder="All sensors"
            className="border px-2 py-1 rounded w-48"
          />
          <datalist id="sensor-list">
            {sensorOptions.map((s) => (
              <option key={s} value={s} />
            ))}
          </datalist>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Plot name</label>
          <input
            list="plot-list"
            value={plotName}
            onChange={(e) => {
              setPlotName(e.target.value);
              setPage(0);
            }}
            placeholder="All plots"
            className="border px-2 py-1 rounded w-48"
          />
          <datalist id="plot-list">
            {plotOptions.map((p) => (
              <option key={p} value={p} />
            ))}
          </datalist>
        </div>
      </div>

      <MeasurementTable measurements={measurementsPage?.content ?? []} />

      {/* PAGE SIZE */}
      <div className="mb-4">
        <label htmlFor="pageSize" className="mr-2 font-medium">Rows per page:</label>
        <select
          id="pageSize"
          value={size}
          onChange={(e) => {
            const newSize = Number(e.target.value);
            setSize(newSize);
            setPage(0);
            localStorage.setItem("pageSize", newSize);
          }}
          className="border rounded px-2 py-1"
        >
          {[10, 25, 50, 100].map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      {/* PAGINACIJA */}
      <div className="mt-4 flex gap-2 items-center">
        <button
          onClick={() => setPage((p) => Math.max(p - 1, 0))}
          disabled={page === 0}
          className="border px-3 py-1 rounded disabled:opacity-50"
        >
          Previous
        </button>
        <span>
          Page {page + 1} of {measurementsPage.totalPages}
        </span>
        <button
          onClick={() =>
            setPage((p) =>
              p + 1 < measurementsPage.totalPages ? p + 1 : p
            )
          }
          disabled={page + 1 >= measurementsPage.totalPages}
          className="border px-3 py-1 rounded disabled:opacity-50"
        >
          Next
        </button>
      </div>
    </div>
  );
}
