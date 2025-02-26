<template>
	<el-container>
		<el-footer>
			<div class="copyright-container">
				<div>{{ `Copyright © 2024 - ${year} ${author} All Rights Reserved 版权所有` }}</div>
				<el-link type="primary" href="http://beian.miit.gov.cn/" :underline="false" target="_blank">{{ icp }}</el-link>
			</div>
		</el-footer>
	</el-container>
</template>

<script setup>
import { ref } from 'vue'

const year = ref(new Date().getFullYear())
const author = ref(import.meta.env.VITE_APP_AUTHOR)
const icp = ref(import.meta.env.VITE_APP_ICP)
</script>

<style scoped lang="scss">
.copyright-container {
	padding: 16px 0;
	text-align: center;
}
</style>
