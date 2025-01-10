import {defineStore} from 'pinia';

const useBreadcrumbStore = defineStore('breadcrumb', {
    state: () => ({
        itemList: [],
    }),
    actions: {
        setItemList (itemList) {
            this.itemList = itemList;
        },
        clearItemList () {
            this.itemList = [];
        },
    },
    persist: true
});

export default useBreadcrumbStore
