package com.kefu.admin.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * @author feng
 * @date 2019-06-08
 */
@Data
@ToString(callSuper = true)
public class MessageListDto implements Serializable {
    private static final long serialVersionUID = -2621292550543059724L;

    /**
     * 当前用户编号
     */
    private Integer userId;

    /**
     * 联系人用户编号
     */
    private Integer contactUserId;

    /**
     * 返回消息编号小于此编号的数据
     */
    private Integer lessMessageId;
}
