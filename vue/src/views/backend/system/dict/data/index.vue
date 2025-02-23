<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.label" clearable placeholder="请输入标签"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.value" clearable placeholder="请输入键值"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.typeId" clearable filterable placeholder="请选择类型">
                <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id"/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.sort" clearable placeholder="请输入排序"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.status" clearable filterable placeholder="请选择状态">
                <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
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
        <el-table-column label="标签" prop="label"/>
        <el-table-column label="键值" prop="value"/>
        <el-table-column label="类型" prop="type.name"/>
        <el-table-column label="排序" prop="sort"/>
        <el-table-column label="状态" prop="status">
          <template v-slot="{ row }">
            <el-switch v-model="row.status" active-value="1" inactive-value="0" @change="() => handleStatus(row.id)"/>
          </template>
        </el-table-column>
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
        <el-form-item label="标签" prop="label">
          <el-input v-model="form.data.label" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="键值" prop="value">
          <el-input v-model="form.data.value" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="类型" prop="typeId">
          <el-select v-model="form.data.typeId" clearable filterable placeholder="请选择类型">
            <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.data.sort" autocomplete="new"/>
        </el-form-item>
        <el-form-item v-if="form.data.id" label="状态" prop="status">
          <el-select v-model="form.data.status" clearable filterable placeholder="请选择状态">
            <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value"/>
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
import {nextTick, onMounted, reactive, ref} from 'vue'
import {useRoute} from 'vue-router'
import {
  getDictDataOne,
  getDictDataPage,
  handleStatusDictData,
  removeDictDataBatchByIds,
  saveDictData
} from '@/api/dictData'
import {getDictTypeList} from '@/api/dictType.js'
import {ElMessage} from 'element-plus'
import {downloadFile} from '@/utils/common.js'
import {useTable} from "@/hooks/useTable/index.js";

const route = useRoute()

const queryParams = reactive({
  label: '',
  value: '',
  typeId: null,
  sort: null,
  status: ''
})
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
    (page) => getDictDataPage({...queryParams, pageNo: page.pageNo, pageSize: page.pageSize}),
    {immediate: false}
)
const typeList = ref([])
const statusList = [
  { label: '禁用', value: '0' },
  { label: '正常', value: '1' }
]
const form = ref({
  visible: false,
  title: '',
  data: {}
})
const formRef = ref(null)
const rules = {
  label: [{required: true, message: '请输入标签', trigger: 'blur'}],
  value: [{required: true, message: '请输入键值', trigger: 'blur'}],
  typeId: [{required: true, message: '请输入类型ID', trigger: 'blur'}],
  sort: [{required: true, message: '请输入排序', trigger: 'blur'}],
  status: [{required: true, message: '请选择状态', trigger: 'change'}]
}

const showAdd = () => {
  nextTick(() => {
    if (!formRef.value) return
    formRef.value.resetFields()
  })
  form.value = {
    visible: true,
    title: '添加字典数据',
    data: {
      label: '',
      value: '',
      typeId: null,
      sort: null,
      status: '',
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
  getDictDataOne(params).then(res => {
    if (res.code !== 200) return
    form.value = {
      visible: true,
      title: '编辑字典数据',
      data: {
        ...res.data
      }
    }
  })
}

const handleSave = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    saveDictData(form.value.data).then(res => {
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
  getRecords()
}

const handleReset = () => {
  queryParams.label = ''
  queryParams.value = ''
  queryParams.typeId = null
  queryParams.sort = null
  queryParams.status = ''
  getRecords()
}

const handleDelete = (id) => {
  const params = id || selectedKeys.value
  onDelete(() => removeDictDataBatchByIds(params), {})
}

const handleStatus = (id) => {
  handleStatusDictData(id).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
    } else {
      ElMessage.success('操作成功！')
      getRecords()
    }
  })
}

const handleExport = () => {
  downloadFile('/dict/data/export', queryParams)
}

onMounted(() => {
  queryParams.typeId = route.query.typeId || null
  getDictTypeList({}).then(res => {
    typeList.value = res.data || []
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
