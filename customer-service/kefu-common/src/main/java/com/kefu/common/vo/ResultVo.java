package com.kefu.common.vo;

import com.kefu.common.constant.CommonConstants;
import com.kefu.common.exception.common.ApiException;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一请求响应的格式
 *
 * @author feng
 * @date 2019-05-17
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 801497136589413470L;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 异常信息
     */
    private String error;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 创建一个统一的响应结果
     *
     * @param status 状态码
     * @param msg    提示信息
     * @param data   返回数据
     * @param error  异常信息
     * @param <T>    返回数据的类型
     * @return
     */
    public static <T> ResultVo create(Integer status, String msg, T data, String error) {
        ResultVo resultVO = new ResultVo();
        resultVO.setStatus(status);
        resultVO.setMessage(msg);
        resultVO.setData(data);
        resultVO.setError(error);
        resultVO.setTimestamp(System.currentTimeMillis());
        return resultVO;
    }

    /**
     * 200 - 请求成功，无自定义提示信息，无自定义返回数据
     *
     * @return
     */
    public static ResultVo success() {
        return success("成功", null);
    }

    /**
     * 200 - 请求成功，无自定义提示信息
     *
     * @param data 自定义返回数据
     * @param <T>  自定义返回数据的类型
     * @return
     */
    public static <T> ResultVo success(T data) {
        return success("成功", data);
    }

    /**
     * 200 - 请求成功
     *
     * @param msg  提示信息
     * @param data 自定义返回数据
     * @param <T>  自定义返回数据的类型
     * @return
     */
    public static <T> ResultVo success(String msg, T data) {
        return create(200, msg, data, null);
    }

    /**
     * 自定义错误代码
     *
     * @param code  错误码
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error(int code, String msg, String error) {
        return create(code, msg, null, error);
    }

    /**
     * 400 - 语义有误，当前请求无法被服务器理解。除非进行修改，否则客户端不应该重复提交这个请求
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error400(String msg, String error) {
        return create(400, msg, null, error);
    }

    /**
     * 401 - 证授权相关错误
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error401(String msg, String error) {
        return create(401, msg, null, error);
    }

    /**
     * 404 - 请求失败，请求所希望得到的资源未被在服务器上发现
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error404(String msg, String error) {
        return create(404, msg, null, error);
    }

    /**
     * 408 - 请求超时。客户端没有在服务器预备等待的时间内完成一个请求的发送。客户端可以随时再次提交这一请求而无需进行任何更改。
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error408(String msg, String error) {
        return create(408, msg, null, error);
    }

    /**
     * 415 - 对于当前请求的方法和所请求的资源，请求所支持的格式错误
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error415(String msg, String error) {
        return create(415, msg, null, error);
    }

    /**
     * 500 - 服务器内部错误
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error500(String msg, String error) {
        return create(500, msg, null, error);
    }

    /**
     * 502 - 网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
     *
     * @param msg   提示信息
     * @param error 异常信息
     * @return
     */
    public static ResultVo error502(String msg, String error) {
        return create(502, msg, null, error);
    }

    public static ResultVo error(Exception exception) {
        if (exception instanceof ApiException) {
            return error(((ApiException) exception).getCode(), null, exception.getMessage());
        } else {
            return error(CommonConstants.ERROR_CODE, null, exception.getMessage());
        }
    }
}
