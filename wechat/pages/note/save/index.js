// pages/note/save/index.js
import {
  getNoteCategoryList
} from '../../../api/note/category'
import {
  saveNote
} from '../../../api/note/index'
import {
  baseUrl
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail: {},
    title: '',
    content: '',
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
    fileList: [],
    props: {
      label: 'name',
      value: 'id'
    },
    loading: true
  },

  getRecords() {
    getNoteCategoryList({}).then(res => {
      this.setData({
        categoryList: res || []
      })
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
      this.setData({
        title: res?.title || '',
        content: res?.content || '',
        detail: res,
        categoryValue: [res?.categoryId || ''],
        categoryLabel: this.data.categoryList?.find(item => item.id === res?.categoryId)?.name || '',
        visibleValue: [res?.visible || ''],
        visibleLabel: this.data.visibleList?.find(item => item.value === res?.visible)?.label || '',
        fileList: res?.attachmentList.map(item => ({
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
      wx.showToast({
        title: '保存成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/note/detail/index?id=${this.data.detail.id}`
        });
      }, 1000)
    }).finally(() => {
      this.setData({
        loading: false
      })
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
      wx.showToast({
        title: '发布成功！~',
        icon: 'none'
      })

      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/note/detail/index?id=${this.data.detail.id}`
        });
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