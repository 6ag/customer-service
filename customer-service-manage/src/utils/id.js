import Cookies from 'js-cookie'

const idKey = 'socket_visitor_id'

/**
 * 获取cookie中的唯一标识
 */
export function getId() {
  let id = Cookies.get(idKey)
  if (!id) {
    id = genNoDuplicateId()
    Cookies.set(idKey, id)
  }
  console.log(`唯一标识 ${id}`)
  return id
}

/**
 * 生成不重复ID
 */
function genNoDuplicateId() {
  return (Math.random() * 10000000).toString(16).substr(0, 4) + '-' + (new Date()).getTime() + '-' + Math.random().toString().substr(2, 5)
}
