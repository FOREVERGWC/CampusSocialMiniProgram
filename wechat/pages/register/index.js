// pages/register/index.js
import {
  sendRegisterCode
} from '../../api/email/index.js'
import {
  register
} from '../../api/auth.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    username: '',
    password: '',
    confirmPwd: '',
    email: '',
    code: ''
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  handleCaptcha() {
    if (!this.data.email) {
      wx.showToast({
        title: '请输入邮箱',
        icon: 'none'
      })
      return
    }

    const data = {
      email: this.data.email
    }

    sendRegisterCode(data).then(res => {
      wx.showToast({
        title: '发送成功！请注意查收',
        icon: 'none'
      })
    })
  },

  handleRegister() {
    if (!this.data.username || !this.data.password || !this.data.confirmPwd || !this.data.email || !this.data.code) {
      wx.showToast({
        title: '请填写参数',
        icon: 'none'
      })
      return
    }

    const data = {
      username: this.data.username,
      password: this.data.password,
      confirmPwd: this.data.confirmPwd,
      email: this.data.email,
      code: this.data.code
    }

    register(data).then(res => {
      wx.showToast({
        title: '注册成功！请登录',
        icon: 'none'
      })
    })
  },

  navigateToLogin() {
    wx.navigateTo({
      url: '/pages/login/index'
    })
  },

  navigateToForget() {
    wx.navigateTo({
      url: '/pages/forget/index'
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