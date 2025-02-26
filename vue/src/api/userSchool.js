import request from '@/utils/request'

/**
 * 添加、修改用户学校
 * @param data 用户学校
 * @returns {*} 结果
 */
export const saveUserSchool = data => {
	return request({
		url: '/userSchool',
		method: 'POST',
		data: data
	})
}

/**
 * 删除用户学校
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeUserSchoolBatchByIds = ids => {
	return request({
		url: `/userSchool/${ids}`,
		method: 'DELETE'
	})
}

/**
 * 查询用户学校列表
 * @param params 用户学校
 * @returns {*} 结果
 */
export const getUserSchoolList = params => {
	return request({
		url: '/userSchool/list',
		method: 'GET',
		params: params
	})
}

/**
 * 查询用户学校分页
 * @param params 用户学校
 * @returns {*} 结果
 */
export const getUserSchoolPage = params => {
	return request({
		url: '/userSchool/page',
		method: 'GET',
		params: params
	})
}

/**
 * 查询用户学校
 * @param params 用户学校
 * @returns {*} 结果
 */
export const getUserSchoolOne = params => {
	return request({
		url: '/userSchool',
		method: 'GET',
		params: params
	})
}
