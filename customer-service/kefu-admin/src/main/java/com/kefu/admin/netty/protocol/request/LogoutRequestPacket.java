package com.kefu.admin.netty.protocol.request;

import com.kefu.admin.netty.protocol.Packet;
import com.kefu.admin.netty.protocol.command.Command;

import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class LogoutRequestPacket extends Packet {

    @Override
    public Short getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
