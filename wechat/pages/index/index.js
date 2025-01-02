// pages/my/index.js
import {
  records
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    activeTab: '1',
    records: [],
    loading: true,
    refreshing: false,
    end: true
  },

  getRecords() {
    setTimeout(() => {
      this.setData({
        records: records,
        loading: false
      })
    }, 1000)
  },

  onTabsChange(event) {
    this.setData({
      activeTab: event.detail.value
    });
  },

  onTabsClick(event) {
    console.log(`Click tab, tab-panel value is ${event.detail.value}.`);
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