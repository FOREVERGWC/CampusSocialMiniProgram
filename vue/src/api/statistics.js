import request from '@/utils/request'

/**
 * 统计博客
 * @returns {*} 结果
 */
export const getBlog = (params) => {
    return request({
        url: '/statistics/blog', method: 'GET', params: params
    })
}

/**
 * 仪表盘统计
 * @returns {*} 结果
 */
export const getDashboardInfo = () => {
    return request({
        url: '/statistics/dashboard/info', method: 'GET'
    })
}
