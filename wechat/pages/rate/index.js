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
    getRateItemList(this.data.queryParams).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      res.data.forEach(item => {
        item.attachmentList.forEach(attachement => {
          attachement.filePath = baseUrl + attachement.filePath
        })
      })

      this.setData({
        records: res.data || []
      })
    }).catch(error => {
      if (error.code === 401) {
        setTimeout(() => {
          wx.navigateTo({
            url: '/pages/login/index'
          })
        }, 3000)
      }
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