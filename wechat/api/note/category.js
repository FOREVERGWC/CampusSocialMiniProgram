import request from '../../utils/request';

/**
 * 添加、修改笔记类别
 * @param data 笔记类别
 * @returns {*} 结果
 */
export const saveNoteCategory = (data) => {
  return request({
    url: '/note/category',
    method: 'POST',
    data: data
  })
}

/**
 * 删除笔记类别
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeNoteCategoryBatchByIds = (ids) => {
  return request({
    url: `/note/category/${ids}`,
    method: 'DELETE'
  })
}

/**
 * 查询笔记类别列表
 * @param params 笔记类别
 * @returns {*} 结果
 */
export const getNoteCategoryList = (params) => {
  return request({
    url: '/note/category/list',
    method: 'GET',
    params: params
  })
}

/**
 * 查询笔记类别分页
 * @param params 笔记类别
 * @returns {*} 结果
 */
export const getNoteCategoryPage = (params) => {
  return request({
    url: '/note/category/page',
    method: 'GET',
    params: params
  })
}

/**
 * 查询笔记类别
 * @param params 笔记类别
 * @returns {*} 结果
 */
export const getNoteCategoryOne = (params) => {
  return request({
    url: '/note/category',
    method: 'GET',
    params: params
  })
}