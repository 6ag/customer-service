package com.kefu.admin.netty.handler;

import com.kefu.admin.netty.protocol.request.LogoutRequestPacket;
import com.kefu.admin.netty.protocol.response.LogoutResponsePacket;
import com.kefu.admin.netty.utils.ChannelUtil;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author feng
 * @date 2019-04-21
 */
@ChannelHandler.Sharable
@Component
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        ChannelUtil.unBindUser(ctx.channel());

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
