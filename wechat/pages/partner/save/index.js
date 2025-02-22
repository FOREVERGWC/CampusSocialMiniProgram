// pages/partner/save/index.js
import {
  getPartnerSubjectList
} from '../../../api/partner/subject'
import {
  savePartner
} from '../../../api/partner/index'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail: {},
    title: '',
    content: '',
    num: null,
    subjectList: [],
    subjectVisible: false,
    subjectValue: [],
    subjectLabel: '',
    start: new Date().getTime(),
    endDatetimeVisible: false,
    endDatetimeValue: null,
    endDatetimeLabel: '',
    props: {
      label: 'name',
      value: 'id'
    },
    loading: false
  },

  getRecords() {
    getPartnerSubjectList({}).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        subjectList: res.data || []
      })
    }).catch(error => {
      if (error.code === 401) {
        setTimeout(() => {
          wx.navigateTo({
            url: '/pages/login/index'
          })
        }, 3000)
      }
    })
  },

  submitDraft() {
    savePartner({}).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        detail: res.data,
        title: res.data?.title || '',
        content: res.data?.content || '',
        num: res.data?.num,
        subjectValue: [res.data?.subjectId || ''],
        subjectLabel: this.data.subjectList?.find(item => item.id === res.data?.subjectId)?.name || '',
        endDatetimeValue: res.data?.endTime,
        endDatetimeLabel: res.data?.endTime,
        fileList: res.data?.attachmentList.map(item => ({
          id: item.id,
          url: baseUrl + item.filePath,
          name: item.fileName,
          type: 'image'
        })) || []
      })
    }).finally(() => {
      this.setData({
        loading: false
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

  handleDraft() {
    const data = {
      title: this.data.title,
      content: this.data.content,
      subjectId: this.data.subjectValue[0],
      num: this.data.num,
      endTime: this.data.endDatetimeValue,
      status: '0'
    }
    savePartner(data).then(res => {
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

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/partner/detail/index?id=${this.data.detail.id}`
        });
      }, 1000)
    }).finally(() => {
      this.setData({
        loading: false
      })
    })
  },

  handlePublish() {
    this.setData({
      loading: true
    })
    const data = {
      id: this.data.detail.id,
      title: this.data.title,
      content: this.data.content,
      subjectId: this.data.subjectValue[0],
      num: this.data.num,
      endTime: this.data.endDatetimeValue,
      status: '1'
    }
    savePartner(data).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.switchTab({
          url: '/pages/partner/index'
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
    this.submitDraft()
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