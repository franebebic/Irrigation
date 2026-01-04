import './index.css';
import { StrictMode } from 'react'
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import { keycloak } from "./keycloak";

keycloak
    .init({ onLoad: "login-required", pkceMethod: "S256" })
    .then(() => {
        ReactDOM.createRoot(document.getElementById("root")).render(
            <StrictMode>
                <App />
            </StrictMode>
        );
    })
    .catch((err) => {
        console.error("Keycloak init failed", err);
    });