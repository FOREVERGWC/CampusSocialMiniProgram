import request from '../../utils/request';

/**
 * 添加、修改笔记
 * @param data 笔记
 * @returns {*} 结果
 */
export const saveNote = (data) => {
  return request({
    url: '/note',
    method: 'POST',
    data: data
  })
}

/**
 * 删除笔记
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeNoteBatchByIds = (ids) => {
  return request({
    url: `/note/${ids}`,
    method: 'DELETE'
  })
}

/**
 * 查询笔记列表
 * @param params 笔记
 * @returns {*} 结果
 */
export const getNoteList = (params) => {
  return request({
    url: '/note/list',
    method: 'GET',
    params: params
  })
}

/**
 * 查询笔记分页
 * @param params 笔记
 * @returns {*} 结果
 */
export const getNotePage = (params) => {
  return request({
    url: '/note/page',
    method: 'GET',
    params: params
  })
}

/**
 * 查询笔记
 * @param params 笔记
 * @returns {*} 结果
 */
export const getNoteOne = (params) => {
  return request({
    url: '/note',
    method: 'GET',
    params: params
  })
}

/**
 * 查询笔记
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getNoteById = (id) => {
  return request({
    url: `/note/${id}`,
    method: 'GET'
  })
}

/**
 * 置顶或取消置顶笔记
 * @param id 笔记ID
 * @returns {*} 结果
 */
export const handleTopNote = (id) => {
  return request({
    url: `/note/top/${id}`,
    method: 'PUT'
  })
}

/**
 * 允许或禁止笔记评论
 * @param id 笔记ID
 * @returns {*} 结果
 */
export const handleCommentNote = (id) => {
  return request({
    url: `/note/comment/${id}`,
    method: 'PUT'
  })
}