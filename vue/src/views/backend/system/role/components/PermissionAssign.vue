<template>
	<el-dialog
		:title="form.title"
		v-model="form.visible"
		@update:visible="handleVisible"
		destroy-on-close
		width="40%"
		@close="handleClose">
		<el-tree-v2
			ref="treeRef"
			:data="permissionList"
			:props="node"
			:default-checked-keys="form.checkedIdList"
			:default-expanded-keys="form.expandedIdList"
			show-checkbox
			:height="600"
			check-strictly />
		<template #footer>
			<el-button @click="handleClose">取 消</el-button>
			<el-button type="primary" @click="handleSave">确 定</el-button>
		</template>
	</el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { handleRolePermission } from '@/api/sys/role/index.js'
import { getPermissionTree } from '@/api/sys/permission/index.js'
import { getRolePermissionLinkList } from '@/api/rolePermissionLink.js'
import { getExpandedIds } from '@/utils/common.js'

const props = defineProps({
	id: String,
	visible: Boolean
})

const node = ref({
	value: 'id',
	label: 'name',
	children: 'children'
})

const emit = defineEmits(['update:visible', 'refresh'])

const permissionList = ref([])

const form = reactive({
	visible: props.visible,
	title: '分配权限',
	checkedIdList: [],
	expandedIdList: []
})
const treeRef = ref(null)

const getPermissionInfo = () => {
	getPermissionTree({}).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		permissionList.value = res.data || []
		form.expandedIdList = getExpandedIds(res.data)
		console.log(res.data, form.expandedIdList)
	})
	const params = {
		roleId: props.id
	}
	getRolePermissionLinkList(params).then(res => {
		form.checkedIdList = res.data?.map(item => item.permissionId) || []
		nextTick(() => {
			if (!treeRef.value) return
			treeRef.value.setExpandedKeys(form.expandedIdList)
			treeRef.value.setCheckedKeys(form.checkedIdList)
		})
	})
}

const handleClose = () => {
	emit('update:visible', false)
}

const handleVisible = value => {
	form.visible = value
	emit('update:visible', value)
}

const handleSave = () => {
	const data = {
		roleId: props.id,
		permissionIdList: [...treeRef.value.getCheckedKeys(), ...treeRef.value.getHalfCheckedKeys()]
	}
	handleRolePermission(data).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		ElMessage.success('保存成功！')
		handleClose()
		emit('refresh')
	})
}

onMounted(() => {
	if (props.visible) {
		getPermissionInfo()
	}
})

watch(
	() => props.visible,
	value => {
		form.visible = value
		if (value) {
			getPermissionInfo()
		}
	}
)
</script>

<style lang="scss" scoped></style>
