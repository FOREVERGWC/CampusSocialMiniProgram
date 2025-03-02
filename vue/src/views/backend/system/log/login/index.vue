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
import SearchForm from '@/components/SearchForm/index.js'
import { option } from './index.js'

const queryParams = reactive({
	os: '',
	browser: '',
	ip: '',
	location: '',
	status: null,
	msg: '',
	createTimeRange: []
})
const { loading, records, getRecords, pagination, selectedKeys, multiple, handleSelectionChange, onDelete } = useTable(
	page => getLogLoginPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize }),
	{ immediate: false }
)
const userList = ref([])

const handleSearch = () => {
	addDataRange(queryParams, queryParams.createTimeRange, 'CreateTime')
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
	queryParams.createTimeRange = []
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
