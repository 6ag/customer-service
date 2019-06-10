package com.kefu.admin.netty.protocol.response;

import com.kefu.admin.entity.User;
import com.kefu.admin.netty.protocol.Packet;
import com.kefu.admin.netty.protocol.command.Command;

import lombok.Data;

/**
 * 登录响应数据包
 *
 * @author feng
 * @date 2019-04-20
 */
@Data
public class LoginResponsePacket extends Packet {

    /**
     * 登录的用户对象
     */
    private User user;

    /**
     * 联系人用户对象，这个字段只有访客才会有值，用于分配一个客服
     */
    private User contact;

    private Boolean success;

    @Override
    public Short getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
