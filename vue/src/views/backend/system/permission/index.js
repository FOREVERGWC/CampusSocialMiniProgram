export const statusList = [
	{ label: '禁用', value: '0' },
	{ label: '正常', value: '1' }
]

export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'input',
			label: '名称',
			field: 'name'
		},
		{
			type: 'input',
			label: '权限标识',
			field: 'code'
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		}
	]
}
