import request from '@/utils/request.js';

/**
 * 添加、修改收藏
 * @param data 收藏
 * @returns {*} 结果
 */
export const saveFavorite = (data) => {
    return request({
        url: '/favorite', method: 'POST', data: data
    })
}

/**
 * 删除收藏
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeFavoriteBatchByIds = (ids) => {
    return request({
        url: `/favorite/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询收藏列表
 * @param params 收藏
 * @returns {*} 结果
 */
export const getFavoriteList = (params) => {
    return request({
        url: '/favorite/list', method: 'GET', params: params
    })
}

/**
 * 查询收藏分页
 * @param params 收藏
 * @returns {*} 结果
 */
export const getFavoritePage = (params) => {
    return request({
        url: '/favorite/page', method: 'GET', params: params
    })
}

/**
 * 查询收藏
 * @param params 收藏
 * @returns {*} 结果
 */
export const getFavoriteOne = (params) => {
    return request({
        url: '/favorite', method: 'GET', params: params
    })
}
