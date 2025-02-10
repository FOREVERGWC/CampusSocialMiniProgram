// pages/my/components/my-note/index.js
import {
  baseUrl,
  defaultAvatar
} from '../../../../utils/common'
import {
  countMyNoteVisible,
  getMyNotePage
} from '../../../../api/note/index'

Component({
  options: {
    styleIsolation: 'apply-shared'
  },

  /**
   * 组件的属性列表
   */
  properties: {
    userId: {
      type: [Number, String],
      observer: function (_new, _old) {
        if (_new !== _old) {
          this.getCount()
          this.getRecords();
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    queryParams: {
      pageNo: 1,
      pageSize: 8
    },
    activeTab: '1',
    visible: {
      public: 0,
      private: 0,
    },
    records: [],
    total: 0,
    pages: 0,
    loading: true,
    refreshing: false,
    end: true
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onTabClick(e) {
      const selectedTab = e.currentTarget.dataset.tab;
      if (this.data.activeTab !== selectedTab) {
        this.setData({
          activeTab: selectedTab,
          'queryParams.visible': selectedTab
        }, () => {
          this.getRecords()
        })
      }
    },

    onToDetail(event) {
      const id = event.currentTarget.id;
      wx.navigateTo({
        url: `/pages/note/detail/index?id=${id}`
      });
    },

    getCount() {
      if (!this.properties.userId) {
        return;
      }

      countMyNoteVisible().then(res => {
        if (res.code !== 200) {
          wx.showToast({
            title: res.msg,
            icon: 'none'
          });
          return
        }

        this.setData({
          'visible.public': res.data?.['1'] || 0,
          'visible.private': res.data?.['0'] || 0
        })
      })
    },

    getRecords() {
      if (!this.properties.userId) {
        return;
      }

      this.setData({
        end: false
      })

      this.setData({
        'queryParams.userId': this.properties.userId,
        'queryParams.visible': this.data.activeTab
      }, () => {
        getMyNotePage(this.data.queryParams).then(res => {
          if (res.code !== 200) {
            wx.showToast({
              title: res.msg,
              icon: 'none'
            });
            return
          }

          res.data?.records.forEach(item => {
            item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
            item.attachmentList.forEach(attachement => {
              attachement.filePath = baseUrl + attachement.filePath
            })
          })

          this.setData({
            records: res.data?.records || [],
            total: res.data?.total || 0,
            pages: res.data?.pages || 0
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

        this.setData({
          loading: false,
          refreshing: false
        })
      })
    },

    refresh() {
      this.getCount();
      this.getRecords();
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {
      this.setData({
        queryParams: {
          pageNo: 1,
          pageSize: 8
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
    }
  },

  attached() {
    this.setData({
      'queryParams.userId': this.properties.userId
    })
  }
})