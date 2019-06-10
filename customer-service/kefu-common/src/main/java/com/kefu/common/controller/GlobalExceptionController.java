package com.kefu.common.controller;

import com.kefu.common.vo.ResultVo;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一处理控制器的异常
 *
 * @author feng
 * @date 2019-05-19
 */
@Slf4j
@RestControllerAdvice("com.kefu.admin.controller")
@ResponseBody
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVo handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e);
        return ResultVo.error500("请求参数不正确", e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return ResultVo.error500("请求类型不支持！", e.getMessage());
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVo handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return ResultVo.error500("不被支持的访问方法！", e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResultVo handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return ResultVo.error500("不支持的媒体类型！", e.getMessage());
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResultVo globalException(HttpServletRequest request, Exception e) {
        log.error("捕捉其他所有异常,{}", e.getMessage());
        return ResultVo.error500(e.getMessage(), e.getCause().toString());
    }

}
