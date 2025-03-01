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
 * 注册用户
 * @param data 用户信息
 * @returns {*} 结果
 */
export const register = data => {
  return request({
    url: '/register',
    method: 'POST',
    data: data
  })
}

/**
 * 退出登录
 * @returns {*} 结果
 */
export const logout = () => {
  return request({
    url: '/logout',
    method: 'POST'
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