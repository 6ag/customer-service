package com.kefu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.common.db.entity.BaseEntity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_role")
public class Role extends BaseEntity implements Serializable {

    /**
     * 角色英文名称
     */
    @TableField(value = "name_en")
    private String nameEn;

    /**
     * 角色中文名称
     */
    @TableField(value = "name_zh")
    private String nameZh;

    /**
     * 级别越低，权限越高 最低1开始
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME_EN = "name_en";

    public static final String COL_NAME_ZH = "name_zh";

    public static final String COL_LEVEL = "level";

    public static final String COL_REMARK = "remark";
}
