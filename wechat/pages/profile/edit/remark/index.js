// pages/profile/edit/remark/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    remark: ''
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  handleSubmit(e) {
    const key = e.currentTarget.dataset.key
    const value = e.currentTarget.dataset.value
    // TODO: 校验规则
    getApp().globalData.userInfo[`${key}`] = value

    wx.showToast({
      title: '修改成功！~',
      icon: 'none'
    })

    setTimeout(() => {
      wx.navigateBack({
        delta: 1
      })
    }, 1000)
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
    this.setData({
      remark: getApp().globalData?.userInfo?.remark || ''
    })
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