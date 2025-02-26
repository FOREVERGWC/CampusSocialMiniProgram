<template>
	<el-container>
		<el-aside :class="['hidden-sm-and-down', isCollapse ? 'el-aside--collapse' : '']">
			<el-menu :default-active="menu" router :collapse="isCollapse">
				<div class="menu-header">
					<span class="title">{{ title }}</span>
					<el-icon>
						<Expand v-show="isCollapse" @click="handleCollapse" />
						<Fold v-show="!isCollapse" @click="handleCollapse" />
					</el-icon>
				</div>

				<menu-item v-for="(item, index) in menuList" :key="index" :item="item" @click="handleClickMenu" />
			</el-menu>
		</el-aside>
		<el-container>
			<el-header>
				<Breadcrumb />

				<div class="right-container">
					<el-button size="large" icon="FullScreen" circle @click="handleFullScreen"></el-button>
					<el-dropdown @command="handleCommand">
						<span class="header-dropdown">
							<el-avatar :src="avatar" alt="" @error="() => true">
								<img alt="" src="@/assets/imgs/profile.png" />
							</el-avatar>
							<span class="header-username">{{ username }}</span>
							<el-icon class="el-icon--right">
								<ArrowDown />
							</el-icon>
						</span>
						<template #dropdown>
							<el-dropdown-menu>
								<el-dropdown-item @click.native.prevent="toFrontend">
									<span>前台</span>
								</el-dropdown-item>
								<el-dropdown-item divided command="profile">
									<span>个人中心</span>
								</el-dropdown-item>
								<el-dropdown-item divided command="logout">
									<span>退出登录</span>
								</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
				</div>
			</el-header>

			<el-main>
				<router-view />
			</el-main>

			<el-footer class="hidden-md-and-up">
				<el-menu :default-active="menu" mode="horizontal" router>
					<div v-for="(item, index) in menuList" :key="index">
						<el-sub-menu v-if="item?.children?.length > 0" v-show="item.hidden" :index="item.path">
							<template #title>
								<el-icon>
									<component :is="item.icon" />
								</el-icon>
								<span>{{ item.name }}</span>
							</template>
							<el-menu-item
								v-for="(item, index) in item.children"
								v-show="item.visible"
								:key="index"
								:index="item.path"
								@click="handleClickMenu(item.path)">
								<el-icon>
									<component :is="item.icon" />
								</el-icon>
								{{ item.name }}
							</el-menu-item>
						</el-sub-menu>
						<el-menu-item v-else v-show="item.visible" :index="item.path" @click="handleClickMenu(item.path)">
							<el-icon>
								<component :is="item.icon" />
							</el-icon>
							<span>{{ item.name }}</span>
						</el-menu-item>
					</div>
				</el-menu>
			</el-footer>
		</el-container>
	</el-container>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import useUserStore from '@/store/modules/user.js'
import useBreadcrumbStore from '@/store/modules/breadcrumb.js'
import useNavigationStore from '@/store/modules/navigation.js'
import { getRoleList } from '@/api/role.js'
import useRoleStore from '@/store/modules/role.js'
import { ElMessageBox } from 'element-plus'
import usePermissionStore from '@/store/modules/permission.js'

const router = useRouter()
const userStore = useUserStore()
const breadcrumbStore = useBreadcrumbStore()
const navigationStore = useNavigationStore()
const roleStore = useRoleStore()
const permissionStore = usePermissionStore()

const title = ref(import.meta.env.VITE_APP_TITLE || '后台管理系统')
// const menu = ref(navigationStore.menu)
const menu = ref(router.currentRoute.value.fullPath)
const menuList = computed(() => permissionStore.sidebarRouters)
const isCollapse = ref(false)
const username = ref(userStore.username)
const avatar = computed(() => import.meta.env.VITE_APP_BASE_API + userStore.avatar)

const handleClickMenu = item => {
	console.log(item)
}

const handleCollapse = () => {
	isCollapse.value = !isCollapse.value
}

const handleFullScreen = () => {
	const full = document.fullscreenElement
	if (!full) {
		document.documentElement.requestFullscreen()
	} else {
		document.exitFullscreen()
	}
}

const toFrontend = () => {
	router.push('/')
}

const handleCommand = value => {
	switch (value) {
		case 'profile':
			router.push('/profile')
			break
		case 'logout':
			ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				userStore.handleLogout().then(() => {
					location.href = router.resolve('/index').href
				})
			})
			break
		default:
			break
	}
}

const getRole = () => {
	getRoleList({}).then(res => {
		roleStore.setRoleList(res.data)
	})
}

router.afterEach((to, from) => {
	console.log(from)
	breadcrumbStore.setItemList(to)
	navigationStore.setMenu(to.path)
})

onMounted(() => {
	getRole()
})

watch(
	() => router,
	(newValue, oldValue) => {
		console.log(oldValue)
		menu.value = newValue.currentRoute.value.fullPath
	},
	{ immediate: true }
)
</script>

<style lang="scss" scoped>
.el-container {
	height: 100%;

	.el-aside {
		width: 256px;
		height: 100%;

		&.el-aside--collapse {
			width: 64px;
		}

		.el-menu {
			height: 100%;
			border-right: none;

			&.el-menu--collapse {
				.title {
					display: none;
				}
			}

			.menu-header {
				display: flex;
				justify-content: center;
				align-items: center;
				font-size: 24px;
				padding: 16px;
				gap: 8px;

				.title {
					font-family: '微软雅黑', sans-serif;
					font-weight: bolder;
					height: 60px;
					line-height: 60px;
					text-align: center;
				}

				.el-icon:hover {
					color: #409eff;
				}
			}
		}

		&::-webkit-scrollbar {
			display: none;
		}
	}

	.el-container {
		.el-header {
			box-shadow: 0 0 12px rgba(0, 0, 0, 0.12);
			display: flex;
			align-items: center;
			justify-content: space-between;

			.right-container {
				display: flex;
				align-items: center;
				gap: 16px;

				.el-dropdown {
					.header-dropdown {
						display: flex;
						align-items: center;
						user-select: none;

						.header-username {
							margin-left: 10px;
							margin-right: 10px;
						}
					}
				}
			}
		}

		.el-main {
			background-color: #f3f8ff;
			padding: 16px;
		}
	}
}
</style>
