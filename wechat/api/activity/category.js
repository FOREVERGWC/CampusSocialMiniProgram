import request from '../../utils/request';

/**
 * 添加、修改活动类别
 * @param data 活动类别
 * @returns {*} 结果
 */
export const saveActivityCategory = (data) => {
  return request({
    url: '/activity/category',
    method: 'POST',
    data: data
  })
}

/**
 * 删除活动类别
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeActivityCategoryBatchByIds = (ids) => {
  return request({
    url: `/activity/category/${ids}`,
    method: 'DELETE'
  })
}

/**
 * 查询活动类别列表
 * @param params 活动类别
 * @returns {*} 结果
 */
export const getActivityCategoryList = (params) => {
  return request({
    url: '/activity/category/list',
    method: 'GET',
    params: params
  })
}

/**
 * 查询活动类别分页
 * @param params 活动类别
 * @returns {*} 结果
 */
export const getActivityCategoryPage = (params) => {
  return request({
    url: '/activity/category/page',
    method: 'GET',
    params: params
  })
}

/**
 * 查询活动类别
 * @param params 活动类别
 * @returns {*} 结果
 */
export const getActivityCategoryOne = (params) => {
  return request({
    url: '/activity/category',
    method: 'GET',
    params: params
  })
}