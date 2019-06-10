<template>
  <div class="chat-container">

    <!-- 数据为空 -->
    <div v-if="messageList && messageList.length === 0" class="loading">
      <div style="margin-top: 300px; font-size: 16px;">
        <span>未查找到聊天记录</span>
      </div>
    </div>

    <!-- 刚进页面第一次加载 -->
    <div v-if="messageList && messageList.length === 0" class="loading">
      <div style="margin-top: 300px; font-size: 16px;">
        <div>加载中...</div>
      </div>
    </div>

    <!-- 导航栏 -->
    <div v-if="messageList && messageList.length > 0" class="title">
      <p v-text="contact.nickname" />
    </div>

    <!-- 可上下滑滚动区域 -->
    <div
      id="scrollLoader-container"
      class="container-main"
      :style="'maxHeight:' + (maxHeight - 50) + 'px'"
    >
      <div v-if="topLoading" class="loading">
        <div class="loader">加载历史记录...</div>
      </div>

      <div :style="'min-height:' + realMinHeight + 'px; overflow-x:hidden'">
        <!-- 消息内容列表 -->
        <div v-if="messageList && messageList.length > 0" class="message">
          <ul>
            <li
              v-for="message in messageList"
              :key="message.id"
              :class="isOneself(message) ? 'an-move-right' : 'an-move-left'"
            >
              <!-- 时间 -->
              <div class="time"><span v-text="message.createdAt" /></div>
              <!-- 系统提示 -->
              <div v-if="message.type === '10000'" class="time system">
                <span v-html="message.content" />
              </div>
              <div v-else :class="'main' + (isOneself(message) ? ' self' : '')">
                <!-- 头像 -->
                <img
                  class="avatar"
                  :src="isOneself(message) ? user.avatar : contact.avatar"
                  alt="头像图片"
                >

                <!-- 文本 -->
                <div v-if="message.type === '1'" v-emotion="message.content" class="text" />

                <!-- 图片 -->
                <div v-else-if="message.type === '2'" class="text">
                  <img :src="message.content" class="image" alt="聊天图片">
                </div>

                <!-- 其他 -->
                <div
                  v-else
                  class="text"
                  v-text="'[暂未支持的消息类型:' + message.type + ']\n\r' + message.content"
                />
              </div>
            </li>
          </ul>
        </div>
      </div>

    </div>

    <div class="input">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="inputText"
            type="textarea"
            placeholder="协助TA"
            :rows="5"
          />
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="sendMessage">发送</el-button>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
import EventDispatcher from '@/utils/dispatch-event'
import { encode, decode } from '@/utils/codec'
import Command from '@/utils/command'
import { createPacket } from '@/utils/packet'
import conversationApi from '@/api/conversation'
import messageApi from '@/api/message'
import { getId } from '@/utils/id'

