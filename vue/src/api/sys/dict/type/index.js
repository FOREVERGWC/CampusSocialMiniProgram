import request from '@/utils/request.js'

/**
 * 添加、修改字典类型
 * @param data 字典类型
 * @returns {*} 结果
 */
export const saveDictType = data => {
	return request({
		url: '/dict/type',
		method: 'POST',
		data: data
	})
}

/**
 * 删除字典类型
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeDictTypeBatchByIds = ids => {
	return request({
		url: `/dict/type/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询字典类型列表
 * @param params 字典类型
 * @returns {*} 结果
 */
export const getDictTypeList = params => {
	return request({
		url: '/dict/type/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询字典类型分页
 * @param params 字典类型
 * @returns {*} 结果
 */
export const getDictTypePage = params => {
	return request({
		url: '/dict/type/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询字典类型
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getDictTypeById = id => {
	return request({
		url: `/dict/type/${id}`,
		method: 'GET'
	})
}

/**
 * 查询字典类型
 * @param params 字典类型
 * @returns {*} 结果
 */
export const getDictTypeOne = params => {
	return request({
		url: '/dict/type',
		method: 'GET',
		params: params
	})
}

/**
 * 导出字典类型
 * @param params 字典类型
 * @returns {*} 结果
 */
export const exportDictTypeExcel = params => {
	return request({
		url: '/dict/type/export',
		method: 'GET',
		responseType: 'blob',
		params: params
	})
}

/**
 * 恢复或停用字典类型
 * @param id 字典类型ID
 * @returns {*} 结果
 */
export const handleStatusDictType = id => {
	return request({
		url: `/dict/type/status/${id}`,
		method: 'PUT'
	})
}
