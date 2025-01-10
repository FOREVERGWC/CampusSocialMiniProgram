import request from '@/utils/request';

/**
 * 添加、修改订单
 * @param data 订单
 * @returns {*} 结果
 */
export const saveOrder = (data) => {
    return request({
        url: '/order', method: 'POST', data: data
    })
}

/**
 * 删除订单
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeOrderBatchByIds = (ids) => {
    return request({
        url: `/order/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询订单列表
 * @param params 订单
 * @returns {*} 结果
 */
export const getOrderList = (params) => {
    return request({
        url: '/order/list', method: 'GET', params: params
    })
}

/**
 * 查询订单分页
 * @param params 订单
 * @returns {*} 结果
 */
export const getOrderPage = (params) => {
    return request({
        url: '/order/page', method: 'GET', params: params
    })
}

/**
 * 查询订单
 * @param params 订单
 * @returns {*} 结果
 */
export const getOrderOne = (params) => {
    return request({
        url: '/order', method: 'GET', params: params
    })
}

/**
 * 付款
 * @param id 主键ID
 * @returns {*} 结果
 */
export const payOrder = (id) => {
    return request({
        url: `/order/pay/${id}`, method: 'POST'
    })
}

/**
 * 退款
 * @param id 主键ID
 * @returns {*} 结果
 */
export const refundOrder = (id) => {
    return request({
        url: `/order/refund/${id}`, method: 'POST'
    })
}
