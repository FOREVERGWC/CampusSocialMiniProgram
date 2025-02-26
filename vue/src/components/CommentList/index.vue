<template>
	<div class="space-y-4">
		<!-- 评论头部 -->
		<div class="flex items-center justify-between">
			<h3 class="text-lg font-medium">评论 ({{ total }})</h3>
		</div>

		<!-- 评论列表 -->
		<div class="space-y-4" v-loading="loading">
			<div v-for="item in commentList" :key="item.id" class="bg-gray-50 p-4 rounded-lg">
				<!-- 主评论 -->
				<div class="flex space-x-3">
					<el-avatar :size="40" :src="getUrl(item.user?.avatar)" />
					<div class="flex-1">
						<div class="flex items-center space-x-2">
							<span class="font-medium">{{ item.user?.name }}</span>
						</div>
						<p class="py-5 text-gray-700">{{ item.content }}</p>
						<div>
							<span class="text-gray-400 text-sm">{{ item.createTime }}</span>
							<el-button link type="primary" @click="handleReply(item)">回复</el-button>
						</div>
					</div>
				</div>

				<!-- 嵌套回复 -->
				<div v-if="item.children?.length" class="ml-12 mt-4 space-y-4">
					<div v-for="reply in item.children" :key="reply.id" class="bg-white p-3 rounded">
						<div class="flex space-x-3">
							<el-avatar :size="32" :src="getUrl(reply.user?.avatar)" />
							<div class="flex-1">
								<div class="flex items-center space-x-2">
									<span class="font-medium">{{ reply.user?.name }}</span>
									<span class="text-gray-400" v-if="reply.replyId !== item.id">回复</span>
									<span class="font-medium" v-if="reply.replyId !== item.id">
										{{
											reply.replyUserId === item.userId
												? item.user?.name
												: getReplyUser(item.children, reply.replyUserId)?.name
										}}
									</span>
								</div>
								<p class="mt-1 text-gray-700">{{ reply.content }}</p>
								<div class="mt-2">
									<span class="text-gray-400 text-sm">{{ reply.createTime }}</span>
									<el-button link type="primary" @click="handleReply(reply, item)">回复</el-button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 分页 -->
		<el-pagination
			:current-page="queryParams.pageNo"
			:page-size="queryParams.pageSize"
			:page-sizes="[20, 30, 40, 50]"
			:total="total"
			layout="total, sizes, prev, pager, next, jumper"
			@size-change="handleSizeChange"
			@current-change="handleCurrentChange" />

		<!-- 评论输入框 -->
		<div class="mt-4 bg-white py-4 rounded-lg">
			<el-input
				v-model="form.content"
				type="textarea"
				:rows="3"
				:placeholder="replyTo ? `回复 ${replyTo.user?.name}` : '发表评论'" />
			<div class="flex justify-between mt-3">
				<div v-if="replyTo" class="flex items-center text-gray-500">
					回复给: {{ replyTo.user?.name }}
					<el-button class="ml-2" link @click="cancelReply">取消回复</el-button>
				</div>
				<el-button type="primary" @click="submitComment">发表评论</el-button>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, computed, onMounted, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { getCommentPage, saveComment } from '@/api/sys/comment/index.js'

const props = defineProps({
	bizId: {
		type: [String, Number],
		required: true
	},
	bizType: {
		type: Number,
		required: true
	}
})

const loading = ref(true)
const queryParams = reactive({
	pageNo: 1,
	pageSize: 20,
	bizId: props.bizId,
	bizType: props.bizType
})

const commentList = ref([])
const total = ref(0)
const form = ref({
	bizId: props.bizId,
	bizType: props.bizType,
	content: '',
	replyId: null
})
const replyTo = ref(null)

const getPage = () => {
	loading.value = true
	getCommentPage(queryParams).then(res => {
		commentList.value = res.data?.records || []
		total.value = res.data?.total || 0
		loading.value = false
	})
}

// 处理回复
const handleReply = (comment, parent = null) => {
	replyTo.value = {
		...comment,
		parentId: parent?.id
	}
	form.value.replyId = comment.id
}

// 取消回复
const cancelReply = () => {
	replyTo.value = null
	form.value.replyId = null
}

// 获取被回复的用户信息
const getReplyUser = (children, userId) => {
	return children.find(item => item.userId === userId)?.user
}

// 提交评论
const submitComment = async () => {
	if (!form.value.content.trim()) {
		ElMessage.warning('请输入评论内容')
		return
	}

	const res = await saveComment(form.value)
	if (res.code === 200) {
		ElMessage.success('评论成功')
		form.value.content = ''
		replyTo.value = null
		form.value.replyId = null
		getPage() // 刷新评论列表
	} else {
		ElMessage.error(res.msg || '评论失败')
	}
}

const getUrl = computed(() => path => {
	if (!path) return ''
	return import.meta.env.VITE_APP_BASE_API + path
})

const handleSizeChange = val => {
	queryParams.pageSize = val
	getPage()
}

const handleCurrentChange = val => {
	queryParams.pageNo = val
	getPage()
}

onMounted(() => {
	getPage()
})
</script>
