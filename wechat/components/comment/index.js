// components/comment/index.js
import {
  getCommentPage,
  saveComment
} from '../../api/comment/index'
import {
  handleLike
} from '../../api/like/index'
import {
  baseUrl,
  defaultAvatar,
  processUpdateTime
} from '../../utils/common'

Component({
  options: {
    styleIsolation: 'apply-shared'
  },

  /**
   * 组件的属性列表
   */
  properties: {
    bizId: {
      type: [Number, String],
      observer: function (_new, _old) {
        if (_new !== _old) {
          this.getList();
        }
      }
    },
    bizType: {
      type: [Number, String],
      observer: function (_new, _old) {
        if (_new !== _old) {
          this.getList();
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
    records: [],
    total: 0,
    pages: 0,
    loading: true,
    refreshing: false,
    end: true,
    avatar: getApp().globalData.avatar,
    content: '',
    reply: {
      id: null,
      userId: null,
      username: ''
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getList() {
      if (!this.properties.bizId || !this.properties.bizType) {
        return;
      }

      this.setData({
        end: false
      })

      this.setData({
        'queryParams.bizId': this.properties.bizId,
        'queryParams.bizType': this.properties.bizType
      }, () => {
        getCommentPage(this.data.queryParams).then(res => {
          res?.records.forEach(item => {
            item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
          })

          processUpdateTime(res?.records)

          this.setData({
            records: res?.records || [],
            total: res?.total || 0,
            pages: res?.pages || 0
          })
        })

        this.setData({
          loading: false,
          refreshing: false
        })
      })
    },

    onInput(e) {
      this.setData({
        content: e.detail.value
      });
    },

    onReplyClick(e) {
      this.setData({
        'reply.id': e.currentTarget.id,
        'reply.userId': e.currentTarget.dataset.userId,
        'reply.username': e.currentTarget.dataset.username,
        'content': `@${e.currentTarget.dataset.username} `
      });
    },

    onReply() {
      if (this.data.content === '') {
        wx.showToast({
          title: '请输入评论内容！~',
          icon: 'none'
        })

        return
      }

      const data = {
        bizId: this.properties.bizId,
        bizType: this.properties.bizType,
        content: this.data.content,
        replyId: this.data.reply.id
      };
      saveComment(data).then(res => {
        wx.showToast({
          title: '评论成功！~',
          icon: 'none'
        })
        this.setData({
          content: '',
          reply: {
            id: null,
            userId: null,
            username: ''
          }
        });
        this.getList()
      }).catch(error => {
        console.log('评论失败', error);
      })
    },

    handleLikeComment(e) {
      const data = {
        bizType: 5,
        bizId: e.currentTarget.id
      }
      handleLike(data).then(res => {
        const {
          records
        } = this.data;

        const index = e.currentTarget.dataset.ancestorId !== '0' ?
          records.findIndex(item => item.id === e.currentTarget.dataset.ancestorId) :
          records.findIndex(item => item.id === e.currentTarget.id);

        if (index === -1) {
          return;
        }

        const item = e.currentTarget.dataset.ancestorId !== '0' ?
          records[index].children?.find(child => child.id === e.currentTarget.id) :
          records[index];

        item.count.like.hasDone = !item.count.like.hasDone;
        item.count.like.num = res;

        this.setData({
          records: records
        });
        // TODO 操作成功
      }).catch(() => {
        // TODO 操作失败
      })
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

      this.getList();
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

      getCommentPage(this.data.queryParams).then(res => {
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

        records.forEach(item => {
          item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
        })

        processUpdateTime(records)

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
    }
  },

  attached() {
    this.setData({
      'queryParams.bizId': this.properties.bizId,
      'queryParams.bizType': this.properties.bizType
    })
  }
})