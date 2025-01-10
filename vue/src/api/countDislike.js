import request from '@/utils/request';

/**
 * 添加、修改点踩量
 * @param data 点踩量
 * @returns {*} 结果
 */
export const saveCountDislike = (data) => {
    return request({
        url: '/countDislike', method: 'POST', data: data
    })
}

/**
 * 删除点踩量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountDislikeBatchByIds = (ids) => {
    return request({
        url: `/countDislike/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询点踩量列表
 * @param params 点踩量
 * @returns {*} 结果
 */
export const getCountDislikeList = (params) => {
    return request({
        url: '/countDislike/list', method: 'GET', params: params
    })
}

/**
 * 查询点踩量分页
 * @param params 点踩量
 * @returns {*} 结果
 */
export const getCountDislikePage = (params) => {
    return request({
        url: '/countDislike/page', method: 'GET', params: params
    })
}

/**
 * 查询点踩量
 * @param params 点踩量
 * @returns {*} 结果
 */
export const getCountDislikeOne = (params) => {
    return request({
        url: '/countDislike', method: 'GET', params: params
    })
}
