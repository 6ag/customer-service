package com.kefu.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.admin.entity.enums.GenderEnum;
import com.kefu.admin.entity.enums.UserStatusEnum;
import com.kefu.common.db.entity.BaseEntity;
import com.kefu.common.util.EnumValueDeserializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_user")
public class User extends BaseEntity implements Serializable {

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 性别 [0.女 1.男 2.未知]
     */
    @TableField(value = "gender")
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private GenderEnum gender;

    /**
     * 状态 [0.禁用 1.正常 2.已删除]
     */
    @TableField(value = "status")
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private UserStatusEnum status;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @JSONField(serialize = false)
    private String password;

    /**
     * 微信open id
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 团队编号
     */
    @TableField(value = "team_id")
    private Integer teamId;

    private static final long serialVersionUID = 1L;

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_EMAIL = "email";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_GENDER = "gender";

    public static final String COL_STATUS = "status";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_OPEN_ID = "open_id";

    public static final String COL_TEAM_ID = "team_id";

    /**
     * 团队信息
     */
    @TableField(exist = false)
    private Team team;

    /**
     * 角色集合 - 暂时不用
     */
    @TableField(exist = false)
    private List<Role> roles;
}
