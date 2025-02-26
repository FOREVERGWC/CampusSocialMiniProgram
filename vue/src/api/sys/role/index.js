import request from '@/utils/request.js'

/**
 * 添加、修改角色
 * @param data 角色
 * @returns {*} 结果
 */
export const saveRole = data => {
	return request({
		url: '/role',
		method: 'POST',
		data: data
	})
}

/**
 * 删除角色
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRoleBatchByIds = ids => {
	return request({
		url: `/role/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询角色列表
 * @param params 角色
 * @returns {*} 结果
 */
export const getRoleList = params => {
	return request({
		url: '/role/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询角色分页
 * @param params 角色
 * @returns {*} 结果
 */
export const getRolePage = params => {
	return request({
		url: '/role/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询角色
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getRoleById = id => {
	return request({
		url: `/role/${id}`,
		method: 'GET'
	})
}

/**
 * 查询角色
 * @param params 角色
 * @returns {*} 结果
 */
export const getRoleOne = params => {
	return request({
		url: '/role',
		method: 'GET',
		params: params
	})
}

/**
 * 导出角色
 * @param params 角色
 * @returns {*} 结果
 */
export const exportRoleExcel = params => {
	return request({
		url: '/role/export',
		method: 'GET',
		responseType: 'blob',
		params: params
	})
}

/**
 * 恢复或停用角色
 * @param id 角色ID
 * @returns {*} 结果
 */
export const handleStatusRole = id => {
	return request({
		url: `/role/status/${id}`,
		method: 'PUT'
	})
}

/**
 * 角色分配菜单
 * @param data 菜单分配信息
 * @returns {*} 结果
 */
export const handleRoleMenu = data => {
	return request({
		url: '/role/menu',
		method: 'POST',
		data: data
	})
}

/**
 * 角色分配权限
 * @param data 权限分配信息
 * @returns {*} 结果
 */
export const handleRolePermission = data => {
	return request({
		url: '/role/permission',
		method: 'POST',
		data: data
	})
}
