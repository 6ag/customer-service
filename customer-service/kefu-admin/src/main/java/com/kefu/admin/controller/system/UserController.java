package com.kefu.admin.controller.system;


import com.alibaba.fastjson.JSON;
import com.kefu.admin.common.jwt.JwtUser;
import com.kefu.admin.dto.UserDto;
import com.kefu.admin.dto.UserPageDto;
import com.kefu.common.vo.ResultVo;
import com.kefu.admin.service.UserService;
import com.kefu.admin.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户
 *
 * @author feng
 * @date 2019-05-19
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户详细信息
     *
     * @return
     */
    @GetMapping("/info")
    public ResultVo getUserInfo() {
        log.info("获取用户信息");
        // 在 com.kefu.admin.common.jwt.JwtAuthenticationTokenFilter 里设置了principal为jwtUser
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.findUserInfoByUsername(jwtUser.getUsername());
        return ResultVo.success(userVo);
    }

    /**
     * 新增用户 userDto={"roleIds":[1],"userInfo":{"email":"18727365789@163.com","gender":"2","nickname":"dfdfdfdf","username":"dsfadf"}}
     *
     * @param userDto 用户信息
     * @return
     */
    @PostMapping
    public ResultVo addUser(@RequestBody UserDto userDto) {
        log.info("创建用户,userDto={}", JSON.toJSONString(userDto));
        userService.addUser(userDto);
        return ResultVo.success();
    }

    /**
     * 删除用户
     *
     * @param userId 用户编号
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResultVo deleteUser(@PathVariable Integer userId) {
        log.info("删除用户,userId={}", userId);
        if (ObjectUtils.isEmpty(userId) || userId == 0) {
            return ResultVo.error400("用户编号为空", null);
        }
        return ResultVo.success();
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return
     */
    @PutMapping
    public ResultVo updateUser(@RequestBody UserDto userDto) {
        log.info("更新用户信息,userDto={}", JSON.toJSONString(userDto));
        userService.updateUser(userDto);
        return ResultVo.success();
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户编号
     * @return
     */
    @GetMapping("/{userId}")
    public ResultVo getUserInfo(@PathVariable Integer userId) {
        log.info("获取用户信息,userId={}", userId);
        if (ObjectUtils.isEmpty(userId) || userId == 0) {
            return ResultVo.error400("用户编号为空", null);
        }
        UserVo userVo = userService.findUserInfoById(userId);
        return ResultVo.success(userVo);
    }

    /**
     * 获取用户分页数据
     *
     * @param userPageDto 分页查询条件
     * @return
     */
    @PostMapping("/list")
    public ResultVo getUserPageList(@RequestBody UserPageDto userPageDto) {
        log.info("获取用户分页数据,{}", JSON.toJSONString(userPageDto));
        return ResultVo.success(userService.findUserPageList(userPageDto));
    }

    /**
     * 更新用户拥有的角色
     *
     * @param userId  用户编号
     * @param roleIds 角色编号集合
     * @return
     */
    @PutMapping("/{userId}/roles")
    public ResultVo updateUserRole(@PathVariable Integer userId, @RequestBody List<Integer> roleIds) {
        log.info("修改用户角色,userId={},roleIds={}", userId, roleIds);
        userService.updateUserRoleRelation(userId, roleIds);
        return ResultVo.success();
    }

    /**
     * 获取用户拥有的角色
     *
     * @param userId 用户编号
     * @return
     */
    @GetMapping("/{userId}/roles")
    public ResultVo roleInfo(@PathVariable Integer userId) {
        return ResultVo.success();
    }
}
