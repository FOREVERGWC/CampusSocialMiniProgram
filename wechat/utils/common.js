import SparkMD5 from 'spark-md5'

export const baseUrl = 'http://localhost:9091'
export const defaultAvatar = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

export function formatTimeAgo(updateTime) {
  const now = new Date(); // 获取当前时间
  const updateDate = new Date(updateTime); // 将传入的时间字符串转化为 Date 对象
  const diff = now - updateDate; // 计算时间差，单位是毫秒

  const seconds = Math.floor(diff / 1000); // 秒
  const minutes = Math.floor(seconds / 60); // 分钟
  const hours = Math.floor(minutes / 60); // 小时
  const days = Math.floor(hours / 24); // 天
  const years = Math.floor(days / 365); // 年

  // 获取昨天的日期并进行对比
  const yesterday = new Date(now);
  yesterday.setDate(now.getDate() - 1);

  // 判断是否是昨天
  const isYesterday = updateDate.getDate() === yesterday.getDate() &&
    updateDate.getMonth() === yesterday.getMonth() &&
    updateDate.getFullYear() === yesterday.getFullYear();

  if (seconds < 60) {
    return `${seconds}秒前`;
  } else if (minutes < 60) {
    return `${minutes}分钟前`;
  } else if (hours < 24) {
    return `${hours}小时前`;
  } else if (days === 1 && isYesterday) {
    // 昨天的时间格式化为 "昨天 hh:mm"
    const hours = updateDate.getHours();
    const minutes = updateDate.getMinutes();
    return `昨天 ${hours}:${minutes < 10 ? '0' : ''}${minutes}`;
  } else if (days < 7) {
    return `${days}天前`;
  } else if (years === 0 && days >= 3) {
    // 如果是今年，并且是3天前的日期，则显示为 "X月X日"
    const month = updateDate.getMonth() + 1; // 月份是从0开始的，所以加1
    const date = updateDate.getDate();
    return `${month}月${date}日`;
  } else {
    // 如果差距超过一年，直接返回原始时间字符串，并截取日期部分 (YYYY-MM-DD)
    return updateTime.split(' ')[0]; // 截取掉时分秒，只返回日期部分
  }
}

export const processUpdateTime = (records) => {
  records.forEach(item => {
    item.updateTime = formatTimeAgo(item.updateTime);

    if (item.children && Array.isArray(item.children) && item.children.length > 0) {
      processUpdateTime(item.children);
    }
  })
}

export const calculateFileHash = async (chunkSize, url) => {
  return new Promise((resolve, reject) => {
    console.log(url);
    wx.request({
      url: url,
      responseType: 'arraybuffer',
      success: async (res) => {
        if (res.statusCode === 200) {
          const buffer = res.data;
          const fileSize = buffer.byteLength;
          const contentType = res.header['Content-Type'] || res.header['content-type'];
          const fileType = getFileTypeByContentType(contentType)
          try {
            const chunks = createChunks(buffer, chunkSize)
            const hashCode = await getHashCode(chunks)
            resolve({
              hashCode: hashCode,
              fileSize: fileSize,
              fileType: fileType
            });
          } catch (error) {
            reject('计算哈希值时出错: ' + error);
          }
        } else {
          reject('文件请求失败，状态码: ' + res.statusCode);
        }
      },
      fail: (error) => {
        reject('请求失败: ' + error.errMsg);
      }
    })
  })
}

const bufferToBlob = (buffer, mimeType = 'application/octet-stream') => {
  return new Blob([buffer], { type: mimeType });
};

const bufferToFile = (buffer, fileName, mimeType = 'application/octet-stream') => {
  const blob = bufferToBlob(buffer, mimeType);
  blob.name = fileName; // 小程序不支持 File，手动添加 name 字段
  return blob;
};


const generateUUID = () => {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0,
      v = c === 'x' ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
};

const getMimeType = (filePath) => {
  return new Promise((resolve, reject) => {
    wx.getFileSystemManager().readFile({
      filePath,
      success: (res) => {
        const buffer = new Uint8Array(res.data);
        const mimeType = detectMimeType(buffer);
        resolve(mimeType);
      },
      fail: (err) => {
        console.error('MIME 类型检测失败:', err);
        resolve('application/octet-stream'); // 默认 MIME 类型
      }
    });
  });
};

const detectMimeType = (buffer) => {
  const signatures = {
    'image/png': [0x89, 0x50, 0x4E, 0x47],
    'image/jpeg': [0xFF, 0xD8, 0xFF],
    'image/gif': [0x47, 0x49, 0x46, 0x38],
    'application/pdf': [0x25, 0x50, 0x44, 0x46],
    'application/zip': [0x50, 0x4B, 0x03, 0x04],
    'text/plain': [0x74, 0x65, 0x78, 0x74],
  };

  for (const [mime, signature] of Object.entries(signatures)) {
    if (signature.every((byte, index) => buffer[index] === byte)) {
      return mime;
    }
  }

  return 'application/octet-stream'; // 默认 MIME 类型
};

/**
 * 创建文件分片
 * @param {*} file 文件
 * @param {*} chunkSize 分片大小
 */
export const createChunks = (buffer, chunkSize) => {
  const chunks = [];
  const bufferLength = buffer.byteLength;
  
  // 按照 chunkSize 分割 buffer
  for (let i = 0; i < bufferLength; i += chunkSize) {
    const chunk = buffer.slice(i, Math.min(i + chunkSize, bufferLength));
    chunks.push(chunk);
  }

  return chunks;
};

// export const createChunks = (file, chunkSize) => {
//   return Array.from({
//       length: Math.ceil(file.size / chunkSize)
//     }, (_, index) =>
//     file.slice(index * chunkSize, Math.min((index + 1) * chunkSize, file.size))
//   )
// }

/**
 * 计算文件散列值
 * @param {*} chunks 
 */
export const getHashCode = (chunks) => {
  return new Promise(resolve => {
    const spark = new SparkMD5.ArrayBuffer();

    for (let i = 0; i < chunks.length; i++) {
      spark.append(chunks[i]); // 直接添加 ArrayBuffer
    }

    const result = spark.end();
    resolve(result);
  });
};
// export const getHashCode = (chunks) => {
//   return new Promise(resolve => {
//     const spark = new SparkMD5.ArrayBuffer();

//     const read = (i) => {
//       if (i >= chunks.length) {
//         const result = spark.end();
//         resolve(result);
//         return;
//       }

//       const blob = chunks[i];
//       const reader = new FileReader();

//       reader.onload = e => {
//         const arrayBuffer = e.target.result;

//         spark.append(arrayBuffer);
//         read(i + 1);
//       };

//       reader.onerror = (error) => {
//         console.error('Error reading chunk:', error);
//       };

//       reader.readAsArrayBuffer(blob);
//     };

//     read(0);
//   });
// };

/**
 * 根据响应类型获取文件类型
 * @param {*} contentType 响应类型
 */
export const getFileTypeByContentType = (contentType) => {
  const map = {
    'image/jpeg': 'jpeg',
    'image/png': 'png'
  }

  return map[contentType]
}

export const countryList = [{
    label: '中国',
    value: '1'
  },
  {
    label: '美国',
    value: '2'
  },
  {
    label: '俄罗斯',
    value: '3'
  },
  {
    label: '英国',
    value: '4'
  },
  {
    label: '法国',
    value: '5'
  }
]

export const provinceList = [{
    label: '河南',
    value: '1'
  },
  {
    label: '广东',
    value: '2'
  }
]

export const cityList = [{
    label: '郑州',
    value: '1'
  },
  {
    label: '昆明',
    value: '2'
  }
]