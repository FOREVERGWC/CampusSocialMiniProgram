// pages/profile/edit/gender/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    genderList: [{
        label: '女',
        value: '0',
        icon: 'female',
      },
      {
        label: '男',
        value: '1',
        icon: 'male',
      }, {
        label: '未知',
        value: '2',
        icon: '',
      }
    ],
    genderVisible: false,
    genderValue: [],
    genderLabel: '',
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
      [`${key}Label`]: label[0],
    });

    getApp().globalData.userInfo[`${key}`] = value[0]

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
    this.setData({
      genderValue: [getApp().globalData?.userInfo?.gender || '2'],
      genderLabel: this.data.genderList.find(item => item.value === getApp().globalData?.userInfo?.gender)?.label || '未知'
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