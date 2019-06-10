import request from '@/utils/request'

export default {
  /**
   * 获取带分页的团队列表
   * @param {Object} teamPageDto 分页团队列表查询条件
   */
  getTeamList(teamPageDto) {
    return request({
      url: '/team/list',
      method: 'post',
      data: teamPageDto
    })
  },

  /**
   * 新增团队
   * @param {Object} teamInfo 团队信息
   */
  addTeam(teamInfo) {
    return request({
      url: '/team',
      method: 'post',
      data: teamInfo
    })
  },

  /**
   * 删除团队
   * @param {Number} teamId 团队编号
   */
  deleteTeam(teamId) {
    return request({
      url: `/team/${teamId}`,
      method: 'delete'
    })
  },

  /**
   * 修改团队
   * @param {Object} teamInfo 团队信息
   */
  updateTeam(teamInfo) {
    return request({
      url: '/team',
      method: 'put',
      data: teamInfo
    })
  }
}
