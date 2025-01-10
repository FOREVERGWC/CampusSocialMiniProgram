import request from '@/utils/request';

/**
 * 添加、修改角色、权限关系
 * @param data 角色、权限关系
 * @returns {*} 结果
 */
export const saveRolePermissionLink = (data) => {
    return request({
        url: '/role/permission/link', method: 'POST', data: data
    })
}

/**
 * 删除角色、权限关系
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRolePermissionLinkBatchByIds = (ids) => {
    return request({
        url: `/role/permission/link/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询角色、权限关系列表
 * @param params 角色、权限关系
 * @returns {*} 结果
 */
export const getRolePermissionLinkList = (params) => {
    return request({
        url: '/role/permission/link/list', method: 'GET', params: params
    })
}

/**
 * 查询角色、权限关系分页
 * @param params 角色、权限关系
 * @returns {*} 结果
 */
export const getRolePermissionLinkPage = (params) => {
    return request({
        url: '/role/permission/link/page', method: 'GET', params: params
    })
}

/**
 * 查询角色、权限关系
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getRolePermissionLinkById = (id) => {
    return request({
        url: `/role/permission/link/${id}`, method: 'GET'
    })
}

/**
 * 查询角色、权限关系
 * @param params 角色、权限关系
 * @returns {*} 结果
 */
export const getRolePermissionLinkOne = (params) => {
    return request({
        url: '/role/permission/link', method: 'GET', params: params
    })
}
