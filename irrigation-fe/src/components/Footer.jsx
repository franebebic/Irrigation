import React from "react";

export default function Footer() {
  return (
    <footer className="fixed bottom-0 left-0 right-0 border-t p-4 text-center text-sm text-muted-foreground bg-background z-50">
      <div>
        © {new Date().getFullYear()} Frane Bebić · v1.0.0
      </div>
      <div>
        <a
          href="https://github.com/franebebic/Irrigation"
          className="underline hover:text-primary"
        >
          GitHub
        </a>
      </div>
    </footer>
  );
}
