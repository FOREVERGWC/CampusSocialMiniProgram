<template>
  <el-select size="large" v-model="value" clearable filterable :placeholder="props.placeholder">
    <el-option v-for="item in optionList" :key="item.value" :label="item.label" :value="item.value"/>
  </el-select>
</template>

<script setup>
import {onMounted, ref, watch} from "vue";
import {getDictDataList} from "@/api/dictData.js";

const props = defineProps({
  type: {
    type: String,
    required: true
  },
  size: {
    type: String,
    default: 'default'
  },
  modelValue: {
    type: [String, Number],
    required: true
  },
  placeholder: {
    type: String,
    default: '请选择'
  }
})

const value = ref(props.modelValue)
const optionList = ref([])

const emit = defineEmits(["update:modelValue"])

watch(() => props.modelValue, (newVal) => {
      value.value = newVal
    }
)

watch(value, (newVal) => {
  emit("update:modelValue", newVal)
})

onMounted(() => {
  const params = {
    code: props.type
  }
  getDictDataList(params).then(res => {
    optionList.value = res.data || []
  })
})
</script>

<style scoped lang="scss">

</style>