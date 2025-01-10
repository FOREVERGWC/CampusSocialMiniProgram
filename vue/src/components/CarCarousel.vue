<template>
  <el-carousel>
    <el-carousel-item v-for="(item, index) in carousels" :key="index">
      <img :src="item" alt="" />
    </el-carousel-item>
  </el-carousel>
</template>

<script setup>
import carousel_1 from '@/assets/imgs/carousel/1.png'
import carousel_2 from '@/assets/imgs/carousel/2.png'

const carousels = [carousel_1, carousel_2]
</script>

<style lang="scss" scoped>
.el-carousel {
  width: 100%;
  height: 100vh;

  :deep(.el-carousel__container) {
    height: 100%;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
}
</style>
