/**
 * 事件派发器
 * @constructor
 */
const EventDispatcher = function() {
  this.listeners = {}
}

/**
* 添加监听器
* @param eventKey
* @param fun
* @param context
* @returns {{func: *, context: *}}
*/
EventDispatcher.prototype.addListener = function(eventKey, fun, context) {
  let list = this.listeners[eventKey]
  if (list === undefined) {
    list = []
    this.listeners[eventKey] = list
  }
  const listener = {
    func: fun,
    context: context
  }
  list.push(listener)
  return listener
}

/**
* 移除监听器
* @param eventKey
* @param fun
* @param context
*/
EventDispatcher.prototype.removeListener = function(eventKey, fun, context) {
  const list = this.listeners[eventKey]
  if (list !== undefined) {
    const size = list.length
    for (let i = 0; i < size; i++) {
      const listener = list[i]
      if (listener.func === fun && listener.context === context) {
        list.splice(i, 1)
        return
      }
    }
  }
}

/**
* 给监听器派发事件
* @param eventKey
* @param event
*/
EventDispatcher.prototype.dispatchEvent = function(eventKey, event) {
  const list = this.listeners[eventKey]
  if (list !== undefined) {
    const size = list.length
    for (let i = 0; i < size; i++) {
      const listener = list[i]
      const fun = listener.func
      const context = listener.context
      if (context != null) {
        fun.call(context, event)
      } else {
        fun(event)
      }
    }
  }
}

export default EventDispatcher
