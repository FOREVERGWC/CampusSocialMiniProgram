<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-card>
					<el-row :gutter="20">
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.rateId" clearable filterable placeholder="请选择评分">
								<el-option v-for="item in rateList" :key="item.id" :label="item.title" :value="item.id" />
							</el-select>
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.rateItemId" clearable filterable placeholder="请选择评分项">
								<el-option v-for="item in rateItemList" :key="item.id" :label="item.title" :value="item.id" />
							</el-select>
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.userId" clearable filterable placeholder="请选择用户">
								<el-option v-for="item in userList" :key="item.id" :label="item.name" :value="item.id" />
							</el-select>
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.score" clearable placeholder="请输入分数" />
						</el-col>
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-button icon="Search" plain type="info" @click="handleSearch">查询</el-button>
						</el-col>
						<el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
							<el-button icon="Refresh" plain type="warning" @click="handleReset">重置</el-button>
						</el-col>
					</el-row>
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
				<el-table-column label="评分" prop="rate.title" />
				<el-table-column label="评分项" prop="rateItem.title" />
				<el-table-column label="用户" prop="user.username" />
				<el-table-column label="分数" prop="score">
					<template v-slot="{ row }">
						<el-rate
							v-model="row.score"
							:colors="colors"
							:max="10"
							:low-threshold="3"
							:high-threshold="7"
							allow-half
							disabled />
					</template>
				</el-table-column>
				<el-table-column label="备注" prop="remark" />
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
				<el-form-item label="评分" prop="rateId">
					<el-select v-model="form.data.rateId" clearable filterable placeholder="请选择评分">
						<el-option v-for="item in rateList" :key="item.id" :label="item.title" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="评分项" prop="rateItemId">
					<el-select v-model="form.data.rateItemId" clearable filterable placeholder="请选择评分项">
						<el-option v-for="item in rateItemList" :key="item.id" :label="item.title" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="用户" prop="userId">
					<el-select v-model="form.data.userId" clearable filterable placeholder="请选择用户">
						<el-option v-for="item in userList" :key="item.id" :label="item.name" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="分数" prop="score">
					<el-rate
						v-model="form.data.score"
						:colors="colors"
						:max="10"
						:low-threshold="3"
						:high-threshold="7"
						allow-half />
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
	getRateRecordOne,
	getRateRecordPage,
	removeRateRecordBatchByIds,
	saveRateRecord
} from '@/api/biz/rate/record.js'
import { getRateList } from '@/api/biz/rate/index.js'
import { getRateItemList } from '@/api/biz/rate/item.js'
import { getUserList } from '@/api/user.js'
import { ElMessage } from 'element-plus'
import { downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'

const queryParams = reactive({
	rateId: null,
	rateItemId: null,
	userId: null,
	score: null
})
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getRateRecordPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), {
		immediate: false
	})
const rateList = ref([])
const rateItemList = ref([])
const userList = ref([])
const form = ref({
	visible: false,
	title: '',
	data: {}
})
const formRef = ref(null)
const rules = {
	rateId: [{ required: true, message: '请输入评分ID', trigger: 'blur' }],
	rateItemId: [{ required: true, message: '请输入评分项ID', trigger: 'blur' }],
	userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
	score: [{ required: true, message: '请输入分数', trigger: 'blur' }]
}

const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900'])

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加评分记录',
		data: {
			rateId: null,
			rateItemId: null,
			userId: null,
			score: null,
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
	getRateRecordOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑评分记录',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveRateRecord(form.value.data)
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
	queryParams.rateId = null
	queryParams.rateItemId = null
	queryParams.userId = null
	queryParams.score = null
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeRateRecordBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/rate/record/export', queryParams)
}

onMounted(() => {
	getRateList({}).then(res => {
		rateList.value = res.data || []
	})
	getRateItemList({}).then(res => {
		rateItemList.value = res.data || []
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
