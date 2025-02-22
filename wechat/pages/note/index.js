// pages/note/index.js
import {
  getNotePage
} from '../../api/note/index'
import {
  baseUrl,
  defaultAvatar
} from '../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    queryParams: {
      pageNo: 1,
      pageSize: 8,
      orderBy: 'createTime',
      isAsc: false
    },
    records: [],
    total: 0,
    pages: 0,
    loading: true,
    refreshing: false,
    end: true
  },

  getRecords() {
    this.setData({
      end: false
    })

    getNotePage(this.data.queryParams).then(res => {
      res?.records.forEach(item => {
        item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
        item.attachmentList.forEach(attachement => {
          attachement.filePath = baseUrl + attachement.filePath
        })
      })

      this.setData({
        records: res?.records || [],
        total: res?.total || 0,
        pages: res?.pages || 0
      })
    }).finally(() => {
      this.setData({
        loading: false,
        refreshing: false
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
        isAsc: false
      },
      refreshing: true,
      records: []
    });

    this.getRecords();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (this.data.loading) {
      return
    }

    if (this.data.end) {
      wx.showToast({
        title: '已经到底啦！~',
        icon: 'none'
      })

      return
    }

    if (this.data.queryParams.pageNo >= this.data.pages) {
      this.setData({
        loading: false,
        end: true
      });

      wx.showToast({
        title: '已经到底啦！~',
        icon: 'none'
      });

      return
    }

    this.setData({
      'queryParams.pageNo': this.data.queryParams.pageNo + 1,
      loading: true
    });

    getNotePage(this.data.queryParams).then(res => {
      const records = res?.records || [];
      if (records.length === 0) {
        this.setData({
          end: true,
          loading: false
        });
        wx.showToast({
          title: '已经到底啦！~',
          icon: 'none'
        });
        return;
      }

      this.setData({
        records: [...this.data.records, ...records],
        total: res?.total || 0,
        pages: res?.pages || 0,
        loading: false
      });
    }).finally(() => {
      this.setData({
        loading: false
      });
    });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})