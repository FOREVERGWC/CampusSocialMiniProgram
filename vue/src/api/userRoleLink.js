import request from '@/utils/request';

/**
 * 添加、修改用户、角色关系
 * @param data 用户、角色关系
 * @returns {*} 结果
 */
export const saveUserRoleLink = (data) => {
    return request({
        url: '/user/role/link', method: 'POST', data: data
    })
}

/**
 * 删除用户、角色关系
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeUserRoleLinkBatchByIds = (ids) => {
    return request({
        url: `/user/role/link/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询用户、角色关系列表
 * @param params 用户、角色关系
 * @returns {*} 结果
 */
export const getUserRoleLinkList = (params) => {
    return request({
        url: '/user/role/link/list', method: 'GET', params: params
    })
}

/**
 * 查询用户、角色关系分页
 * @param params 用户、角色关系
 * @returns {*} 结果
 */
export const getUserRoleLinkPage = (params) => {
    return request({
        url: '/user/role/link/page', method: 'GET', params: params
    })
}

/**
 * 查询用户、角色关系
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getUserRoleLinkById = (id) => {
    return request({
        url: `/user/role/link/${id}`, method: 'GET'
    })
}

/**
 * 查询用户、角色关系
 * @param params 用户、角色关系
 * @returns {*} 结果
 */
export const getUserRoleLinkOne = (params) => {
    return request({
        url: '/user/role/link', method: 'GET', params: params
    })
}
