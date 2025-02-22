import {
  saveRate
} from "../../../api/rate/index";
import {
  baseUrl
} from '../../../utils/common'

// pages/rate/save/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail: {},
    fileList: [],
    title: '',
    content: '',
    loading: false
  },

  submitDraft() {
    saveRate({}).then(res => {
      this.setData({
        detail: res,
        fileList: res?.attachmentList.map(item => ({
          id: item.id,
          url: baseUrl + item.filePath,
          name: item.fileName,
          type: 'image'
        })) || [],
        title: res?.title || '',
        content: res?.content || '',
      })
    }).finally(() => {
      this.setData({
        loading: false
      })
    })
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  handleDraft() {
    const data = {
      title: this.data.title,
      content: this.data.content,
      status: '0'
    }
    saveRate(data).then(res => {
      wx.showToast({
        title: '保存成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        })
      }, 1000)
    }).finally(() => {
      this.setData({
        loading: false
      })
    })
  },

  handlePublish() {
    this.setData({
      loading: true
    })
    const data = {
      id: this.data.detail.id,
      title: this.data.title,
      content: this.data.content,
      status: '1'
    }
    saveRate(data).then(res => {
      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        })
      }, 1000)
    }).finally(() => {
      this.setData({
        loading: false
      })
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
    this.submitDraft()
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