export default {
  data() {
    return {
      messageList: [], // 聊天信息列表
      conversationList: [], // 会话列表
      conversation: null, // 当前选中的会话
      listQuery: {
        userId: 0,
        contactUserId: 0,
        lessMessageId: 0
      }, // 列表查询条件

      topLoading: false,
      stopTopLoading: false, // 是否停止传播滚动到顶部事件
      isUpperLaoding: false,
      isRefreshedAll: false,

      minHeight: 700,
      maxHeight: 700,

      inputText: '', // 输入的文本内容

      socket: null, // socket
      eventDispatcher: null, // 事件派发器
      interval: null, // 间隔执行定时器

      contact: null, // 联系人用户对象
      user: null // 当前用户对象
    }
  },
  computed: {
    realMinHeight() {
      return this.minHeight + 30
    }
  },
  // 不能操作DOM
  created() {
    this.getConversationList()
  },
  // 可以操作DOM
  mounted() {
    // 事件派发器
    this.eventDispatcher = new EventDispatcher()
    // 监听滚动
    this.listenScroll()
    // 监听事件
    this.listenEvent()

    const _this = this
    if (window.WebSocket) {
      // socket
      this.socket = new WebSocket('ws://localhost:9999/chat')
      this.socket.binaryType = 'arraybuffer'

      // 接收到消息
      this.socket.onmessage = function(event) {
        // 解码
        const packet = decode(event.data)
        // 不打印心跳包日志
        // if (packet && packet.command !== Command.HEART_BEAT_RESPONSE) {
        //   console.log(`接收到消息 ${JSON.stringify(packet)}`)
        // }

        // 派发接收数据事件
        _this.eventDispatcher.dispatchEvent(packet.command, packet)
      }

      // 连接建立
      this.socket.onopen = function(event) {
        console.log(`连接建立 ${JSON.stringify(event)}`)
        // 心跳检测
        _this.heartCheck()
        // socket连接成功后，登录netty
        _this.loginNetty()
      }

      // 连接关闭
      this.socket.onclose = function(event) {
        console.log(`连接关闭 ${JSON.stringify(event)}`)
      }

      // 连接发生错误
      this.socket.onerror = function(event) {
        console.log(`连接错误 ${JSON.stringify(event)}`)
      }
    } else {
      console.log('当前浏览器不支持WebSocket')
    }

    // 刷新浏览器
    window.onbeforeunload = function() {
      if (!window.WebSocket) {
        console.log('当前浏览器不支持WebSocket')
        return
      }

      // 当websocket状态打开
      if (this.socket && this.socket.readyState === WebSocket.OPEN) {
        this.socket.close()
      } else {
        console.log('连接没有开启')
      }
    }
  },
  beforeDestroy() {
    // 清除定时器
    if (this.interval) {
      console.log('清除定时器')
      window.clearInterval(this.interval)
    }
  },
  methods: {
    // 滚动到聊天框底部
    scrollToBottom() {
      const _this = this
      setTimeout(() => {
        const scrollContainer = _this.$el.querySelector('#scrollLoader-container')
        console.log(`滚动到最底部 scrollTop=${scrollContainer.scrollTop}, scrollHeight=${scrollContainer.scrollHeight}, clientHeight=${scrollContainer.clientHeight}`)
        scrollContainer.scrollTop = scrollContainer.scrollHeight - scrollContainer.clientHeight
        console.log(`滚动到最底部 scrollTop=${scrollContainer.scrollTop}, scrollHeight=${scrollContainer.scrollHeight}, clientHeight=${scrollContainer.clientHeight}`)
      }, 50)
    },
    // 获取会话列表
    getConversationList() {
      conversationApi.getConversationList(this.$store.getters.id).then((response) => {
        if (response.status === 200) {
          this.conversationList = response.data
        }
      })
    },
    // 获取聊天信息列表
    getMessageList(done) {
      const _this = this
      messageApi.getMessageList(this.listQuery).then((response) => {
        if (response.status === 200) {
          if (response.data.length === 0) {
            _this.isRefreshedAll = true
            if (done) {
              done(true)
            }
          } else {
            _this.messageList = response.data.reverse().concat(_this.messageList) // 倒序合并
            if (done) {
              done()
            } else {
              _this.scrollToBottom()
            }
          }
          console.log(response.data)
          _this.isUpperLaoding = false
        }
      })
    },
    // 发送数据包
    sendPacket(packet) {
      if (!window.WebSocket) {
        console.log('当前浏览器不支持WebSocket')
        return
      }

      // 当websocket状态打开
      if (this.socket && this.socket.readyState === WebSocket.OPEN) {
        // 不打印心跳包日志
        if (packet && packet.command !== Command.HEART_BEAT_REQUEST) {
          console.log(`发送消息 ${JSON.stringify(packet)}`)
        }
        this.socket.send(encode(packet))
      } else {
        console.log('连接没有开启，发送失败')
      }
    },
    // 事件监听
    listenEvent() {
      const _this = this

      // 登录
      this.eventDispatcher.addListener(Command.LOGIN_RESPONSE, packet => {
        if (packet.success) {
          // 当前用户信息
          _this.user = packet.user
          // 联系人
          _this.contact = packet.contact

          _this.messageList = []
          _this.listQuery.userId = _this.user.id
          _this.listQuery.contactUserId = _this.contact.id
          _this.listQuery.lessMessageId = 0
          _this.getMessageList()

          console.log(`登录成功 ${JSON.stringify(packet)}`)
        }
      })

      // 消息
      this.eventDispatcher.addListener(Command.MESSAGE_RESPONSE, packet => {
        _this.messageList.push(packet)
        _this.scrollToBottom()
        console.log(`收到信息 ${JSON.stringify(packet)}`)
      })
    },
    // 心跳检测
    heartCheck() {
      const _this = this
      this.interval = window.setInterval(() => {
        // console.log(`发送心跳,${new Date().toTimeString()}`)
        _this.sendPacket(createPacket({}, Command.HEART_BEAT_REQUEST))
      }, 5000)
    },
    // 登录
    loginNetty() {
      const data = {
        username: getId()
      }
      this.sendPacket(createPacket(data, Command.LOGIN_REQUEST))
    },
    // 发送信息
    sendMessage() {
      console.log(`发送信息:${this.inputText}`)
      const data = {
        content: this.inputText,
        type: 1,
        toUserId: this.contact.id
      }
      this.sendPacket(createPacket(data, Command.MESSAGE_REQUEST))
      // 清空文本框
      this.inputText = ''
    },
    // 是否是自己发送的信息
    isOneself(message) {
      return message.fromUserId === this.user.id
    },
    // 根据会话获取联系人
    getContact(conversation) {
      // 当前用户是会话发起方，则联系人就是会话的接收方。反之同理
      if (this.user.id === conversation.fromUserId) {
        return conversation.toUser
      } else {
        return conversation.fromUser
      }
    },
    // 监听聊天窗口滚动，并触发上下拉刷新
    listenScroll() {
      const _this = this
      const topDone = stopTopLoading => {
        _this.topLoading = false
        if (stopTopLoading) {
          _this.stopTopLoading = true
        }
      }

      setTimeout(function() {
        const scrollContainer = _this.$el.querySelector('#scrollLoader-container')

        scrollContainer.onscroll = function() {
          console.log(`滚动中 scrollTop=${scrollContainer.scrollTop}, scrollHeight=${scrollContainer.scrollHeight}, clientHeight=${scrollContainer.clientHeight}`)
          if (scrollContainer.scrollTop <= 0 && !_this.stopTopLoading) {
            if (_this.topLoading) {
              return
            }

            _this.topLoading = true

            if (_this.isUpperLaoding) {
              return
            }

            if (_this.isRefreshedAll) {
              topDone(true)
              _this.isUpperLaoding = false
              return
            }

            _this.listQuery.lessMessageId = _this.messageList[0].id
            _this.getMessageList(topDone)
          }
        }
      }, 50)
    }
  }
}
</script>

