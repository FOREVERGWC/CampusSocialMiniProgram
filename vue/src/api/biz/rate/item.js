import request from '@/utils/request.js'

/**
 * 添加、修改评分项
 * @param data 评分项
 * @returns {*} 结果
 */
export const saveRateItem = data => {
	return request({
		url: '/rate/item',
		method: 'POST',
		data: data
	})
}

/**
 * 删除评分项
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRateItemBatchByIds = ids => {
	return request({
		url: `/rate/item/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询评分项列表
 * @param params 评分项
 * @returns {*} 结果
 */
export const getRateItemList = params => {
	return request({
		url: '/rate/item/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评分项分页
 * @param params 评分项
 * @returns {*} 结果
 */
export const getRateItemPage = params => {
	return request({
		url: '/rate/item/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询评分项
 * @param params 评分项
 * @returns {*} 结果
 */
export const getRateItemOne = params => {
	return request({
		url: '/rate/item',
		method: 'GET',
		params: params
	})
}
