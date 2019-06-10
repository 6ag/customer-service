import request from '@/utils/request'

export default {
  /**
   * 获取会话列表
   * @param userId 用户编号
   * @returns {AxiosPromise}
   */
  getConversationList(userId) {
    return request({
      url: `/conversation/list/${userId}`,
      method: 'get'
    })
  }
}
