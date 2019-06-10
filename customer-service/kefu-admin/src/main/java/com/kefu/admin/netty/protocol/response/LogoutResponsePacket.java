package com.kefu.admin.netty.protocol.response;

import com.kefu.admin.netty.protocol.Packet;
import com.kefu.admin.netty.protocol.command.Command;

import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class LogoutResponsePacket extends Packet {

    private Boolean success;

    @Override
    public Short getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
