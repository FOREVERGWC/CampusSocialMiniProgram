<template>
	<div>
		<el-card>
			<component :is="SearchForm" v-model="queryParams" :option="option" @search="handleSearch" @reset="handleReset" />
		</el-card>

		<el-card>
			<el-table
				v-loading="loading"
				:cell-style="{ textAlign: 'center' }"
				:data="records"
				:header-cell-style="{ textAlign: 'center' }"
				stripe
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" />
				<el-table-column type="expand">
					<template v-slot="{ row }">
						<el-descriptions :column="3" border>
							<el-descriptions-item>
								<template #label>
									<span>
										<el-icon>
											<male />
										</el-icon>
										姓名
									</span>
								</template>
								<span>{{ row.name }}</span>
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<span>
										<el-icon>
											<male />
										</el-icon>
										性别
									</span>
								</template>
								<span>
									<DictItem code="gender" :value="row.gender" />
									{{ row.genderText }}
								</span>
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<span>
										<el-icon>
											<male />
										</el-icon>
										生日
									</span>
								</template>
								<span>{{ row.birthday }}</span>
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<span>
										<el-icon>
											<key />
										</el-icon>
										微信小程序开放ID
									</span>
								</template>
								<span>{{ row.openId }}</span>
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<span>
										<el-icon>
											<wallet />
										</el-icon>
										余额
									</span>
								</template>
								<span>{{ row.balance }}</span>
							</el-descriptions-item>
						</el-descriptions>
					</template>
				</el-table-column>
				<el-table-column label="序号" type="index" width="70" />
				<el-table-column label="用户名" prop="username" />
				<el-table-column label="昵称" prop="nickname" />
				<el-table-column label="头像">
					<template v-slot="{ row }">
						<div style="display: flex; align-items: center; justify-content: center">
							<el-image
								v-if="row.avatar"
								:preview-src-list="[getUrl(row.avatar)]"
								:src="getUrl(row.avatar)"
								preview-teleported>
								<template #error>
									<img alt="" src="@/assets/imgs/profile.png" />
								</template>
							</el-image>
						</div>
					</template>
				</el-table-column>
				<el-table-column label="状态" width="100">
					<template v-slot="{ row }">
						<el-switch v-model="row.status" active-value="1" inactive-value="0" @change="() => handleStatus(row.id)" />
					</template>
				</el-table-column>
				<el-table-column label="角色">
					<template v-slot="{ row }">
						<el-tag type="info" v-for="item in row.roleList">
							{{ item.name }}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="手机" prop="phone" />
				<el-table-column label="邮箱" prop="email" />
				<el-table-column label="最后登录IP" prop="loginIp" />
				<el-table-column label="最后登录时间" prop="loginTime" width="150" />
				<el-table-column label="注册时间" prop="createTime" width="150" />
				<el-table-column label="操作" width="380">
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
	</div>
</template>

<script setup>
import SearchForm from '@/components/SearchForm/index.js'
import { computed, onMounted, reactive, ref } from 'vue'
import useRoleStore from '@/store/modules/role.js'
import { useTable } from '@/hooks/useTable/index.js'
import { addDataRange } from '@/utils/common.js'
import { getUserPage } from '@/api/sys/user/index.js'

const roleStore = useRoleStore()
const roleList = ref(roleStore.roleList)

const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'input',
			label: '用户名',
			field: 'username'
		},
		{
			type: 'input',
			label: '姓名',
			field: 'name'
		},
		{
			type: 'date-picker',
			label: '生日',
			field: 'birthday'
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: [
				{ label: '禁用', value: '0' },
				{ label: '正常', value: '1' }
			]
		},
		{
			type: 'select',
			label: '角色',
			field: 'role',
			options: roleList,
			props: {
				props: {
					label: 'name',
					value: 'id'
				}
			}
		},
		{
			type: 'input',
			label: '手机',
			field: 'phone'
		},
		{
			type: 'input',
			label: '邮箱',
			field: 'email'
		},
		{
			type: 'input',
			label: '最后登录IP',
			field: 'loginIp'
		}
	]
}

const loginTimeRange = ref([])
const createTimeRange = ref([])
const queryParams = reactive({
	username: '',
	name: '',
	birthday: '',
	status: '',
	role: '',
	phone: '',
	email: '',
	loginIp: ''
})

const handleSearch = () => {
	addDataRange(queryParams, loginTimeRange.value, 'LoginTime')
	addDataRange(queryParams, createTimeRange.value, 'CreateTime')
	getRecords()
}

const handleReset = () => {
	loginTimeRange.value = []
	createTimeRange.value = []
	option.columns.forEach(item => {
		queryParams[item.field] = null
	})
	getRecords()
}

const { loading, records, getRecords, pagination, selectedKeys, single, multiple, handleSelectionChange, onDelete } =
	useTable(
		page => {
			addDataRange(queryParams, loginTimeRange.value, 'LoginTime')
			addDataRange(queryParams, createTimeRange.value, 'CreateTime')
			return getUserPage({ ...queryParams, pageNo: page.pageNo, pageSize: page.pageSize })
		},
		{ immediate: false }
	)

const getUrl = computed(() => path => {
	return import.meta.env.VITE_APP_BASE_API + path
})

onMounted(() => {
	getRecords()
})
</script>

<style scoped lang="scss">
.el-image,
.el-image img {
	width: 40px;
	height: 40px;
	border-radius: 50%;
}
</style>
