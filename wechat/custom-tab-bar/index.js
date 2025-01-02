// custom-tab-bar/index.js
Component({

  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    value: 0,
    tabBar: [{
        icon: 'home',
        label: '首页',
        url: "/pages/index/index"
      },
      {
        icon: 'app',
        label: '分类',
        url: "/pages/note/category/index"
      },
      {
        icon: 'plus',
        label: '',
        url: "/pages/note/save/index"
      },
      {
        icon: 'chat',
        label: '消息',
        count: 4,
        url: "/pages/message/index"
      },
      {
        icon: 'user',
        label: '我',
        url: "/pages/my/index"
      }
    ]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onChange(e) {
      wx.switchTab({
        url: this.data.tabBar[e.detail.value].url
      });
    },
    init() {
      const page = getCurrentPages().pop();
      this.setData({
        value: this.data.tabBar.findIndex(item => item.url === `/${page.route}`)
      });
    }
  }
})