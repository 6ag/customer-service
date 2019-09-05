package com.kefu.admin.netty.protocol;

import com.kefu.admin.netty.protocol.command.Command;
import com.kefu.admin.netty.protocol.request.HeartBeatRequestPacket;
import com.kefu.admin.netty.protocol.request.LoginRequestPacket;
import com.kefu.admin.netty.protocol.request.LogoutRequestPacket;
import com.kefu.admin.netty.protocol.request.MessageRequestPacket;
import com.kefu.admin.netty.protocol.response.HeartBeatResponsePacket;
import com.kefu.admin.netty.protocol.response.LoginResponsePacket;
import com.kefu.admin.netty.protocol.response.LogoutResponsePacket;
import com.kefu.admin.netty.protocol.response.MessageResponsePacket;
import com.kefu.admin.netty.serialize.Serializer;
import com.kefu.admin.netty.serialize.impl.JSONSerializer;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据包编解码器
 * 自定义协议规则:
 * magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
 *
 * @author feng
 * @date 2019-04-20
 */
@Slf4j
@Component
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Short, Class<? extends Packet>> PACKET_TYPE_MAP;
    private static final Map<Byte, Serializer> SERIALIZER_MAP;

    static {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.HEART_BEAT_REQUEST, HeartBeatRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.HEART_BEAT_RESPONSE, HeartBeatResponsePacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 编码
     * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令2字节 + 数据长度4字节 + 数据内容
     * 总字节长度 = 12 + 数据内容长度
     *
     * @param byteBuf 字节码容器
     * @param packet  Packet数据包
     * @return ByteBuf字节码
     */
    public void encode(ByteBuf byteBuf, Packet packet) {
        // 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeShort(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码
     * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令2字节 + 数据长度4字节 + 数据内容
     * 总字节长度 = 12 + 数据内容长度
     *
     * @param byteBuf ByteBuf字节码
     * @return Packet数据包
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        short command = byteBuf.readShort();

        // 数据包长度
        int lenght = byteBuf.readInt();

        byte[] bytes = new byte[lenght];
        // 数据包
        byteBuf.readBytes(bytes);

        // 根据指令获取数据的原类型
        Class<? extends Packet> requestType = PACKET_TYPE_MAP.get(command);
        // 根据序列化算法获取序列化器
        Serializer serializer = SERIALIZER_MAP.get(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            // 将数据包反序列化成 java 对象
            Packet packet = serializer.deserialize(requestType, bytes);
            if (!packet.getCommand().equals(Command.HEART_BEAT_REQUEST)) {
                log.info("解码完成,packet={}", packet);
            }
            return packet;
        }
        return null;
    }

}
