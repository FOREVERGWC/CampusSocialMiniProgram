import request from '@/utils/request';

/**
 * 添加、修改活动
 * @param data 活动
 * @returns {*} 结果
 */
export const saveActivity = (data) => {
    return request({
        url: '/activity', method: 'POST', data: data
    })
}

/**
 * 删除活动
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeActivityBatchByIds = (ids) => {
    return request({
        url: `/activity/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询活动列表
 * @param params 活动
 * @returns {*} 结果
 */
export const getActivityList = (params) => {
    return request({
        url: '/activity/list', method: 'GET', params: params
    })
}

/**
 * 查询活动分页
 * @param params 活动
 * @returns {*} 结果
 */
export const getActivityPage = (params) => {
    return request({
        url: '/activity/page', method: 'GET', params: params
    })
}

/**
 * 查询活动
 * @param params 活动
 * @returns {*} 结果
 */
export const getActivityOne = (params) => {
    return request({
        url: '/activity', method: 'GET', params: params
    })
}
