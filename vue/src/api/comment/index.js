import request from '@/utils/request.js'

/**
 * 添加、修改评论
 * @param data 评论
 * @returns {*} 结果
 */
export const saveComment = data => {
	return request({
		url: '/comment',
		method: 'POST',
		data: data
	})
}

/**
 * 删除评论
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCommentBatchByIds = ids => {
	return request({
		url: `/comment/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询评论列表
 * @param params 评论
 * @returns {*} 结果
 */
export const getCommentList = params => {
	return request({
		url: '/comment/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评论分页
 * @param params 评论
 * @returns {*} 结果
 */
export const getCommentPage = params => {
	return request({
		url: '/comment/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评论
 * @param params 评论
 * @returns {*} 结果
 */
export const getCommentOne = params => {
	return request({
		url: '/comment',
		method: 'GET',
		params: params
	})
}
