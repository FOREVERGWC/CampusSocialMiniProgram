export const constantRoutes = [
	{
		path: '/',
		name: 'Admin',
		meta: { title: '首页', icon: 'dashboard', hidden: true },
		redirect: '/index',
		component: () => import('@/views/backend/index.vue'),
		children: [
			{
				path: '/index',
				name: 'Index',
				meta: { title: '首页', icon: 'dashboard', hidden: true },
				component: () => import('@/views/backend/index/index.vue')
			},
			{
				path: '/profile',
				name: 'Profile',
				meta: { title: '个人中心', icon: 'Stopwatch', hidden: false },
				component: () => import('@/views/backend/profile/index.vue')
			},
			{
				path: '/alipay',
				name: 'AliPay',
				meta: { title: '付款', icon: 'Stopwatch', hidden: true },
				component: () => import('@/views/backend/pay/index.vue')
			}
		]
	},
	{
		path: '/login',
		name: 'Login',
		meta: { title: '登录', icon: 'Stopwatch', hidden: true },
		component: () => import('@/views/LoginView.vue')
	},
	{
		path: '/register',
		name: 'Register',
		meta: { title: '注册', icon: 'Stopwatch', hidden: true },
		component: () => import('@/views/RegisterView.vue')
	},
	{
		path: '/reset',
		name: 'Reset',
		meta: { title: '找回密码', icon: 'Stopwatch', hidden: true },
		component: () => import('@/views/ResetView.vue')
	},
	{
		path: '/:pathMatch(.*)*',
		name: 'NotFound',
		meta: { title: '404', icon: 'Stopwatch', hidden: true },
		component: () => import('@/views/404.vue')
	}
]

export const dynamicRoutes = []
