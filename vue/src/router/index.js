import { createRouter, createWebHistory } from 'vue-router'
import { constantRoutes } from '@/router/routes.js'

const router = createRouter({
	history: createWebHistory(),
	routes: constantRoutes
})

export default router
