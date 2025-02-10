// pages/release/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    records: [{
        id: 1,
        title: '校园趣事',
        content: '校园趣事',
        url: '/pages/note/save/index'
      },
      {
        id: 2,
        title: '校园评分',
        content: '校园评分',
        url: '/pages/rate/save/index'
      },
      {
        id: 3,
        title: '找搭子',
        content: '找搭子',
        url: '/pages/note/save/index'
      },
      {
        id: 4,
        title: '活动',
        content: '活动',
        url: '/pages/activity/save/index'
      }
    ]
  },

  goToDetail(e) {
    wx.navigateTo({
      url: e.currentTarget.dataset.url
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getTabBar().init()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})