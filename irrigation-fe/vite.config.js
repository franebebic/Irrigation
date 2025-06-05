import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': '/src',
    },
  },
  server: {
    proxy: {
            '/measurements': 'http://localhost:8080',
            '/sensors': 'http://localhost:8080',
            '/plots': 'http://localhost:8080',
            '/crops': 'http://localhost:8080',
            '/plantations': 'http://localhost:8080',
      },
  },
});
