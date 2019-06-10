/**
 * 是否是外部链接，地址、电话、邮件
 * @param {String} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 验证用户名
 * @param {String} username
 * @returns {Boolean}
 */
export function validUsername(username) {
  return true
}
