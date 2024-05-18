import { defineConfig } from 'vite'
export default defineConfig({
  server: {
    proxy: {
      '/create-title': 'http://localhost:8080',
    },
  },
})
