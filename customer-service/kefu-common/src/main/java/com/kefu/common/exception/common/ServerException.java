package com.kefu.common.exception.common;

import com.kefu.common.constant.CommonConstants;
import com.kefu.common.support.ErrorInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务异常类
 */
@Setter
@Getter
public class ServerException extends RuntimeException {

    private static final long serialVersionUID = -5207675902325618172L;

    /**
     * 错误码
     */
    private Integer code = CommonConstants.ERROR_CODE;

    /**
     * 错误内容
     */
    private String message;

    public ServerException(String message) {
        this.message = message;
    }

    public ServerException(Integer code, String message) {
        if (code != null) {
            this.code = code;
        }
        this.message = message;
    }

    public ServerException(ErrorInfo errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }
}
