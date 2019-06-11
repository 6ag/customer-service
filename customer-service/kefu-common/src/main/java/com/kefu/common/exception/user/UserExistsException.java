package com.kefu.common.exception.user;

/**
 * 新增用户的时候可能抛出用户已存在异常
 *
 * @author feng
 * @date 2019-05-20
 */
public class UserExistsException extends RuntimeException {

    private static final long serialVersionUID = -6257476137002602963L;

    public UserExistsException(String message) {
        super(message);
    }
}
