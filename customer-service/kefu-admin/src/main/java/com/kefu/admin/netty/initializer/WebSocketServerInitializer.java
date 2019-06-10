package com.kefu.admin.netty.initializer;

import com.kefu.admin.netty.codec.WebSocketPacketCodec;
import com.kefu.admin.netty.config.NettyProperties;
import com.kefu.admin.netty.handler.MyIdleStateHandler;
import com.kefu.admin.netty.utils.PipelineUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * websocket连接初始化Channel，给Channel关联的pipeline添加handler
 *
 * @author feng
 * @date 2019-04-22
 */
@Component
public class WebSocketServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private PipelineUtil pipelineUtil;

    @Autowired
    private WebSocketPacketCodec webSocketPacketCodec;

    /**
     * websocket路径
     */
    private String websocketPath;

    /**
     * 最大内容长度
     */
    private int maxContentLength;

    public WebSocketServerInitializer(NettyProperties nettyProperties) {
        this.websocketPath = nettyProperties.getWebsocket().getPath();
        this.maxContentLength = nettyProperties.getWebsocket().getHttpObjectArrgregator().getMaxContentLength();
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 处理第一次连接http的握手请求
        pipeline.addLast(new HttpServerCodec());
        // 写文件内容
        pipeline.addLast(new ChunkedWriteHandler());
        // 保证接收的http请求的完整性
        pipeline.addLast(new HttpObjectAggregator(maxContentLength));
        // 处理其他的WebSocketFrame
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath));

        // 空闲检测
        ch.pipeline().addLast(new MyIdleStateHandler());

        // WebSocket数据包编解码器
        ch.pipeline().addLast(webSocketPacketCodec);

        // 添加tcp/websocket通用handler
        pipelineUtil.addHandler(pipeline);
    }
}
