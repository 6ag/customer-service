import request from '@/utils/request'

export default {
  /**
   * 获取所有角色
   */
  getAllRole() {
    return request({
      url: '/role',
      method: 'get'
    })
  },

  /**
   * 获取带分页的角色列表
   * @param {Object} rolePageDto 分页角色列表查询条件
   */
  getRoleList(rolePageDto) {
    return request({
      url: '/role/list',
      method: 'post',
      data: rolePageDto
    })
  },

  /**
   * 获取角色信息
   * @param {Number} roleId 角色编号
   */
  getRoleInfoById(roleId) {
    return request({
      url: `/role/${roleId}`,
      method: 'get'
    })
  },

  /**
   * 删除角色
   * @param {Number} roleId 角色编号
   */
  deleteRole(roleId) {
    return request({
      url: `/role/${roleId}`,
      method: 'delete'
    })
  },

  /**
   * 新增角色
   * @param {Object} roleDto 角色信息
   */
  addRole(roleDto) {
    return request({
      url: '/role',
      method: 'post',
      data: roleDto
    })
  },

  /**
   * 更新角色
   * @param {Object} roleDto 角色信息
   */
  updateRole(roleDto) {
    return request({
      url: '/role',
      method: 'put',
      data: roleDto
    })
  },

  /**
   * 获取指定用户的最高角色级别
   * @param {Number} userId 用户编号
   */
  getTallestRoleLevel(userId) {
    return request({
      url: `/role/level/${userId}`,
      method: 'get'
    })
  }

}
