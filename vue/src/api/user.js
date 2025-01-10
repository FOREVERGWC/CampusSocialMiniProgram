import request from '@/utils/request';

/**
 * 添加、修改用户信息
 * @param data 用户信息
 * @returns {*} 结果
 */
export const saveUser = (data) => {
    return request({
        url: '/user', method: 'POST', data: data
    })
}

/**
 * 删除用户信息
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeUserBatchByIds = (ids) => {
    return request({
        url: `/user/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询用户信息列表
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserList = (params) => {
    return request({
        url: '/user/list', method: 'GET', params: params
    })
}

/**
 * 查询用户信息分页
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserPage = (params) => {
    return request({
        url: '/user/page', method: 'GET', params: params
    })
}

/**
 * 查询用户信息
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getUserById = (id) => {
    return request({
        url: `/user`, method: 'GET'
    })
}

/**
 * 查询用户信息
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserOne = (params) => {
    return request({
        url: '/user', method: 'GET', params: params
    })
}

/**
 * 导出用户
 * @param params 用户
 * @returns {*} 结果
 */
export const exportUserExcel = (params) => {
    return request({
        url: '/user/export', method: 'GET', responseType: 'blob', params: params
    })
}

/**
 * 解禁或禁用用户
 * @param id 用户ID
 * @returns {*} 结果
 */
export const handleStatusUser = (id) => {
    return request({
        url: `/user/status/${id}`, method: 'PUT'
    })
}

/**
 * 用户分配角色
 * @param data 角色分配信息
 * @returns {*} 结果
 */
export const handleUserRole = (data) => {
    return request({
        url: '/user/role', method: 'POST', data: data
    })
}
