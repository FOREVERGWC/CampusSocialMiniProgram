<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.os" clearable placeholder="请输入操作系统"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.browser" clearable placeholder="请输入浏览器"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.ip" clearable placeholder="请输入IP"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.location" clearable placeholder="请输入IP属地"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.status" clearable filterable placeholder="请选择是否状态">
                <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.msg" clearable placeholder="请输入消息"/>
            </el-col>
						<el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
							<el-date-picker v-model="createTimeRange" clearable
															type="datetimerange"
															start-placeholder="登录开始时间" end-placeholder="登录结束时间"
															value-format="YYYY-MM-DD HH:mm:ss"
															unlink-panels
							/>
						</el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button icon="Search" plain type="info" @click="getPage">查询</el-button>
            </el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button icon="Refresh" plain type="warning" @click="handleReset">
                重置
              </el-button>
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
                  <el-button :disabled="multiple" icon="Delete" plain type="danger">
                    删除
                  </el-button>
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
      <el-table v-loading="loading" :cell-style="{ textAlign: 'center' }" :data="logLoginList"
                :header-cell-style="{ textAlign: 'center' }" stripe
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="序号" type="index" width="70"/>
        <el-table-column label="用户名" prop="createBy"/>
        <el-table-column label="登录类型" prop="loginType"/>
        <el-table-column label="操作系统" prop="os"/>
        <el-table-column label="浏览器" prop="browser"/>
        <el-table-column label="IP" prop="ip"/>
        <el-table-column label="IP属地" prop="location"/>
        <el-table-column label="状态" prop="status">
          <template v-slot="{ row }">
            <el-tag :type="row.status ? 'success' : 'danger'" plain>{{ row.status ? '成功' : '失败' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="消息" prop="msg"/>
        <el-table-column label="登录时间" prop="createTime"/>
        <el-table-column label="操作" width="180">
          <template v-slot="{ row }">
            <el-popconfirm title="确认删除该行吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button icon="Delete" plain style="margin-left: 10px" type="danger">
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
          :current-page="queryParams.pageNo"
          :page-size="queryParams.pageSize"
          :page-sizes="[20, 30, 40, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </el-card>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, toRaw} from 'vue'
import {getLogLoginPage, removeLogLoginBatchByIds} from '@/api/logLogin.js'
import {getUserList} from '@/api/user.js'
import {ElMessage} from "element-plus"
import { addDataRange, downloadFile } from '@/utils/common.js'

const loading = ref(true)
const createTimeRange = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 20,
  os: '',
  browser: '',
  ip: '',
  location: '',
  status: null,
  msg: ''
})
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const userList = ref([])
const logLoginList = ref([])
const total = ref(0)
const statusList = [
  {label: '是', value: true},
  {label: '否', value: false}
]

const getPage = () => {
  loading.value = true
	addDataRange(queryParams, createTimeRange.value, 'CreateTime')
  getUserList({}).then(res => {
    userList.value = res.data || []
  })
  getLogLoginPage(queryParams).then(res => {
    logLoginList.value = res.data?.records || []
    total.value = res.data?.total || 0
    loading.value = false
  })
}

const handleDelete = (id) => {
  const params = id || ids.value
  removeLogLoginBatchByIds(params).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success('删除成功！')
  }).finally(() => {
    getPage()
  })
}

const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => toRaw(item).id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

const handleReset = () => {
	createTimeRange.value = []
  queryParams.pageNo = 1
  queryParams.pageSize = 20
  queryParams.os = ''
  queryParams.browser = ''
  queryParams.ip = ''
  queryParams.location = ''
  queryParams.status = null
  queryParams.msg = ''
  getPage()
}

const handleExport = () => {
  downloadFile('/log/login/export', queryParams)
}

const handleSizeChange = (val) => {
  queryParams.pageSize = val
  getPage()
}

const handleCurrentChange = (val) => {
  queryParams.pageNo = val
  getPage()
}

onMounted(() => {
  getPage()
})
</script>

<style lang="scss" scoped>
.el-select, :deep(.el-date-editor) {
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
