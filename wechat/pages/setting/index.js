// pages/setting/index.js
import {
  logout
} from '../../api/auth'
import {
  formatFileSize
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    storageSize: '0 KB'
  },

  getStorageSize() {
    const storageSize = formatFileSize(wx.getStorageInfoSync().currentSize)
    this.setData({
      storageSize: storageSize
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
    this.getStorageSize()
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

  },

  onBack() {
    setTimeout(() => {
      wx.navigateBack({
        delta: 1
      })
    }, 1000)
  },

  switchAccount() {
    // TODO 切换账号逻辑
    wx.showToast({
      title: '切换账号',
      icon: 'none'
    });
  },

  handleLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: async (res) => {
        if (res.confirm) {
          wx.clearStorageSync();
          await logout()
          wx.reLaunch({
            url: '/pages/login/index'
          });
        }
      }
    });
  }
})