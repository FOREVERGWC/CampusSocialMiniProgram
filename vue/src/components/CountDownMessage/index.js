import { ElMessage } from 'element-plus'

export const CountDownMessage = (type, timer = 3, message, onClose) => {
	const random = Math.floor(Math.random() * 10000)
	const customClass = `custom_${random}`
	const item = ElMessage({
		message: message.replace('{}', timer),
		type: type,
		duration: 0,
		customClass: customClass
	})

	const parentDom = document.getElementsByClassName(customClass)[0]
	const childDom = parentDom.querySelectorAll('p')[0]
	const countdown = setInterval(() => {
		childDom.innerHTML = message.replace('{}', --timer)
		if (timer <= 0) {
			clearInterval(countdown)
			onClose()
			item.close()
		}
	}, 1000)
}
