import request from '@/utils/request.js';

/**
 * 添加、修改组局
 * @param data 组局
 * @returns {*} 结果
 */
export const savePartner = (data) => {
    return request({
        url: '/partner', method: 'POST', data: data
    })
}

/**
 * 删除组局
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removePartnerBatchByIds = (ids) => {
    return request({
        url: `/partner/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询组局列表
 * @param params 组局
 * @returns {*} 结果
 */
export const getPartnerList = (params) => {
    return request({
        url: '/partner/list', method: 'GET', params: params
    })
}

/**
 * 查询组局分页
 * @param params 组局
 * @returns {*} 结果
 */
export const getPartnerPage = (params) => {
    return request({
        url: '/partner/page', method: 'GET', params: params
    })
}

/**
 * 查询组局
 * @param params 组局
 * @returns {*} 结果
 */
export const getPartnerOne = (params) => {
    return request({
        url: '/partner', method: 'GET', params: params
    })
}
