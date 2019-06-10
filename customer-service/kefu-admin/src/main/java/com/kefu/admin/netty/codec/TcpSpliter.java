package com.kefu.admin.netty.codec;

import com.kefu.admin.netty.protocol.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * tcp连接数据包拆包器
 *
 * @author feng
 * @date 2019-04-21
 */
public class TcpSpliter extends LengthFieldBasedFrameDecoder {

    /**
     * 自定义协议长度字段偏移量
     */
    private static final int LENGTH_FIELD_OFFSET = 7;

    /**
     * 自定义协议长度字段长度
     */
    private static final int LENGTH_FIELD_LENGHT = 4;

    public TcpSpliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGHT);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            System.out.println("非法协议，关闭连接");
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
