package com.kefu.common.util;

import com.kefu.common.constant.CommonConstants;
import com.kefu.common.exception.common.ApiException;
import com.kefu.common.exception.common.ServerException;
import com.kefu.common.support.ErrorInfo;

public class ApiUtils {

    public static void error(ErrorInfo errorInfo) {
        error(errorInfo.getCode(), errorInfo.getMessage(), errorInfo.isServerError());
    }

    public static void error(String message) {
        error(CommonConstants.ERROR_CODE, message, false);
    }

    public static void error(Integer code, String message) {
        error(code, message, false);
    }

    public static void error(Integer code, String message, boolean isServerError) {
        if (isServerError) {
            throw new ServerException(code, message);
        }
        throw new ApiException(code, message);
    }
}
