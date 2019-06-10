package com.kefu.admin.common.jwt;

import com.alibaba.fastjson.JSON;
import com.kefu.common.vo.ResultVo;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 未授权处理
 *
 * @author feng
 * @date 2019-05-20
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResultVo resultVo;
        if (authException instanceof BadCredentialsException) {
            resultVo = ResultVo.error401("用户名或密码错误", authException.getMessage());
        } else {
            resultVo = ResultVo.error401("无效token", authException.getMessage());
        }
        response.getWriter().write(JSON.toJSONString(resultVo));
    }
}
