<template>
  <div>
    <el-row :gutter="20" justify="center">
      <el-col :xs="24" :sm="24" :md="20" :lg="20" :xl="20">
        <UserCenterHeader :user="user"/>
      </el-col>
    </el-row>
    <el-row :gutter="20" justify="center">
      <el-col :xs="24" :sm="24" :md="20" :lg="7" :xl="7">
        <el-card>

        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="20" :lg="13" :xl="13">
        <el-card>

        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import UserCenterHeader from './components/UserCenterHeader.vue'
import useUserStore from "@/store/modules/user.js";
import {reactive} from "vue";

const userStore = useUserStore()

const user = reactive(userStore.user)
</script>

<style scoped lang="scss">

</style>
