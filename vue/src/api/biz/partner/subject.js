import request from '@/utils/request.js';

/**
 * 添加、修改组局主题
 * @param data 组局主题
 * @returns {*} 结果
 */
export const savePartnerSubject = (data) => {
    return request({
        url: '/partner/subject', method: 'POST', data: data
    })
}

/**
 * 删除组局主题
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removePartnerSubjectBatchByIds = (ids) => {
    return request({
        url: `/partner/subject/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询组局主题列表
 * @param params 组局主题
 * @returns {*} 结果
 */
export const getPartnerSubjectList = (params) => {
    return request({
        url: '/partner/subject/list', method: 'GET', params: params
    })
}

/**
 * 查询组局主题分页
 * @param params 组局主题
 * @returns {*} 结果
 */
export const getPartnerSubjectPage = (params) => {
    return request({
        url: '/partner/subject/page', method: 'GET', params: params
    })
}

/**
 * 查询组局主题
 * @param params 组局主题
 * @returns {*} 结果
 */
export const getPartnerSubjectOne = (params) => {
    return request({
        url: '/partner/subject', method: 'GET', params: params
    })
}
