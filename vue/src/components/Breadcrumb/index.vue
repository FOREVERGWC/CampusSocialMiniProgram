<template>
	<el-breadcrumb :separator-icon="ArrowRight">
		<el-breadcrumb-item :to="{ path: item.path }" @click="handleClick(item.path)" v-for="item in list" :key="item.path">
			<div class="breadcrumb-content">
				<el-icon>
					<component :is="item.meta.icon" />
				</el-icon>
				<span>{{ item.meta.title }}</span>
			</div>
		</el-breadcrumb-item>
	</el-breadcrumb>
</template>

<script setup>
import { computed } from 'vue'
import { ArrowRight } from '@element-plus/icons-vue'
import { useRoute } from 'vue-router'
// import useNavigationStore from "@/store/modules/navigation.js";

const route = useRoute()
// const navigationStore = useNavigationStore()

const list = computed(() => {
	return route.matched || []
})

const handleClick = path => {
	// TODO 刷新菜单选中项
	// navigationStore.setMenu(path)
	console.log(path)
}
</script>

<style lang="scss" scoped>
.breadcrumb-content {
	display: flex;
	align-items: center;

	.el-icon {
		margin-right: 8px;
	}
}
</style>
