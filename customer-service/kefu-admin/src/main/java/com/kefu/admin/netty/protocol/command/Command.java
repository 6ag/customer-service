package com.kefu.admin.netty.protocol.command;

/**
 * 指令
 *
 * @author feng
 * @date 2019-04-20
 */
public interface Command {

    Short HEART_BEAT_REQUEST = 1000;
    Short HEART_BEAT_RESPONSE = 1001;

    Short LOGIN_REQUEST = 2000;
    Short LOGIN_RESPONSE = 2001;

    Short LOGOUT_REQUEST = 2002;
    Short LOGOUT_RESPONSE = 2003;

    Short MESSAGE_REQUEST = 3000;
    Short MESSAGE_RESPONSE = 3001;

}
