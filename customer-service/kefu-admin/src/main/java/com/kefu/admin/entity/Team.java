package com.kefu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.common.db.entity.BaseEntity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_team")
public class Team extends BaseEntity implements Serializable {

    /**
     * 团队/公司名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 行业
     */
    @TableField(value = "industry")
    private String industry;

    /**
     * 所在省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 所在市
     */
    @TableField(value = "city")
    private String city;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_INDUSTRY_ID = "industry";

    public static final String COL_PROVINCE = "province";

    public static final String COL_CITY = "city";
}
