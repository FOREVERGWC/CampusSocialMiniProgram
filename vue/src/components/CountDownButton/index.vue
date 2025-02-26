<template>
	<div>
		<el-button @click="handleCaptcha" :disabled="disabled">{{ disabled ? `${timer}秒` : '发送' }}</el-button>
	</div>
</template>

<script setup>
import { onUnmounted, ref } from 'vue'

const disabled = ref(false)
const timer = ref(60)
let interval = null

const props = defineProps({
	handleClick: {
		type: Function,
		required: true
	}
})

const handleCaptcha = () => {
	if (disabled.value) return
	props.handleClick().then(res => {
		if (!res) return
		startCountdown()
	})
}

const startCountdown = () => {
	disabled.value = true
	interval = setInterval(() => {
		if (timer.value > 0) {
			timer.value--
		} else {
			clearInterval(interval)
			disabled.value = false
			timer.value = 60
		}
	}, 1000)
}

onUnmounted(() => {
	if (interval) {
		clearInterval(interval)
	}
})
</script>

<style scoped lang="scss">
div {
	display: flex;
	justify-content: space-between;
	align-items: center;

	.el-button {
		margin-left: 10px;
		width: 100px;
		flex-shrink: 0;
	}
}
</style>
