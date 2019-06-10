package com.kefu.admin.dto;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author feng
 * @date 2019-05-21
 */
@Data
public class RegisterUserDto implements Serializable {

    private static final long serialVersionUID = 6445149916048588249L;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 18, message = "用户名长度为5-18位")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度为6-18位")
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;
}
