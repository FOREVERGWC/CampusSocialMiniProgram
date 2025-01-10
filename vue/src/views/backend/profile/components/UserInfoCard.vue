<template>
  <el-card>
    <div class="user-info">
      <div class="avatar-container">
        <avatar-upload v-model="localUser.avatar" :bizId="localUser.id" bizType="1" @success="handleAvatarUploadSuccess"/>
      </div>
      <h3>{{ localUser.nickname || localUser.username }}</h3>

      <div v-for="field in editableFields" :key="field.key" class="user-field">
        <p v-if="!field.editing">
          <strong>{{ field.label }}:</strong>
          <span v-if="field.key === 'gender'">
            {{ genderMap[field.value] || '未知' }}
          </span>
          <span v-else-if="field.key === 'birthday'">
            {{ field.value }}
          </span>
          <span v-else>
            {{ field.value || '未填写' }}
          </span>
          <el-icon class="edit-icon" @click="field.editing = true">
            <Edit/>
          </el-icon>
        </p>

        <el-form-item v-else :label="field.label">
          <el-input
              v-if="field.key !== 'gender' && field.key !== 'birthday'"
              v-model="field.value"
              @blur="handleFieldSave(field)"
          />
          <el-select
              v-else-if="field.key === 'gender'"
              v-model="field.value"
              placeholder="请选择性别"
              @blur="handleFieldSave(field)"
          >
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
            <el-option label="未知" value="2"></el-option>
          </el-select>
          <el-date-picker
              v-else
              v-model="field.value"
              type="date"
              placeholder="请选择生日"
              value-format="YYYY-MM-DD"
              @blur="handleFieldSave(field)"
          />
        </el-form-item>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import {ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {Edit} from "@element-plus/icons-vue";
import AvatarUpload from "@/components/AvatarUpload/index.js";
import {saveUser} from "@/api/user.js";
import {formatDate} from "../../../../utils/common.js";

const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["refreshUser"]);

// 本地用户对象，用于处理局部更新
const localUser = ref({...props.user});

// 性别映射
const genderMap = {
  "0": "女",
  "1": "男",
  "2": "未知",
};

// 可编辑字段配置
const editableFields = ref([
  {key: "nickname", label: "昵称", value: localUser.value.nickname, editing: false},
  {key: "name", label: "姓名", value: localUser.value.name, editing: false},
  {key: "gender", label: "性别", value: localUser.value.gender, editing: false},
  {key: "email", label: "邮箱", value: localUser.value.email, editing: false},
  {key: "phone", label: "电话", value: localUser.value.phone, editing: false},
  {key: "birthday", label: "生日", value: localUser.value.birthday, editing: false},
]);

// 监听 props.user 变化，并更新本地用户对象
watch(
    () => props.user,
    (newUser) => {
      localUser.value = {...newUser};
      editableFields.value.forEach((field) => {
        field.value = localUser.value[field.key];
      });
    }
);

// 保存用户字段
const handleFieldSave = (field) => {
  field.editing = false;
  const data = {id: localUser.value.id, [field.key]: field.value};

  saveUser(data)
      .then(() => {
        ElMessage.success(`${field.label}更新成功`);
        emit("refreshUser"); // 触发父组件的刷新函数
      })
      .catch(() => {
        ElMessage.error(`${field.label}更新失败`);
      });
};

// 头像上传成功后处理
const handleAvatarUploadSuccess = (response) => {
  localUser.value.avatar = response;
  saveUser({id: localUser.value.id, avatar: localUser.value.avatar})
      .then(() => {
        ElMessage.success("头像更新成功");
        emit("refreshUser"); // 触发父组件的刷新函数
      })
      .catch(() => {
        ElMessage.error("头像更新失败");
      });
};
</script>

<style scoped lang="scss">
.user-info {
  text-align: left;

  .avatar-container {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }

  .edit-icon {
    cursor: pointer;
    margin-left: 8px;
  }

  h3 {
    text-align: center;
    margin-bottom: 10px;
  }

  p {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 5px 0;
  }

  .user-field {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;

    p,
    .el-input,
    .el-select,
    .el-date-picker {
      flex: 1;
    }
  }
}

.el-form-item,
.el-input,
.el-select,
:deep(.el-date-editor) {
  width: 100%;
}
</style>
