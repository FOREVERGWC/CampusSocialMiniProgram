<template>
	<div>
		<el-popover trigger="click" :width="768" placement="bottom-start">
			<template #reference>
				<el-button :icon="modelValue">{{ modelValue }}</el-button>
			</template>
			<el-input v-model="search" placeholder="请输入图标名称" />
			<div class="el-icon-picker">
				<component
					v-for="icon in filteredIcons"
					:key="icon"
					:class="[icon, 'icon', { 'icon-active': icon === modelValue }]"
					:is="icon"
					@click="emits('update:modelValue', icon)" />
			</div>
		</el-popover>
	</div>
</template>

<script setup>
import { defineEmits, defineProps, ref, computed } from 'vue'
import * as ElIcon from '@element-plus/icons-vue'
import _ from 'lodash-es'

const icons = ref(Object.keys(ElIcon))
const search = ref('')

defineProps({
	modelValue: {
		type: String,
		required: true,
		default: ''
	}
})

const emits = defineEmits(['update:modelValue'])

const filteredIcons = computed(() => {
	const query = search.value.toLowerCase()
	return _.filter(icons.value, icon => icon.toLowerCase().includes(query))
})
</script>

<style lang="scss" scoped>
.el-icon-picker {
	height: 256px;
	overflow-y: scroll;
	display: flex;
	justify-content: space-around;
	flex-wrap: wrap;

	.icon {
		display: inline-block;
		width: 24px;
		height: 24px;
		color: var(--el-text-color-regular);
		font-size: 20px;
		border-radius: 4px;
		cursor: pointer;
		text-align: center;
		line-height: 45px;
		margin: 5px;
	}

	.icon:hover,
	.icon-active {
		color: var(--el-color-primary);
	}
}
</style>
