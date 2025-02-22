const service = {
  baseURL: 'http://localhost:9091',
  timeout: 5000,
  header: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  interceptors: {
    request: (config) => {
      const token = getApp().globalData.token;
      if (token) {
        config.header.token = `Bearer ${token}`;
      }

      if (config.method === 'GET' && config.params) {
        config.url = `${config.url}?${tansParams(config.params)}`;
        config.data = {};
      }
      return config;
    },

    response: (response) => response.data
  }
};

/**
 * 参数处理函数
 * @param {*} params 请求参数
 */
const tansParams = (params) => {
  let result = '';
  for (const key in params) {
    const value = params[key];
    if (value !== null && value !== '' && typeof value !== 'undefined') {
      if (typeof value === 'object') {
        for (const subKey in value) {
          if (value[subKey] !== null && value[subKey] !== '') {
            const fullKey = `${key}[${subKey}]`;
            result += `${encodeURIComponent(fullKey)}=${encodeURIComponent(value[subKey])}&`;
          }
        }
      } else {
        result += `${encodeURIComponent(key)}=${encodeURIComponent(value)}&`;
      }
    }
  }
  return result.slice(0, -1);
};

const request = (options = {}) => {
  const {
    url,
    method = 'GET',
    data = {},
    params = {},
    header = {}
  } = options;

  const config = {
    url: service.baseURL + url,
    method: method.toUpperCase(),
    data: data,
    params: params,
    header: {
      ...service.header,
      ...header
    },
    timeout: service.timeout
  };

  const interceptedConfig = service.interceptors.request(config);

  return new Promise((resolve, reject) => {
    wx.request({
      ...interceptedConfig,
      success: (res) => {
        try {
          const {
            code,
            msg,
            data
          } = service.interceptors.response(res);

          if (code === 401) {
            wx.showToast({
              title: '请先登录！',
              icon: 'none'
            });

            getApp().globalData.user = {}
            getApp().globalData.avatar = 'https://tdesign.gtimg.com/mobile/demos/avatar1.png'
            getApp().globalData.schoolInfo = {}
            getApp().globalData.token = ''

            setTimeout(() => {
              wx.navigateTo({
                url: '/pages/login/index'
              })
            }, 3000)
          } else if (code !== 200) {
            throw new Error(msg)
          }

          resolve(data);
        } catch (error) {
          wx.showToast({
            title: error.message || '业务异常',
            icon: 'none'
          });

          reject(error);
        }
      },
      fail: (error) => {
        wx.showToast({
          title: '请求超时！服务器开小差啦~',
          icon: 'none'
        });

        reject(error);
      }
    });
  });
};

export default request;