import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import CropsPage from "./pages/CropsPage";
import PlantationsPage from "./pages/PlantationsPage";
import PlotsPage from "./pages/PlotsPage";
import SensorsPage from "./pages/SensorsPage";
import ValvesPage from "./pages/ValvesPage";
import MeasurementsPage from "./pages/MeasurementsPage";
import ActivitiesPage from "./pages/ActivitiesPage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


function App() {
  return (
    <div className="flex flex-col min-h-screen">
      <main className="flex-1">
        <BrowserRouter>
          <Routes>
              <Route path="/" element={<MainLayout />}>
              <Route path="crops" element={<CropsPage />} />
              <Route path="plantations" element={<PlantationsPage />} />
              <Route path="plots" element={<PlotsPage />} />
              <Route path="sensors" element={<SensorsPage />} />
              <Route path="valves" element={<ValvesPage />} />
              <Route path="measurements" element={<MeasurementsPage />} />
              <Route path="activities" element={<ActivitiesPage />} />
            </Route>
          </Routes>
        </BrowserRouter>
       </main>
    </div>
  );
}

export default App;
