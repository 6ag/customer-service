import request from '@/utils/request'

export default {
  /**
   * 获取用户信息，通过请求头携带的token
   */
  getUserInfo() {
    return request({
      url: '/user/info',
      method: 'get'
    })
  },

  /**
   * 获取用户信息
   * @param {Number} userId 用户编号
   */
  getUserInfoById(userId) {
    return request({
      url: `/user/${userId}`,
      method: 'get'
    })
  },

  /**
   * 获取用户列表，带条件的分页查询
   * @param {Object} userPageDto 用户列表分页查询条件
   */
  getUserList(userPageDto) {
    return request({
      url: '/user/list',
      method: 'post',
      data: userPageDto
    })
  },

  /**
   * 更新用户信息
   * @param {Object} userDto 用户信息
   */
  updateUser(userDto) {
    return request({
      url: '/user',
      method: 'put',
      data: userDto
    })
  },

  /**
   * 创建用户
   * @param {Object} userDto 用户信息
   */
  addUser(userDto) {
    return request({
      url: '/user',
      method: 'post',
      data: userDto
    })
  },

  /**
   * 删除用户
   * @param {Number} userId 用户编号
   */
  deleteUser(userId) {
    return request({
      url: `/user/${userId}`,
      method: 'delete'
    })
  }

}
