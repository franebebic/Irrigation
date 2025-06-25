import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { useEffect } from "react";

// Fix za prikaz marker ikone u Leafletu (webpack bug)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
  iconRetinaUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
  shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

import { useMap } from "react-leaflet";

function RecenterMap({ lat, lon }) {
  const map = useMap();

  useEffect(() => {
    map.setView([lat, lon], map.getZoom());
  }, [lat, lon]);

  return null;
}

function PlotLocationMap({ latitude, longitude, onChange }) {
  function LocationUpdater() {
    useMapEvents({
      click(e) {
        onChange(e.latlng.lat, e.latlng.lng);
      },
    });
    return null;
  }

  return (
    <MapContainer
      center={[latitude, longitude]}
      zoom={16}
      style={{ height: "400px", width: "100%", borderRadius: "0.5rem", overflow: "hidden" }}
    >
      <TileLayer
        url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
        attribution='&copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye'
      />
      <Marker position={[latitude, longitude]} />
      <LocationUpdater />
      <RecenterMap lat={latitude} lon={longitude} />
    </MapContainer>
  );
}

export default PlotLocationMap;
