<template>
  <div class="main-container">
    <el-card class="main-card">
      <h2 class="title">{{ title }}</h2>
      <el-form :model="form" :rules="rules" ref="formRef" size="default">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" autocomplete="new"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock"
                    autocomplete="new"
                    show-password/>
        </el-form-item>
        <el-form-item prop="confirmPwd">
          <el-input v-model="form.confirmPwd" type="password" placeholder="确认密码" prefix-icon="Lock"
                    autocomplete="new"
                    show-password/>
        </el-form-item>
        <el-form-item class="email" prop="email">
          <el-input v-model="form.email" placeholder="邮箱" prefix-icon="Message" autocomplete="new"/>
          <CountDownButton :handleClick="handleCaptcha"/>
        </el-form-item>
        <el-form-item v-if="enabled" prop="code">
          <el-input v-model="form.code" placeholder="验证码" prefix-icon="Message" autocomplete="new"
                    @keyup.enter="handleRegister"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </el-form-item>
      </el-form>
      <div class="login-link">
        <span>已有账号？请</span>
        <el-link type="primary" :underline="false" href="/login">登录</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {register} from "@/api/auth.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {sendRegisterCode} from "@/api/email.js";
import {CountDownMessage} from "@/components/CountDownMessage/index.js";

const router = useRouter()

const title = ref(import.meta.env.VITE_APP_TITLE);
const enabled = ref(true)
const form = ref({
  username: '',
  password: '',
  confirmPwd: '',
  email: '',
  code: ''
});
const rules = {
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}],
  confirmPwd: [{required: true, message: '请输入确认密码', trigger: 'blur'}],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur'}
  ],
  code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
};
const formRef = ref(null)

const handleCaptcha = () => {
  return new Promise((resolve) => {
    formRef.value.validateField('email', (valid) => {
      if (!valid) {
        resolve(false);
        return;
      }
      const data = {email: form.value.email};
      sendRegisterCode(data).then(res => {
        if (res.code !== 200) {
          ElMessage.error(res.msg);
          resolve(false);
          return;
        }
        ElMessage.success('发送成功！请注意查收');
        resolve(true);
      });
    });
  });
}

const handleRegister = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    register(form.value).then(res => {
      if (res.code !== 200) {
        ElMessage.error(res.msg);
        return;
      }
      CountDownMessage('success', 3, `注册成功！{}秒后跳转到登录页面`, () => router.push('/login'))
    });
  });
};
</script>

<style scoped lang="scss">
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #F3F8FF;
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

    .email {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .el-input {
        flex: 1;
      }
    }

    .el-button {
      width: 100%;
    }

    .login-link {
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
