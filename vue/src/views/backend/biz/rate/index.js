export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
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
			label: '标题',
			field: 'title'
		},
		{
			type: 'input',
			label: '内容',
			field: 'content'
		}
	]
}
