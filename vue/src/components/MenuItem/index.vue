<template>
	<el-sub-menu v-if="item?.children?.length > 0" v-show="!item.meta.hidden" :index="item.path">
		<template #title>
			<el-icon>
				<component v-if="item.meta && item.meta.icon" :is="item.meta.icon" />
			</el-icon>
			<span>{{ item.meta.title }}</span>
		</template>
		<menu-item
			v-for="child in item.children"
			:key="child.path"
			:item="child"
			@click="handleClickMenu(child.path)"></menu-item>
	</el-sub-menu>
	<el-menu-item v-else v-show="!item.meta.hidden" :index="item.path" @click="handleClickMenu(item.path)">
		<el-icon>
			<component v-if="item.meta && item.meta.icon" :is="item.meta.icon" />
		</el-icon>
		<span>{{ item.meta.title }}</span>
	</el-menu-item>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

defineProps({
	item: {
		type: Object,
		required: true
	}
})

const emit = defineEmits(['click'])

const handleClickMenu = path => {
	emit('click', path)
}
</script>

<style lang="scss" scoped></style>
