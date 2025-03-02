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
				<el-table-column label="名称" prop="name" />
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
				<el-form-item label="名称" prop="name">
					<el-input v-model="form.data.name" autocomplete="new" />
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
import { getSchoolOne, getSchoolPage, removeSchoolBatchByIds, saveSchool } from '@/api/biz/school/index.js'
import { ElMessage } from 'element-plus'
import { downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'
import SearchForm from '@/components/SearchForm/index.js'
import { option } from './index.js'

const queryParams = reactive({
	name: ''
})
const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(page => getSchoolPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }), {
		immediate: false
	})
const form = ref({
	visible: false,
	title: '',
	data: {}
})
const formRef = ref(null)
const rules = {
	name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
}

const showAdd = () => {
	nextTick(() => {
		if (!formRef.value) return
		formRef.value.resetFields()
	})
	form.value = {
		visible: true,
		title: '添加学校',
		data: {
			name: '',
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
	getSchoolOne(params).then(res => {
		if (res.code !== 200) return
		form.value = {
			visible: true,
			title: '编辑学校',
			data: {
				...res.data
			}
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveSchool(form.value.data)
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
	queryParams.name = ''
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeSchoolBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/school/export', queryParams)
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
