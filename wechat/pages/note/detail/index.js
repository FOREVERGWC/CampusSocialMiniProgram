// pages/note/detail/index.js
import {
  comments
} from '../../../utils/common'
import {
  getNoteById
} from '../../../api/note/index'
import {
  baseUrl,
  defaultAvatar
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    detail: {},
    comments: comments
  },

  getDetail() {
    getNoteById(this.data.id).then(res => {
      res.data.user.avatar = res.data.user.avatar ? baseUrl + res.data.user.avatar : defaultAvatar
      res.data.attachmentList.forEach(item => {
        item.filePath = baseUrl + item.filePath
      })

      this.setData({
        detail: res.data
      })
    })
  },

  handleLike() {
    const detail = this.data.detail;
    detail.hasLike = !detail.hasLike;
    detail.like += detail.hasLike ? 1 : -1;
    this.setData({
      detail: detail
    });
  },

  handleCollection() {
    const detail = this.data.detail;
    detail.hasCollection = !detail.hasCollection;
    detail.collection += detail.hasCollection ? 1 : -1;
    this.setData({
      detail: detail
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