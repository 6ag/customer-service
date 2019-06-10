package com.kefu.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.admin.entity.enums.MessageStatusEnum;
import com.kefu.admin.entity.enums.MessageTypeEnum;
import com.kefu.common.db.entity.BaseEntity;
import com.kefu.common.util.EnumValueDeserializer;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_message")
public class Message extends BaseEntity implements Serializable {

    /**
     * 消息内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发送方用户编号
     */
    @TableField(value = "from_user_id")
    private Integer fromUserId;

    /**
     * 接收方用户编号
     */
    @TableField(value = "to_user_id")
    private Integer toUserId;

    /**
     * 消息类型 [1.文字 2.图片]
     */
    @TableField(value = "type")
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private MessageTypeEnum type;

    /**
     * 消息状态 [1.未读 2.已读]
     */
    @TableField(value = "status")
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private MessageStatusEnum status;

    private static final long serialVersionUID = 1L;

    public static final String COL_CONTENT = "content";

    public static final String COL_FROM_USER_ID = "from_user_id";

    public static final String COL_TO_USER_ID = "to_user_id";

    public static final String COL_TYPE = "type";

    public static final String COL_STATUS = "status";
}
