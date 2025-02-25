<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.title" clearable placeholder="请输入标题"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.content" clearable placeholder="请输入内容"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.categoryId" clearable filterable placeholder="请选择类别">
                <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id"/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-date-picker v-model="activityDateTimeRange" clearable
                              type="datetimerange"
                              start-placeholder="活动开始时间" end-placeholder="活动结束时间"
                              value-format="YYYY-MM-DD HH:mm:ss"
                              unlink-panels
              />
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.location" clearable placeholder="请输入地点"/>
            </el-col>
            <el-col :lg="2" :md="2" :sm="12" :xl="2" :xs="12">
              <el-button icon="Search" plain type="info" @click="handleSearch">查询</el-button>
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
              <el-button icon="Download" plain @click="handleExport">导出</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <el-table v-loading="loading" :cell-style="{ textAlign: 'center' }" :data="records"
                :header-cell-style="{ textAlign: 'center' }" stripe
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column label="序号" type="index" width="70"/>
        <el-table-column label="标题" prop="title"/>
        <el-table-column label="类别" prop="category.name"/>
        <el-table-column label="开始时间" prop="startDatetime"/>
        <el-table-column label="结束时间" prop="endDatetime"/>
        <el-table-column label="地点" prop="location"/>
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
          :current-page="pagination.current"
          :page-size="pagination.pageSize"
          :page-sizes="[20, 30, 40, 50]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="pagination.onCurrentChange"
          @size-change="pagination.onPageSizeChange">
      </el-pagination>
    </el-card>

    <el-dialog :title="form.title" v-model="form.visible" destroy-on-close width="40%">
      <el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.data.title" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.data.content" :rows="10" autocomplete="new" type="textarea"/>
        </el-form-item>
        <el-form-item label="类别" prop="categoryId">
          <el-select v-model="form.data.categoryId" clearable filterable placeholder="请选择类别">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startDatetime">
          <el-date-picker v-model="form.data.startDatetime" placeholder="请选择开始时间"
                          type="datetime"
                          value-format="YYYY-MM-DD HH:mm:ss"/>
        </el-form-item>
        <el-form-item label="结束时间" prop="endDatetime">
          <el-date-picker v-model="form.data.endDatetime" placeholder="请选择结束时间"
                          type="datetime"
                          value-format="YYYY-MM-DD HH:mm:ss"/>
        </el-form-item>
        <el-form-item label="地点" prop="location">
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
import {nextTick, onMounted, reactive, ref} from 'vue'
import {getActivityOne, getActivityPage, removeActivityBatchByIds, saveActivity} from '@/api/biz/activity/index.js'
import {getActivityCategoryList} from '@/api/biz/activity/category.js'
import {ElMessage} from "element-plus"
import {addDataRange, downloadFile} from "@/utils/common.js";
import {useTable} from "@/hooks/useTable/index.js";

const activityDateTimeRange = ref([])
const queryParams = reactive({
  title: '',
  content: '',
  categoryId: null,
  location: ''
})
const categoryList = ref([])
const {
  loading,
  records,
  getRecords,
  pagination,
  selectedKeys,
  single,
  multiple,
  handleSelectionChange,
  onDelete
} = useTable(
    (page) => getActivityPage({...queryParams, pageNo: page.pageNo, pageSize: page.pageSize}),
    {immediate: false}
)
const form = ref({
  visible: false,
  title: '',
  data: {}
})
const formRef = ref(null)
const rules = {
  title: [{required: true, message: '请输入标题', trigger: 'blur'}],
  content: [{required: true, message: '请输入内容', trigger: 'blur'}],
  categoryId: [{required: true, message: '请选择类别', trigger: 'change'}],
  startDatetime: [{required: true, message: '请选择开始时间', trigger: 'change'}],
  endDatetime: [{required: true, message: '请选择结束时间', trigger: 'change'}],
  location: [{required: true, message: '请输入地点', trigger: 'blur'}]
}

const showAdd = () => {
  nextTick(() => {
    if (!formRef.value) return
    formRef.value.resetFields()
  })
  form.value = {
    visible: true,
    title: '添加活动',
    data: {
      title: '',
      content: '',
      categoryId: null,
      startDatetime: '',
      endDatetime: '',
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
  const params = {id: row.id || selectedKeys.value[0]}
  getActivityOne(params).then(res => {
    if (res.code !== 200) return
    form.value = {
      visible: true,
      title: '编辑活动',
      data: {
        ...res.data
      }
    }
  })
}

const handleSave = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    saveActivity(form.value.data).then(res => {
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success('保存成功！')
      form.value.visible = false
    }).finally(() => {
      getRecords()
    })
  })
}

const handleSearch = () => {
  addDataRange(queryParams, activityDateTimeRange.value, 'ActivityDateTime')
  getRecords()
}

const handleReset = () => {
  queryParams.title = ''
  queryParams.content = ''
  queryParams.categoryId = null
  queryParams.location = ''
  activityDateTimeRange.value = []
  getRecords()
}

const handleDelete = (id) => {
  const params = id || selectedKeys.value
  onDelete(() => removeActivityBatchByIds(params), {})
}

const handleExport = () => {
  downloadFile('/activity/export', queryParams)
}

onMounted(() => {
  getActivityCategoryList({}).then(res => {
    categoryList.value = res.data || []
  })
  getRecords()
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
