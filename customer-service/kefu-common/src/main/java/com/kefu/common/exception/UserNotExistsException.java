package com.kefu.common.exception;

/**
 * 用户不存在异常
 * @author feng
 * @date 2019-05-28
 */
public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException(String message) {
        super(message);
    }
}
