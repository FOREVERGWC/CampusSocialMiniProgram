import { defineStore } from 'pinia'

const useNavigationStore = defineStore('navigation', {
	state: () => ({
		menu: '/dashboard'
	}),
	actions: {
		setMenu(menu) {
			this.menu = menu
		}
	},
	persist: true
})

export default useNavigationStore
