import request from '@/utils/request';

/**
 * 添加、修改角色、菜单关系
 * @param data 角色、菜单关系
 * @returns {*} 结果
 */
export const saveRoleMenuLink = (data) => {
    return request({
        url: '/role/menu/link', method: 'POST', data: data
    })
}

/**
 * 删除角色、菜单关系
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRoleMenuLinkBatchByIds = (ids) => {
    return request({
        url: `/role/menu/link/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询角色、菜单关系列表
 * @param params 角色、菜单关系
 * @returns {*} 结果
 */
export const getRoleMenuLinkList = (params) => {
    return request({
        url: '/role/menu/link/list', method: 'GET', params: params
    })
}

/**
 * 查询角色、菜单关系分页
 * @param params 角色、菜单关系
 * @returns {*} 结果
 */
export const getRoleMenuLinkPage = (params) => {
    return request({
        url: '/role/menu/link/page', method: 'GET', params: params
    })
}

/**
 * 查询角色、菜单关系
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getRoleMenuLinkById = (id) => {
    return request({
        url: `/role/menu/link/${id}`, method: 'GET'
    })
}

/**
 * 查询角色、菜单关系
 * @param params 角色、菜单关系
 * @returns {*} 结果
 */
export const getRoleMenuLinkOne = (params) => {
    return request({
        url: '/role/menu/link', method: 'GET', params: params
    })
}
