// components/campus-rating/index.js
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

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onToDetail(event) {
      const id = event.currentTarget.dataset.id;
      wx.navigateTo({
        url: `/pages/rate/index?id=${id}`
      });
    }
  }
})