// pages/note/save/index.js
import {
  getNoteCategoryList
} from '../../../api/note/category'
import {
  saveNote
} from '../../../api/note/index'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: '',
    content: '',
    detail: {},
    categoryList: [],
    categoryVisible: false,
    categoryValue: [],
    categoryLabel: '',
    visibleList: [{
        label: '私有',
        value: '0'
      },
      {
        label: '公开',
        value: '1'
      }
    ],
    visibleVisible: false,
    visibleValue: [],
    visibleLabel: '',
    props: {
      label: 'name',
      value: 'id'
    }
  },

  getRecords() {
    getNoteCategoryList({}).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        categoryList: res.data || []
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
    const data = {
      title: '',
      content: '',
      visible: '1',
      categoryId: '1',
      top: 0,
      commentable: true
    }
    saveNote(data).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        title: res.data?.title || '',
        content: res.data?.content || '',
        detail: res.data,
        categoryValue: [res.data?.categoryId || ''],
        categoryLabel: this.data.categoryList?.find(item => item.id === res.data?.categoryId)?.name || '',
        visibleValue: [res.data?.visible || ''],
        visibleLabel: this.data.visibleList?.find(item => item.value === res.data?.visible)?.label || '',
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
      ...this.data.detail,
      title: this.data.title,
      content: this.data.content,
      categoryId: this.data.categoryValue[0],
      visible: this.data.visibleValue[0],
      status: '0'
    }
    saveNote(data).then(res => {
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
          url: `/pages/note/detail/index?id=${this.data.detail.id}`
        });
      }, 1000)
    })
  },

  handlePublish() {
    const data = {
      ...this.data.detail,
      title: this.data.title,
      content: this.data.content,
      categoryId: this.data.categoryValue[0],
      visible: this.data.visibleValue[0],
      status: '1'
    }
    saveNote(data).then(res => {
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
        wx.redirectTo({
          url: `/pages/note/detail/index?id=${this.data.detail.id}`
        });
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