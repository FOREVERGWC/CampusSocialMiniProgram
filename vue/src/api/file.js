import request from '@/utils/request'

/**
 * 校验文件
 * @param params 参数
 * @returns {*} 结果
 */
export const checkFile = params => {
	return request({
		url: '/file/check',
		method: 'GET',
		params: params
	})
}

/**
 * 上传文件
 * @param data 文件
 * @returns {*} 结果
 */
export const uploadFile = data => {
	return request({
		url: '/file/upload',
		method: 'POST',
		data: data,
		headers: {
			'Content-Type': 'multipart/form-data'
		}
	})
}
