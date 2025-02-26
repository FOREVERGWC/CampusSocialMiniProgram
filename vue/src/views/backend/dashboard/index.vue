<template>
	<div>
		<el-row :gutter="8">
			<el-col :lg="6" :md="6" :sm="12" :xl="6" :xs="12">
				<el-card>
					<el-statistic :value="info.noteCount">
						<template #title>
							<el-icon>
								<Notebook />
							</el-icon>
							帖子
						</template>
					</el-statistic>
				</el-card>
			</el-col>
			<el-col :lg="6" :md="6" :sm="12" :xl="6" :xs="12">
				<el-card>
					<el-statistic :value="info.userCount">
						<template #title>
							<el-icon>
								<User />
							</el-icon>
							用户
						</template>
					</el-statistic>
				</el-card>
			</el-col>
			<el-col :lg="6" :md="6" :sm="12" :xl="6" :xs="12">
				<el-card>
					<el-statistic :value="info.commentCount">
						<template #title>
							<el-icon>
								<ChatDotSquare />
							</el-icon>
							评论
						</template>
					</el-statistic>
				</el-card>
			</el-col>
			<el-col :lg="6" :md="6" :sm="12" :xl="6" :xs="12">
				<el-card>
					<el-statistic :value="info.viewCount">
						<template #title>
							<el-icon>
								<View />
							</el-icon>
							浏览量
						</template>
					</el-statistic>
				</el-card>
			</el-col>
		</el-row>
		<el-row :gutter="8">
			<el-col :lg="12" :md="12" :sm="24" :xl="12" :xs="24">
				<el-card>
					<template #header>
						<div class="header-content">
							<h4>快捷访问</h4>
						</div>
					</template>
				</el-card>
			</el-col>
			<el-col :lg="12" :md="12" :sm="24" :xl="12" :xs="24">
				<el-card>
					<template #header>
						<div class="header-content">
							<h4>通知</h4>
							<span class="view-all">查看全部</span>
						</div>
					</template>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getDashboardInfo } from '@/api/biz/statistics/index.js'

const info = ref({
	noteCount: 0,
	userCount: 0,
	commentCount: 0,
	viewCount: 0
})

const getInfo = () => {
	getDashboardInfo().then(res => {
		const data = res.data || {}
		for (const key in data) {
			if (Object.prototype.hasOwnProperty.call(data, key) && !isNaN(data[key])) {
				data[key] = parseInt(data[key])
			}
		}
		info.value = data
	})
}

onMounted(() => {
	getInfo()
})

// TODO 统计接口添加基本信息查询接口，SELECT COUNT(1)获取数量回显
</script>

<style lang="scss" scoped>
.header-content {
	display: flex;
	justify-content: space-between;
	align-items: center;

	.view-all {
		cursor: pointer;

		&:hover {
			color: gray;
		}
	}
}

.el-col {
	margin-bottom: 8px;
}
</style>
