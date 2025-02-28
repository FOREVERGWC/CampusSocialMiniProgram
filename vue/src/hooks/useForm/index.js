import { reactive } from 'vue'
import _ from 'lodash-es'

export const useForm = initValue => {
	const getInitValue = () => _.cloneDeep(initValue)
	const option = reactive(getInitValue())
	const resetOption = () => {
		Object.assign(option, getInitValue())
	}

	const setValue = (field, key, value) => {
		if (!option.columns.length) return
		const obj = option.columns.find(item => item.field === field)
		if (!obj) {
			console.log(`filed不存在！${field}`)
			return
		}
		obj[key] = value
	}

	const setPropsValue = (field, key, value) => {
		if (!option.columns.length) return
		const obj = option.columns.find(item => item.field === field)
		if (!obj) {
			console.log(`filed不存在！${field}`)
			return
		}
		if (!obj.props) {
			obj.props = {}
		}
		obj.props[key] = value
	}

	return {
		option,
		resetOption,
		setValue,
		setPropsValue
	}
}
