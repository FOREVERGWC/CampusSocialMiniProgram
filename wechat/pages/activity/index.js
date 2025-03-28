// pages/activity/index.js
import {
  getActivityPage
} from '../../api/activity/index'
import {
  getActivityCategoryList
} from '../../api/activity/category'
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
    categoryList: [],
    queryParams: {
      pageNo: 1,
      pageSize: 8,
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

    getActivityCategoryList({}).then(res => {
      const item = {
        id: null,
        name: '全部'
      }
      res.unshift(item)
      this.setData({
        categoryList: res || []
      })
    })

    getActivityPage(this.data.queryParams).then(res => {
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
      'queryParams.categoryId': id
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
        orderBy: 'createTime',
        isAsc: false,
        categoryId: this.data.queryParams.categoryId
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

    getActivityPage(this.data.queryParams).then(res => {
      const records = res?.records || []

      if (!records.length) {
        this.setData({
          end: true
        })
        showEndToast()
        return
      }

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