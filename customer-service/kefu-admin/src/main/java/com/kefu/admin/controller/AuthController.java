package com.kefu.admin.controller;

import com.kefu.admin.entity.User;
import com.kefu.admin.service.UserService;
import com.kefu.admin.dto.LoginUserDto;
import com.kefu.common.vo.ResultVo;
import com.kefu.admin.dto.RegisterUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 授权相关接口，不需要验证权限就能访问
 *
 * @author feng
 * @date 2019-05-19
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginUserDto loginUserDto) {
        log.info("登录用户:{}", loginUserDto.toString());
        String token = userService.login(loginUserDto.getUsername(), loginUserDto.getPassword());
        return ResultVo.success(token);
    }

    @PostMapping("/register")
    public ResultVo register(@RequestBody RegisterUserDto registerUserDto) {
        log.info("注册用户:{}", registerUserDto.toString());
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setEmail(registerUserDto.getEmail());
        user.setNickname(registerUserDto.getNickname());
        userService.register(user);
        return ResultVo.success();
    }

    @PostMapping("/refresh")
    public ResultVo refresh(@RequestHeader("${jwt.tokenHeader}") String token) {
        log.info("刷新token:{}", token);
        return ResultVo.success(userService.refreshToken(token));
    }

}
