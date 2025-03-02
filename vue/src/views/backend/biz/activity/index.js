export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
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
			label: '类别',
			field: 'categoryId',
			props: {
				type: 'biz_activity_category,name,id'
			}
		},
		{
			type: 'date-picker',
			label: '活动时间',
			field: 'activityDateTimeRange',
			props: {
				type: 'datetimerange',
				startPlaceholder: '活动开始时间',
				endPlaceholder: '活动结束时间',
				valueFormat: 'YYYY-MM-DD HH:mm:ss',
				unlinkPanels: true
			}
		},
		{
			type: 'input',
			label: '地点',
			field: 'location'
		}
	]
}
