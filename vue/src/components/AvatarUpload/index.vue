<template>
	<el-upload
		class="avatar-uploader"
		:show-file-list="false"
		:before-upload="beforeUpload"
		:http-request="customUpload"
	>
		<el-avatar shape="square" v-if="url" :src="url" alt="" class="avatar" @error="() => true">
			<img alt="" src="@/assets/imgs/profile.png" />
		</el-avatar>
		<el-icon v-else class="avatar-uploader-icon">
			<Plus />
		</el-icon>
	</el-upload>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { checkFile, uploadFile } from '@/api/file'
import SparkMD5 from 'spark-md5'

const props = defineProps({
	modelValue: {
		type: String,
		required: false,
		default: ''
	},
  bizId: {
    type: [String, Number],
    required: true
  },
  bizType: {
    type: Number,
    default: 0
  }
})

const emit = defineEmits(['update:modelValue', 'success'])
const url = ref('')
const chunkSize = ref(10 * 1024 * 1024)

const updateUrl = () => {
	if (props.modelValue) {
		url.value = `${import.meta.env.VITE_APP_BASE_API}${props.modelValue}`
	}
}

const beforeUpload = (file) => {
	const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp', 'image/bmp']

	if (!allowedTypes.includes(file.type)) {
		ElMessage.error('头像只能为 JPG, PNG, GIF, WEBP 或 BMP 格式！')
		return false
	}

	if (file.size / 1024 / 1024 > 2) {
		ElMessage.error('头像最大不能超过 2MB！')
		return false
	}

	return true
}

const customUpload = async (params) => {
	const fileName = params.file.name
	const fileSize = params.file.size
	const chunks = createChunks(params.file, chunkSize.value)
	const hashCode = await getHashCode(chunks)
	const chunkTotal = chunks.length
	const checkParams = {
		hashCode: hashCode,
    bizId: props.bizId,
    bizType: props.bizType,
		chunkTotal: chunkTotal
	}
	const data = await checkFile(checkParams).then(res => {
		return new Promise((resolve, reject) => {
			if (res.code !== 200) {
				ElMessage.error(res.msg)
				params.onError()
				reject()
			}
			resolve(res.data)
		})
	})
	if (data.hasUpload) {
		ElMessage.success('上传成功！')
		return
	}
	let modelValue
	for (let i = 0; i < chunkTotal; i++) {
		if (data.indexList && data.indexList.length > 0 && !data.indexList.includes(i)) {
			continue
		}
		const res = await uploadChunk(chunks[i], hashCode, fileName, fileSize, chunkSize.value, i, chunkTotal)
		if (res.code !== 200) {
			params.onError()
			return
		}
		modelValue = res.data
	}
	emit('update:modelValue', modelValue)
	url.value = `${import.meta.env.VITE_APP_BASE_API}${modelValue}`
	ElMessage.success('上传成功！')
	params.onSuccess()
	emit('success', modelValue)
}

const createChunks = (file, chunkSize) => {
	return Array.from({ length: Math.ceil(file.size / chunkSize) }, (_, index) =>
		file.slice(index * chunkSize, Math.min((index + 1) * chunkSize, file.size))
	)
}

const getHashCode = (chunks) => {
	const spark = new SparkMD5()
	return new Promise(resolve => {
		const read = (i) => {
			if (i >= chunks.length) {
				resolve(spark.end())
				return
			}
			const blob = chunks[i]
			const reader = new FileReader()
			reader.onload = e => {
				spark.append(e.target.result)
				read(i + 1)
			}
			reader.readAsArrayBuffer(blob)
		}
		read(0)
	})
}

const uploadChunk = (chunk, hashCode, fileName, fileSize, chunkSize, chunkIndex, chunkTotal) => {
	const form = new FormData()
	form.append('file', chunk)
  form.append('bizId', props.bizId)
  form.append('bizType', props.bizType)
	form.append('hashCode', hashCode)
	form.append('fileName', fileName)
	form.append('fileSize', fileSize)
	form.append('chunkSize', chunkSize)
	form.append('chunkIndex', chunkIndex)
	form.append('chunkTotal', chunkTotal)
	return new Promise((resolve, reject) => {
		uploadFile(form).then(res => {
			if (res.code !== 200) {
				ElMessage.error(res.msg)
				reject(res)
			}
			resolve(res)
		})
	})
}

watch(() => props.modelValue, updateUrl)

onMounted(updateUrl)
</script>

<style lang="scss" scoped>
.avatar-uploader {
	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}

	:deep(.el-upload) {
		border: 1px dashed var(--el-border-color);
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		transition: var(--el-transition-duration-fast);

		&:hover {
			border-color: var(--el-color-primary);
		}
	}

	:deep(.avatar-uploader-icon) {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		text-align: center;
		line-height: 178px;
	}
}
</style>
