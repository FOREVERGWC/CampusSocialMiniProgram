import request from "../utils/request";

/**
 * 登录
 * @param data 登录请求体
 * @returns {*} 结果
 */
export const login = (data) => {
  return request({
    url: '/login',
    method: 'POST',
    data: data
  })
}

/**
 * 获取当前用户信息
 * @returns {*} 结果
 */
export const getByToken = () => {
  return request({
    url: '/token',
    method: 'GET'
  })
}