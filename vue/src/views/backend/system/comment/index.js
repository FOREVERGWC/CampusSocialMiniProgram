export const bizTypeList = [
	{ label: '笔记', value: 8 },
	{ label: '评分', value: 9 },
	{ label: '评分项', value: 10 },
	{ label: '组局', value: 11 },
	{ label: '活动', value: 12 }
]

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
			field: 'bizType',
			options: bizTypeList
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
		}
	]
}
