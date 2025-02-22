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
      type: [Number, String]
    },
    bizType: {
      type: [Number, String]
    },
    fileList: {
      type: [Array]
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
      try {
        const {
          bizId,
          bizType
        } = this.properties;
        const files = e.detail.files;
        const file = files[0];

        const {
          hashCode,
          fileSize
        } = await calculateFileHash(this.data.chunkSize, file.url);

        const params = {
          hashCode,
          bizId,
          bizType,
          chunkTotal: 1
        };

        const {
          data
        } = await checkFile(params);

        if (data.hasUpload) {
          this.handleUploadSuccess(data.id, data.filePath, data.fileName);

          return;
        }

        await this.uploadFile({
          url: file.url,
          name: file.name,
          hashCode,
          fileSize
        });
      } catch (error) {
        console.error('文件上传失败:', error);
        wx.showToast({
          title: '上传失败！请重试',
          icon: 'none'
        });
      }
    },

    handleRemove(e) {
      removeAttachmentBatchByIds([e.detail.file.id]).then(res => {
        this.setData({
          fileList: this.properties.fileList.filter(item => item.id !== e.detail.file.id)
        })

        wx.showToast({
          title: '删除成功！~',
          icon: 'none'
        })
      })
    },

    handleUploadSuccess(id, filePath, fileName) {
      wx.showToast({
        title: '上传成功！',
        icon: 'none'
      });

      const file = {
        id: id,
        url: baseUrl + filePath,
        name: fileName,
        type: 'image'
      };

      this.setData({
        fileList: [...this.properties.fileList, file]
      });
    },

    uploadFile({
      url,
      name,
      hashCode,
      fileSize
    }) {
      return new Promise((resolve, reject) => {
        wx.uploadFile({
          url: 'http://localhost:9091/file/upload',
          filePath: url,
          name: 'file',
          formData: {
            bizId: this.properties.bizId,
            bizType: this.properties.bizType,
            hashCode,
            fileName: name,
            fileSize,
            chunkSize: this.data.chunkSize,
            chunkIndex: 0,
            chunkTotal: 1
          },
          success: (res) => {
            const {
              data
            } = JSON.parse(res.data);
            this.handleUploadSuccess(data.id, data.filePath, data.fileName);
            resolve(data);
          },
          fail: reject
        });
      });
    }
  }
})