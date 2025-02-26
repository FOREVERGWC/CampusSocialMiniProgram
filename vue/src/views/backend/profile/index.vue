<template>
	<el-row :gutter="20">
		<!-- 左侧个人信息卡片 -->
		<el-col :span="6">
			<user-info-card :user="user" @refreshUser="loadUserInfo" />
		</el-col>

		<!-- 右侧选项卡 -->
		<el-col :span="18">
			<el-tabs v-model="activeTab">
				<el-tab-pane label="修改密码" name="password">
					<el-form :model="passwordForm" @submit.prevent="handleUpdatePassword">
						<el-form-item label="邮箱">
							<el-input v-model="user.email" disabled prefix-icon="Message" />
						</el-form-item>
						<el-form-item label="验证码" prop="code">
							<el-input v-model="passwordForm.code" placeholder="请输入验证码" prefix-icon="Document">
								<template #append>
									<el-button @click="sendCode" :disabled="codeButtonDisabled">{{ codeButtonText }}</el-button>
								</template>
							</el-input>
						</el-form-item>
						<el-form-item label="新密码" prop="password">
							<el-input v-model="passwordForm.password" type="password" show-password />
						</el-form-item>
						<el-form-item label="确认密码" prop="confirmPwd">
							<el-input v-model="passwordForm.confirmPwd" type="password" show-password />
						</el-form-item>
						<el-button type="primary" @click="handleUpdatePassword">修改密码</el-button>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="修改邮箱" name="email">
					<el-form :model="emailForm">
						<el-form-item label="原始邮箱">
							<el-input v-model="emailForm.email" disabled prefix-icon="Message" />
						</el-form-item>
						<el-form-item label="验证码" prop="code">
							<el-input v-model="emailForm.code" placeholder="请输入验证码" prefix-icon="Document">
								<template #append>
									<el-button @click="sendNewCode" :disabled="codeButtonDisabled">{{ codeButtonText }}</el-button>
								</template>
							</el-input>
						</el-form-item>
						<el-form-item label="新邮箱" prop="newEmail">
							<el-input v-model="emailForm.newEmail" />
						</el-form-item>
						<el-form-item label="确认邮箱" prop="confirmEmail">
							<el-input v-model="emailForm.confirmEmail" />
						</el-form-item>
						<el-button type="primary" @click="handleUpdateEmail">修改邮箱</el-button>
					</el-form>
				</el-tab-pane>
				<!--				<el-tab-pane label="修改手机" name="phone"/>-->
			</el-tabs>
		</el-col>
	</el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import UserInfoCard from './components/UserInfoCard.vue'
import useUserStore from '@/store/modules/user.js'
import { getByToken, resetPassword, updateEmail } from '@/api/auth.js'
import { sendResetCode } from '@/api/email.js'
import { useRouter } from 'vue-router'

const user = ref({
	id: '',
	username: '',
	nickname: '',
	name: '',
	avatar: '',
	gender: '',
	email: '',
	phone: '',
	birthday: ''
})

const router = useRouter()
const userStore = useUserStore()

// 修改密码表单
const passwordForm = ref({
	email: '',
	code: '',
	password: '',
	confirmPwd: ''
})

const emailForm = ref({
	email: '',
	code: '',
	newEmail: '',
	confirmEmail: ''
})

// 选项卡
const activeTab = ref('password')

// 验证码按钮状态
const codeButtonText = ref('发送验证码')
const codeButtonDisabled = ref(false)
const countdown = ref(120)

// 获取用户信息
const loadUserInfo = () => {
	getByToken().then(res => {
		user.value = res.data
		passwordForm.value.email = res.data.email
		emailForm.value.email = res.data.email
		userStore.setUser(res.data)
	})
}

// 发送验证码
const sendCode = async () => {
	codeButtonDisabled.value = true
	const res = await sendResetCode({ email: user.value.email })
	if (res.code !== 200) {
		ElMessage.error(res.msg || '验证码发送失败')
		codeButtonDisabled.value = false
		return
	}
	ElMessage.success('验证码发送成功')
	startCountdown()
}

const sendNewCode = async () => {
	codeButtonDisabled.value = true
	const res = await sendResetCode({ email: emailForm.value.newEmail })
	if (res.code !== 200) {
		ElMessage.error(res.msg || '验证码发送失败')
		codeButtonDisabled.value = false
		return
	}
	ElMessage.success('验证码发送成功')
	startCountdown()
}

// 开始验证码倒计时
const startCountdown = () => {
	codeButtonText.value = `${countdown.value}秒后重新发送`
	const interval = setInterval(() => {
		countdown.value--
		if (countdown.value <= 0) {
			clearInterval(interval)
			codeButtonText.value = '发送验证码'
			codeButtonDisabled.value = false
			countdown.value = 120
		} else {
			codeButtonText.value = `${countdown.value}秒后重新发送`
		}
	}, 1000)
}

// 更新密码
const handleUpdatePassword = () => {
	if (passwordForm.value.password !== passwordForm.value.confirmPwd) {
		ElMessage.error('新密码和确认密码不一致')
		return
	}
	resetPassword(passwordForm.value).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		ElMessage.success('修改成功！')
		passwordForm.value = {
			email: user.value.email,
			code: '',
			password: '',
			confirmPwd: ''
		}
	})
}

const handleUpdateEmail = () => {
	updateEmail(emailForm.value).then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		ElMessage.success('修改成功！重新登陆后生效')
		setTimeout(() => {
			userStore.handleLogout().then(() => {
				location.href = router.resolve('/index').href
			})
		}, 3000)
	})
}
// 加载用户信息
onMounted(() => {
	loadUserInfo()
})
</script>

<style lang="scss" scoped></style>
