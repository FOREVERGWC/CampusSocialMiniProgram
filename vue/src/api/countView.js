import request from '@/utils/request';

/**
 * 添加、修改浏览量
 * @param data 浏览量
 * @returns {*} 结果
 */
export const saveCountView = (data) => {
    return request({
        url: '/countView', method: 'POST', data: data
    })
}

/**
 * 删除浏览量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountViewBatchByIds = (ids) => {
    return request({
        url: `/countView/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询浏览量列表
 * @param params 浏览量
 * @returns {*} 结果
 */
export const getCountViewList = (params) => {
    return request({
        url: '/countView/list', method: 'GET', params: params
    })
}

/**
 * 查询浏览量分页
 * @param params 浏览量
 * @returns {*} 结果
 */
export const getCountViewPage = (params) => {
    return request({
        url: '/countView/page', method: 'GET', params: params
    })
}

/**
 * 查询浏览量
 * @param params 浏览量
 * @returns {*} 结果
 */
export const getCountViewOne = (params) => {
    return request({
        url: '/countView', method: 'GET', params: params
    })
}