<style scoped>
  /* 消息列表 */
  .chat-container {
    background-color: #efefef;
    z-index: 100;
    overflow: hidden;
    min-width: 300px;
    margin: 0 auto;
    padding: 0;
    position: relative;
  }

  .title {
    background: #000;
    text-align: center;
    color: #fff;
    width: 100%;
    height: 50px;
    line-height: 50px;
    font-size: 14px;
  }

  .loading {
    text-align: center;
    color: #b0b0b0;
    line-height: 100px;
  }

  .message {
    padding: 10px 15px;
    background-color: #f5f5f5;
  }

  .message li {
    margin-bottom: 15px;
    left: 0;
    position: relative;
    display: block;
  }

  .message .time {
    margin: 10px 0;
    text-align: center;
  }

  .message .text {
    display: inline-block;
    position: relative;
    max-width: calc(100% - 75px);
    min-height: 35px;
    line-height: 2.1;
    font-size: 15px;
    padding: 6px 10px;
    text-align: left;
    word-break: break-all;
    background-color: #fff;
    color: #000;
    border-radius: 4px;
    box-shadow: 0px 1px 7px -5px #000;
  }

  .message .avatar {
    float: left;
    margin: 0 10px 0 0;
    border-radius: 3px;
    background: #fff;
    width: 45px;
    height: 45px;
  }

  .message .time > span {
    display: inline-block;
    padding: 0 5px;
    font-size: 12px;
    color: #fff;
    border-radius: 2px;
    background-color: #dadada;
  }

  .message .system > span {
    padding: 4px 9px;
    text-align: left;
  }

  .message .text:before {
    content: " ";
    position: absolute;
    top: 9px;
    right: 100%;
    border: 6px solid transparent;
    border-right-color: #fff;
  }

  .message .main {
    text-align: left;
  }

  .message .self {
    text-align: right;
  }

  .message .self .avatar {
    float: right;
    margin: 0 0 0 10px;
  }

  .message .self .text {
    background-color: #9eea6a;
  }

  .message .self .text:before {
    right: inherit;
    left: 100%;
    border-right-color: transparent;
    border-left-color: #9eea6a;
  }

  .message .image {
    max-width: 200px;
  }

  img.static-emotion-gif,
  img.static-emotion {
    vertical-align: middle !important;
  }

  .an-move-left {
    left: 0;
    animation: moveLeft 0.7s ease;
    -webkit-animation: moveLeft 0.7s ease;
  }

  .an-move-right {
    left: 0;
    animation: moveRight 0.7s ease;
    -webkit-animation: moveRight 0.7s ease;
  }

  @keyframes moveRight {
    0% {
      left: -20px;
      opacity: 0;
    }
    100% {
      left: 0;
      opacity: 1;
    }
  }

  @-webkit-keyframes moveRight {
    0% {
      left: -20px;
      opacity: 0;
    }
    100% {
      left: 0;
      opacity: 1;
    }
  }

  @keyframes moveLeft {
    0% {
      left: 20px;
      opacity: 0;
    }
    100% {
      left: 0;
      opacity: 1;
    }
  }

  @-webkit-keyframes moveLeft {
    0% {
      left: 20px;
      opacity: 0;
    }
    100% {
      left: 0;
      opacity: 1;
    }
  }

  @media (max-width: 367px) {
    .fzDInfo {
      width: 82%;
    }
  }

  /* 上下拉刷新 */
  .container-main {
    margin: 0 auto;
    overflow: auto;
    overflow-x: hidden;
    padding: 0;
  }

  .loading {
    width: 100%;
    height: 40px;
    position: relative;
    overflow: hidden;
    text-align: center;
    margin: 5px 0;
    color: #999;
    font-size: 13px;
  }

  .loader {
    font-size: 10px;
    margin: 8px auto;
    text-indent: -9999em;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    background: #999;
    background: -moz-linear-gradient(left, #999 10%, rgba(255, 255, 255, 0) 42%);
    background: -webkit-linear-gradient(left, #999 10%, rgba(255, 255, 255, 0) 42%);
    background: -o-linear-gradient(left, #999 10%, rgba(255, 255, 255, 0) 42%);
    background: -ms-linear-gradient(left, #999 10%, rgba(255, 255, 255, 0) 42%);
    background: linear-gradient(to right, #999 10%, rgba(255, 255, 255, 0) 42%);
    position: relative;
    -webkit-animation: load3 1s infinite linear;
    animation: load3 1s infinite linear;
  }

  .loader:before {
    width: 50%;
    height: 50%;
    background: #999;
    border-radius: 100% 0 0 0;
    position: absolute;
    top: 0;
    left: 0;
    content: "";
  }

  .loader:after {
    background: #f5f5f5;
    width: 72%;
    height: 75%;
    border-radius: 68%;
    content: "";
    margin: auto;
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
  }

  @-webkit-keyframes load3 {
    0% {
      -webkit-transform: rotate(0deg);
      transform: rotate(0deg);
    }
    100% {
      -webkit-transform: rotate(360deg);
      transform: rotate(360deg);
    }
  }

  @keyframes load3 {
    0% {
      -webkit-transform: rotate(0deg);
      transform: rotate(0deg);
    }
    100% {
      -webkit-transform: rotate(360deg);
      transform: rotate(360deg);
    }
  }
</style>
