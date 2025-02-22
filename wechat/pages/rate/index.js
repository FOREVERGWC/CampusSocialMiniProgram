// pages/rate/index.js
import {
  getRateById
} from '../../api/rate/index'
import {
  getRateItemList
} from '../../api/rate/item'
import {
  baseUrl
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    queryParams: {
      rateId: null,
      status: '1'
    },
    detail: {},
    records: [],
    loading: true,
    refreshing: false,
    end: true
  },

  getRecords() {
    getRateById(this.data.queryParams.rateId).then(res => {
      res?.attachmentList.forEach(attachement => {
        attachement.filePath = baseUrl + attachement.filePath
      })

      this.setData({
        detail: res || {}
      })
    })
    getRateItemList(this.data.queryParams).then(res => {
      res?.forEach(item => {
        item.attachmentList.forEach(attachement => {
          attachement.filePath = baseUrl + attachement.filePath
        })
      })

      this.setData({
        records: res || []
      })
    })
  },

  onToDetail(event) {
    const id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/rate/detail/index?id=${id}`
    });
  },

  goToAdd() {
    wx.navigateTo({
      url: `/pages/rate/item/save/index?rateId=${this.data.queryParams.rateId}`
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const id = options.id;
    this.setData({
      queryParams: {
        rateId: id,
        status: '1'
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