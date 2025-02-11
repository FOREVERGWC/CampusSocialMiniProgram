import {
  getSchoolList
} from "../../../../api/school/index";
import {
  saveUserSchool
} from "../../../../api/user/school";

// pages/profile/edit/school/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    end: new Date().getTime(),
    schoolList: [],
    schoolVisible: false,
    schoolValue: [],
    schoolLabel: '',
    intakeVisible: false,
    intakeValue: null,
    intakeLabel: '',
    studentId: '',
    schoolInfo: {},
    props: {
      label: 'name',
      value: 'id'
    }
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

  onPickerCancel(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}Visible`]: false
    });
  },

  onInput(e) {
    const key = e.currentTarget.dataset.key;
    this.setData({
      [`${key}`]: e.detail.value
    })
  },

  getRecords() {
    getSchoolList({}).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        schoolList: res.data || [],
      })
    }).catch(error => {
      if (error.code === 401) {
        setTimeout(() => {
          wx.navigateTo({
            url: '/pages/login/index'
          })
        }, 3000)
      }
    }).finally(() => {
      this.setData({
        schoolInfo: getApp().globalData.schoolInfo,
        schoolValue: [getApp().globalData.schoolInfo?.school?.id || []],
        schoolLabel: this.data.schoolList.find(item => item.id === getApp().globalData.schoolInfo?.school?.id)?.name || '',
        intakeValue: getApp().globalData.schoolInfo?.intakeDate || '',
        intakeLabel: getApp().globalData.schoolInfo?.intakeDate || '',
        studentId: getApp().globalData.schoolInfo?.studentId || ''
      })
    })
  },

  handleSubmit() {
    const data = {
      id: this.data.schoolInfo?.id,
      schoolId: this.data.schoolValue[0],
      intakeDate: this.data.intakeValue,
      studentId: this.data.studentId
    }
    saveUserSchool(data).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      wx.showToast({
        title: '保存成功！~',
        icon: 'none'
      })

      data.school = {}

      getApp().globalData.schoolInfo = data
      getApp().globalData.schoolInfo.school.name = this.data.schoolLabel

      setTimeout(() => {
        wx.navigateBack({
          delta: 1
        })
      }, 1000)
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