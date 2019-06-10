package com.kefu.admin.netty.handler;

import com.alibaba.fastjson.JSON;
import com.kefu.admin.entity.Message;
import com.kefu.admin.entity.User;
import com.kefu.admin.netty.protocol.request.MessageRequestPacket;
import com.kefu.admin.netty.protocol.response.MessageResponsePacket;
import com.kefu.admin.netty.utils.ChannelUtil;
import com.kefu.admin.service.MessageService;
import com.kefu.admin.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息转发请求逻辑处理器
 *
 * @author feng
 * @date 2019-04-21
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        // 消息发送方
        User fromUser = ChannelUtil.getUser(ctx.channel());

        // 将消息存入数据库
        Message message = new Message();
        message.setType(msg.getType());
        message.setContent(msg.getContent());
        message.setFromUserId(fromUser.getId());
        message.setToUserId(msg.getToUserId());
        boolean result = messageService.save(message);
        if (!result) {
            log.info("消息存入数据库失败,message={}", JSON.toJSONString(message));
            return;
        }

        // 消息响应数据包
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setId(message.getId());
        messageResponsePacket.setType(message.getType());
        messageResponsePacket.setContent(message.getContent());
        messageResponsePacket.setFromUserId(message.getFromUserId());
        messageResponsePacket.setToUserId(message.getToUserId());
        messageResponsePacket.setStatus(message.getStatus());
        messageResponsePacket.setCreatedAt(message.getCreatedAt());
        messageResponsePacket.setUpdatedAt(message.getUpdatedAt());

        // 发送给消息发送方，用于判断消息是否发送成功
        ctx.channel().writeAndFlush(messageResponsePacket);

        // 消息接收方
        User toUser = userService.getById(msg.getToUserId());
        if (toUser == null) {
            log.info("userId={}用户不存在，消息发送失败!", msg.getToUserId());
            return;
        }

        // 消息接收方的Channel
        Channel toChannel = ChannelUtil.getChannel(toUser.getUsername());
        if (toChannel != null && ChannelUtil.hasLogin(toChannel)) {
            // 发送给消息接收方
            toChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.info("username={}不在线!", toUser.getUsername());
        }

    }
}
