import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const BACKEND_ORIGIN = 'http://localhost:8080'
const BACKEND_CONTEXT = '/transtemare-web'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: BACKEND_ORIGIN,
        rewrite: (path) => path.replace(/^\/api/, BACKEND_CONTEXT),
        changeOrigin: true,
        configure: (proxy) => {
          proxy.on('proxyRes', (proxyRes) => {
            // 1. Rewrite Set-Cookie Path so the browser sends JSESSIONID
            //    on every /api/* request (not just /transtemare-web/*).
            const cookies = proxyRes.headers['set-cookie']
            if (cookies) {
              proxyRes.headers['set-cookie'] = cookies.map((c) =>
                c.replace(/;\s*Path=\/transtemare-web/gi, '; Path=/')
              )
            }

            // 2. Rewrite Location header so 302 redirects go back through
            //    the proxy instead of hitting the backend directly (CORS).
            const location = proxyRes.headers['location']
            if (location && typeof location === 'string') {
              proxyRes.headers['location'] = location.replace(
                new RegExp(`${BACKEND_ORIGIN}${BACKEND_CONTEXT}`, 'g'),
                '/api'
              )
            }
          })
        },
      },
    },
  },
})
