import request from "@/utils/request";

/**
 * PC端登录
 * @param data PC端登录请求体
 * @returns {*} 结果
 */
export const login = (data) => {
    return request({
        url: '/login', method: 'POST', data: data
    })
}

/**
 * 注册用户
 * @param data 用户信息
 * @returns {*} 结果
 */
export const register = (data) => {
    return request({
        url: '/register', method: 'POST', data: data
    })
}

/**
 * 退出登录
 * @returns {*} 结果
 */
export const logout = () => {
    return request({
        url: '/logout', method: 'POST'
    })
}

/**
 * 获取当前用户信息
 * @returns {*} 结果
 */
export const getByToken = () => {
    return request({
        url: '/token', method: 'GET'
    })
}

/**
 * 获取当前用户路由信息
 * @returns {*} 结果
 */
export const getRoute = () => {
    return request({
        url: '/route', method: 'GET'
    })
}

/**
 * 获取验证码图片
 * @returns {*} 结果
 */
export const getCaptcha = () => {
    return request({
        url: '/captcha', method: 'GET'
    })
}

/**
 * 重置密码
 * @param data 密码信息
 * @returns {*} 结果
 */
export const resetPassword = (data) => {
    return request({
        url: '/password/reset', method: 'PUT', data: data
    })
}