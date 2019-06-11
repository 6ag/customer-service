package com.kefu.common.util;

import com.kefu.common.constant.CommonConstants;
import com.kefu.common.support.ErrorInfo;

import java.util.Collection;

public class Assert {

    /**
     * 抛出异常
     *
     * @param msg
     */
    @Deprecated
    public static void error(String msg) {
        ApiUtils.error(CommonConstants.ERROR_CODE, msg, false);
    }

    public static void paramNotNull(Object object, String paramName) {
        String message = "参数 [" + paramName + "] 不能为空";
        notNull(object, message);
        notEmpty(object, message);
    }

    public static void arrayNotEmpty(Collection<?> object, String paramName) {
        String message = "参数 [" + paramName + "] 不能为空";
        notNull(object, message);
        isTrue(object.size() != 0, message);
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, message);
        isTrue(type.isInstance(obj), message);
    }

    public static void isInstanceOf(Class<?> type, Object obj) {
        isInstanceOf(type, obj, "Object of class [" + obj.getClass().getName() + "] must be an instance of " + type);
    }

    /**
     * 非空字符串
     *
     * @param obj
     * @param message
     * @param isServerError
     */
    public static void notEmpty(Object obj, String message, boolean isServerError) {
        notNull(obj, message, isServerError);
        isTrue(!"".equals(obj), message, isServerError);
    }

    public static void notEmpty(Object obj, String message) {
        notEmpty(obj, message, false);
    }

    public static void notEmpty(Object obj) {
        notEmpty(obj, "data is Empty");
    }

    public static void notEmpty(Object obj, ErrorInfo errorInfo) {
        notNull(obj, errorInfo);
        isTrue(!"".equals(obj), errorInfo);
    }

    /**
     * 对象非空
     */
    public static void notNull(Object object, String message, boolean isServerError) {
        isTrue(object != null, message, isServerError);
    }

    /**
     * 如果object为null,则抛出异常信息message
     *
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        notNull(object, message, false);
    }

    /**
     * 如果object为null,则抛出异常信息object is null
     *
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "object is null");
    }

    /**
     * 如果object为null,则抛出异常errorInfo
     *
     * @param object
     * @param errorInfo
     */
    public static void notNull(Object object, ErrorInfo errorInfo) {
        isTrue(object != null, errorInfo);
    }

    /**
     * isTrue
     *
     * @param bool
     * @param message
     * @param isServerError
     */
    public static void isTrue(Boolean bool, String message, boolean isServerError) {
        if (!bool) {
            ApiUtils.error(CommonConstants.ERROR_CODE, message, isServerError);
        }
    }

    public static void isTrue(Boolean bool, String message) {
        isTrue(bool, message, false);
    }

    /**
     * bool 为 false ,就抛出 errorInfo
     *
     * @param bool
     * @param errorInfo
     */
    public static void isTrue(Boolean bool, ErrorInfo errorInfo) {
        if (!bool) {
            ApiUtils.error(errorInfo);
        }
    }

}
