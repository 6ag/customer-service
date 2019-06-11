package com.kefu.common.exception.common;

import com.kefu.common.constant.CommonConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * Api异常类
 */
@Setter
@Getter
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -2347637330150490204L;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误内容
     */
    private String message;

    public ApiException(String message) {
        this.code = CommonConstants.ERROR_CODE;
        this.message = message;
    }

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
