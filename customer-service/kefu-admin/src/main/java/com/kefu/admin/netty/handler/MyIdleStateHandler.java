package com.kefu.admin.netty.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 空闲检查
 *
 * @author feng
 * @date 2019-04-21
 */
@Slf4j
public class MyIdleStateHandler extends IdleStateHandler {

    /**
     * 15秒检查一次。通常服务器空闲检测时间要比客户端心跳检测时间2倍还多一些
     */
    private static final int READER_IDLE_TIME = 15;

    public MyIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info( "{}秒内未读到数据，关闭连接", READER_IDLE_TIME);
        ctx.channel().close();
    }
}
