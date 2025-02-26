<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-card>
					<el-row :gutter="20">
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.hashCode" clearable placeholder="请输入散列值" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.bizId" clearable filterable placeholder="请选择业务">
								<el-option v-for="item in bizList" :key="item.id" :label="item.name" :value="item.id" />
							</el-select>
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.bizType" clearable placeholder="请输入业务类型" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.bucketName" clearable placeholder="请输入桶名" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.filePath" clearable placeholder="请输入文件路径" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.fileName" clearable placeholder="请输入文件名称" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.fileSize" clearable placeholder="请输入文件大小" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.chunkTotal" clearable placeholder="请输入分片数量" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.chunkSize" clearable placeholder="请输入分片大小" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.status" clearable filterable placeholder="请选择是否上传状态">
								<el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
							</el-select>
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
				<el-table-column label="散列值" prop="hashCode" />
				<el-table-column label="业务ID" prop="bizId" />
				<el-table-column label="业务类型" prop="bizType" />
				<el-table-column label="桶名" prop="bucketName" />
				<el-table-column label="文件路径" prop="filePath" />
				<el-table-column label="文件名称" prop="fileName" />
				<el-table-column label="文件大小" prop="fileSize" />
				<el-table-column label="分片数量" prop="chunkTotal" />
				<el-table-column label="分片大小" prop="chunkSize" />
				<el-table-column label="上传状态" prop="status" />
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
				<el-form-item label="散列值" prop="hashCode">
					<el-input v-model="form.data.hashCode" autocomplete="new" />
				</el-form-item>
				<el-form-item label="业务" prop="bizId">
					<el-select v-model="form.data.bizId" clearable filterable placeholder="请选择业务">
						<el-option v-for="item in bizList" :key="item.id" :label="item.name" :value="item.id" />
					</el-select>
				</el-form-item>
				<el-form-item label="业务类型" prop="bizType">
					<el-input v-model="form.data.bizType" autocomplete="new" />
				</el-form-item>
				<el-form-item label="桶名" prop="bucketName">
					<el-input v-model="form.data.bucketName" autocomplete="new" />
				</el-form-item>
				<el-form-item label="文件路径" prop="filePath">
					<el-input v-model="form.data.filePath" autocomplete="new" />
				</el-form-item>
				<el-form-item label="文件名称" prop="fileName">
					<el-input v-model="form.data.fileName" autocomplete="new" />
				</el-form-item>
				<el-form-item label="文件大小" prop="fileSize">
					<el-input v-model="form.data.fileSize" autocomplete="new" />
				</el-form-item>
				<el-form-item label="分片数量" prop="chunkTotal">
					<el-input v-model="form.data.chunkTotal" autocomplete="new" />
				</el-form-item>
				<el-form-item label="分片大小" prop="chunkSize">
					<el-input v-model="form.data.chunkSize" autocomplete="new" />
				</el-form-item>
				<el-form-item label="上传状态" prop="status">
					<el-select v-model="form.data.status" clearable filterable placeholder="请选择是否上传状态">
						<el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
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
import { getAttachmentOne, getAttachmentPage, removeAttachmentBatchByIds, saveAttachment } from '@/api/sys/attachment'
import { ElMessage } from 'element-plus'
import { useTable } from '@/hooks/useTable/index.js'
import { downloadFile } from '@/utils/common.js'

const queryParams = reactive({
	hashCode: '',
	bizId: null,
	bizType: null,
	bucketName: '',
	filePath: '',
	fileName: '',
	fileSize: null,
	chunkTotal: null,
	chunkSize: null,
	status: null
})
const bizList = ref([])
const statusList = [
	{ label: '是', value: true },
	{ label: '否', value: false }
]
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getAttachmentPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), {
		immediate: false
	})
const form = ref({
	visible: false,
	title: '',
	data: {}
})
const formRef = ref(null)
const rules = {
	hashCode: [{ required: true, message: '请输入散列值', trigger: 'blur' }],
	bizId: [{ required: true, message: '请输入业务ID', trigger: 'blur' }],
	bizType: [{ required: true, message: '请输入业务类型', trigger: 'blur' }],
	bucketName: [{ required: true, message: '请输入桶名', trigger: 'blur' }],
	filePath: [{ required: true, message: '请输入文件路径', trigger: 'blur' }],
	fileName: [{ required: true, message: '请输入文件名称', trigger: 'blur' }],
	fileSize: [{ required: true, message: '请输入文件大小', trigger: 'blur' }],
	chunkTotal: [{ required: true, message: '请输入分片数量', trigger: 'blur' }],
	chunkSize: [{ required: true, message: '请输入分片大小', trigger: 'blur' }],
	status: [{ required: true, message: '请选择上传状态', trigger: 'blur' }]
}

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加附件',
		data: {
			hashCode: '',
			bizId: null,
			bizType: null,
			bucketName: '',
			filePath: '',
			fileName: '',
			fileSize: null,
			chunkTotal: null,
			chunkSize: null,
			status: null,
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
	getAttachmentOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑附件',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveAttachment(form.value.data)
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
	queryParams.hashCode = ''
	queryParams.bizId = null
	queryParams.bizType = null
	queryParams.bucketName = ''
	queryParams.filePath = ''
	queryParams.fileName = ''
	queryParams.fileSize = null
	queryParams.chunkTotal = null
	queryParams.chunkSize = null
	queryParams.status = null
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeAttachmentBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/attachment/export', queryParams)
}

onMounted(() => {
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
