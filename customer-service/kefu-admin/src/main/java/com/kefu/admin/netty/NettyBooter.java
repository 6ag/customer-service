package com.kefu.admin.netty;

import com.kefu.admin.netty.config.NettyProperties;
import com.kefu.admin.netty.server.TcpChatServer;
import com.kefu.admin.netty.server.WebSocketChatServer;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * NettyServer启动器
 *
 * @author feng
 * @date 2019-04-22
 */
@Component
public class NettyBooter {

    private static final String WEBSOCKET = "websocket";
    private static final String TCP = "tcp";

    /**
     * Netty属性配置
     */
    private final NettyProperties nettyProperties;

    /**
     * WebSocket服务端启动器
     */
    private final WebSocketChatServer webSocketChatServer;

    /**
     * Tcp服务端启动器
     */
    private final TcpChatServer tcpChatServer;

    public NettyBooter(WebSocketChatServer webSocketChatServer, TcpChatServer tcpChatServer, NettyProperties nettyProperties) {
        this.webSocketChatServer = webSocketChatServer;
        this.tcpChatServer = tcpChatServer;
        this.nettyProperties = nettyProperties;
    }

    @PostConstruct
    private void nettyServerStart() {
        // 根据netty配置协议，运行不同的启动器
        if (WEBSOCKET.equals(nettyProperties.getProtocol().toLowerCase())) {
            webSocketChatServer.start();
        } else {
            tcpChatServer.start();
        }
    }
}
