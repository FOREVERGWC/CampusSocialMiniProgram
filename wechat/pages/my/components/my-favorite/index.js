// pages/my/components/my-favorite/index.js
import {
  baseUrl,
  defaultAvatar
} from '../../../../utils/common'
import {
  getMyFavoriteNotePage
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
    activeTab: '0',
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
          activeTab: selectedTab
        })
        // TODO 查询笔记、投票、活动
      }
    },

    onToDetail(event) {
      const id = event.currentTarget.id;
      wx.navigateTo({
        url: `/pages/note/detail/index?id=${id}`
      });
    },

    getRecords() {
      if (!this.properties.userId) {
        return;
      }

      this.setData({
        end: false
      })

      this.setData({
        'queryParams.userId': this.properties.userId
      }, () => {
        getMyFavoriteNotePage(this.data.queryParams).then(res => {
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
      this.getRecords();
    }
  },

  attached() {
    this.setData({
      'queryParams.userId': this.properties.userId
    })
  }
})