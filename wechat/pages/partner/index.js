// pages/partner/index.js
import {
  getPartnerPage
} from '../../api/partner/index'
import {
  getPartnerSubjectList
} from '../../api/partner/subject'
import {
  baseUrl,
  defaultAvatar
} from '../../utils/common'
import {
  showEndToast
} from '../../utils/toast/index'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentTab: 0,
    scrollLeft: 0,
    subjectList: [],
    queryParams: {
      pageNo: 1,
      pageSize: 8,
      status: '1',
      orderBy: 'createTime',
      isAsc: false
    },
    records: [],
    total: 0,
    pages: 0,
    loading: false,
    refreshing: false,
    end: false
  },

  getRecords() {
    this.setData({
      loading: true
    })

    getPartnerSubjectList({}).then(res => {
      const item = {
        id: null,
        name: '全部'
      }
      res.unshift(item)
      this.setData({
        subjectList: res || []
      })
    })

    getPartnerPage(this.data.queryParams).then(res => {
      res?.records.forEach(item => {
        item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
        item.attachmentList?.forEach(attachement => {
          attachement.filePath = baseUrl + attachement.filePath
        })
      })

      this.setData({
        records: res?.records || [],
        total: res?.total || 0,
        pages: res?.pages || 0,
        end: !res?.records?.length || this.data.queryParams.pageNo >= (res?.pages || 0)
      })
    }).finally(() => {
      this.setData({
        loading: false,
        refreshing: false
      })
    })
  },

  tabSelect(e) {
    const id = e.currentTarget.id
    const index = e.currentTarget.dataset.index
    this.setData({
      currentTab: index,
      scrollLeft: (index - 1) * 60,
      'queryParams.subjectId': id
    }, () => {
      this.getRecords()
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
    this.setData({
      queryParams: {
        pageNo: 1,
        pageSize: 8,
        status: '1',
        orderBy: 'createTime',
        isAsc: false,
        subjectId: this.data.queryParams.subjectId
      },
      refreshing: true,
      records: []
    })

    this.getRecords()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (this.data.loading || this.data.end) {
      this.data.end && showEndToast()
      return
    }

    this.setData({
      loading: true,
      'queryParams.pageNo': this.data.queryParams.pageNo + 1
    })

    getPartnerPage(this.data.queryParams).then(res => {
      const records = res?.records || []

      if (!records.length) {
        this.setData({
          end: true,
        })
        showEndToast()
        return
      }

      records.forEach(item => {
        item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
        item.attachmentList?.forEach(attachement => {
          attachement.filePath = baseUrl + attachement.filePath
        })
      })

      this.setData({
        records: [...this.data.records, ...records],
        total: res?.total || 0,
        pages: res?.pages || 0,
        end: this.data.queryParams.pageNo >= (res?.pages || 0)
      })
    }).error(() => {
      this.setData({
        'queryParams.pageNo': this.data.queryParams.pageNo - 1
      })
    }).finally(() => {
      this.setData({
        loading: false
      })
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})