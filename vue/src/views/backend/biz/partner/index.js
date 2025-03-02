export const topList = [
	{ label: '是', value: true },
	{ label: '否', value: false }
]

export const visibleList = [
	{ label: '私有', value: '0' },
	{ label: '公开', value: '1' }
]

export const commentableList = [
	{ label: '是', value: true },
	{ label: '否', value: false }
]

export const statusList = [
	{ label: '未发布', value: '0' },
	{ label: '已发布', value: '1' }
]

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
		},
		{
			type: 'dict-select',
			label: '主题',
			field: 'subjectId',
			props: {
				type: 'biz_partner_subject,name,id'
			}
		},
		{
			type: 'input',
			label: '活动人数',
			field: 'num'
		},
		{
			type: 'date-picker',
			label: '截止时间',
			field: 'endTimeRange',
			props: {
				type: 'datetimerange',
				startPlaceholder: '截止时间',
				endPlaceholder: '截止时间',
				valueFormat: 'YYYY-MM-DD HH:mm:ss',
				unlinkPanels: true
			}
		}
	]
}
