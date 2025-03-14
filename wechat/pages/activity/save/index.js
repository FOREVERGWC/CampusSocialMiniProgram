import {
  getActivityCategoryList
} from '../../../api/activity/category'
import {
  saveActivity
} from "../../../api/activity/index";

// pages/activity/save/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: '',
    content: '',
    categoryList: [],
    categoryVisible: false,
    categoryValue: [],
    categoryLabel: '',
    detail: {},
    start: new Date().getTime(),
    startDatetimeVisible: false,
    startDatetimeValue: null,
    startDatetimeLabel: '',
    endDatetimeVisible: false,
    endDatetimeValue: null,
    endDatetimeLabel: '',
    location: '',
    props: {
      label: 'name',
      value: 'id'
    },
    loading: false
  },

  getRecords() {
    getActivityCategoryList({}).then(res => {
      this.setData({
        categoryList: res || []
      })
    })
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  onPicker(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}Visible`]: true
    })
  },

  onDateTimePickerChange(e) {
    const key = e.currentTarget.dataset.key;
    const value = e.detail.value;
    this.setData({
      [`${key}Visible`]: false,
      [`${key}Value`]: value,
      [`${key}Label`]: value,
    });
  },

  onPickerChange(e) {
    const key = e.currentTarget.dataset.key;
    const label = e.detail.label
    const value = e.detail.value;
    this.setData({
      [`${key}Visible`]: false,
      [`${key}Value`]: value,
      [`${key}Label`]: label[0],
    });
  },

  onPickerCancel(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}Visible`]: false
    });
  },

  handleSubimt() {
    this.setData({
      loading: true
    })
    const data = {
      title: this.data.title,
      content: this.data.content,
      categoryId: this.data.categoryValue[0],
      startDatetime: this.data.startDatetimeValue,
      endDatetime: this.data.endDatetimeValue,
      location: this.data.location
    }
    saveActivity(data).then(res => {
      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.switchTab({
          url: '/pages/activity/index'
        })
      }, 1000)
    }).finally(() => {
      this.setData({
        loading: false
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