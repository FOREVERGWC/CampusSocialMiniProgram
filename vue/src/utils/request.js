import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'
import useUserStore from '@/store/modules/user.js'

const request = axios.create({
	baseURL: '/api',
	timeout: 30000
})

/**
 * 请求拦截器
 */
request.interceptors.request.use(
	config => {
		const userStore = useUserStore()
		if (!config.headers['Content-Type']) {
			config.headers['Content-Type'] = 'application/json;charset=utf-8'
		}
		config.headers['token'] = userStore.token
		if (config.method === 'get' && config.params) {
			let url = config.url + '?' + tansParams(config.params)
			url = url.slice(0, -1)
			config.params = {}
			config.url = url
		}
		return config
	},
	error => {
		console.error(`请求错误：${error}`)
		return Promise.reject(error)
	}
)

/**
 * 响应拦截器
 */
request.interceptors.response.use(
	response => {
		const res = response.data
		if (response.request.responseType === 'blob' || response.request.responseType === 'arraybuffer') {
			return response
		}
		if (res.code === 401) {
			ElMessage.error(res.msg)
			useUserStore()
				.handleLogout()
				.then(() => {
					location.href = router.resolve('/index').href
				})
			// router.push('/login')
		}
		return res
	},
	error => {
		console.error(`响应错误：${error}`)
		return Promise.reject(error)
	}
)

/**
 * 参数处理
 * @param params 参数
 * @returns {string} 字符串
 */
export const tansParams = params => {
	let result = ''
	for (const propName of Object.keys(params)) {
		const value = params[propName]
		const part = encodeURIComponent(propName) + '='
		if (value !== null && value !== '' && typeof value !== 'undefined') {
			if (typeof value === 'object') {
				for (const key of Object.keys(value)) {
					if (value[key] !== null && value[key] !== '' && typeof value[key] !== 'undefined') {
						const params = propName + '[' + key + ']'
						const subPart = encodeURIComponent(params) + '='
						result += subPart + encodeURIComponent(value[key]) + '&'
					}
				}
			} else {
				result += part + encodeURIComponent(value) + '&'
			}
		}
	}
	return result
}

export default request
