package com.kefu.admin.netty.codec;

import com.kefu.admin.netty.protocol.Packet;
import com.kefu.admin.netty.protocol.PacketCodeC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * WebSocket数据包编解码器
 *
 * @author feng
 * @date 2019-04-21
 */
@ChannelHandler.Sharable
@Component
public class WebSocketPacketCodec extends MessageToMessageCodec<WebSocketFrame, Packet> {

    @Autowired
    private PacketCodeC packetCodeC;

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        packetCodeC.encode(byteBuf, msg);
        out.add(new BinaryWebSocketFrame(byteBuf));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        out.add(packetCodeC.decode(msg.content()));
    }

}
