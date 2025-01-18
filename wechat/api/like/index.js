import request from '../../utils/request';

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