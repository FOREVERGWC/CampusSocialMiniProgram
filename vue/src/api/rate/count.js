import request from '@/utils/request.js';

/**
 * 添加、修改评分量
 * @param data 评分量
 * @returns {*} 结果
 */
export const saveCountRate = (data) => {
    return request({
        url: '/countRate', method: 'POST', data: data
    })
}

/**
 * 删除评分量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountRateBatchByIds = (ids) => {
    return request({
        url: `/countRate/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询评分量列表
 * @param params 评分量
 * @returns {*} 结果
 */
export const getCountRateList = (params) => {
    return request({
        url: '/countRate/list', method: 'GET', params: params
    })
}

/**
 * 查询评分量分页
 * @param params 评分量
 * @returns {*} 结果
 */
export const getCountRatePage = (params) => {
    return request({
        url: '/countRate/page', method: 'GET', params: params
    })
}

/**
 * 查询评分量
 * @param params 评分量
 * @returns {*} 结果
 */
export const getCountRateOne = (params) => {
    return request({
        url: '/countRate', method: 'GET', params: params
    })
}
