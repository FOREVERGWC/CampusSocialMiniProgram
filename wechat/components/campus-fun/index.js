// components/campus-fun/index.js
import {
  baseUrl
} from '../../utils/common'

Component({
  options: {
    styleIsolation: 'apply-shared'
  },

  /**
   * 组件的属性列表
   */
  properties: {
    records: {
      type: Array,
      value: []
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    baseUrl: baseUrl
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onToDetail(event) {
      const id = event.currentTarget.dataset.id;
      wx.navigateTo({
        url: `/pages/note/detail/index?id=${id}`
      });
    }
  }
})