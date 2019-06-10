package com.kefu.common.exception;

/**
 * 新增用户的时候可能抛出用户已存在异常
 *
 * @author feng
 * @date 2019-05-20
 */
public class UserExistsException extends RuntimeException {

    public UserExistsException(String message) {
        super(message);
    }
}
