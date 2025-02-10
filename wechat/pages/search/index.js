// pages/search/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    key: '',
    refresh: false
  },

  onRefreshTap() {
    this.setData({
      refresh: true
    })
    setTimeout(() => {
      this.setData({
        refresh: false
      })
    }, 1000)
  },

  onInput(e) {
    this.setData({
      key: e.detail.value
    })
  },

  onSearchConfirm() {
    wx.navigateTo({
      url: `/pages/search/detail/index?key=${this.data.key}`
    });
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