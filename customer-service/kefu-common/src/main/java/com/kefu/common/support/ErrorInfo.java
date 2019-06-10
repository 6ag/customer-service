package com.kefu.common.support;

/**
 * 错误信息接口
 * 枚举类通过实现该接口，向Assert提供错误信息
 */
public interface ErrorInfo {
    String getMessage();

    Integer getCode();

    boolean isServerError();
}
