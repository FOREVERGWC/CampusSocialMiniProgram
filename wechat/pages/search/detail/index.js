// pages/search/detail/index.js
import {
  getNotePage
} from '../../../api/note/index'
import {
  getRatePage
} from '../../../api/rate/index'
import {
  getActivityPage
} from '../../../api/activity/index'
import {
  getPartnerPage
} from '../../../api/partner/index'
import {
  baseUrl,
  defaultAvatar
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    key: '',
    queryParams: {
      pageNo: 1,
      pageSize: 8
    },
    activeTab: '0',
    records: [],
    total: 0,
    pages: 0,
    loading: true,
    refreshing: false,
    end: true
  },

  onInput(e) {
    this.setData({
      key: e.detail.value,
      'queryParams.title': e.detail.value
    })
  },

  onTabClick(e) {
    const selectedTab = e.currentTarget.dataset.tab;
    if (this.data.activeTab !== selectedTab) {
      this.setData({
        activeTab: selectedTab
      }, () => {
        this.getRecords()
      })
    }
  },

  getRecords() {
    this.setData({
      end: false
    })

    if (this.data.activeTab === '0') {
      this.getNoteRecords()
    } else if (this.data.activeTab === '1') {
      this.getRateRecords()
    } else if (this.data.activeTab == '2') {
      this.getActivityRecords()
    } else if (this.data.activeTab == '3') {
      this.getPartnerRecords()
    }

    this.setData({
      loading: false,
      refreshing: false
    })
  },

  getNoteRecords() {
    this.setData({
      'queryParams.status': '1',
      'queryParams.visibke': '1'
    }, () => {
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
      })
    })
  },

  getRateRecords() {
    getRatePage(this.data.queryParams).then(res => {
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
    })
  },

  getActivityRecords() {
    getActivityPage(this.data.queryParams).then(res => {
      this.setData({
        records: res?.records || [],
        total: res?.total || 0,
        pages: res?.pages || 0
      })
    })
  },

  getPartnerRecords() {
    getPartnerPage(this.data.queryParams).then(res => {
      res?.records.forEach(item => {
        item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
        // item.attachmentList.forEach(attachement => {
        //   attachement.filePath = baseUrl + attachement.filePath
        // })
      })

      this.setData({
        records: res?.records || [],
        total: res?.total || 0,
        pages: res?.pages || 0
      })
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const key = options.key;
    this.setData({
      key: key,
      'queryParams.title': key
    })
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
        title: this.data.key
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