export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'dict-select',
			label: '关注者',
			field: 'followerId',
			props: {
				type: 'sys_user,username,id'
			}
		},
		{
			type: 'dict-select',
			label: '被关注者',
			field: 'followedId',
			props: {
				type: 'sys_user,username,id'
			}
		}
	]
}
