import request from '@/utils/request'

/**
 * 添加、修改登录日志
 * @param data 登录日志
 * @returns {*} 结果
 */
export const saveLogLogin = data => {
	return request({
		url: '/log/login',
		method: 'POST',
		data: data
	})
}

/**
 * 删除登录日志
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeLogLoginBatchByIds = ids => {
	return request({
		url: `/log/login/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询登录日志列表
 * @param params 登录日志
 * @returns {*} 结果
 */
export const getLogLoginList = params => {
	return request({
		url: '/log/login/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询登录日志分页
 * @param params 登录日志
 * @returns {*} 结果
 */
export const getLogLoginPage = params => {
	return request({
		url: '/log/login/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询登录日志
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getLogLoginById = id => {
	return request({
		url: `/log/login/${id}`,
		method: 'GET'
	})
}

/**
 * 查询登录日志
 * @param params 登录日志
 * @returns {*} 结果
 */
export const getLogLoginOne = params => {
	return request({
		url: '/log/login',
		method: 'GET',
		params: params
	})
}

/**
 * 导出登录日志
 * @param params 登录日志
 * @returns {*} 结果
 */
export const exportLogLoginExcel = params => {
	return request({
		url: '/log/login/export',
		method: 'GET',
		responseType: 'blob',
		params: params
	})
}
