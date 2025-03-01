<template>
	<div>
		<h2 style="text-align: center">搜索测试</h2>
		<my-form v-model="form" :option="option" @search="handleSearch" @reset="handleReset" />
	</div>
</template>

<script setup>
import MyForm from '@/components/SearchForm/index.js'
import { reactive } from 'vue'
import { useForm } from '@/hooks/useForm/index.js'

const initOption = {
	fold: {
		enable: true,
		index: 0
	},
	btns: {
		col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 },
		hidden: false
	},
	columns: [
		{
			type: 'input',
			label: '姓名',
			field: 'name',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			type: 'input-number',
			label: '年龄',
			field: 'age',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			type: 'radio-group',
			label: '性别',
			field: 'gender',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			type: 'input',
			label: '手机',
			field: 'phone',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			type: 'select',
			label: '爱好',
			field: 'like',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 },
			options: [
				{ label: '吃', value: '1' },
				{ label: '喝', value: '2' },
				{ label: '玩', value: '3' },
				{ label: '乐', value: '4' }
			]
		},
		{
			type: 'date-picker',
			label: '生日',
			field: 'birthday',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			type: 'textarea',
			label: '介绍',
			field: 'intro',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		},
		{
			field: 'btns',
			col: { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }
		}
	]
}

const { option } = useForm(initOption)

const form = reactive({
	name: '',
	age: null,
	gender: null,
	phone: '',
	like: '',
	birthday: '',
	intro: ''
})

const handleSearch = () => {
	console.log('搜索结果', form)
}

const handleReset = () => {
	option.columns.forEach(item => {
		form[item.field] = null
	})
	console.log('重置结果', form)
}
</script>

<style scoped lang="scss"></style>
