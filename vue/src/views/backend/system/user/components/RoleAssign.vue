<template>
	<el-dialog
		:title="form.title"
		v-model="form.visible"
		@update:visible="handleVisible"
		destroy-on-close
		width="40%"
		@close="handleClose">
		<el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
			<el-form-item label="角色" prop="roleIdList">
				<el-select v-model="form.roleIdList" multiple clearable filterable placeholder="请选择角色">
					<el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="handleClose">取 消</el-button>
			<el-button type="primary" @click="handleSave">确 定</el-button>
		</template>
	</el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import useRoleStore from '@/store/modules/role.js'
import { getUserOne, handleUserRole } from '@/api/user.js'
import { ElMessage } from 'element-plus'

const props = defineProps({
	id: String,
	visible: Boolean
})

const emit = defineEmits(['update:visible', 'refresh'])

const roleStore = useRoleStore()
const roleList = ref(roleStore.roleList)

const form = reactive({
	visible: props.visible,
	title: '分配角色',
	roleIdList: []
})
const formRef = ref(null)
const rules = {
	roleIdList: [{ required: true, message: '请选择角色', trigger: 'change' }]
}

const getUserInfo = () => {
	getUserOne({ id: props.id }).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		form.roleIdList = res.data.roleIdList
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
	formRef.value.validate(valid => {
		if (!valid) return
		const data = { userId: props.id, roleIdList: form.roleIdList }
		handleUserRole(data).then(res => {
			if (res.code !== 200) {
				ElMessage.error(res.msg)
				return
			}
			ElMessage.success('保存成功！')
			handleClose()
			emit('refresh')
		})
	})
}

onMounted(() => {
	if (props.visible) {
		getUserInfo()
	}
})

watch(
	() => props.visible,
	value => {
		form.visible = value
		if (value) {
			getUserInfo()
		}
	}
)
</script>

<style lang="scss" scoped></style>
