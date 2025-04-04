<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-card>
					<component
						:is="SearchForm"
						v-model="queryParams"
						:option="option"
						@search="handleSearch"
						@reset="handleReset" />
				</el-card>
			</el-col>
		</el-row>

		<el-row>
			<el-col :span="24">
				<el-card>
					<el-row :gutter="20">
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-button icon="Plus" plain type="primary" @click="showAdd">新增</el-button>
						</el-col>
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-button :disabled="single" icon="Edit" plain type="success" @click="showEdit">修改</el-button>
						</el-col>
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-popconfirm title="确认删除选中数据吗？" @confirm="handleDelete(null)">
								<template #reference>
									<el-button :disabled="multiple" icon="Delete" plain type="danger">删除</el-button>
								</template>
							</el-popconfirm>
						</el-col>
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-button icon="Download" plain @click="handleExport">导出</el-button>
						</el-col>
					</el-row>
				</el-card>
			</el-col>
		</el-row>

		<el-card>
			<el-table
				v-loading="loading"
				:cell-style="{ textAlign: 'center' }"
				:data="records"
				:header-cell-style="{ textAlign: 'center' }"
				stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" />
				<el-table-column label="序号" type="index" width="70" />
				<el-table-column label="业务ID" prop="bizId" />
				<el-table-column label="业务类型" prop="bizType">
					<template v-slot="{ row }">
						<span v-if="row.bizType === 8">笔记</span>
						<span v-else-if="row.bizType === 9">评分</span>
						<span v-else-if="row.bizType === 10">评分项</span>
						<span v-else-if="row.bizType === 11">组局</span>
						<span v-else-if="row.bizType === 12">活动</span>
					</template>
				</el-table-column>
				<el-table-column label="内容" prop="content" />
				<el-table-column label="回复ID" prop="replyId" />
				<el-table-column label="用户" prop="user.username" />
				<el-table-column label="操作" width="180">
					<template v-slot="{ row }">
						<el-button icon="Edit" plain type="primary" @click="showEdit(row)">编辑</el-button>
						<el-popconfirm title="确认删除该行吗？" @confirm="handleDelete(row.id)">
							<template #reference>
								<el-button icon="Delete" plain style="margin-left: 10px" type="danger">删除</el-button>
							</template>
						</el-popconfirm>
					</template>
				</el-table-column>
			</el-table>

			<el-pagination
				:current-page="pagination.current"
				:page-size="pagination.pageSize"
				:page-sizes="[20, 30, 40, 50]"
				:total="pagination.total"
				layout="total, sizes, prev, pager, next, jumper"
				@current-change="pagination.onCurrentChange"
				@size-change="pagination.onPageSizeChange" />
		</el-card>

		<el-dialog v-model="form.visible" :title="form.title" destroy-on-close width="40%">
			<el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
				<el-form-item label="业务ID" prop="bizId">
					<el-input v-model="form.data.bizId" autocomplete="new" placeholder="请选择业务ID" />
				</el-form-item>
				<el-form-item label="业务类型" prop="bizType">
					<el-select v-model="form.data.bizType" clearable filterable placeholder="请选择业务类型">
						<el-option v-for="item in bizTypeList" :key="item.value" :label="item.label" :value="item.value" />
					</el-select>
				</el-form-item>
				<el-form-item label="内容" prop="content">
					<el-input v-model="form.data.content" autocomplete="new" />
				</el-form-item>
				<el-form-item label="回复" prop="replyId">
					<el-select v-model="form.data.replyId" clearable filterable placeholder="请选择回复">
						<el-option v-for="item in replyList" :key="item.id" :label="item.name" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="用户" prop="userId">
					<el-select v-model="form.data.userId" clearable filterable placeholder="请选择用户">
						<el-option v-for="item in userList" :key="item.id" :label="item.username" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="备注" prop="remark">
					<el-input v-model="form.data.remark" :rows="5" autocomplete="new" type="textarea" />
				</el-form-item>
			</el-form>
			<template #footer>
				<el-button @click="form.visible = false">取 消</el-button>
				<el-button type="primary" @click="handleSave">确 定</el-button>
			</template>
		</el-dialog>
	</div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import {
	getCommentList,
	getCommentOne,
	getCommentPage,
	removeCommentBatchByIds,
	saveComment
} from '@/api/sys/comment/index.js'
import { getUserList } from '@/api/sys/user/index.js'
import { ElMessage } from 'element-plus'
import { downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'
import SearchForm from '@/components/SearchForm/index.js'
import { bizTypeList, option } from './index.js'

const queryParams = reactive({
	bizId: null,
	bizType: '',
	content: '',
	replyId: null,
	userId: null,
	os: '',
	ip: '',
	location: ''
})
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getCommentPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), {
		immediate: false
	})
const replyList = ref([])
const userList = ref([])
const form = ref({
	visible: false,
	title: '',
	data: {}
})
const formRef = ref(null)
const rules = {
	bizId: [{ required: true, message: '请输入业务ID', trigger: 'blur' }],
	bizType: [{ required: true, message: '请选择业务类型', trigger: 'change' }],
	content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
	// replyId: [{ required: true, message: '请输入回复ID', trigger: 'blur' }],
	userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
	os: [{ required: true, message: '请输入操作系统', trigger: 'blur' }],
	ip: [{ required: true, message: '请输入IP', trigger: 'blur' }],
	location: [{ required: true, message: '请输入IP属地', trigger: 'blur' }]
}

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加评论',
		data: {
			bizId: null,
			bizType: '',
			content: '',
			replyId: null,
			userId: null,
			os: '',
			ip: '',
			location: '',
			remark: ''
		}
	}
}

const showEdit = row => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	const params = { id: row.id || selectedKeys.value[0] }
	getCommentOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑评论',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveComment(form.value.data)
			.then(res => {
				if (res.code !== 200) {
					ElMessage.error(res.msg)
					return
				}
				ElMessage.success('保存成功！')
				form.value.visible = false
			})
			.finally(() => {
				getRecords()
			})
	})
}

const handleSearch = () => {
	getRecords()
}

const handleReset = () => {
	queryParams.bizId = null
	queryParams.bizType = ''
	queryParams.content = ''
	queryParams.replyId = null
	queryParams.userId = null
	queryParams.os = ''
	queryParams.ip = ''
	queryParams.location = ''
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeCommentBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/comment/export', queryParams)
}

onMounted(() => {
	getCommentList({}).then(res => {
		replyList.value = res.data || []
	})
	getUserList({}).then(res => {
		userList.value = res.data || []
	})
	getRecords()
})
</script>

<style lang="scss" scoped>
.el-select,
:deep(.el-date-editor) {
	width: 100%;
}

.el-col {
	margin-bottom: 8px;

	.el-button {
		width: 100%;
	}
}

.el-pagination {
	margin-top: 8px;
}
</style>
