// pages/rate/item/save/index.js
import {
  getRateById
} from '../../../../api/rate/index'
import {
  saveRateItem
} from "../../../../api/rate/item";
import {
  baseUrl
} from '../../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    rateId: null,
    rate: {},
    rateItem: {},
    fileList: [],
    title: '',
    content: '',
    loading: false
  },

  getRecords() {
    getRateById(this.data.rateId).then(res => {
      res?.attachmentList.forEach(attachement => {
        attachement.filePath = baseUrl + attachement.filePath
      })

      this.setData({
        rate: res || {}
      })
    })
  },

  submitDraft() {
    const data = {
      rateId: this.data.rateId
    }
    saveRateItem(data).then(res => {
      this.setData({
        rateItem: res,
        fileList: res?.attachmentList.map(item => ({
          id: item.id,
          url: baseUrl + item.filePath,
          name: item.fileName,
          type: 'image'
        })) || [],
        title: res?.title || '',
        content: res?.content || ''
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
      rateId: this.data.rateId,
      status: '0'
    }
    saveRateItem(data).then(res => {
      wx.showToast({
        title: '保存成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/rate/item/detail/index?id=${this.data.rateItem.id}`
        });
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
      id: this.data.rateItem.id,
      title: this.data.title,
      content: this.data.content,
      rateId: this.data.rateId,
      status: '1'
    }
    saveRateItem(data).then(res => {
      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/rate/index?id=${this.data.rateId}`,
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
    const rateId = options.rateId;
    this.setData({
      rateId: rateId
    });
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
    this.getRecords()
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