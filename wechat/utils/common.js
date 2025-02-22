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

/**
 * 计算文件散列值
 * @param {*} chunkSize 分片大小
 * @param {*} url 文件地址
 */
export const calculateFileHash = async (chunkSize, url) => {
  const res = await new Promise((resolve, reject) => {
    wx.request({
      url,
      responseType: 'arraybuffer',
      success: resolve,
      fail: reject
    });
  });

  if (res.statusCode !== 200) {
    throw new Error(`文件请求失败，状态码: ${res.statusCode}`);
  }

  const buffer = res.data;
  const fileSize = buffer.byteLength;
  const contentType = res.header['Content-Type'] || res.header['content-type'];

  const [fileType, hashCode] = await Promise.all([
    getFileTypeByContentType(contentType),
    getHashCode(createChunks(buffer, chunkSize))
  ]);

  return {
    hashCode,
    fileSize,
    fileType
  };
}

/**
 * 创建文件分片
 * @param {*} file 文件
 * @param {*} chunkSize 分片大小
 */
export const createChunks = (buffer, chunkSize) => {
  const bufferLength = buffer.byteLength;
  const chunkCount = Math.ceil(bufferLength / chunkSize);

  return Array.from({
    length: chunkCount
  }, (_, index) => {
    const start = index * chunkSize;
    const end = Math.min(start + chunkSize, bufferLength);
    return buffer.slice(start, end);
  });
};

/**
 * 计算文件散列值
 * @param {*} chunks 文件分片
 */
export const getHashCode = (chunks) => {
  const spark = new SparkMD5.ArrayBuffer();

  chunks.forEach(chunk => {
    spark.append(chunk);
  });

  return spark.end();
};

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

/**
 * 国家列表
 */
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

/**
 * 省份列表
 */
export const provinceList = [{
    label: '河南',
    value: '1'
  },
  {
    label: '广东',
    value: '2'
  }
]

/**
 * 城市列表
 */
export const cityList = [{
    label: '郑州',
    value: '1'
  },
  {
    label: '昆明',
    value: '2'
  }
]