export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'dict-select',
			label: '评分',
			field: 'rateId',
			props: {
				type: 'biz_rate,title,id'
			}
		},
		{
			type: 'dict-select',
			label: '评分项',
			field: 'rateItemId',
			props: {
				type: 'biz_rate_item,title,id'
			}
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
			label: '分数',
			field: 'score'
		}
	]
}
