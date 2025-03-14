// pages/my/index.js
import {
  baseUrl
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    user: {},
    avatar: '',
    schoolInfo: {},
    stats: {
      follows: 0,
      fans: 0,
      likes: 0,
      public: 0,
      private: 0,
      collections: 0
    },
    activeTab: '0'
  },

  goToEdit() {
    wx.navigateTo({
      url: '/pages/profile/edit/index'
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
    this.setData({
      user: getApp().globalData.user,
      avatar: getApp().globalData.avatar,
      schoolInfo: getApp().globalData.schoolInfo
    })
    let componentInstance = null;
    if (this.data.activeTab === '0') {
      componentInstance = this.selectComponent('#myNote');
    } else if (this.data.activeTab === '1') {
      componentInstance = this.selectComponent('#myFavorite');
    } else if (this.data.activeTab === '2') {
      componentInstance = this.selectComponent('#myLike');
    }

    if (componentInstance && componentInstance.refresh) {
      componentInstance.refresh();
    }
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
    if (this.data.user.nickName) {
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