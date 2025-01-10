import {defineStore} from 'pinia';

const useRoleStore = defineStore('role', {
    state: () => ({
        roleList: [],
    }),
    actions: {
        setRoleList(roleList) {
            this.roleList = roleList;
        },
        clearRoleList() {
            this.roleList = [];
        },
    },
    persist: true
});

export default useRoleStore
