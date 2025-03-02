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
			label: '类别',
			field: 'categoryId',
			props: {
				type: 'biz_note_category,name,id'
			}
		},
		{
			type: 'select',
			label: '是否置顶',
			field: 'top',
			options: topList
		},
		{
			type: 'select',
			label: '是否可见',
			field: 'visible',
			options: visibleList
		},
		{
			type: 'select',
			label: '是否允许评论',
			field: 'commentable',
			options: commentableList
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		}
	]
}
