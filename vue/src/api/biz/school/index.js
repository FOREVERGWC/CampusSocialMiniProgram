import request from '@/utils/request.js'

/**
 * 添加、修改学校
 * @param data 学校
 * @returns {*} 结果
 */
export const saveSchool = data => {
	return request({
		url: '/school',
		method: 'POST',
		data: data
	})
}

/**
 * 删除学校
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeSchoolBatchByIds = ids => {
	return request({
		url: `/school/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询学校列表
 * @param params 学校
 * @returns {*} 结果
 */
export const getSchoolList = params => {
	return request({
		url: '/school/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询学校分页
 * @param params 学校
 * @returns {*} 结果
 */
export const getSchoolPage = params => {
	return request({
		url: '/school/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询学校
 * @param params 学校
 * @returns {*} 结果
 */
export const getSchoolOne = params => {
	return request({
		url: '/school',
		method: 'GET',
		params: params
	})
}
