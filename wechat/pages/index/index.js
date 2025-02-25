// pages/my/index.js
import {
  getNotePage
} from '../../api/note/index'
import {
  getRatePage
} from '../../api/rate/index'
import {
  getPartnerPage
} from '../../api/partner/index'
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
    activeTab: '1',
    queryParams: {
      pageNo: 1,
      pageSize: 8,
      orderBy: 'createTime',
      isAsc: false,
      status: '1'
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

    if (this.data.activeTab === '0') {
      // wx.navigateTo({
      //   url: '/pages/rate/index',
      // })
    } else if (this.data.activeTab === '1') {
      this.getNoteRecords()
    } else if (this.data.activeTab === '2') {
      this.getRateRecords()
    } else if (this.data.activeTab == '3') {
      this.getPartnerRecords()
    }

    // this.setData({
    //   loading: false,
    //   refreshing: false
    // })
  },

  getNoteRecords() {
    this.setData({
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
          pages: res?.pages || 0,
          end: !res?.records?.length || this.data.queryParams.pageNo >= (res?.pages || 0)
        })
      }).finally(() => {
        this.setData({
          loading: false,
          refreshing: false
        })
      })
    })
  },

  getRateRecords() {
    getRatePage(this.data.queryParams).then(res => {
      res?.records.forEach(item => {
        item.attachmentList.forEach(attachement => {
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

  onTabsChange(event) {
    const activeTab = event.detail.value;

    if (activeTab === '4') {
      wx.navigateTo({
        url: `/pages/search/index`
      })
      return
    }

    this.setData({
      activeTab: activeTab
    });
  },

  onTabsClick(event) {
    this.getRecords()
  },

  onStickyScroll(event) {
    console.log(event.detail);
  },

  onToDetail(event) {
    const id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/note/detail/index?id=${id}`
    });
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
        status: '1'
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

    if (this.data.activeTab === '1') {
      getNotePage(this.data.queryParams).then(res => {
        const records = res?.records || [];

        if (!records.length) {
          this.setData({
            end: true
          });
          showEndToast()
          return
        }

        this.setData({
          records: [...this.data.records, ...records],
          total: res?.total || 0,
          pages: res?.pages || 0,
          end: this.data.queryParams.pageNo >= (res?.pages || 0)
        });
      }).error(() => {
        this.setData({
          'queryParams.pageNo': this.data.queryParams.pageNo - 1
        })
      }).finally(() => {
        this.setData({
          loading: false
        });
      });
    } else if (this.data.activeTab === '2') {
      getRatePage(this.data.queryParams).then(res => {
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
        });
      }).error(() => {
        this.setData({
          'queryParams.pageNo': this.data.queryParams.pageNo - 1
        })
      }).finally(() => {
        this.setData({
          loading: false
        });
      });
    } else if (this.data.activeTab === '3') {
      getPartnerPage(this.data.queryParams).then(res => {
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
        });
      }).error(() => {
        this.setData({
          'queryParams.pageNo': this.data.queryParams.pageNo - 1
        })
      }).finally(() => {
        this.setData({
          loading: false
        });
      });
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})