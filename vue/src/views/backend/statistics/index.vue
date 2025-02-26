<template>
	<div>
		<el-card>
			<el-select v-model="year" clearable placeholder="请选择年份" @change="handleGetBlog">
				<el-option v-for="year in yearList" :key="year" :value="year">{{ year }}</el-option>
			</el-select>
			<el-button @click="handleReset">重置</el-button>
			<el-button @click="handleClear">清空</el-button>
			<div ref="chart" style="width: 100%; height: 500px"></div>
		</el-card>
	</div>
</template>

<script>
import * as echarts from 'echarts'
import { getBlog } from '@/api/statistics.js'

export default {
	name: 'StatisticsView',
	data() {
		return {
			chart: null,
			year: new Date().getFullYear(),
			yearList: Array.from({ length: 10 }, (_, i) => new Date().getFullYear() - i),
			option: {
				title: {
					text: '博客数量'
				},
				tooltip: {},
				xAxis: {
					type: 'category',
					data: []
				},
				yAxis: {
					type: 'value'
				},
				series: [
					{
						data: [],
						type: 'line'
					}
				]
			}
		}
	},
	created() {
		this.handleGetBlog()
	},
	mounted() {
		this.initChart()
		this.updateChart()
	},
	watch: {
		'option.series': {
			handler() {
				this.updateChart()
			},
			deep: true
		}
	},
	methods: {
		initChart() {
			this.chart = echarts.init(this.$refs.chart)
		},
		updateChart() {
			this.chart.setOption(this.option)
		},
		handleGetBlog() {
			const params = {
				year: this.year
			}
			getBlog(params).then(res => {
				this.option.title.text = `${this.year !== null ? this.year + '年' : ''}博客数量统计`
				this.option.xAxis.data = res.data.map(item => item.month) || []
				this.option.series[0].data = res.data.map(item => item.count) || []
			})
		},
		handleClear() {
			this.year = null
			this.handleGetBlog()
		},
		handleReset() {
			this.year = new Date().getFullYear()
			this.handleGetBlog()
		}
	}
}
</script>

<style lang="scss" scoped></style>
