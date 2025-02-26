import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig(({ mode }) => {
	const env = loadEnv(mode, process.cwd())
	return {
		plugins: [vue()],
		resolve: {
			alias: {
				'@': resolve(__dirname, 'src')
			}
		},
		server: {
			port: 8082,
			open: true,
			proxy: {
				'/api': {
					target: env.VITE_APP_BASE_API,
					changeOrigin: true,
					rewrite: path => path.replace(/^\/api/, '')
				}
			}
		}
	}
})
