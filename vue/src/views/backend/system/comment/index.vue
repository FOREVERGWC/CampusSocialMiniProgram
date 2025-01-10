<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model='queryParams.bizId' clearable filterable placeholder='请选择业务'>
                <el-option v-for='item in bizList' :key='item.id' :label='item.name' :value='item.id'/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.bizKey" clearable placeholder="请输入业务类型"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.content" clearable placeholder="请输入内容"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model='queryParams.replyId' clearable filterable placeholder='请选择回复'>
                <el-option v-for='item in replyList' :key='item.id' :label='item.name' :value='item.id'/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model='queryParams.userId' clearable filterable placeholder='请选择用户'>
                <el-option v-for='item in userList' :key='item.id' :label='item.name' :value='item.id'/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.os" clearable placeholder="请输入操作系统"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.ip" clearable placeholder="请输入IP"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.location" clearable placeholder="请输入IP属地"/>
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
              <vue3-json-excel :fields="commentFields" :json-data="commentList" name='评论列表.xls'>
                <el-button icon="Download" plain>导出</el-button>
              </vue3-json-excel>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <el-table v-loading="loading" :cell-style="{ textAlign: 'center' }" :data="commentList"
                :header-cell-style="{ textAlign: 'center' }" stripe
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="序号" type="index" width="70"/>
        <el-table-column label="业务ID" prop="bizId"/>
        <el-table-column label="业务类型" prop="bizKey"/>
        <el-table-column label="内容" prop="content"/>
        <el-table-column label="回复ID" prop="replyId"/>
        <el-table-column label="用户ID" prop="userId"/>
        <el-table-column label="操作系统" prop="os"/>
        <el-table-column label="IP" prop="ip"/>
        <el-table-column label="IP属地" prop="location"/>
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
        <el-form-item label="业务" prop="bizId">
          <el-select v-model='form.data.bizId' clearable filterable placeholder='请选择业务'>
            <el-option v-for='item in bizList' :key='item.id' :label='item.name' :value='item.id'/>
          </el-select>
        </el-form-item>
        <el-form-item label="业务类型" prop="bizKey">
          <el-input v-model="form.data.bizKey" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.data.content" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="回复" prop="replyId">
          <el-select v-model='form.data.replyId' clearable filterable placeholder='请选择回复'>
            <el-option v-for='item in replyList' :key='item.id' :label='item.name' :value='item.id'/>
          </el-select>
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <el-select v-model='form.data.userId' clearable filterable placeholder='请选择用户'>
            <el-option v-for='item in userList' :key='item.id' :label='item.name' :value='item.id'/>
          </el-select>
        </el-form-item>
        <el-form-item label="操作系统" prop="os">
          <el-input v-model="form.data.os" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="IP" prop="ip">
          <el-input v-model="form.data.ip" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="IP属地" prop="location">
          <el-input v-model="form.data.location" autocomplete="new"/>
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
import {
  getCommentOne,
  getCommentPage,
  getCommentList,
  removeCommentBatchByIds,
  saveComment
} from '@/api/comment/index.js'
import {getUserList} from '@/api/user.js'
import {ElMessage} from "element-plus"

const loading = ref(true)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 20,
  bizId: null,
  bizKey: '',
  content: '',
  replyId: null,
  userId: null,
  os: '',
  ip: '',
  location: ''
})
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const bizList = ref([])
const replyList = ref([])
const userList = ref([])
const commentList = ref([])
const total = ref(0)
const commentFields = {
  '序号': {
    field: 'id',
    callback: (id) => {
      const index = commentList.value.findIndex(item => item.id === id)
      return index + 1
    }
  },
  '主键ID': 'id',
  '业务ID': 'bizId',
  '业务类型': 'bizKey',
  '内容': 'content',
  '回复ID': 'replyId',
  '用户ID': 'userId',
  '操作系统': 'os',
  'IP': 'ip',
  'IP属地': 'location'
}
const form = ref({
  visible: false,
  title: '',
  data: {}
})
const formRef = ref(null)
const rules = {
  bizId: [{required: true, message: '请输入业务ID', trigger: 'blur'}],
  bizKey: [{required: true, message: '请输入业务类型', trigger: 'blur'}],
  content: [{required: true, message: '请输入内容', trigger: 'blur'}],
  replyId: [{required: true, message: '请输入回复ID', trigger: 'blur'}],
  userId: [{required: true, message: '请输入用户ID', trigger: 'blur'}],
  os: [{required: true, message: '请输入操作系统', trigger: 'blur'}],
  ip: [{required: true, message: '请输入IP', trigger: 'blur'}],
  location: [{required: true, message: '请输入IP属地', trigger: 'blur'}]
}

const getPage = () => {
  loading.value = true
  getCommentList({}).then(res => {
    replyList.value = res.data || []
  })
  getUserList({}).then(res => {
    userList.value = res.data || []
  })
  getCommentPage(queryParams).then(res => {
    commentList.value = res.data?.records || []
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
    title: '添加评论',
    data: {
      bizId: null,
      bizKey: '',
      content: '',
      replyId: null,
      userId: null,
      os: '',
      ip: '',
      location: '',
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
  getCommentOne(params).then(res => {
    if (res.code !== 200) return
    form.value = {
      visible: true,
      title: '编辑评论',
      data: {
        ...res.data
      }
    }
  })
}

const handleSave = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    saveComment(form.value.data).then(res => {
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
  removeCommentBatchByIds(params).then(res => {
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
  queryParams.bizId = null
  queryParams.bizKey = ''
  queryParams.content = ''
  queryParams.replyId = null
  queryParams.userId = null
  queryParams.os = ''
  queryParams.ip = ''
  queryParams.location = ''
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
