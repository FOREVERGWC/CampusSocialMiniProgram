<template>
  <div v-html="data"/>
</template>

<script setup>
import {nextTick, onMounted, ref} from "vue";
import {useRoute} from "vue-router";

const route = useRoute()

const data = ref('')

onMounted(() => {
  data.value = route.query.data
  console.log(data.value)
  nextTick(() => {
    document.forms[0].submit()
  })
})
</script>

<style scoped lang="scss">

</style>