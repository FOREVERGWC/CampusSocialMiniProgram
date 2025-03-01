import request from '../../utils/request';

/**
 * 根据邮箱发送注册验证码邮件
 * @param data 注册邮箱
 * @returns {*} 结果
 */
export const sendRegisterCode = data => {
  return request({
    url: '/email/register',
    method: 'POST',
    data: data
  })
}

/**
 * 根据邮箱发送改密验证码邮件
 * @param data 注册邮箱
 * @returns {*} 结果
 */
export const sendResetCode = data => {
  return request({
    url: '/email/reset',
    method: 'POST',
    data: data
  })
}

/**
 * 根据邮箱发送登录验证码邮件
 * @param data 注册邮箱
 * @returns {*} 结果
 */
export const sendLoginCode = data => {
  return request({
    url: '/email/login',
    method: 'POST',
    data: data
  })
}