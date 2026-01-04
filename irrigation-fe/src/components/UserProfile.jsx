import { keycloak } from "@/keycloak";
import { LogOut } from "lucide-react";

function getDisplayName() {
    const t = keycloak.tokenParsed || {};
    return (
        t.preferred_username ||
        t.name ||
        t.email ||
        "User"
    );
}

function getInitials(name) {
    const parts = String(name).trim().split(/\s+/).filter(Boolean);
    const first = parts[0]?.[0] ?? "U";
    const second = parts.length > 1 ? parts[parts.length - 1][0] : "";
    return (first + second).toUpperCase();
}

export default function UserProfile() {
    const handleLogout = () => {
        keycloak.logout({ redirectUri: window.location.origin });
    };

    const name = getDisplayName();
    const initials = getInitials(name);

    return (
        <div className="flex items-center gap-3 min-w-0">
            <div className="h-8 w-8 rounded-full bg-muted flex items-center justify-center text-xs font-semibold">
                {initials}
            </div>

            <div className="min-w-0">
                <div className="text-sm font-medium leading-none truncate max-w-[140px]">
                    {name}
                </div>
                {keycloak.tokenParsed?.email && (
                    <div className="text-xs text-muted-foreground truncate max-w-[140px]">
                        {keycloak.tokenParsed.email}
                    </div>
                )}
            </div>

            <button
                onClick={handleLogout}
                className="p-2 rounded-md hover:bg-muted transition-colors"
                title="Logout"
                aria-label="Logout"
                type="button"
            >
                <LogOut className="h-4 w-4" />
            </button>
        </div>
    );
}
