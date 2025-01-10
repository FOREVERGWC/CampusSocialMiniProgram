<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model='queryParams.followerId' clearable filterable placeholder='请选择关注者'>
                <el-option v-for='item in followerList' :key='item.id' :label='item.name' :value='item.id'/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model='queryParams.followedId' clearable filterable placeholder='请选择被关注者'>
                <el-option v-for='item in followedList' :key='item.id' :label='item.name' :value='item.id'/>
              </el-select>
            </el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button icon="Search" plain type="info" @click="getPage">查询</el-button>
            </el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button icon="Refresh" plain type="warning" @click="resetQuery">
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
              <el-button icon="Plus" plain type="primary" @click="showAdd">新增</el-button>
            </el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button :disabled="single" icon="Edit" plain type="success" @click="showEdit">
                修改
              </el-button>
            </el-col>
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
              <vue3-json-excel :fields="followFields" :json-data="followList" name='关注列表.xls'>
                <el-button icon="Download" plain>导出</el-button>
              </vue3-json-excel>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <el-table v-loading="loading" :cell-style="{ textAlign: 'center' }" :data="followList"
                :header-cell-style="{ textAlign: 'center' }" stripe
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="序号" type="index" width="70"/>
        <el-table-column label="关注者ID" prop="followerId"/>
        <el-table-column label="被关注者ID" prop="followedId"/>
        <el-table-column label="操作" width="180">
          <template v-slot="{ row }">
            <el-button icon="Edit" plain type="primary" @click="showEdit(row)">编辑</el-button>
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

    <el-dialog v-model="form.visible" :title="form.title" destroy-on-close width="40%">
      <el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
        <el-form-item label="关注者" prop="followerId">
          <el-select v-model='form.data.followerId' clearable filterable placeholder='请选择关注者'>
            <el-option v-for='item in followerList' :key='item.id' :label='item.name' :value='item.id'/>
          </el-select>
        </el-form-item>
        <el-form-item label="被关注者" prop="followedId">
          <el-select v-model='form.data.followedId' clearable filterable placeholder='请选择被关注者'>
            <el-option v-for='item in followedList' :key='item.id' :label='item.name' :value='item.id'/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.data.remark" :rows="5" autocomplete="new" type="textarea"/>
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
import {computed, nextTick, onMounted, reactive, ref, toRaw} from 'vue'
import {getFollowOne, getFollowPage, removeFollowBatchByIds, saveFollow} from '@/api/follow.js'
import {getUserList} from '@/api/user.js'
import {ElMessage} from "element-plus"

const loading = ref(true)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 20,
  followerId: null,
  followedId: null
})
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const followerList = ref([])
const followedList = ref([])
const followList = ref([])
const total = ref(0)
const followFields = {
  '序号': {
    field: 'id',
    callback: (id) => {
      const index = followList.value.findIndex(item => item.id === id)
      return index + 1
    }
  },
  '主键ID': 'id',
  '关注者ID': 'followerId',
  '被关注者ID': 'followedId'
}
const form = ref({
  visible: false,
  title: '',
  data: {}
})
const formRef = ref(null)
const rules = {
  followerId: [{required: true, message: '请输入关注者ID', trigger: 'blur'}],
  followedId: [{required: true, message: '请输入被关注者ID', trigger: 'blur'}]
}

const getPage = () => {
  loading.value = true
  getUserList({}).then(res => {
    followerList.value = res.data || []
  })
  getUserList({}).then(res => {
    followedList.value = res.data || []
  })
  getFollowPage(queryParams).then(res => {
    followList.value = res.data?.records || []
    total.value = res.data?.total || 0
    loading.value = false
  })
}

const showAdd = () => {
  nextTick(() => {
    if (!formRef.value) return
    formRef.value.resetFields()
  })
  form.value = {
    visible: true,
    title: '添加关注',
    data: {
      followerId: null,
      followedId: null,
      remark: ''
    }
  }
}

const showEdit = (row) => {
  nextTick(() => {
    if (!formRef.value) return
    formRef.value.resetFields()
  })
  const params = {id: row.id || ids.value[0]}
  getFollowOne(params).then(res => {
    if (res.code !== 200) return
    form.value = {
      visible: true,
      title: '编辑关注',
      data: {
        ...res.data
      }
    }
  })
}

const handleSave = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    saveFollow(form.value.data).then(res => {
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success('保存成功！')
      form.value.visible = false
    }).finally(() => {
      getPage()
    })
  })
}

const handleDelete = (id) => {
  const params = id || ids.value
  removeFollowBatchByIds(params).then(res => {
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

const resetQuery = () => {
  queryParams.pageNo = 1
  queryParams.pageSize = 20
  queryParams.followerId = null
  queryParams.followedId = null
  getPage()
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

const getUrl = computed(() => (path) => {
  return import.meta.env.VITE_APP_BASE_API + path
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
