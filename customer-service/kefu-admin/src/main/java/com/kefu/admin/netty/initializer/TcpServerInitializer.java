package com.kefu.admin.netty.initializer;

import com.kefu.admin.netty.codec.TcpPacketCodec;
import com.kefu.admin.netty.codec.TcpSpliter;
import com.kefu.admin.netty.handler.MyIdleStateHandler;
import com.kefu.admin.netty.utils.PipelineUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * tcp连接初始化Channel，给Channel关联的pipeline添加handler
 *
 * @author feng
 * @date 2019-04-22
 */
@Component
public class TcpServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private PipelineUtil pipelineUtil;

    @Autowired
    private TcpPacketCodec tcpPacketCodec;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 空闲检测
        pipeline.addLast(new MyIdleStateHandler());
        // 处理粘包半包
        pipeline.addLast(new TcpSpliter());
        // 数据包编解码器
        pipeline.addLast(tcpPacketCodec);

        // 添加tcp/websocket通用handler
        pipelineUtil.addHandler(pipeline);

    }
}
