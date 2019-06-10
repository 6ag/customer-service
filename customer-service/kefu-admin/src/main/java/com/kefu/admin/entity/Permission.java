package com.kefu.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kefu.admin.entity.enums.PermissionTypeEnum;
import com.kefu.common.db.entity.BaseEntity;
import com.kefu.common.util.EnumValueDeserializer;
import com.kefu.common.util.tree.ITree;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName(value = "tb_permission")
public class Permission extends BaseEntity implements Serializable, ITree<Permission> {
    /**
     * 上级编号
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 路由路径,path
     */
    @TableField(value = "path")
    private String path;

    /**
     * 路由组件,component
     */
    @TableField(value = "component")
    private String component;

    /**
     * 权限标识,meta.resources
     */
    @TableField(value = "resources")
    private String resources;

    /**
     * 权限名称,meta.title
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限图标,meta.icon
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 权限类型 [menu.菜单 button.按钮]
     */
    @TableField(value = "type")
    @JSONField(serializeUsing = EnumValueDeserializer.class, deserializeUsing = EnumValueDeserializer.class)
    private PermissionTypeEnum type;

    /**
     * 目录排序 1开始
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    private static final long serialVersionUID = 1L;

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_PATH = "path";

    public static final String COL_COMPONENT = "component";

    public static final String COL_RESOURCES = "resources";

    public static final String COL_NAME = "name";

    public static final String COL_ICON = "icon";

    public static final String COL_TYPE = "type";

    public static final String COL_SORT = "sort";

    public static final String COL_REMARK = "remark";

    /**
     * 下级权限
     * transient 这个关键词去掉了，返回结果也忽略了这个字段...
     */
    @TableField(exist = false)
    private List<Permission> children;

    public Permission() {
    }

    public Permission(Integer parentId, String resources, String name, PermissionTypeEnum type, String remark) {
        this.parentId = parentId;
        this.resources = resources;
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public Permission(Integer parentId, String path, String component, String resources, String name, String icon, PermissionTypeEnum type, Integer sort, String remark) {
        this.parentId = parentId;
        this.path = path;
        this.component = component;
        this.resources = resources;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.sort = sort;
        this.remark = remark;
    }

    public Permission(Integer id, Integer parentId, String resources, String name, PermissionTypeEnum type, String remark) {
        this.setId(id);
        this.parentId = parentId;
        this.resources = resources;
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public Permission(Integer id, Integer parentId, String path, String component, String resources, String name, String icon, PermissionTypeEnum type, Integer sort, String remark) {
        this.setId(id);
        this.parentId = parentId;
        this.path = path;
        this.component = component;
        this.resources = resources;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.sort = sort;
        this.remark = remark;
    }
}
