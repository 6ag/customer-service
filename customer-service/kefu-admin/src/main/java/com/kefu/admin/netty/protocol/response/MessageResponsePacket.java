package com.kefu.admin.netty.protocol.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kefu.admin.entity.Message;
import com.kefu.admin.entity.enums.MessageStatusEnum;
import com.kefu.admin.entity.enums.MessageTypeEnum;
import com.kefu.admin.netty.protocol.Packet;
import com.kefu.admin.netty.protocol.command.Command;
import com.kefu.common.util.EnumValueDeserializer;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务端发送至客户端的消息数据包
 *
 * @author feng
 * @date 2019-04-20
 */
@Data
@NoArgsConstructor
public class MessageResponsePacket extends Packet {

    /**
     * 消息编号
     */
    private Integer id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送方用户编号
     */
    private Integer fromUserId;

    /**
     * 接收方用户编号
     */
    private Integer toUserId;

    /**
     * 消息类型 [1.文字 2.图片]
     */
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private MessageTypeEnum type;

    /**
     * 消息状态 [1.未读 2.已读]
     */
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private MessageStatusEnum status;

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @Override
    public Short getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
