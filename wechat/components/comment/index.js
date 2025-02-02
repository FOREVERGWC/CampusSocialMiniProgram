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
    commentList: [],
    total: 0,
    pages: 0,
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
        'queryParams.bizId': this.properties.bizId,
        'queryParams.bizType': this.properties.bizType
      }, () => {
        getCommentPage(this.data.queryParams).then(res => {
          if (res.code !== 200) {
            wx.showToast({
              title: res.msg,
              icon: 'none'
            });
            return
          }

          res.data?.records.forEach(item => {
            item.user.avatar = item.user.avatar ? baseUrl + item.user.avatar : defaultAvatar
          })

          processUpdateTime(res.data?.records)

          this.setData({
            commentList: res.data?.records || [],
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
        if (res.code !== 200) {
          wx.showToast({
            title: res.msg,
            icon: 'none'
          });
          return
        }

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
        if (res.code !== 200) {
          return
        }

        const {
          commentList
        } = this.data;
        const index = commentList.findIndex(item => item.id === e.currentTarget.id);

        if (index === -1) {
          return;
        }

        const item = commentList[index];
        item.count.like.hasDone = !item.count.like.hasDone;
        item.count.like.num = res.data;

        this.setData({
          commentList: commentList
        });
        // TODO 操作成功
      }).catch(() => {
        // TODO 操作失败
      })
    }
  },

  attached() {
    this.setData({
      'queryParams.bizId': this.properties.bizId,
      'queryParams.bizType': this.properties.bizType
    })
  }
})