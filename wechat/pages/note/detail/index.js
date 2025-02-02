// pages/note/detail/index.js
import {
  getNoteById
} from '../../../api/note/index'
import {
  handleLike
} from '../../../api/like/index'
import {
  handleFavorite
} from '../../../api/favorite/index'
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
    detail: {}
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

  handleLikeNote() {
    const data = {
      bizType: 8,
      bizId: this.data.id
    }
    handleLike(data).then(res => {
      if (res.code !== 200) {
        return
      }
      const detail = this.data.detail;
      detail.count.like.hasDone = !detail.count.like.hasDone;
      detail.count.like.num = res.data;
      this.setData({
        detail: detail
      });
      // TODO 操作成功
    }).catch(() => {
      // TODO 操作失败
    })
  },

  handleFavoriteNote() {
    const data = {
      bizType: 8,
      bizId: this.data.id
    }
    handleFavorite(data).then(res => {
      if (res.code !== 200) {
        return
      }
      const detail = this.data.detail;
      detail.count.favorite.hasDone = !detail.count.favorite.hasDone;
      detail.count.favorite.num = res.data;
      this.setData({
        detail: detail
      });
      // TODO 操作成功
    }).catch(() => {
      // TODO 操作失败
    })
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