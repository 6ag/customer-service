package com.kefu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.common.db.entity.BaseEntity;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_faq")
public class Faq extends BaseEntity implements Serializable {

    /**
     * 创建者
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 所属团队编号
     */
    @TableField(value = "team_id")
    private Integer teamId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 排序 1开始
     */
    @TableField(value = "sort")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public static final String COL_TEAM_ID = "team_id";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_SORT = "sort";

    /**
     * 创建者
     */
    @TableField(exist = false)
    private User user;
}
