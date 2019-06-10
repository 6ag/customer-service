/**
 * 生成数据包
 * @param {Object} data 数据包参数
 * @param {Number} command 指令
 */
export function createPacket(data, command) {
  return Object.assign({
    version: 1,
    command: command
  }, data)
}
