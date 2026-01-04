import { NavLink, Outlet, useLocation } from "react-router-dom";
import {
  Leaf, Sprout, Droplets, Gauge,
  CalendarCheck, BarChart, Map, Rss
} from "lucide-react";
import { cn } from "@/lib/utils";
import Footer from "@/components/Footer";
import UserProfile from "@/components/UserProfile";

const sections = [
  {
    label: "Configuration",
    items: [
      { to: "/crops", label: "Crops", icon: Leaf },
      { to: "/plantations", label: "Plantations", icon: Sprout },
      { to: "/plots", label: "Plots", icon: Map },
      { to: "/sensors", label: "Sensors", icon: Rss },
      { to: "/valves", label: "Valves", icon: Droplets },
    ],
  },
  {
    label: "Monitoring",
    items: [
      { to: "/measurements", label: "Measurements", icon: Gauge },
      { to: "/activities", label: "Activities", icon: CalendarCheck },
    ],
  },
  {
    label: "Analytics",
    items: [
      {
        to: "/dashboard",
        label: "Dashboard",
        icon: BarChart,
        external: true,
      },
    ],
  },
];

export default function MainLayout() {
  const location = useLocation();

  const isSectionActive = (items) =>
    items.some((item) => location.pathname.startsWith(item.to));

  return (
    <div className="flex flex-col min-h-screen bg-muted/40">
      <div className="flex flex-1">
        <aside className="w-64 border-r bg-white p-4 shadow-sm">
          <div className="flex items-center justify-between gap-3 mb-6 min-w-0">
            <div className="text-xl font-bold whitespace-nowrap">
              Irrigation
            </div>

            <div className="max-w-[220px] min-w-0">
              <UserProfile />
            </div>
          </div>

          {sections.map(({ label, items }) => (
            <div key={label} className="mb-4">
              <div
                className={cn(
                  "px-3 text-xs font-semibold uppercase tracking-wider mb-2 transition-colors",
                  isSectionActive(items) ? "text-primary" : "text-muted-foreground"
                )}
              >
                {label}
              </div>

              <nav className="space-y-1">
                {items.map(({ to, label, icon: Icon, external }) =>
                  external ? (
                    <a
                      key={to}
                      href={to}
                      rel="noopener noreferrer"
                      className="flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium text-muted-foreground hover:bg-muted transition-colors"
                    >
                      <Icon className="h-5 w-5" />
                      {label}
                    </a>
                  ) : (
                    <NavLink
                      key={to}
                      to={to}
                      className={({ isActive }) =>
                        cn(
                          "flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium transition-colors hover:bg-muted",
                          isActive ? "bg-muted text-primary font-semibold" : "text-muted-foreground"
                        )
                      }
                    >
                      <Icon className="h-5 w-5" />
                      {label}
                    </NavLink>
                  )
                )}
              </nav>
            </div>
          ))}
        </aside>

<main className={cn("flex-1", location.pathname === "/dashboard" ? "p-0" : "p-6")}>
  <Outlet />
</main>
      </div>

      <Footer />
    </div>
  );
}
