import request from '@/utils/request';

/**
 * 添加、修改字典数据
 * @param data 字典数据
 * @returns {*} 结果
 */
export const saveDictData = (data) => {
    return request({
        url: '/dict/data', method: 'POST', data: data
    })
}

/**
 * 删除字典数据
 * @param ids ID列表
 * @returns {*} 结果
 */
export const removeDictDataBatchByIds = (ids) => {
    return request({
        url: `/dict/data/${ids}`, method: 'DELETE'
    })
}

/**
 * 查询字典数据列表
 * @param params 字典数据
 * @returns {*} 结果
 */
export const getDictDataList = (params) => {
    return request({
        url: '/dict/data/list', method: 'GET', params: params
    })
}

/**
 * 查询字典数据分页
 * @param params 字典数据
 * @returns {*} 结果
 */
export const getDictDataPage = (params) => {
    return request({
        url: '/dict/data/page', method: 'GET', params: params
    })
}

/**
 * 查询字典数据
 * @param id 主键ID
 * @returns {*} 结果
 */
export const getDictDataById = (id) => {
    return request({
        url: `/dict/data/${id}`, method: 'GET'
    })
}

/**
 * 查询字典数据
 * @param params 字典数据
 * @returns {*} 结果
 */
export const getDictDataOne = (params) => {
    return request({
        url: '/dict/data', method: 'GET', params: params
    })
}

/**
 * 导出字典数据
 * @param params 字典数据
 * @returns {*} 结果
 */
export const exportDictDataExcel = (params) => {
    return request({
        url: '/dict/data/export', method: 'GET', responseType: 'blob', params: params
    })
}

/**
 * 恢复或停用字典数据
 * @param id 字典数据ID
 * @returns {*} 结果
 */
export const handleStatusDictData = (id) => {
    return request({
        url: `/dict/data/status/${id}`,
        method: 'PUT'
    })
}