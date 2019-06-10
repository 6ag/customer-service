package com.kefu.admin.vo;

import com.kefu.common.util.tree.ITree;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单VO
 *
 * @author feng
 * @date 2019-05-20
 */
@Data
@NoArgsConstructor
public class MenuVo implements Serializable, ITree<MenuVo> {

    private static final long serialVersionUID = 3489343511890007019L;
    /**
     * 权限编号
     */
    private Integer id;

    /**
     * 上级编号
     */
    private Integer parentId;

    /**
     * 路由路径,path
     */
    private String path;

    /**
     * 路由组件,component
     */
    private String component;

    /**
     * 权限标识,meta.resources
     */
    private String resources;

    /**
     * 权限名称,meta.title
     */
    private String name;

    /**
     * 权限图标,meta.icon
     */
    private String icon;

    /**
     * 目录排序 1开始
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 下级菜单
     */
    private List<MenuVo> children;

    public MenuVo(Integer id, Integer parentId, String path, String component, String resources, String name, String icon, Integer sort, String remark) {
        this.id = id;
        this.parentId = parentId;
        this.path = path;
        this.component = component;
        this.resources = resources;
        this.name = name;
        this.icon = icon;
        this.sort = sort;
        this.remark = remark;
    }

}
