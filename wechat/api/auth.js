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