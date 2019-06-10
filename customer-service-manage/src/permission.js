import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// 免登录白名单
const whiteList = ['/login', '/visitor']

// 路由跳转前
router.beforeEach((to, from, next) => {
  NProgress.start()
  document.title = getPageTitle(to.meta.title)
  const hasToken = getToken()
  if (hasToken) {
    // 已登录且要跳转的页面是登录页
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      // 判断当前用户是否已经获取用户信息（用户信息中包含了权限信息）
      if (store.getters.id) {
        next()
      } else {
        // 获取用户信息
        store.dispatch('user/getInfo').then(response => {
          // 动态设置路由
          store.dispatch('permission/generateRouters', store.getters.menus).then(response => {
            // 将已经解析好的路由列表，动态添加到vue的router中
            router.addRoutes(store.getters.dynamicRouters)
            // hack方法 确保addRoutes已完成
            next({ ...to, replace: true })
          })
        }).catch(error => {
          console.log(error)
          store.dispatch('user/logout').then(() => {
            // 为了重新实例化vue-router对象 避免bug
            location.reload()
            // next({ path: '/' })
          })
        })
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      // 否则全部重定向到登录页
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

// 路由跳转后
router.afterEach(() => {
  NProgress.done()
})
