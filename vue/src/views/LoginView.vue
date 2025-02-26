<template>
	<div class="main-container">
		<el-card class="main-card">
			<h2 class="title">{{ title }}</h2>
			<el-tabs v-model="form.loginType">
				<el-tab-pane label="账密登录" name="1" />
				<el-tab-pane label="邮箱登录" name="2" />
				<el-tab-pane label="手机登录" name="3" />
			</el-tabs>
			<el-form :model="form" :rules="rules" ref="formRef" size="default">
				<el-form-item prop="username" v-if="form.loginType === '1'">
					<el-input v-model="form.username" placeholder="用户名" prefix-icon="User" autocomplete="new" />
				</el-form-item>
				<el-form-item class="email" prop="email" v-if="form.loginType === '2'">
					<el-input v-model="form.email" placeholder="邮箱" prefix-icon="Message" autocomplete="new" />
					<CountDownButton :handleClick="handleEmailCaptcha" />
				</el-form-item>
				<el-form-item class="phone" prop="phone" v-if="form.loginType === '3'">
					<el-input v-model="form.phone" placeholder="手机" prefix-icon="Iphone" autocomplete="new" />
					<CountDownButton :handleClick="handlePhoneCaptcha" />
				</el-form-item>
				<el-form-item prop="password" v-if="form.loginType === '1'">
					<el-input
						v-model="form.password"
						type="password"
						placeholder="密码"
						prefix-icon="Lock"
						show-password
						@keyup.enter="handleLogin" />
				</el-form-item>
				<el-form-item prop="code" class="code" v-if="enabled || form.loginType !== '1'">
					<el-input v-model="form.code" placeholder="验证码" prefix-icon="Message" @keyup.enter="handleLogin" />
					<img v-if="form.loginType === '1'" :src="captcha" @click="handleCaptcha" alt="验证码" />
				</el-form-item>
				<el-form-item class="remember-forgot">
					<div class="remember-me">
						<el-checkbox v-model="form.rememberMe">记住我</el-checkbox>
					</div>
					<div class="forgot-password">
						<el-link href="/reset" type="primary" :underline="false">忘记密码</el-link>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleLogin">登录</el-button>
				</el-form-item>
			</el-form>
			<div class="register-link">
				<span>还没有账号？请</span>
				<el-link type="primary" :underline="false" href="/register">注册</el-link>
			</div>
			<el-divider content-position="center">
				<span>第三方账号登录</span>
				<!-- 微信、QQ、GitHub -->
			</el-divider>
		</el-card>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCaptcha } from '@/api/auth.js'
import { ElMessage } from 'element-plus'
import useUserStore from '@/store/modules/user.js'
import { sendLoginCode } from '@/api/email.js'

const router = useRouter()
const userStore = useUserStore()

const title = ref(import.meta.env.VITE_APP_TITLE)
const enabled = ref(true)
const captcha = ref('')
const form = ref({
	username: '',
	email: '',
	phone: '',
	password: '',
	code: '',
	rememberMe: true,
	uuid: '',
	loginType: '1'
})
const rules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
	email: [
		{ required: true, message: '请输入邮箱', trigger: 'blur' },
		{ type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
	],
	phone: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
	password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
	code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}
const formRef = ref(null)

const handleCaptcha = () => {
	getCaptcha().then(res => {
		if (res.code !== 200) {
			ElMessage.error(res.msg)
			return
		}
		enabled.value = res.data.enabled
		captcha.value = res.data.img
		form.value.uuid = res.data.uuid
		form.value.code = ''
	})
}

const handleEmailCaptcha = () => {
	return new Promise(resolve => {
		formRef.value.validateField('email', valid => {
			if (!valid) {
				resolve(false)
				return
			}
			const data = { email: form.value.email }
			sendLoginCode(data).then(res => {
				if (res.code !== 200) {
					ElMessage.error(res.msg)
					resolve(false)
					return
				}
				ElMessage.success('发送成功！请注意查收')
				resolve(true)
			})
		})
	})
}

const handlePhoneCaptcha = () => {
	return new Promise(resolve => {
		formRef.value.validateField('phone', valid => {
			if (!valid) {
				resolve(false)
				return
			}
			const data = { email: form.value.email }
			sendLoginCode(data).then(res => {
				if (res.code !== 200) {
					ElMessage.error(res.msg)
					resolve(false)
					return
				}
				ElMessage.success('发送成功！请注意查收')
				resolve(true)
			})
		})
	})
}

const handleLogin = () => {
	formRef.value.validate(valid => {
		if (!valid) return
		userStore.handleLogin(form.value).then(() => {
			router.replace('/')
		})
	})
}

onMounted(() => {
	handleCaptcha()
})
</script>

<style scoped lang="scss">
.main-container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f3f8ff;
	padding: 20px;

	.main-card {
		width: 100%;
		max-width: 400px;
		padding: 20px;
		box-sizing: border-box;

		.title {
			text-align: center;
			font-weight: bold;
			margin-bottom: 20px;
		}

		.email,
		.phone,
		.code {
			display: flex;
			justify-content: space-between;
			align-items: center;

			.el-input {
				flex: 1;
			}

			img {
				margin-left: 10px;
				cursor: pointer;
				width: 100px;
				height: 32px;
				flex-shrink: 0;
			}
		}

		.remember-forgot {
			display: flex;
			justify-content: space-between;
			align-items: center;

			.remember-me {
				flex: 1;
				text-align: left;
			}

			.forgot-password {
				flex: 1;
				text-align: right;
			}
		}

		.el-button {
			width: 100%;
		}

		.register-link {
			display: flex;
			justify-content: flex-end;
			margin-top: 10px;

			span {
				margin-right: 2px;
			}
		}
	}
}
</style>
