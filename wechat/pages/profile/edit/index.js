// pages/profile/edit/index.js
import {
  editUser
} from '../../../api/user/index'
import {
  saveUserInfo
} from '../../../api/user/info'
import {
  getMyUserSchoolOne
} from '../../../api/user/school'
import {
  baseUrl,
  countryList,
  provinceList,
  cityList
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    countryList: countryList,
    provinceList: provinceList,
    cityList: cityList,
    user: {},
    userInfo: {},
    area: '',
    avatar: `${baseUrl}${getApp().globalData.user.avatar}`,
    schoolInfo: {},
  },

  getDetail() {
    getMyUserSchoolOne().then(res => {
      if (res.code !== 200) {
        return
      }

      getApp().globalData.schoolInfo = res.data || {}
    })
  },

  async handleSubmit() {
    const userData = {
      nickname: this.data.user.nickname
    }
    const {
      code: userCode,
      data: userResData,
      msg: userResMsg
    } = await editUser(userData)

    if (userCode !== 200) {
      wx.showToast({
        title: userResMsg,
        icon: 'none'
      });
      return
    }

    const userInfoData = {
      id: this.data.userInfo.id,
      gender: this.data.userInfo.gender,
      birthday: this.data.userInfo.birthday,
      country: this.data.userInfo.country,
      province: this.data.userInfo.province,
      city: this.data.userInfo.city,
      remark: this.data.userInfo.remark
    }

    const {
      code: infoCode,
      data: infoResData,
      msg: infoResMsg
    } = await saveUserInfo(userInfoData)

    if (infoCode !== 200) {
      wx.showToast({
        title: infoResMsg,
        icon: 'none'
      });
      return
    }

    wx.showToast({
      title: '保存成功！~',
      icon: 'none'
    });

    getApp().globalData.userInfo = infoResData
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

    console.log('aa', country, province, city);
    this.setData({
      user: getApp().globalData.user,
      userInfo: getApp().globalData.userInfo,
      area: (this.data.countryList.find(item => item.value === country)?.label || '') + ' - ' +
        (this.data.provinceList.find(item => item.value === province)?.label || '') + ' - ' + (this.data.cityList.find(item => item.value === city)?.label || ''),
      schoolInfo: getApp().globalData.schoolInfo
    })

    this.getDetail()
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