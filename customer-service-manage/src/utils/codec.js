// magic4字节 + 版本1字节 + 序列化算法1字节 + 指令2字节 + 数据长度4字节 = 12字节
const prefixLength = 12

/**
* 编码
* 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令2字节 + 数据长度4字节 + 数据内容
* 总长度 = 12 + 数据内容
* @param packet 数据包
* @returns {ArrayBuffer}
*/
export function encode(packet) {
  const bytes = stringToBytes(JSON.stringify(packet))
  const buffer = new ArrayBuffer(prefixLength + bytes.length)
  if (buffer.byteLength !== prefixLength + bytes.length) {
    console.log('编码分配内存失败，内存不足')
    return null
  }
  const dataView = new DataView(buffer)

  dataView.setInt32(0, 0x12345678)
  dataView.setInt8(4, packet.version)
  dataView.setInt8(5, 1) // 写死1表示json序列化
  dataView.setInt16(6, packet.command)
  dataView.setInt32(8, bytes.length)
  for (let i = prefixLength; i < bytes.length + prefixLength; i++) {
    dataView.setUint8(i, bytes[i - prefixLength])
  }
  return dataView.buffer
}

/**
* 解码
* 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令2字节 + 数据长度4字节 + 数据内容
* 总长度 = 12 + 数据内容
* @param buffer {ArrayBuffer}
* @returns {JSON}
*/
export function decode(buffer) {
  const dataView = new DataView(buffer)
  const lenght = dataView.getInt32(8)
  const bytes = []
  for (let i = prefixLength; i < lenght + prefixLength; i++) {
    bytes[i - prefixLength] = dataView.getUint8(i)
  }
  const json = bytesToString(bytes)
  return JSON.parse(json)
}

/**
* 字符串转byte数组
* @param str
* @returns {Array}
*/
function stringToBytes(str) {
  const bytes = []
  let c
  const len = str.length
  for (let i = 0; i < len; i++) {
    c = str.charCodeAt(i)
    if (c >= 0x010000 && c <= 0x10FFFF) {
      bytes.push(((c >> 18) & 0x07) | 0xF0)
      bytes.push(((c >> 12) & 0x3F) | 0x80)
      bytes.push(((c >> 6) & 0x3F) | 0x80)
      bytes.push((c & 0x3F) | 0x80)
    } else if (c >= 0x000800 && c <= 0x00FFFF) {
      bytes.push(((c >> 12) & 0x0F) | 0xE0)
      bytes.push(((c >> 6) & 0x3F) | 0x80)
      bytes.push((c & 0x3F) | 0x80)
    } else if (c >= 0x000080 && c <= 0x0007FF) {
      bytes.push(((c >> 6) & 0x1F) | 0xC0)
      bytes.push((c & 0x3F) | 0x80)
    } else {
      bytes.push(c & 0xFF)
    }
  }
  return bytes
}

/**
* byte数组转字符串
* @param bytes
* @returns {string|string|string}
*/
function bytesToString(bytes) {
  if (typeof bytes === 'string') {
    return bytes
  }
  let str = ''; const _arr = bytes
  for (let i = 0; i < _arr.length; i++) {
    const one = _arr[i].toString(2); const v = one.match(/^1+?(?=0)/)
    if (v && one.length === 8) {
      const bytesLength = v[0].length
      let store = _arr[i].toString(2).slice(7 - bytesLength)
      for (let st = 1; st < bytesLength; st++) {
        store += _arr[st + i].toString(2).slice(2)
      }
      str += String.fromCharCode(parseInt(store, 2))
      i += bytesLength - 1
    } else {
      str += String.fromCharCode(_arr[i])
    }
  }
  return str
}
