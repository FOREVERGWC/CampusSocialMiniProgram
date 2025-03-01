<template>
	<el-select-v2 v-model="value" v-bind="setting" :options="optionList" />
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { getDictDataList } from '@/api/sys/dict/data/index.js'

const props = defineProps({
	modelValue: {
		type: [String, Number, null],
		required: true
	},
	type: {
		type: String,
		required: true
	},
	setting: Object
})

const value = ref(props.modelValue)
const optionList = ref([])

const emit = defineEmits(['update:modelValue'])

watch(
	() => props.modelValue,
	newVal => {
		value.value = newVal
	}
)

watch(value, newVal => {
	emit('update:modelValue', newVal)
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

<style scoped lang="scss"></style>
