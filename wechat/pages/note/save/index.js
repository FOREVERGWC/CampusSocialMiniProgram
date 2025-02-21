// pages/note/save/index.js
import {
  getNoteCategoryList
} from '../../../api/note/category'
import {
  saveNote
} from '../../../api/note/index'
import {
  removeAttachmentBatchByIds
} from '../../../api/attachment/index'
import {
  checkFile
} from '../../../api/file/index'
import {
  baseUrl,
  calculateFileHash
} from '../../../utils/common'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: '',
    content: '',
    detail: {},
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
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        categoryList: res.data || []
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
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        title: res.data?.title || '',
        content: res.data?.content || '',
        detail: res.data,
        categoryValue: [res.data?.categoryId || ''],
        categoryLabel: this.data.categoryList?.find(item => item.id === res.data?.categoryId)?.name || '',
        visibleValue: [res.data?.visible || ''],
        visibleLabel: this.data.visibleList?.find(item => item.value === res.data?.visible)?.label || '',
        fileList: res.data?.attachmentList.map(item => ({
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

  async handleAdd(e) {
    const fileList = this.data.fileList;
    const files = e.detail.files;
    console.log(e.currentTarget.id, fileList, files);
    const chunkSize = 10 * 1024 * 1024
    const {
      hashCode,
      fileSize
    } = await calculateFileHash(chunkSize, files[0].url)
    // TODO: 文件分片处理
    const checkParams = {
      hashCode: hashCode,
      bizId: e.currentTarget.id,
      bizType: 8,
      chunkTotal: 1
    }
    const {
      data
    } = await checkFile(checkParams)
    if (data.hasUpload) {
      wx.showToast({
        title: '上传成功！~',
        icon: 'none'
      })
      console.log(data);
      const item = {
        id: data.id,
        url: baseUrl + data.filePath,
        name: data.fileName,
        type: 'image'
      }
      this.setData({
        fileList: [...fileList, item]
      });

      return
    }

    const _this = this
    wx.uploadFile({
      url: 'http://localhost:9091/file/upload',
      filePath: files[0].url,
      name: 'file',
      formData: {
        bizId: e.currentTarget.id,
        bizType: 8,
        hashCode: hashCode,
        fileName: files[0].name,
        fileSize: fileSize,
        chunkSize: chunkSize,
        chunkIndex: 0,
        chunkTotal: 1
      },
      success(res) {
        const data = JSON.parse(res.data)
        const item = {
          id: data.data.id,
          url: baseUrl + data.data.filePath,
          name: data.data.fileName,
          type: 'image'
        }
        _this.setData({
          fileList: [...fileList, item]
        });
        _this.data.fileList.forEach(file => {
          console.log("Name:", file.name);
          console.log("URL:", file.url);
          console.log("Type:", file.type);
        });
      }
    })
  },

  // handleClick(e) {
  //   console.log(e.detail.file);
  // },

  // handleSuccess(e) {
  //   console.log('success', e);
  //   const files = e.detail.files;
  //   this.setData({
  //     fileList: files
  //   });
  // },

  handleRemove(e) {
    removeAttachmentBatchByIds([e.detail.file.id]).then(res => {
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

      this.setData({
        fileList: this.data.fileList.filter(item => item.id !== e.detail.file.id)
      })

      wx.showToast({
        title: '删除成功！~',
        icon: 'none'
      })
    })
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
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

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
      if (res.code !== 200) {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        });
        return
      }

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