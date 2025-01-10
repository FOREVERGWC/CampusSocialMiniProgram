import request from '@/utils/request.js';

/**
 * 添加、修改收藏量
 * @param data 收藏量
 * @returns {*} 结果
 */
export const saveCountFavorite = (data) => {
    return request({
        url: '/countFavorite', method: 'POST', data: data
    })
}

/**
 * 删除收藏量
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeCountFavoriteBatchByIds = (ids) => {
    return request({
        url: `/countFavorite/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询收藏量列表
 * @param params 收藏量
 * @returns {*} 结果
 */
export const getCountFavoriteList = (params) => {
    return request({
        url: '/countFavorite/list', method: 'GET', params: params
    })
}

/**
 * 查询收藏量分页
 * @param params 收藏量
 * @returns {*} 结果
 */
export const getCountFavoritePage = (params) => {
    return request({
        url: '/countFavorite/page', method: 'GET', params: params
    })
}

/**
 * 查询收藏量
 * @param params 收藏量
 * @returns {*} 结果
 */
export const getCountFavoriteOne = (params) => {
    return request({
        url: '/countFavorite', method: 'GET', params: params
    })
}
