import request from '@/utils/request'

/**
 * 添加、修改附件
 * @param data 附件
 * @returns {*} 结果
 */
export const saveAttachment = data => {
	return request({
		url: '/attachment',
		method: 'POST',
		data: data
	})
}

/**
 * 删除附件
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeAttachmentBatchByIds = ids => {
	return request({
		url: `/attachment/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询附件列表
 * @param params 附件
 * @returns {*} 结果
 */
export const getAttachmentList = params => {
	return request({
		url: '/attachment/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询附件分页
 * @param params 附件
 * @returns {*} 结果
 */
export const getAttachmentPage = params => {
	return request({
		url: '/attachment/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询附件
 * @param params 附件
 * @returns {*} 结果
 */
export const getAttachmentOne = params => {
	return request({
		url: '/attachment',
		method: 'GET',
		params: params
	})
}
