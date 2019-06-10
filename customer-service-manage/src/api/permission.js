import request from '@/utils/request'

export default {
  /**
   * 获取所有权限
   */
  getAllPermission() {
    return request({
      url: '/permission/tree',
      method: 'get'
    })
  },

  /**
   * 获取所有菜单
   */
  getAllMenus() {
    return request({
      url: '/permission/menus',
      method: 'get'
    })
  },

  /**
   * 新增权限
   * @param {Object} permissionInfo 权限信息
   */
  addPermission(permissionInfo) {
    return request({
      url: '/permission',
      method: 'post',
      data: permissionInfo
    })
  },

  /**
   * 删除权限
   * @param {Number} permissionId 权限编号
   */
  deletePermission(permissionId) {
    return request({
      url: `/permission/${permissionId}`,
      method: 'delete'
    })
  },

  /**
   * 修改权限
   * @param {Object} permissionInfo 权限信息
   */
  updatePermission(permissionInfo) {
    return request({
      url: '/permission',
      method: 'put',
      data: permissionInfo
    })
  }
}
