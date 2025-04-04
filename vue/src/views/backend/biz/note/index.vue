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
				<el-table-column label="用户" prop="user.username" />
				<el-table-column label="标题" prop="title" />
				<el-table-column label="类别" prop="category.name" />
				<el-table-column label="置顶">
					<template v-slot="{ row }">
						<el-switch v-model="row.top" @change="handleTop(row.id)" />
					</template>
				</el-table-column>
				<el-table-column label="可见性" prop="visible" />
				<el-table-column label="允许评论">
					<template v-slot="{ row }">
						<el-switch v-model="row.commentable" @change="handleComment(row.id)" />
					</template>
				</el-table-column>
				<el-table-column label="状态" prop="status" />
				<el-table-column label="浏览量" prop="count.view" />
				<el-table-column label="点赞量" prop="count.like.num" />
				<el-table-column label="点踩量" prop="count.dislike" />
				<el-table-column label="评论量" prop="count.comment" />
				<el-table-column label="收藏量" prop="count.favorite.num" />
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

		<el-dialog :title="form.title" v-model="form.visible" destroy-on-close width="40%">
			<el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
				<el-form-item label="用户" prop="userId">
					<el-select v-model="form.data.userId" clearable filterable placeholder="请选择用户">
						<el-option v-for="item in userList" :key="item.id" :label="item.username" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="标题" prop="title">
					<el-input v-model="form.data.title" autocomplete="new" />
				</el-form-item>
				<el-form-item label="内容" prop="content">
					<el-input v-model="form.data.content" :rows="10" autocomplete="new" type="textarea" />
				</el-form-item>
				<el-form-item label="类别" prop="categoryId">
					<el-select v-model="form.data.categoryId" clearable filterable placeholder="请选择类别">
						<el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="置顶" prop="top">
					<el-select v-model="form.data.top" clearable filterable placeholder="请选择是否置顶">
						<el-option v-for="item in topList" :key="item.value" :label="item.label" :value="item.value" />
					</el-select>
				</el-form-item>
				<el-form-item label="可见性" prop="visible">
					<el-select v-model="form.data.visible" clearable filterable placeholder="请选择可见性">
						<el-option v-for="item in visibleList" :key="item.value" :label="item.label" :value="item.value" />
					</el-select>
				</el-form-item>
				<el-form-item label="允许评论" prop="commentable">
					<el-select v-model="form.data.commentable" clearable filterable placeholder="请选择是否允许评论">
						<el-option v-for="item in commentableList" :key="item.value" :label="item.label" :value="item.value" />
					</el-select>
				</el-form-item>
				<el-form-item v-if="form.data.id" label="状态" prop="status">
					<el-select v-model="form.data.status" clearable filterable placeholder="请选择状态">
						<el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
					</el-select>
				</el-form-item>
				<el-form-item v-if="form.data.id" label="图片">
					<picture-upload v-model="form.data.attachmentList" :bizId="form.data.id" :bizType="8" />
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
	getNoteOne,
	getNotePage,
	handleCommentNote,
	handleTopNote,
	removeNoteBatchByIds,
	saveNote
} from '@/api/biz/note/index.js'
import { getUserList } from '@/api/sys/user/index.js'
import { getNoteCategoryList } from '@/api/biz/note/category.js'
import { ElMessage } from 'element-plus'
import { downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'
import SearchForm from '@/components/SearchForm/index.js'
import { commentableList, option, statusList, topList, visibleList } from './index.js'

const queryParams = reactive({
	userId: null,
	title: '',
	content: '',
	categoryId: null,
	top: null,
	visible: '',
	commentable: null,
	status: ''
})
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getNotePage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), { immediate: false })
const userList = ref([])
const categoryList = ref([])

const form = ref({
	visible: false,
	title: '',
	data: {}
})
const formRef = ref(null)
const rules = {
	userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
	title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
	content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
	categoryId: [{ required: true, message: '请选择类别', trigger: 'change' }],
	top: [{ required: true, message: '请选择是否置顶', trigger: 'change' }],
	visible: [{ required: true, message: '请选择可见性', trigger: 'change' }],
	commentable: [{ required: true, message: '请输入是否允许评论', trigger: 'change' }],
	status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加笔记',
		data: {
			userId: null,
			title: '',
			content: '',
			categoryId: null,
			top: null,
			visible: '',
			commentable: null,
			status: '',
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
	getNoteOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑笔记',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveNote(form.value.data)
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
	queryParams.userId = null
	queryParams.title = ''
	queryParams.content = ''
	queryParams.categoryId = null
	queryParams.top = null
	queryParams.visible = ''
	queryParams.commentable = null
	queryParams.status = ''
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeNoteBatchByIds(params), {})
}

const handleTop = id => {
	handleTopNote(id).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}

		ElMessage.success('操作成功！')
		getRecords()
	})
}

const handleComment = id => {
	handleCommentNote(id).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}

		ElMessage.success('操作成功！')
		getRecords()
	})
}

const handleExport = () => {
	downloadFile('/note/export', queryParams)
}

onMounted(() => {
	getUserList({}).then(res => {
		userList.value = res.data || []
	})
	getNoteCategoryList({}).then(res => {
		categoryList.value = res.data || []
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
