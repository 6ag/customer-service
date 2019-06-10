import request from '@/utils/request'

export default {
  /**
   * 获取带分页的常见问题列表
   * @param {Object} faqPageDto 分页常见问题列表查询条件
   */
  getFaqList(faqPageDto) {
    return request({
      url: '/faq/list',
      method: 'post',
      data: faqPageDto
    })
  },

  /**
   * 新增常见问题
   * @param {Object} faqInfo 常见问题信息
   */
  addFaq(faqInfo) {
    return request({
      url: '/faq',
      method: 'post',
      data: faqInfo
    })
  },

  /**
   * 删除常见问题
   * @param {Number} faqId 常见问题编号
   */
  deleteFaq(faqId) {
    return request({
      url: `/faq/${faqId}`,
      method: 'delete'
    })
  },

  /**
   * 修改常见问题
   * @param {Object} faqInfo 常见问题信息
   */
  updateFaq(faqInfo) {
    return request({
      url: '/faq',
      method: 'put',
      data: faqInfo
    })
  }
}
