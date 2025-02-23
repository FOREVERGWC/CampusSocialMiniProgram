import {
  getRateItemById
} from "../../../api/rate/item";
import {
  baseUrl
} from '../../../utils/common'

// pages/rate/detail/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    detail: {
      score: 10
    }
  },

  getDetail() {
    getRateItemById(this.data.id).then(res => {
      res.score = +res?.score
      res?.attachmentList.forEach(attachement => {
        attachement.filePath = baseUrl + attachement.filePath
      })

      this.setData({
        detail: res || {}
      })
    })
  },

  goToScore() {
    wx.navigateTo({
      url: `/pages/rate/record/index?rateItemId=${this.data.id}`
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const id = options.id;
    this.setData({
      id: id
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