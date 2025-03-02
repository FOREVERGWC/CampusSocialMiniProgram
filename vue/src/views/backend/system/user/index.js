import { disabledAfterToday, statusList } from '@/utils/common.js'

export const option = {
	fold: {
		enable: true,
		index: 0
	},
	columns: [
		{
			type: 'input',
			label: '用户名',
			field: 'username'
		},
		{
			type: 'input',
			label: '姓名',
			field: 'name'
		},
		{
			type: 'date-picker',
			label: '生日',
			field: 'birthday',
			props: {
				disabledDate: disabledAfterToday
			}
		},
		{
			type: 'select',
			label: '状态',
			field: 'status',
			options: statusList
		},
		{
			type: 'dict-select',
			label: '角色',
			field: 'role',
			props: {
				type: 'sys_role,name,id'
			}
		},
		{
			type: 'input',
			label: '手机',
			field: 'phone'
		},
		{
			type: 'input',
			label: '邮箱',
			field: 'email'
		},
		{
			type: 'input',
			label: '登录IP',
			field: 'loginIp'
		},
		{
			type: 'date-picker',
			label: '登录时间',
			field: 'loginTimeRange',
			props: {
				type: 'datetimerange',
				startPlaceholder: '登录开始时间',
				endPlaceholder: '登录结束时间',
				valueFormat: 'YYYY-MM-DD HH:mm:ss',
				unlinkPanels: true
			}
		},
		{
			type: 'date-picker',
			label: '注册时间',
			field: 'createTimeRange',
			props: {
				type: 'datetimerange',
				startPlaceholder: '注册开始时间',
				endPlaceholder: '注册结束时间',
				valueFormat: 'YYYY-MM-DD HH:mm:ss',
				unlinkPanels: true
			}
		}
	]
}
