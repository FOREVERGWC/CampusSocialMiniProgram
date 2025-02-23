import request from '@/utils/request.js';

/**
 * 添加、修改评分记录
 * @param data 评分记录
 * @returns {*} 结果
 */
export const saveRateRecord = (data) => {
    return request({
        url: '/rate/record', method: 'POST', data: data
    })
}

/**
 * 删除评分记录
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeRateRecordBatchByIds = (ids) => {
    return request({
        url: `/rate/record/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询评分记录列表
 * @param params 评分记录
 * @returns {*} 结果
 */
export const getRateRecordList = (params) => {
    return request({
        url: '/rate/record/list', method: 'GET', params: params
    })
}

/**
 * 查询评分记录分页
 * @param params 评分记录
 * @returns {*} 结果
 */
export const getRateRecordPage = (params) => {
    return request({
        url: '/rate/record/page', method: 'GET', params: params
    })
}

/**
 * 查询评分记录
 * @param params 评分记录
 * @returns {*} 结果
 */
export const getRateRecordOne = (params) => {
    return request({
        url: '/rate/record', method: 'GET', params: params
    })
}
