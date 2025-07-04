import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import CropsPage from "./pages/CropsPage";
import PlantationsPage from "./pages/PlantationsPage";
import PlotsPage from "./pages/PlotsPage";
import SensorsPage from "./pages/SensorsPage";
import ValvesPage from "./pages/ValvesPage";
import MeasurementsPage from "./pages/MeasurementsPage";
import ActivitiesPage from "./pages/ActivitiesPage";
import DashboardPage from "./pages/DashboardPage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
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
          <Route path="dashboard" element={<DashboardPage />} />
        </Route>
      </Routes>
      <ToastContainer />
    </BrowserRouter>
  );
}

export default App;
