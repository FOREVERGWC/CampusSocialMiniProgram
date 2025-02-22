// components/attachment-upload/index.js
import {
  baseUrl,
  calculateFileHash
} from '../../utils/common'
import {
  checkFile
} from '../../api/file/index'
import {
  removeAttachmentBatchByIds
} from '../../api/attachment/index'

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
          // this.getList();
        }
      }
    },
    bizType: {
      type: [Number, String],
      observer: function (_new, _old) {
        if (_new !== _old) {
          // this.getList();
        }
      }
    },
    fileList: {
      type: [Array],
      observer: function (_new, _old) {
        if (_new !== _old) {
          // this.getList();
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    chunkSize: 10 * 1024 * 1024
  },

  /**
   * 组件的方法列表
   */
  methods: {
    async handleAdd(e) {
      const fileList = this.properties.fileList;
      const files = e.detail.files;
      const {
        hashCode,
        fileSize
      } = await calculateFileHash(this.data.chunkSize, files[0].url)
      // TODO: 文件分片处理
      const checkParams = {
        hashCode: hashCode,
        bizId: this.properties.bizId,
        bizType: this.properties.bizType,
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
          bizId: this.properties.bizId,
          bizType: this.properties.bizType,
          hashCode: hashCode,
          fileName: files[0].name,
          fileSize: fileSize,
          chunkSize: this.data.chunkSize,
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
        }
      })
    },

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
          fileList: this.properties.fileList.filter(item => item.id !== e.detail.file.id)
        })

        wx.showToast({
          title: '删除成功！~',
          icon: 'none'
        })
      })
    },
  }
})