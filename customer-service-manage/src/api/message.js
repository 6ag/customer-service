import request from '@/utils/request'

export default {
  /**
   * 获取消息列表
   * @param messageListDto 查询条件
   * @returns {AxiosPromise}
   */
  getMessageList(messageListDto) {
    return request({
      url: '/message/list',
      method: 'post',
      data: messageListDto
    })
  }
}
