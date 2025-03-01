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
			label: '标签',
			field: 'label'
		},
		{
			type: 'input',
			label: '键值',
			field: 'value'
		},
		{
			type: 'dict-select',
			label: '类型',
			field: 'typeId',
			props: {
				type: 'sys_dict_type,name,id'
			}
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		}
	]
}
