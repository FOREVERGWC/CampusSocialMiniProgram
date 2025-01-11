// pages/my/index.js
import {
  records
} from '../../utils/common'
import {
  getNotePage
} from '../../api/note/index'
import {
  getRatePage
} from '../../api/rate/index'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    queryParams: {
      pageNo: 1,
      pageSize: 8
    },
    activeTab: '1',
    records: [],
    loading: true,
    refreshing: false,
    end: true
  },

  getRecords() {
    if (this.data.activeTab === '1') {
      this.getNoteRecords()
    } else if (this.data.activeTab === '2') {
      this.getRateRecords()
    } else if (this.data.activeTab == '3') {
      console.log('待完成');
    }
    this.setData({
      loading: false,
      refreshing: false
    })
  },

  getNoteRecords() {
    getNotePage(this.data.queryParams).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        records: res.data?.records || []
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

  getRateRecords() {
    getRatePage(this.data.queryParams).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        records: res.data?.records || []
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

  onTabsChange(event) {
    this.setData({
      activeTab: event.detail.value
    });
  },

  onTabsClick(event) {
    this.getRecords()
  },

  onStickyScroll(event) {
    console.log(event.detail);
  },

  onToDetail(event) {
    const id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/note/detail/index?id=${id}`
    });
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
    this.getTabBar().init()
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
      queryParams: {
        pageNo: 1,
        pageSize: 8
      },
      refreshing: true
    })

    this.getRecords()
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