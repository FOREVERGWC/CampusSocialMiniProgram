<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-card>
					<el-row :gutter="20">
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.os" clearable placeholder="请输入操作系统" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.browser" clearable placeholder="请输入浏览器" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.ip" clearable placeholder="请输入IP" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.location" clearable placeholder="请输入IP属地" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-select v-model="queryParams.status" clearable filterable placeholder="请选择是否状态">
								<el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
							</el-select>
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-input v-model="queryParams.msg" clearable placeholder="请输入消息" />
						</el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-date-picker
								v-model="createTimeRange"
								clearable
								type="datetimerange"
								start-placeholder="登录开始时间"
								end-placeholder="登录结束时间"
								value-format="YYYY-MM-DD HH:mm:ss"
								unlink-panels />
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
				<el-table-column label="用户名" prop="createBy" />
				<el-table-column label="登录类型" prop="loginType" />
				<el-table-column label="操作系统" prop="os" />
				<el-table-column label="浏览器" prop="browser" />
				<el-table-column label="IP" prop="ip" />
				<el-table-column label="IP属地" prop="location" />
				<el-table-column label="状态" prop="status">
					<template v-slot="{ row }">
						<el-tag :type="row.status ? 'success' : 'danger'" plain>{{ row.status ? '成功' : '失败' }}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="消息" prop="msg" />
				<el-table-column label="登录时间" prop="createTime" />
				<el-table-column label="操作" width="180">
					<template v-slot="{ row }">
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
	</div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getLogLoginPage, removeLogLoginBatchByIds } from '@/api/sys/log/login/index.js'
import { getUserList } from '@/api/sys/user/index.js'
import { addDataRange, downloadFile } from '@/utils/common.js'
import { useTable } from '@/hooks/useTable/index.js'

const createTimeRange = ref([])
const queryParams = reactive({
	os: '',
	browser: '',
	ip: '',
	location: '',
	status: null,
	msg: ''
})
const { loading, records, getRecords, pagination, selectedKeys, multiple, handleSelectionChange, onDelete } = useTable(
	page => getLogLoginPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }),
	{ immediate: false }
)
const userList = ref([])
const statusList = [
	{ label: '是', value: true },
	{ label: '否', value: false }
]

const handleSearch = () => {
	addDataRange(queryParams, createTimeRange.value, 'CreateTime')
	getRecords()
}

const handleReset = () => {
	queryParams.title = ''
	queryParams.content = ''
	queryParams.location = ''
	queryParams.os = ''
	queryParams.browser = ''
	queryParams.ip = ''
	queryParams.location = ''
	queryParams.status = null
	queryParams.msg = ''
	createTimeRange.value = []
	getRecords()
}

const handleDelete = id => {
	const params = id || selectedKeys.value
	onDelete(() => removeLogLoginBatchByIds(params), {})
}

const handleExport = () => {
	downloadFile('/log/login/export', queryParams)
}

onMounted(() => {
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
