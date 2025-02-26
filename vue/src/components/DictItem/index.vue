<template>
	<div>
		<el-tag type="success" plain>{{ getData(props.value)?.label || '未知' }}</el-tag>
	</div>
</template>

<script setup>
import { getDictDataList } from '@/api/sys/dict/data/index.js'
import { defineProps, onMounted, reactive, ref } from 'vue'

const props = defineProps({
	code: {
		type: String,
		required: true
	},
	value: {
		type: String,
		required: true
	}
})

const loading = ref(true)
const queryParams = reactive({
	code: props.code
})
const dictDataList = ref([])

const getList = () => {
	loading.value = true
	getDictDataList(queryParams).then(res => {
		dictDataList.value = res.data || []
		loading.value = false
	})
}

const getData = value => dictDataList.value.find(item => item.value === value || +item.value === value)

onMounted(() => {
	getList()
})
</script>

<style scoped lang="scss"></style>
