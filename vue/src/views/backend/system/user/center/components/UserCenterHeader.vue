<template>
	<el-card shadow="hover" class="user-center-header">
		<el-row :gutter="20">
			<el-col :xs="10" :sm="10" :md="2" :lg="2" :xl="2">
				<div style="display: flex; align-items: center; justify-content: center">
					<el-avatar :size="120" :src="avatar" alt="" @error="() => true">
						<img alt="" src="../../../../../../assets/imgs/profile.png" />
					</el-avatar>
				</div>
			</el-col>
			<el-col :xs="14" :sm="14" :md="22" :lg="22" :xl="22">
				<div class="user-content">
					<div class="user-info">
						<div class="username">{{ user.nickname || user.username }}</div>
						<div class="remark">{{ user.remark || '这个人很懒，还没有添加简介' }}</div>
					</div>
					<el-button type="primary" link @click="openEditDialog">编辑</el-button>
				</div>

				<el-divider class="divider"></el-divider>

				<div class="stats">
					<div class="stat-item">
						<div class="stat-title">文章数量</div>
						<div class="stat-value">{{ user.articleCount || 0 }}</div>
					</div>
					<div class="stat-item">
						<div class="stat-title">粉丝数量</div>
						<div class="stat-value">{{ user.fanCount || 0 }}</div>
					</div>
					<div class="stat-item">
						<div class="stat-title">关注数量</div>
						<div class="stat-value">{{ user.followCount || 0 }}</div>
					</div>
				</div>
			</el-col>
		</el-row>
	</el-card>

	<UserEditDialog v-model:visible="editDialogVisible" :userId="user.id" @saved="onUserSaved" />
</template>

<script setup>
import { computed, ref } from 'vue'
import { ElMessage } from 'element-plus'
import UserEditDialog from './UserEditDialog.vue'

const props = defineProps({
	user: {
		type: Object,
		required: true
	}
})

const avatar = computed(() => import.meta.env.VITE_APP_BASE_API + props.user.avatar)

const editDialogVisible = ref(false)

const openEditDialog = () => {
	editDialogVisible.value = true
}

const onUserSaved = () => {
	ElMessage.success('用户信息已更新')
	// 可以触发父组件的更新逻辑，比如重新拉取用户数据
}
</script>

<style lang="scss" scoped>
.user-center-header {
	.user-content {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.user-info {
		flex: 1;

		.username {
			font-size: 18px;
			font-weight: bold;
		}

		.remark {
			color: #909399;
			margin-top: 4px;
		}
	}

	.divider {
		margin-top: 16px;
	}

	.stats {
		display: flex;
		justify-content: space-between;

		.stat-item {
			text-align: center;

			.stat-title {
				color: #909399;
				font-size: 14px;
			}

			.stat-value {
				font-size: 16px;
				font-weight: bold;
				margin-top: 4px;
			}
		}
	}
}
</style>
