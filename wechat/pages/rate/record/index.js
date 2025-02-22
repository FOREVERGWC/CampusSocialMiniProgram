// pages/rate/record/index.js
import {
  getRateItemById
} from "../../../api/rate/item";
import {
  saveRateRecord,
  getMyRateRecordOne
} from "../../../api/rate/record";
import {
  baseUrl
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    rateItemId: null,
    rateItem: {},
    detail: {},
    score: 10,
    remark: '',
    loading: true
  },

  getDetail() {
    getRateItemById(this.data.rateItemId).then(res => {
      res?.attachmentList.forEach(attachement => {
        attachement.filePath = baseUrl + attachement.filePath
      })

      this.setData({
        rateItem: res || {}
      })
    })

    const params = {
      rateItemId: this.data.rateItemId
    }
    getMyRateRecordOne(params).then(res => {
      this.setData({
        id: res?.id || null,
        score: res?.score || 10,
        remark: res?.remark || '',
        detail: res
      })
    }).finally(() => {
      this.setData({
        loading: false
      })
    })
  },

  onChange(e) {
    this.setData({
      score: e.detail.value
    });
  },

  onInput(e) {
    this.setData({
      remark: e.detail.value
    });
  },

  handleSubmit() {
    this.setData({
      loading: true
    })
    const data = {
      id: this.data.id,
      rateItemId: this.data.rateItemId,
      score: this.data.score,
      remark: this.data.remark
    }

    saveRateRecord(data).then(res => {
      wx.showToast({
        title: '投票成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/rate/detail/index?id=${this.data.rateItemId}`
        });
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
    const rateItemId = options.rateItemId;
    this.setData({
      rateItemId: rateItemId
    });
    this.getDetail()
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