package com.kefu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.common.db.entity.BaseEntity;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_conversation")
public class Conversation extends BaseEntity implements Serializable {

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
     * 未读数量
     */
    @TableField(value = "unread_count")
    private Integer unreadCount;

    /**
     * 最后一条消息编号
     */
    @TableField(value = "last_message_id")
    private Integer lastMessageId;

    private static final long serialVersionUID = 1L;

    public static final String COL_FROM_USER_ID = "from_user_id";

    public static final String COL_TO_USER_ID = "to_user_id";

    public static final String COL_UNREAD_COUNT = "unread_count";

    public static final String COL_LAST_MESSAGE_ID = "last_message_id";

    /**
     * 最后一条消息
     */
    @TableField(exist = false)
    private Message lastMessage;

    /**
     * 发送方用户
     */
    @TableField(exist = false)
    private User fromUser;

    /**
     * 接收方用户
     */
    @TableField(exist = false)
    private User toUser;
}
