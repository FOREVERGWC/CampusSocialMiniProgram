import { defineStore } from 'pinia'

const useMenuListStore = defineStore('menuList', {
	state: () => ({
		menuList: [],
		menuListLoaded: false
	}),
	actions: {
		setMenuList(menus) {
			this.menuList = menus
			this.menuListLoaded = true
		},
		clearMenuList() {
			this.menuList = []
			this.menuListLoaded = false
		}
	},
	persist: true
})

export default useMenuListStore
