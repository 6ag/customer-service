import loginApi from '@/api/login'
import userApi from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  id: 0, // 用户编号
  nickname: '', // 用户昵称
  avatar: '', // 用户头像
  userInfo: {}, // 用户信息
  roles: [], // 角色
  menus: [], // 菜单权限
  buttons: [] // 按钮权限
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USER_INFO: (state, userInfo) => {
    state.id = userInfo.id
    state.nickname = userInfo.nickname
    state.avatar = userInfo.avatar
    state.userInfo = userInfo
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  },
  SET_BUTTONS: (state, buttons) => {
    state.buttons = buttons
  }
}

const actions = {
  // 登录
  login({ commit }, loginForm) {
    return new Promise((resolve, reject) => {
      loginApi.login(loginForm.username.trim(), loginForm.password).then(response => {
        commit('SET_TOKEN', response.data)
        setToken(response.data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      userApi.getUserInfo().then(response => {
        commit('SET_USER_INFO', response.data.userInfo)
        commit('SET_MENUS', response.data.menus)
        commit('SET_BUTTONS', response.data.buttons)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 退出登录
  logout({ commit }) {
    return new Promise((resolve) => {
      commit('SET_TOKEN', '')
      commit('SET_USER_INFO', {})
      commit('SET_ROLES', [])
      removeToken()
      resetRouter()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
