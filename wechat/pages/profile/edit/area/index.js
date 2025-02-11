// pages/profile/edit/area/index.js
import {
  countryList,
  provinceList,
  cityList
} from '../../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    countryList: countryList,
    provinceList: provinceList,
    cityList: cityList,
    areaVisible: false,
    areaValue: [],
    areaLabel: ''
  },

  onPicker(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}Visible`]: true
    })
  },

  onPickerChange(e) {
    const key = e.currentTarget.dataset.key;
    const label = e.detail.label
    const value = e.detail.value;

    this.setData({
      [`${key}Visible`]: false,
      [`${key}Value`]: value,
      [`${key}Label`]: label.join(' - '),
    });

    getApp().globalData.userInfo.country = this.data.areaValue[0]
    getApp().globalData.userInfo.province = this.data.areaValue[1]
    getApp().globalData.userInfo.city = this.data.areaValue[2]

    wx.showToast({
      title: '修改成功！~',
      icon: 'none'
    })

    setTimeout(() => {
      wx.navigateBack({
        delta: 1
      })
    }, 1000)
  },

  onPickerCancel(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}Visible`]: false
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
    const country = getApp().globalData?.userInfo?.country || ''
    const province = getApp().globalData?.userInfo?.province || ''
    const city = getApp().globalData?.userInfo?.city || ''

    this.setData({
      areaValue: [country, province, city],
      areaLabel: (this.data.countryList.find(item => item.value === country)?.label || '') + ' - ' +
        (this.data.provinceList.find(item => item.value === province)?.label || '') + ' - ' + (this.data.cityList.find(item => item.value === city)?.label || '')
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