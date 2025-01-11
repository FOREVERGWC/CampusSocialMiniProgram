// pages/rate/index.js
import {
  records
} from '../../utils/common'
import {
  getRateItemList
} from '../../api/rate/item'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    queryParams: {
      rateId: null
    },
    records: [],
    loading: true,
    refreshing: false,
    end: true
  },

  getRecords() {
    getRateItemList(this.data.queryParams).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const id = options.id;
    this.setData({
      queryParams: {
        rateId: id
      }
    });
    this.getRecords()
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
    this.setData({
      refreshing: true
    })

    setTimeout(() => {
      this.setData({
        records: records,
        refreshing: false
      })
    }, 1000)
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    this.setData({
      loading: true
    })

    setTimeout(() => {
      this.setData({
        records: this.data.records.concat(records),
        loading: false
      })
    }, 1000)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})