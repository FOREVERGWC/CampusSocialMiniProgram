// pages/login/index.js
import {
  login,
  getByToken
} from '../../api/auth.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    username: '',
    password: '',
    jsCode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    wx.login({
      success: (res) => {
        this.setData({
          jsCode: res.code
        })
      }
    })
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

  },

  onUsernameChange(e) {
    this.setData({
      username: e.detail.value
    })
  },

  onPasswordChange(e) {
    this.setData({
      password: e.detail.value
    })
  },

  handleLogin() {
    if (!this.data.username || !this.data.password) {
      wx.showToast({
        title: '请输入用户名和密码',
        icon: 'none'
      })
      return
    }

    const form = {
      username: this.data.username,
      password: this.data.password,
      loginType: 1,
      jsCode: this.data.jsCode
    }

    wx.showLoading({
      title: '登录中...',
      icon: 'none'
    })

    login(form).then(res => {
      wx.hideLoading()

      if (res.code !== 200) {
        wx.showToast({
          title: res.msg || '登录失败！用户名或密码错误',
          icon: 'none'
        })
        return
      }

      getApp().globalData.token = res.data

      wx.showToast({
        title: '登录成功！',
        icon: 'success'
      })

      getByToken().then(res => {
        getApp().globalData.userInfo = res.data || {}
      })

      wx.switchTab({
        url: '/pages/index/index'
      })
    }).catch(error => {
      wx.hideLoading()
      wx.showToast({
        title: '登录失败！服务器开小差啦',
        icon: 'none'
      })
    })
  },

  navigateToRegister() {
    wx.navigateTo({
      url: '/pages/register/index'
    })
  },

  navigateToForget() {
    wx.navigateTo({
      url: '/pages/forget/index'
    })
  }
})