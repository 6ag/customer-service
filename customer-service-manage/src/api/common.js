import request from '@/utils/request'

export default {

  /**
   * 获取省市列表
   */
  getProvinceCityList() {
    return request({
      url: '/common/city',
      method: 'get'
    })
  },

  /**
   * 获取行业列表
   */
  getIndustryList() {
    return request({
      url: '/common/industry',
      method: 'get'
    })
  }

}
