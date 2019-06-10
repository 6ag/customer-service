package com.kefu.admin.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按钮VO
 *
 * @author feng
 * @date 2019-05-20
 */
@Data
@NoArgsConstructor
public class ButtonVo implements Serializable {

    private static final long serialVersionUID = 8384231913419578673L;
    /**
     * 权限编号
     */
    private Integer id;

    /**
     * 上级编号
     */
    private Integer parentId;

    /**
     * 权限标识,meta.resources
     */
    private String resources;

    /**
     * 权限名称,meta.title或按钮名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    public ButtonVo(Integer id, Integer parentId, String resources, String name, String remark) {
        this.id = id;
        this.parentId = parentId;
        this.resources = resources;
        this.name = name;
        this.remark = remark;
    }

}
