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
  checkFile
} from '../../../api/file/index'
import {
  baseUrl,
  defaultAvatar,
  countryList,
  provinceList,
  cityList,
  calculateFileHash
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
    avatar: '',
    schoolInfo: {},
    chunkSize: 10 * 1024 * 1024
  },

  getDetail() {
    getMyUserSchoolOne().then(res => {
      getApp().globalData.schoolInfo = res || {}
    })
  },

  async onChooseAvatar(e) {
    const avatar = e.detail.avatarUrl
    this.setData({
      avatar: avatar
    })
    const {
      hashCode,
      fileSize,
      fileType
    } = await calculateFileHash(this.data.chunkSize, avatar);

    const params = {
      hashCode,
      bizId: this.data.user.id,
      bizType: 1,
      chunkTotal: 1
    };

    const {
      hasUpload,
      attachment
    } = await checkFile(params);

    if (hasUpload) {
      this.handleUploadSuccess(attachment.filePath);

      return;
    }

    await this.uploadFile({
      url: avatar,
      name: `微信头像.${fileType}`,
      hashCode,
      fileSize
    });
  },

  handleUploadSuccess(avatar) {
    const data = {
      avatar: avatar
    }
    editUser(data).then(res => {
      wx.showToast({
        title: '保存成功！~',
        icon: 'none'
      });
    }).finally(() => {
      getApp().globalData.avatar = avatar ? baseUrl + avatar : defaultAvatar
    })
  },

  uploadFile({
    url,
    name,
    hashCode,
    fileSize
  }) {
    return new Promise((resolve, reject) => {
      wx.uploadFile({
        url: 'http://localhost:9091/file/upload',
        filePath: url,
        name: 'file',
        formData: {
          bizId: this.data.user.id,
          bizType: 1,
          hashCode,
          fileName: name,
          fileSize,
          chunkSize: this.data.chunkSize,
          chunkIndex: 0,
          chunkTotal: 1
        },
        success: (res) => {
          const {
            data
          } = JSON.parse(res.data);
          this.handleUploadSuccess(data.filePath);
          resolve(data);
        },
        fail: reject
      });
    });
  },

  async handleSubmit() {
    const userData = {
      nickname: this.data.user.nickname
    }
    await editUser(userData)

    const userInfoData = {
      id: this.data.userInfo.id,
      gender: this.data.userInfo.gender,
      birthday: this.data.userInfo.birthday,
      country: this.data.userInfo.country,
      province: this.data.userInfo.province,
      city: this.data.userInfo.city,
      remark: this.data.userInfo.remark
    }

    const infoResData = await saveUserInfo(userInfoData)

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
      avatar: getApp().globalData.avatar,
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