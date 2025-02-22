// pages/note/category/index.js
import {
  getNoteCategoryList
} from '../../../api/note/category'
import {
  getNotePage
} from '../../../api/note/index'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    queryParams: {
      pageNo: 1,
      pageSize: 8,
      categoryId: null
    },
    categoryList: [],
    records: []
  },

  getRecords() {
    getNoteCategoryList({}).then(res => {
      this.setData({
        categoryList: res || []
      })
    })

    getNotePage(this.data.queryParams).then(res => {
      this.setData({
        records: res?.records || []
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

  },

  onToCategoryDetail(event) {
    const id = event.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/note/category/detail/index?id=${id}`
    })
  }
})