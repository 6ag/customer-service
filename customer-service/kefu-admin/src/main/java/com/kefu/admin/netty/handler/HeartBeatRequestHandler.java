package com.kefu.admin.netty.handler;

import com.kefu.admin.netty.protocol.request.HeartBeatRequestPacket;
import com.kefu.admin.netty.protocol.response.HeartBeatResponsePacket;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 心跳检测
 *
 * @author feng
 * @date 2019-04-21
 */
@ChannelHandler.Sharable
@Component
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
