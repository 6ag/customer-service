package com.kefu.admin.vo;

import com.kefu.admin.entity.Role;
import com.kefu.admin.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户VO
 *
 * @author feng
 * @date 2019-05-20
 */
@Data
@NoArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 2862151578750911342L;
    /**
     * 用户信息
     */
    private User userInfo;

    /**
     * 用户拥有的角色集合
     */
    private List<Role> roles;

    /**
     * 用户拥有的按钮权限集合
     */
    private List<ButtonVo> buttons;

    /**
     * 用户拥有的菜单权限集合
     */
    private List<MenuVo> menus;

    public UserVo(User userInfo) {
        this.userInfo = userInfo;
    }
}
