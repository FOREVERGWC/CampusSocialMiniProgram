import request from '../../utils/request';

/**
 * 查询我的点赞分页
 * @param params 点赞
 * @returns {*} 结果
 */
export const getMyLikePage = (params) => {
  return request({
    url: '/like/my',
    method: 'GET',
    params: params
  })
}

/**
 * 点赞或取消点赞
 * @param {*} data  点赞
 */
export const handleLike = (data) => {
  return request({
    url: `/like`,
    method: 'PUT',
    data: data
  })
}