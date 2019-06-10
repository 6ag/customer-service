/**
 * 事件派发器
 * @constructor
 */
const EventDispatcher = function () {
    this.listeners = {};
};

/**
 * 添加监听器
 * @param eventKey
 * @param fun
 * @param context
 * @returns {{func: *, context: *}}
 */
EventDispatcher.prototype.addListener = function (eventKey, fun, context) {
    let list = this.listeners[eventKey];
    if (list === undefined) {
        list = [];
        this.listeners[eventKey] = list;
    }
    let listener = {
        func: fun,
        context: context
    };
    list.push(listener);
    return listener;
};

/**
 * 移除监听器
 * @param eventKey
 * @param fun
 * @param context
 */
EventDispatcher.prototype.removeListener = function (eventKey, fun, context) {
    let list = this.listeners[eventKey];
    if (list !== undefined) {
        let size = list.length;
        for (let i = 0; i < size; i++) {
            let listener = list[i];
            if (listener.func === fun && listener.context === context) {
                list.splice(i, 1);
                return;
            }
        }
    }
};

/**
 * 给监听器派发事件
 * @param eventKey
 * @param event
 */
EventDispatcher.prototype.dispatchEvent = function (eventKey, event) {
    let list = this.listeners[eventKey];
    if (list !== undefined) {
        let size = list.length;
        for (let i = 0; i < size; i++) {
            let listener = list[i];
            let fun = listener.func;
            let context = listener.context;
            if (context != null) {
                fun.call(context, event);
            } else {
                fun(event);
            }
        }
    }
};