export const typeList = [
	{ label: '目录', value: '1' },
	{ label: '菜单', value: '2' },
	{ label: '按钮', value: '3' }
]

export const statusList = [
	{ label: '禁用', value: '0' },
	{ label: '正常', value: '1' }
]

export const visibleList = [
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
			label: '名称',
			field: 'name'
		},
		{
			type: 'select',
			label: '标题',
			field: 'title'
		},
		{
			type: 'input',
			label: '路由地址',
			field: 'path'
		},
		{
			type: 'input',
			label: '重定向地址',
			field: 'redirect'
		},
		{
			type: 'select',
			label: '类型',
			field: 'type',
			options: typeList
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		},
		{
			type: 'select',
			label: '是否可见',
			field: 'visible',
			options: visibleList
		}
	]
}
