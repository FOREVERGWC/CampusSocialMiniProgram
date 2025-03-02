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
