export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'input',
			label: '业务ID',
			field: 'bizId'
		},
		{
			type: 'select',
			label: '业务类型',
			field: 'bizKey',
			options: []
		},
		{
			type: 'input',
			label: '内容',
			field: 'content'
		},
		{
			type: 'input',
			label: '回复评论ID',
			field: 'replyId'
		},
		{
			type: 'dict-select',
			label: '用户',
			field: 'userId',
			props: {
				type: 'sys_user,username,id'
			}
		},
		{
			type: 'input',
			label: '操作系统',
			field: 'os'
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
		}
	]
}
