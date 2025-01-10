import request from '@/utils/request.js';

/**
 * 添加、修改评论量
 * @param data 评论量
 * @returns {*} 结果
 */
export const saveCountComment = (data) => {
    return request({
        url: '/countComment', method: 'POST', data: data
    })
}

/**
 * 删除评论量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountCommentBatchByIds = (ids) => {
    return request({
        url: `/countComment/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询评论量列表
 * @param params 评论量
 * @returns {*} 结果
 */
export const getCountCommentList = (params) => {
    return request({
        url: '/countComment/list', method: 'GET', params: params
    })
}

/**
 * 查询评论量分页
 * @param params 评论量
 * @returns {*} 结果
 */
export const getCountCommentPage = (params) => {
    return request({
        url: '/countComment/page', method: 'GET', params: params
    })
}

/**
 * 查询评论量
 * @param params 评论量
 * @returns {*} 结果
 */
export const getCountCommentOne = (params) => {
    return request({
        url: '/countComment', method: 'GET', params: params
    })
}
