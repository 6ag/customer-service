/**
 * 判断字符串是否为空
 * @param {String} str 字符串
 * @returns {Boolean}
 */
export function isEmpty(str) {
  return str == null || str === undefined || str === 'undefined' || str === ''
}

/**
 * 判断字符串是否不为空
 * @param {String} str 字符串
 * @returns {Boolean}
 */
export function isNotEmpty(str) {
  return str != null && str !== undefined && str !== 'undefined' || str !== ''
}
