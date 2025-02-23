// components/my-swiper/index.js
Component({
  options: {
    styleIsolation: 'apply-shared'
  },

  /**
   * 组件的属性列表
   */
  properties: {
    attachmentList: {
      type: Array,
      value: []
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    height: '375rpx'
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getHeight(e) {
      const width = wx.getWindowInfo().windowWidth
      const h = e.detail.height
      const w = e.detail.width
      const height = width * h / w + 'px'
      this.setData({
        height: height
      })
    }
  }
})