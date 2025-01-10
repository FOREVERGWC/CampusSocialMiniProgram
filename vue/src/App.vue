<template>
	<div id="app">
		<router-view />
		<CopyrightIcp />
	</div>
</template>

<script setup>

</script>

<style lang="scss">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

html, body, #app {
	height: 100%;
	margin: 0;
	font-size: 14px;
}

a {
	text-decoration: none;
}

.el-tag {
	margin-right: 8px;
}
</style>
