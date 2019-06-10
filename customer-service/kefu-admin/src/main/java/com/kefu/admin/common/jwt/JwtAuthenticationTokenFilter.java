package com.kefu.admin.common.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * jwt认证token过滤器，每次请求都会来
 *
 * @author feng
 * @date 2019-05-20
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token值
        String authHeader = request.getHeader(jwtTokenUtils.getTokenHeader());
        // 是否是以指定的token前缀开头，token默认格式 "Bearer xxxxxxx"
        if (authHeader != null && authHeader.startsWith(jwtTokenUtils.getTokenHead())) {
            // 获取token
            String token = authHeader.substring(jwtTokenUtils.getTokenHead().length());
            log.info("token:{}", token);
            // 获取用户名
            String username = jwtTokenUtils.getUsernameFromToken(token);
            // 校验token
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // TODO: 后面从Redis缓存中获取，不然每次请求都会去查询用户
                JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtils.validateToken(token, jwtUser)) {
                    log.info("token有效");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
