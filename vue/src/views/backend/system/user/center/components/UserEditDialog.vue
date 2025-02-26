<template>
	<el-dialog
		:title="form.title"
		v-model="form.visible"
		@update:visible="handleVisibleUpdate"
		destroy-on-close
		width="40%">
		<el-form ref="formRef" :model="form.data" :rules="rules" label-width="80px">
			<el-form-item label="用户名" prop="username">
				<el-input v-model="form.data.username" autocomplete="new" />
			</el-form-item>
			<el-form-item label="姓名" prop="name">
				<el-input v-model="form.data.name" autocomplete="new" />
			</el-form-item>
			<el-form-item label="性别" prop="gender">
				<el-select v-model="form.data.gender" placeholder="请选择性别">
					<el-option label="男" value="male" />
					<el-option label="女" value="female" />
				</el-select>
			</el-form-item>
			<el-form-item label="头像" prop="avatar">
				<AvatarUpload v-model="form.data.avatar" />
			</el-form-item>
			<el-form-item label="生日" prop="birthday">
				<el-date-picker v-model="form.data.birthday" placeholder="请选择生日" type="date" value-format="YYYY-MM-DD" />
			</el-form-item>
			<el-form-item v-if="form.data.id" label="状态" prop="status">
				<el-select v-model="form.data.status" clearable placeholder="请选择状态">
					<el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value" />
				</el-select>
			</el-form-item>
			<el-form-item label="电话" prop="phone">
				<el-input v-model="form.data.phone" autocomplete="new" />
			</el-form-item>
			<el-form-item label="邮箱" prop="email">
				<el-input v-model="form.data.email" autocomplete="new" />
			</el-form-item>
			<el-form-item label="备注" prop="remark">
				<el-input v-model="form.data.remark" :rows="5" autocomplete="new" type="textarea" />
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="closeDialog">取消</el-button>
			<el-button type="primary" @click="handleSave">确定</el-button>
		</template>
	</el-dialog>
</template>

<script setup>
import { ref, nextTick, watch, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserOne, saveUser } from '@/api/user.js'

const props = defineProps({
	visible: Boolean,
	userId: String
})

const emit = defineEmits(['update:visible', 'saved'])

const formRef = ref(null)
const form = reactive({
	visible: props.visible,
	title: '编辑用户信息',
	data: {}
})

watch(
	() => props.visible,
	newVal => {
		form.visible = newVal
		if (newVal) {
			loadUserData()
		}
	}
)

const rules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
	name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const loadUserData = () => {
	nextTick(() => {
		if (formRef.value) formRef.value.resetFields()
	})
	getUserOne({ id: props.userId }).then(res => {
		if (res.code === 200) {
			form.data = { ...res.data }
		} else {
			ElMessage.error('无法加载用户信息')
		}
	})
}

const handleSave = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		saveUser(form.data).then(res => {
			if (res.code === 200) {
				ElMessage.success('保存成功！')
				emit('saved')
				closeDialog()
			} else {
				ElMessage.error(res.msg)
			}
		})
	})
}

const closeDialog = () => {
	emit('update:visible', false)
}

const handleVisibleUpdate = newVal => {
	form.visible = newVal
	emit('update:visible', newVal)
}
</script>

<style scoped>
.dialog-footer {
	text-align: right;
}
</style>
