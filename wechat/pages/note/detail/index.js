// pages/note/detail/index.js
import {
  getNoteById,
  removeNoteBatchByIds
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
    userId: '',
    id: '',
    detail: {},
    moreVisible: false,
    deleteVisible: false
  },

  onVisibleChange(e) {
    const key = e.currentTarget.dataset.key
    const value = Boolean(e.currentTarget.dataset.value)
    this.setData({
      [`${key}Visible`]: value,
    });
  },

  goToEdit() {
    wx.navigateTo({
      url: `/pages/note/save/index?id=${this.data.id}`
    })
  },

  handleDelete(e) {
    this.onVisibleChange(e)
    removeNoteBatchByIds([this.data.id]).then(res => {
      wx.showToast({
        title: '删除成功！~',
        icon: 'none'
      })
    })
    setTimeout(() => {
      wx.redirectTo({
        url: '/pages/note/index'
      });
    }, 1000)
  },

  getDetail() {
    getNoteById(this.data.id).then(res => {
      res.user.avatar = res.user.avatar ? baseUrl + res.user.avatar : defaultAvatar
      res?.attachmentList.forEach(item => {
        item.filePath = baseUrl + item.filePath
      })

      this.setData({
        detail: res
      })
    })
  },

  handleLikeNote() {
    const data = {
      bizType: 8,
      bizId: this.data.id
    }
    handleLike(data).then(res => {
      const detail = this.data.detail;
      detail.count.like.hasDone = !detail.count.like.hasDone;
      detail.count.like.num = res;
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
      const detail = this.data.detail;
      detail.count.favorite.hasDone = !detail.count.favorite.hasDone;
      detail.count.favorite.num = res;
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
    const userId = getApp().globalData.user.id
    this.setData({
      userId: userId
    })
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