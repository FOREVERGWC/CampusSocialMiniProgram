import request from '@/utils/request'

/**
 * 添加、修改权限
 * @param data 权限
 * @returns {*} 结果
 */
export const savePermission = data => {
	return request({
		url: '/permission',
		method: 'POST',
		data: data
	})
}

/**
 * 删除权限
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removePermissionBatchByIds = ids => {
	return request({
		url: `/permission/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询权限列表
 * @param params 权限
 * @returns {*} 结果
 */
export const getPermissionList = params => {
	return request({
		url: '/permission/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询权限树
 * @param params 权限
 * @returns {*} 结果
 */
export const getPermissionTree = params => {
	return request({
		url: '/permission/tree',
		method: 'GET',
		params: params
	})
}

/**
 * 查询权限分页
 * @param params 权限
 * @returns {*} 结果
 */
export const getPermissionPage = params => {
	return request({
		url: '/permission/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询权限
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getPermissionById = id => {
	return request({
		url: `/permission/${id}`,
		method: 'GET'
	})
}

/**
 * 查询权限
 * @param params 权限
 * @returns {*} 结果
 */
export const getPermissionOne = params => {
	return request({
		url: '/permission',
		method: 'GET',
		params: params
	})
}

/**
 * 导出权限
 * @param params 权限
 * @returns {*} 结果
 */
export const exportPermissionExcel = params => {
	return request({
		url: '/permission/export',
		method: 'GET',
		responseType: 'blob',
		params: params
	})
}

/**
 * 恢复或停用权限
 * @param id 权限ID
 * @returns {*} 结果
 */
export const handleStatusPermission = id => {
	return request({
		url: `/permission/status/${id}`,
		method: 'PUT'
	})
}

/**
 * 查询角色权限树
 * @param roleId 角色ID
 * @returns {*} 结果
 */
export const getPermissionListByRoleId = roleId => {
	return request({
		url: `/permission/role/${roleId}`,
		method: 'GET'
	})
}
