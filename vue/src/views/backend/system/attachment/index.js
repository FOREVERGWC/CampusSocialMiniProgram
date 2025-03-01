export const statusList = [
	{ label: '是', value: true },
	{ label: '否', value: false }
]

export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'input',
			label: '散列值',
			field: 'hashCode'
		},
		{
			type: 'input',
			label: '业务ID',
			field: 'bizId'
		},
		{
			type: 'select',
			label: '业务类型',
			field: 'bizType',
			options: []
		},
		{
			type: 'input',
			label: '桶名',
			field: 'bucketName'
		},
		{
			type: 'input',
			label: '文件路径',
			field: 'filePath'
		},
		{
			type: 'input',
			label: '文件名称',
			field: 'fileName'
		},
		{
			type: 'input',
			label: '文件大小',
			field: 'fileSize'
		},
		{
			type: 'input',
			label: '分片数量',
			field: 'chunkTotal'
		},
		{
			type: 'input',
			label: '分片大小',
			field: 'chunkSize'
		},
		{
			type: 'select',
			label: '上传状态',
			field: 'status',
			options: statusList
		}
	]
}
