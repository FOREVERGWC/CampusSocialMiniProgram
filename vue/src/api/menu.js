import request from '@/utils/request';

/**
 * 添加、修改菜单
 * @param data 菜单
 * @returns {*} 结果
 */
export const saveMenu = (data) => {
    return request({
        url: '/menu', method: 'POST', data: data
    })
}

/**
 * 删除菜单
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeMenuBatchByIds = (ids) => {
    return request({
        url: `/menu/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询菜单列表
 * @param params 菜单
 * @returns {*} 结果
 */
export const getMenuList = (params) => {
    return request({
        url: '/menu/list', method: 'GET', params: params
    })
}

/**
 * 查询菜单树
 * @param params 菜单
 * @returns {*} 结果
 */
export const getMenuTree = (params) => {
    return request({
        url: '/menu/tree', method: 'GET', params: params
    })
}

/**
 * 查询菜单分页
 * @param params 菜单
 * @returns {*} 结果
 */
export const getMenuPage = (params) => {
    return request({
        url: '/menu/page', method: 'GET', params: params
    })
}

/**
 * 查询菜单
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getMenuById = (id) => {
    return request({
        url: `/menu/${id}`, method: 'GET'
    })
}

/**
 * 查询菜单
 * @param params 菜单
 * @returns {*} 结果
 */
export const getMenuOne = (params) => {
    return request({
        url: '/menu', method: 'GET', params: params
    })
}

/**
 * 导出菜单
 * @param params 菜单
 * @returns {*} 结果
 */
export const exportMenuExcel = (params) => {
    return request({
        url: '/menu/export', method: 'GET', responseType: 'blob', params: params
    })
}

/**
 * 恢复或停用菜单
 * @param id 菜单ID
 * @returns {*} 结果
 */
export const handleStatusMenu = (id) => {
    return request({
        url: `/menu/status/${id}`,
        method: 'PUT'
    })
}

/**
 * 显示或隐藏菜单
 * @param id 菜单ID
 * @returns {*} 结果
 */
export const handleVisibleMenu = (id) => {
    return request({
        url: `/menu/visible/${id}`,
        method: 'PUT'
    })
}

/**
 * 查询角色菜单树
 * @param roleId 角色ID
 * @returns {*} 结果
 */
export const getMenuListByRoleId = (roleId) => {
    return request({
        url: `/menu/role/${roleId}`,
        method: 'GET'
    })
}
