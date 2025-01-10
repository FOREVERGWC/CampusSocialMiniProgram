<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <el-row :gutter="20">
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.name" clearable placeholder="请输入名称"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.title" clearable placeholder="请输入标题"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.path" clearable placeholder="请输入路由地址"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-input v-model="queryParams.redirect" clearable placeholder="请输入重定向地址"/>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.type" clearable filterable placeholder="请选择类型">
                <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.status" clearable filterable placeholder="请选择状态">
                <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
            </el-col>
            <el-col :lg="4" :md="4" :sm="12" :xl="4" :xs="12">
              <el-select v-model="queryParams.visible" clearable filterable placeholder="请选择是否可见">
                <el-option v-for="item in visibleList" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
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
      <el-table v-loading="loading" :cell-style="{ textAlign: 'center' }" :data="menuList"
                :header-cell-style="{ textAlign: 'center' }" stripe
                @selection-change="handleSelectionChange"
                row-key="id"
                default-expand-all>
        <el-table-column type="selection" width="55"/>
        <el-table-column label="序号" type="index" width="70"/>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="标题" prop="title"/>
        <el-table-column label="图标" width="55">
          <template v-slot="{ row }">
            <el-icon>
              <component :is="row.icon"/>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column label="路由地址" prop="path"/>
        <el-table-column label="重定向地址" prop="redirect"/>
        <el-table-column label="组件路径" prop="component"/>
        <el-table-column label="类型" prop="typeText"/>
        <el-table-column label="排序" prop="sort" width="55"/>
        <el-table-column label="状态" width="100">
          <template v-slot="{ row }">
            <el-switch v-model="row.status" active-value="1" inactive-value="0" @change="() => handleStatus(row.id)"/>
          </template>
        </el-table-column>
        <el-table-column label="可见" width="100">
          <template v-slot="{ row }">
            <el-switch v-model="row.visible" @change="() => handleVisible(row.id)"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="150"/>
        <el-table-column label="修改时间" prop="updateTime" width="150"/>
        <el-table-column label="备注" prop="remark"/>
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

    <el-dialog :title="form.title" v-model="form.visible" destroy-on-close width="40%">
      <el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.data.name" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.data.title" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="图标">
          <IconPicker v-model="form.data.icon"/>
        </el-form-item>
        <el-form-item label="父级菜单" prop="parentId">
          <el-tree-select
              v-model="form.data.parentId"
              :data="parentList"
              :props="parentProps"
              :render-after-expand="false"
              check-strictly
              clearable
              filterable
              placeholder='请选择父级菜单'
          />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.data.type" clearable filterable placeholder="请选择类型">
            <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="form.data.path" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="重定向地址">
          <el-input v-model="form.data.redirect" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="组件路径" prop="component" v-if="form.data.type === '2'">
          <el-input v-model="form.data.component" autocomplete="new"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.data.sort" autocomplete="new"/>
        </el-form-item>
        <el-form-item v-if="form.data.id" label="状态" prop="status">
          <el-select v-model="form.data.status" clearable filterable placeholder="请选择状态">
            <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="可见" prop="visible">
          <el-select v-model="form.data.visible" clearable filterable placeholder="请选择是否可见">
            <el-option v-for="item in visibleList" :key="item.value" :label="item.label" :value="item.value"/>
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
import {nextTick, onMounted, reactive, ref, toRaw} from 'vue'
import {
  getMenuOne,
  getMenuTree,
  handleStatusMenu,
  handleVisibleMenu,
  removeMenuBatchByIds,
  saveMenu
} from '@/api/menu.js'
import {ElMessage} from "element-plus"
import {downloadFile} from "@/utils/common.js";

const loading = ref(true)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 20,
  name: '',
  title: '',
  parentId: null,
  path: '',
  redirect: '',
  component: '',
  type: '',
  status: '',
  visible: null
})
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const parentList = ref([])
const menuList = ref([])
const total = ref(0)
const typeList = [
  {label: '目录', value: '1'},
  {label: '菜单', value: '2'},
  {label: '按钮', value: '3'}
]
const statusList = [
  {label: '禁用', value: '0'},
  {label: '正常', value: '1'}
]
const visibleList = [
  {label: '是', value: true},
  {label: '否', value: false}
]
const form = ref({
  visible: false,
  title: '',
  data: {}
})
const formRef = ref(null)
const rules = {
  name: [{required: true, message: '请输入名称', trigger: 'blur'}],
  title: [{required: true, message: '请输入标题', trigger: 'blur'}],
  parentId: [{required: true, message: '请输入父级菜单ID', trigger: 'change'}],
  path: [{required: true, message: '请输入路由地址', trigger: 'blur'}],
  component: [{required: true, message: '请输入组件路径', trigger: 'blur'}],
  type: [{required: true, message: '请选择类型', trigger: 'change'}],
  sort: [{required: true, message: '请输入排序', trigger: 'blur'}],
  status: [{required: true, message: '请选择状态', trigger: 'change'}],
  visible: [{required: true, message: '请输入可见(0否、1是)', trigger: 'blur'}]
}

const parentProps = {
  value: 'id',
  label: 'title',
  children: 'children'
}

const getPage = () => {
  loading.value = true
  getMenuTree({}).then(res => {
    parentList.value = res.data || []
    parentList.value.unshift({id: '0', title: '根结点'})
  })
  getMenuTree(queryParams).then(res => {
    menuList.value = res.data || []
    total.value = res.data?.length || 0
    loading.value = false
  })
  // getMenuPage(queryParams).then(res => {
  //   menuList.value = res.data?.records || []
  //   total.value = res.data?.total || 0
  //   loading.value = false
  // })
}

const showAdd = () => {
  nextTick(() => {
    if (!formRef.value) return
    formRef.value.resetFields()
  })
  form.value = {
    visible: true,
    title: '添加菜单',
    data: {
      name: '',
      title: '',
      icon: '',
      parentId: null,
      path: '',
      redirect: '',
      component: '',
      type: '',
      sort: null,
      status: '',
      visible: true,
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
  getMenuOne(params).then(res => {
    if (res.code !== 200) return
    form.value = {
      visible: true,
      title: '编辑菜单',
      data: {
        ...res.data
      }
    }
  })
}

const handleSave = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    saveMenu(form.value.data).then(res => {
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
  removeMenuBatchByIds(params).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success('删除成功！')
  }).finally(() => {
    getPage()
  })
}

const handleStatus = (id) => {
  handleStatusMenu(id).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
    } else {
      ElMessage.success('操作成功！')
      getPage()
    }
  })
}

const handleVisible = (id) => {
  handleVisibleMenu(id).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
    } else {
      ElMessage.success('操作成功！')
      getPage()
    }
  })
}

const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => toRaw(item).id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

const handleReset = () => {
  queryParams.pageNo = 1
  queryParams.pageSize = 20
  queryParams.name = ''
  queryParams.title = ''
  queryParams.parentId = null
  queryParams.path = ''
  queryParams.redirect = ''
  queryParams.component = ''
  queryParams.type = ''
  queryParams.status = ''
  queryParams.visible = null
  getPage()
}

const handleExport = () => {
  downloadFile('/menu/export', queryParams)
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
