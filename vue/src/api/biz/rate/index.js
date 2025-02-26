import request from '@/utils/request.js'

/**
 * 添加、修改评分
 * @param data 评分
 * @returns {*} 结果
 */
export const saveRate = data => {
	return request({
		url: '/rate',
		method: 'POST',
		data: data
	})
}

/**
 * 删除评分
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRateBatchByIds = ids => {
	return request({
		url: `/rate/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询评分列表
 * @param params 评分
 * @returns {*} 结果
 */
export const getRateList = params => {
	return request({
		url: '/rate/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评分分页
 * @param params 评分
 * @returns {*} 结果
 */
export const getRatePage = params => {
	return request({
		url: '/rate/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评分
 * @param params 评分
 * @returns {*} 结果
 */
export const getRateOne = params => {
	return request({
		url: '/rate',
		method: 'GET',
		params: params
	})
}
