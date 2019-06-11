package com.kefu.common.handler;

import com.kefu.common.vo.ResultVo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一处理控制器的异常
 *
 * @author feng
 * @date 2019-05-19
 */
@Slf4j
@RestControllerAdvice("com.kefu.admin.controller")
public class GlobalExceptionHandler {

    /**
     * 捕捉其他所有异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVo globalException(Exception e) {
        log.error("捕捉其他所有异常,{}", e.getMessage());
        return ResultVo.error500(e.getMessage(), e.getCause().toString());
    }

}
