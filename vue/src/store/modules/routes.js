import { defineStore } from 'pinia'

const useRoutesStore = defineStore('routes', {
	state: () => ({
		staticRoutes: [],
		dynamicRoutes: [],
		dynamicRoutesLoaded: false // 新增字段来跟踪动态路由是否已加载
	}),
	actions: {
		setStaticRoutes(routes) {
			this.staticRoutes = routes
		},
		addDynamicRoutes(routes) {
			this.dynamicRoutes.push(...routes)
			this.dynamicRoutesLoaded = true
		},
		setDynamicRoutes(routes) {
			this.dynamicRoutes = routes
			this.dynamicRoutesLoaded = true
		},
		clearRoutes() {
			this.staticRoutes = []
			this.dynamicRoutes = []
			this.dynamicRoutesLoaded = false
		}
	},
	getters: {
		allRoutes: state => [...state.staticRoutes]
	},
	persist: true
})

export default useRoutesStore
