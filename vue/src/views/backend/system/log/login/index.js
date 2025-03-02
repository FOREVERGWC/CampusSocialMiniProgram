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
			label: '操作系统',
			field: 'os'
		},
		{
			type: 'input',
			label: '浏览器',
			field: 'browser'
		},
		{
			type: 'input',
			label: 'IP',
			field: 'ip'
		},
		{
			type: 'input',
			label: 'IP属地',
			field: 'location'
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		},
		{
			type: 'input',
			label: '消息',
			field: 'msg'
		},
		{
			type: 'date-picker',
			label: '登录时间',
			field: 'createTimeRange',
			props: {
				type: 'datetimerange',
				startPlaceholder: '登录开始时间',
				endPlaceholder: '登录结束时间',
				valueFormat: 'YYYY-MM-DD HH:mm:ss',
				unlinkPanels: true
			}
		}
	]
}
