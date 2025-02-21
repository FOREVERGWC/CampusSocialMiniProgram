import request from '../../utils/request';

/**
 * 校验文件
 * @param params 参数
 * @returns {*} 结果
 */
export const checkFile = (params) => {
  return request({
    url: '/file/check',
    method: 'GET',
    params: params
  })
}