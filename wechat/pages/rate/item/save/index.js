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
    queryParams: {
      rateId: null
    },
    detail: {},
    title: '',
    content: '',
    loading: false
  },

  getRecords() {
    getRateById(this.data.queryParams.rateId).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      res.data.attachmentList.forEach(attachement => {
        attachement.filePath = baseUrl + attachement.filePath
      })

      this.setData({
        detail: res.data || {}
      })
    })
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  handleSubimt() {
    this.setData({
      loading: true
    })
    const data = {
      title: this.data.title,
      content: this.data.content,
      rateId: this.data.queryParams.rateId
    }
    saveRateItem(data).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/rate/index?id=${this.data.queryParams.rateId}`,
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
      queryParams: {
        rateId: rateId
      }
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