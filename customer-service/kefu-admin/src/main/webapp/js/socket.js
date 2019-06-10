let socket;
const eventDispatcher = new EventDispatcher();
const ta = document.getElementById("responseText");

// socket接收消息监听
window.onload = function () {
    eventDispatcher.addListener("2", function (packet) {
        ta.value = ta.value + "\n" + "登录成功: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("4", function (packet) {
        ta.value = ta.value + "\n" + "收到消息: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("6", function (packet) {
        ta.value = ta.value + "\n" + "退出登录: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("8", function (packet) {
        ta.value = ta.value + "\n" + "邀请加入群组: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("10", function (packet) {
        ta.value = ta.value + "\n" + "群组成员列表: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("12", function (packet) {
        ta.value = ta.value + "\n" + "加入群组: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("14", function (packet) {
        ta.value = ta.value + "\n" + "退出群组: " + JSON.stringify(packet);
    });

    eventDispatcher.addListener("16", function (packet) {
        ta.value = ta.value + "\n" + "群消息: " + JSON.stringify(packet);
    });
};

// 如果浏览器支持WebSocket
if (window.WebSocket) {
    // 参数就是与服务器连接的地址
    socket = new WebSocket("ws://localhost:9999/chat");

    // 指定接收二进制数据类型
    socket.binaryType = "arraybuffer";

    // 接收到消息
    socket.onmessage = function (event) {
        // 解码
        let packet = decode(event.data);
        // 显示指令，只是用于调试
        showCommand(packet.command);
        // 派发接收数据事件
        eventDispatcher.dispatchEvent(packet.command, packet);
    };

    // 连接建立
    socket.onopen = function (event) {
        ta.value = "连接建立 " + event;
    };

    // 连接关闭
    socket.onclose = function (event) {
        ta.value = ta.value + "\n" + "连接关闭 " + event;
    };

    // 连接发生错误
    socket.onerror = function (event) {
        ta.value = ta.value + "\n" + "连接错误" + event;
    };
} else {
    alert("浏览器不支持WebSocket！");
}


/**
 * 发送消息给服务端
 * @param packet 数据包
 */
function send(packet) {
    if (!window.WebSocket) {
        alert("当前浏览器不支持WebSocket");
        return;
    }

    // 当websocket状态打开
    if (socket !== undefined && socket.readyState === WebSocket.OPEN) {
        socket.send(encode(packet));
    } else {
        alert("连接没有开启");
    }
}

window.onbeforeunload = function () {
    if (!window.WebSocket) {
        alert("当前浏览器不支持WebSocket");
        return;
    }

    // 当websocket状态打开
    if (socket !== undefined && socket.readyState === WebSocket.OPEN) {
        socket.close();
    } else {
        alert("连接没有开启");
    }
};

/**
 * 显示指令
 * @param command 指令
 */
function showCommand(command) {
    let funStr = "";
    switch (command) {
        case 2:
            funStr = "登录";
            break;
        case 4:
            funStr = "一对一消息";
            break;
        case 6:
            funStr = "退出登录";
            break;
        case 8:
            funStr = "创建群组";
            break;
        case 10:
            funStr = "群组成员列表";
            break;
        case 12:
            funStr = "加入群组";
            break;
        case 14:
            funStr = "退出群组";
            break;
        case 16:
            funStr = "群消息";
            break;
        case 18:
            funStr = "心跳检测回应";
            break;
        default:
            break;
    }
    console.log("指令 = " + command + ", 指令功能 = " + funStr);
}

/**
 * 登录
 * @param username 用户名
 * @param password 密码
 */
function login(username, password) {
    let requestPacket = {
        username: username,
        password: password,
        version: 1,
        command: 1
    };
    send(requestPacket);
}

/**
 * 一对一发送消息
 * @param toUserId 接收用户的userId
 * @param message 消息内容
 */
function sendToUser(toUserId, message) {
    let packet = {
        toUserId: toUserId,
        message: message,
        version: 1,
        command: 3
    };
    send(packet);
}

/**
 * 退出登录
 */
function logout() {
    let requestPacket = {
        version: 1,
        command: 5
    };
    send(requestPacket);
}

/**
 * 创建群组
 * @param userIdList 用户userId列表，多个以英文,分割
 */
function createGroup(userIdList) {
    let packet = {
        userIdList: userIdList.split(","),
        version: 1,
        command: 7
    };
    send(packet);
}

/**
 * 群组内成员列表
 * @param groupId 群组ID
 */
function getGroupMembers(groupId) {
    let packet = {
        groupId: groupId,
        version: 1,
        command: 9
    };
    send(packet);
}

/**
 * 加入群组
 * @param groupId 群组ID
 */
function joinGroup(groupId) {
    let packet = {
        groupId: groupId,
        version: 1,
        command: 11
    };
    send(packet);
}

/**
 * 退出群组
 * @param groupId
 */
function quitGroup(groupId) {
    let packet = {
        groupId: groupId,
        version: 1,
        command: 13
    };
    send(packet);
}

/**
 * 群消息
 * @param toGroupId 发送到groupId群
 * @param message 消息内容
 */
function sendToGroup(toGroupId, message) {
    let packet = {
        toGroupId: toGroupId,
        message: message,
        version: 1,
        command: 15
    };
    send(packet);
}
