import request from '@/utils/request';

/**
 * 添加、修改点赞量
 * @param data 点赞量
 * @returns {*} 结果
 */
export const saveCountLike = (data) => {
    return request({
        url: '/countLike', method: 'POST', data: data
    })
}

/**
 * 删除点赞量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountLikeBatchByIds = (ids) => {
    return request({
        url: `/countLike/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询点赞量列表
 * @param params 点赞量
 * @returns {*} 结果
 */
export const getCountLikeList = (params) => {
    return request({
        url: '/countLike/list', method: 'GET', params: params
    })
}

/**
 * 查询点赞量分页
 * @param params 点赞量
 * @returns {*} 结果
 */
export const getCountLikePage = (params) => {
    return request({
        url: '/countLike/page', method: 'GET', params: params
    })
}

/**
 * 查询点赞量
 * @param params 点赞量
 * @returns {*} 结果
 */
export const getCountLikeOne = (params) => {
    return request({
        url: '/countLike', method: 'GET', params: params
    })
}
