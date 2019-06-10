const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,

  token: state => state.user.token,
  id: state => state.user.id,
  nickname: state => state.user.nickname,
  avatar: state => state.user.avatar,
  userInfo: state => state.user.userInfo,
  roles: state => state.user.roles,
  menus: state => state.user.menus,
  buttons: state => state.user.buttons,

  fullRouters: state => state.permission.fullRouters,
  dynamicRouters: state => state.permission.dynamicRouters
}
export default getters
