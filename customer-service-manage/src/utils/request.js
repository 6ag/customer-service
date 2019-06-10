import axios from 'axios'
import { MessageBox, Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import Qs from 'qs'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  withCredentials: false,
  timeout: 5000,
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json; charset=UTF-8'
  }
})

// request拦截器
service.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token
  }
  const method = config.method.toLocaleLowerCase()
  if (method === 'get' && config.data) {
    config.url += '?' + Qs.stringify(config.data)
  }
  return config
}, error => {
  console.log(`请求错误=${JSON.stringify(error)}`)
  return Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(response => {
  // response.data才是后台ResultVo返回的数据
  const res = response.data

  // 成功
  if (res.status === 200) {
    return res
  }

  // 400: Illegal token; 401: Other clients logged in; 403: Token expired;
  if (res.status === 400 || res.status === 401 || res.status === 403) {
    MessageBox.confirm('您已被登出，可以点击取消停留在此页面，或者重新登录', '温馨提示', {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      store.dispatch('user/logout').then(() => {
        // 为了重新实例化vue-router对象 避免bug
        location.reload()
      })
    })
  } else {
    Notification.error({
      title: '发生错误',
      message: res.message,
      duration: 2500
    })
  }

  return Promise.reject(res.message || '发生错误')
}, error => {
  console.log(`请求错误=${JSON.stringify(error)}`)

  let message = ''
  if (error.response && error.response.data.message) {
    message = error.response.data.message
  } else if (error.message) {
    message = error.message
  } else {
    message = '请求失败'
  }

  Notification.error({
    title: '发生错误',
    message: message,
    duration: 2500
  })
  return Promise.reject(error)
})

export default service
