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
				<el-table-column label="内容" prop="content" />
				<el-table-column label="主题" prop="subject.name" />
				<el-table-column label="活动人数" prop="num" />
				<el-table-column label="截止时间" prop="endTime" />
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
					<el-input v-model="form.data.content" autocomplete="new" />
				</el-form-item>
				<el-form-item label="主题" prop="subjectId">
					<el-select v-model="form.data.subjectId" clearable filterable placeholder="请选择主题">
						<el-option v-for="item in subjectList" :key="item.id" :label="item.name" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="活动人数" prop="num">
					<el-input v-model="form.data.num" autocomplete="new" />
				</el-form-item>
				<el-form-item label="截止时间" prop="endTime">
					<el-date-picker
						v-model="form.data.endTime"
						placeholder="请选择截止时间"
						type="date"
						value-format="YYYY-MM-DD HH:mm:ss" />
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
import { getPartnerOne, getPartnerPage, removePartnerBatchByIds, savePartner } from '@/api/biz/partner/index.js'
import { getUserList } from '@/api/sys/user/index.js'
import { getPartnerSubjectList } from '@/api/biz/partner/subject'
import { ElMessage } from 'element-plus'
import { addDataRange, downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'
import SearchForm from '@/components/SearchForm/index.js'
import { option } from './index.js'

const queryParams = reactive({
	userId: null,
	title: '',
	content: '',
	subjectId: null,
	num: null,
	endTime: '',
	endTimeRange: [],
	params: {}
})
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getPartnerPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), {
		immediate: false
	})
const userList = ref([])
const subjectList = ref([])
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
	subjectId: [{ required: true, message: '请输入主题ID', trigger: 'blur' }],
	num: [{ required: true, message: '请输入活动人数', trigger: 'blur' }],
	endTime: [{ required: true, message: '请选择截止时间', trigger: 'change' }]
}

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加组局',
		data: {
			userId: null,
			title: '',
			content: '',
			subjectId: null,
			num: null,
			endTime: '',
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
	getPartnerOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑组局',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		savePartner(form.value.data)
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
	addDataRange(queryParams, queryParams.endTimeRange, 'EndTime')
	getRecords()
}

const handleReset = () => {
	queryParams.userId = null
	queryParams.title = ''
	queryParams.content = ''
	queryParams.subjectId = null
	queryParams.num = null
	queryParams.endTimeRange = []
	queryParams.params = {}
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removePartnerBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/partner/export', queryParams)
}

onMounted(() => {
	getUserList({}).then(res => {
		userList.value = res.data || []
	})
	getPartnerSubjectList({}).then(res => {
		subjectList.value = res.data || []
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
