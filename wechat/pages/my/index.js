// pages/my/index.js
import {
  baseUrl
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUseGetUserProfile: wx.canIUse('getUserProfile'),
    canIUseNickname: wx.canIUse('input.type.nickname'),
    userInfo: getApp().globalData.userInfo,
    avatar: `${baseUrl}${getApp().globalData.userInfo.avatar}`,
    stats: {
      follows: 0,
      fans: 0,
      likes: 0,
      public: 0,
      private: 0,
      collections: 0
    },
    activeTab: '0',
    hasNotes: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getUserInfo()
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

  onChooseAvatar(e) {
    const avatar = e.detail.avatarUrl
    this.setData({
      avatar: avatar
    })
    // TODO 调用上传接口，把微信临时地址上传到后端，并将后端返回的真实地址设置为avatar
    // TODO 提交头像修改
  },

  getUserInfo() {
    // 获取用户信息的逻辑
  },

  editProfile() {
    wx.navigateTo({
      url: '/pages/profile/edit/index'
    })
  },

  onTabChange(e) {
    const newTab = e.detail.value;
    this.setData({
      activeTab: newTab
    });

    let componentInstance = null;
    if (newTab === '0') {
      componentInstance = this.selectComponent('#myNote');
    } else if (newTab === '1') {
      componentInstance = this.selectComponent('#myFavorite');
    } else if (newTab === '2') {
      componentInstance = this.selectComponent('#myLike');
    }

    if (componentInstance && componentInstance.refresh) {
      componentInstance.refresh();
    }
  },

  goToLogin() {
    if (this.data.userInfo.nickName) {
      return
    }
    wx.navigateTo({
      url: '/pages/login/index'
    })
  },

  goToSetting() {
    wx.navigateTo({
      url: '/pages/setting/index'
    });
  }
})