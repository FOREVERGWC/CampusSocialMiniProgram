import _ from 'lodash-es'
import { tansParams } from '@/utils/request.js'
import useUserStore from '@/store/modules/user.js'

export const statusList = [
	{ label: '禁用', value: '0' },
	{ label: '正常', value: '1' }
]

export const genderList = [
	{ label: '女', value: '0' },
	{ label: '男', value: '1' },
	{ label: '未知', value: '2' }
]

export const brandList = [
	{ id: 1, name: '奔驰' },
	{ id: 2, name: '宝马' },
	{ id: 3, name: '奥迪' },
	{ id: 4, name: '本田' },
	{ id: 5, name: '保时捷' },
	{ id: 6, name: '桑塔纳' },
	{ id: 7, name: '五菱宏光' },
	{ id: 8, name: '比亚迪' },
	{ id: 9, name: '别克' },
	{ id: 10, name: '宾利' },
	{ id: 11, name: '凯迪拉克' },
	{ id: 12, name: '东风' },
	{ id: 13, name: '大众' },
	{ id: 14, name: '小米' }
]

export const modelMap = {
	奔驰: [
		{ id: 1, name: '奔驰A级' },
		{ id: 2, name: '奔驰C级' },
		{ id: 3, name: '奔驰E级' },
		{ id: 4, name: '奔驰GLA' },
		{ id: 5, name: '奔驰GLB' },
		{ id: 6, name: '奔驰GLC' },
		{ id: 7, name: '奔驰A级AMG' },
		{ id: 8, name: '奔驰EQA' },
		{ id: 9, name: '奔驰EQB' },
		{ id: 10, name: '奔驰EQC' },
		{ id: 11, name: '奔驰EQE' },
		{ id: 12, name: '奔驰C级新能源' },
		{ id: 13, name: '奔驰E级新能源' }
	],
	宝马: [
		{ id: 1, name: '1系' },
		{ id: 2, name: '2系' },
		{ id: 3, name: '3系' },
		{ id: 4, name: '4系' },
		{ id: 5, name: '5系' },
		{ id: 6, name: '6系' },
		{ id: 7, name: '7系' },
		{ id: 8, name: 'X1' },
		{ id: 9, name: 'X2' },
		{ id: 10, name: 'X3' },
		{ id: 11, name: 'X4' },
		{ id: 12, name: 'X5' },
		{ id: 13, name: 'X6' },
		{ id: 14, name: 'X7' },
		{ id: 15, name: 'Z4' },
		{ id: 16, name: 'M2' },
		{ id: 17, name: 'M3' },
		{ id: 18, name: 'M4' },
		{ id: 19, name: 'M5' },
		{ id: 20, name: 'i3' },
		{ id: 21, name: 'i8' },
		{ id: 22, name: '3系GT' },
		{ id: 23, name: '5系GT' },
		{ id: 24, name: '6系GT12' }
	],
	奥迪: [
		{ id: 1, name: 'A3' },
		{ id: 2, name: 'A4' },
		{ id: 3, name: 'A5' },
		{ id: 4, name: 'A6' },
		{ id: 5, name: 'A7' },
		{ id: 6, name: 'A8' },
		{ id: 7, name: 'Q2' },
		{ id: 8, name: 'Q3' },
		{ id: 9, name: 'Q4' },
		{ id: 10, name: 'Q5' },
		{ id: 11, name: 'Q7' },
		{ id: 12, name: 'Q8' },
		{ id: 13, name: 'S' },
		{ id: 14, name: 'RS' },
		{ id: 15, name: 'e-tron' },
		{ id: 16, name: 'e-tron GT' }
	],
	4: [],
	5: [],
	6: [],
	7: [],
	比亚迪: [
		{ id: 1, name: '秦' },
		{ id: 2, name: '汉' },
		{ id: 3, name: '唐' },
		{ id: 4, name: '宋' },
		{ id: 5, name: '元' },
		{ id: 6, name: '海豚' },
		{ id: 7, name: '海豹' },
		{ id: 8, name: '海鸥' },
		{ id: 9, name: '驱逐舰05' },
		{ id: 10, name: '护卫舰07' }
	],
	9: [],
	10: [],
	11: [],
	12: [],
	13: [],
	小米: [
		{ id: 1, name: 'SU7' },
		{ id: 2, name: 'SU7 Pro' },
		{ id: 3, name: 'SU7 Max' }
	]
}
export const formatDate = datetime => {
	return _.split(datetime, ' ')[0]
}

export const formatTimestamp = timestamp => {
	const date = new Date(Number(timestamp))
	const year = date.getFullYear()
	const month = String(date.getMonth() + 1).padStart(2, '0')
	const day = String(date.getDate()).padStart(2, '0')
	const hours = String(date.getHours()).padStart(2, '0')
	const minutes = String(date.getMinutes()).padStart(2, '0')
	const seconds = String(date.getSeconds()).padStart(2, '0')
	const milliseconds = String(date.getMilliseconds()).padStart(3, '0')
	return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}.${milliseconds}`
}

/**
 * 倒叙获取年份数组
 * @param num 时间范围
 * @returns {{label: number, value: number}[]} 年份数组
 */
export const getYearRange = (num = 20) => {
	const currentYear = new Date().getFullYear()
	return Array.from({ length: num }, (_, i) => {
		const year = currentYear - i
		return { label: year, value: year }
	})
}

/**
 * 禁用今天之后的日期
 * @param date 日期
 * @returns {boolean} 结果
 */
export const disabledAfterToday = date => {
	const today = new Date()
	return date.getTime() > today.getTime()
}

/**
 * 添加范围查询
 * @param object 查询参数
 * @param dataRange 数据范围
 * @param propName 属性名称
 * @returns {*} 结果
 */
export const addDataRange = (object, dataRange, propName) => {
	console.log(object, dataRange)
	object.params =
		typeof object.params === 'object' && object.params !== null && !Array.isArray(object.params) ? object.params : {}
	dataRange = Array.isArray(dataRange) ? dataRange : []
	if (typeof propName === 'undefined') {
		object.params['start'] = dataRange[0]
		object.params['end'] = dataRange[1]
	} else {
		console.log(dataRange[0])
		object.params[`start${propName}`] = dataRange[0]
		object.params[`end${propName}`] = dataRange[1]
	}
}

/**
 * 获取需要展开的节点ID
 * @param data 数据
 * @returns {*[]} 结果
 */
export const getExpandedIds = data => {
	const ids = []
	const stack = [...data]

	while (stack.length > 0) {
		const node = stack.pop()
		if (node.children && node.children.length > 0) {
			ids.push(node.id)
			for (let i = node.children.length - 1; i >= 0; i--) {
				stack.push(node.children[i])
			}
		}
	}

	return ids
}

/**
 * 下载文件
 * @param url 下载地址
 * @param params 参数
 */
export const downloadFile = (url, params = {}) => {
	const userStore = useUserStore()
	window.open(`${import.meta.env.VITE_APP_BASE_API}${url}?${tansParams(params)}&token=${userStore.token}`, '_target')
}
