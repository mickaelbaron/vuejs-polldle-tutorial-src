import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  process.env = {...loadEnv(mode, process.cwd())};

  return {
    plugins: [vue()],
    base: process.env.VITE_APP_SUBPATH,
    build: {
      sourcemap: 'true'
    }
  }
})

