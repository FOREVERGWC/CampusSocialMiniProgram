<template>
	<el-form ref="formRef" :model="modelValue" v-bind="option.form" label-width="80px">
		<el-row v-bind="option.row">
			<template v-for="(item, index) in option.columns" :key="item.field">
				<el-col
					v-if="!isHidden(item.hidden)"
					v-bind="item.col || defaultCol"
					v-show="index <= (option.fold?.index || 0) || (index >= (option.fold?.index || 0) && !collapsed)">
					<el-form-item :label="item.label?.hidden ? '' : item.label?.text || item.label" :prop="item.field">
						<slot :name="item.field">
							<template v-if="item.type === 'input'">
								<el-input
									v-model="modelValue[item.field]"
									v-bind="item.props"
									clearable
									autocomplete="new"
									:placeholder="`请输入${item.label?.text || item.label}`" />
							</template>
							<template v-else-if="item.type === 'select'">
								<el-select-v2
									v-model="modelValue[item.field]"
									v-bind="item.props"
									clearable
									filterable
									:options="item.options"
									:placeholder="`请选择${item.label}`" />
							</template>
							<template v-else-if="item.type === 'date-picker'">
								<el-date-picker
									v-model="modelValue[item.field]"
									v-bind="item.props"
									type="date"
									value-format="YYYY-MM-DD"
									:placeholder="`请选择${item.label}`" />
							</template>
							<template v-else-if="item.type === 'textarea'">
								<el-input
									v-model="modelValue[item.field]"
									v-bind="item.props"
									:rows="5"
									type="textarea"
									:placeholder="`请输入${item.label}`" />
							</template>
							<template v-else-if="item.type === 'input-number'">
								<el-input-number
									v-model="modelValue[item.field]"
									v-bind="item.props"
									:placeholder="`请输入${item.label}`" />
							</template>
							<template v-else-if="item.type === 'radio-group'">
								<el-radio-group v-model="modelValue[item.field]" v-bind="item.props">
									<el-radio value="0">女</el-radio>
									<el-radio value="1">男</el-radio>
									<el-radio value="2">未知</el-radio>
								</el-radio-group>
							</template>
						</slot>
					</el-form-item>
				</el-col>
			</template>
			<el-col v-if="!isHidden(option.btns?.hidden)" v-bind="option.btns?.col || defaultCol">
				<el-form-item>
					<div style="display: flex; justify-content: space-between; flex: 1">
						<el-button icon="Search" plain type="primary" @click="emit('search')">查询</el-button>
						<el-button icon="Refresh" plain type="info" @click="emit('reset')">重置</el-button>
						<el-button
							v-if="option.fold?.enable"
							:icon="collapsed ? 'ArrowDown' : 'ArrowUp'"
							text
							@click="collapsed = !collapsed">
							{{ collapsed ? '展开' : '收起' }}
						</el-button>
					</div>
				</el-form-item>
			</el-col>
		</el-row>
	</el-form>
</template>

<script setup>
import { ref } from 'vue'

const defaultCol = { xs: 24, sm: 12, md: 8, lg: 6, xl: 4 }

const props = defineProps({
	modelValue: {
		type: Object
	},
	option: {
		type: Object
	}
})
const { modelValue, option } = props
const emit = defineEmits(['update:modelValue', 'search', 'reset'])
const collapsed = ref(true)
const formRef = ref()

const isHidden = hide => {
	if (hide === undefined) return false
	if (typeof hide === 'boolean') return hide
	if (typeof hide === 'function') {
		return hide(props.modelValue)
	}
}

defineExpose({ formRef })
</script>

<style scoped lang="scss">
.el-button,
:deep(.el-date-editor),
:deep(.el-input-number) {
	width: 100%;
}
</style>
