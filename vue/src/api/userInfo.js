import request from '@/utils/request';

/**
 * 添加、修改用户信息
 * @param data 用户信息
 * @returns {*} 结果
 */
export const saveUserInfo = (data) => {
    return request({
        url: '/userInfo', method: 'POST', data: data
    })
}

/**
 * 删除用户信息
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeUserInfoBatchByIds = (ids) => {
    return request({
        url: `/userInfo/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询用户信息列表
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserInfoList = (params) => {
    return request({
        url: '/userInfo/list', method: 'GET', params: params
    })
}

/**
 * 查询用户信息分页
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserInfoPage = (params) => {
    return request({
        url: '/userInfo/page', method: 'GET', params: params
    })
}

/**
 * 查询用户信息
 * @param params 用户信息
 * @returns {*} 结果
 */
export const getUserInfoOne = (params) => {
    return request({
        url: '/userInfo', method: 'GET', params: params
    })
}
