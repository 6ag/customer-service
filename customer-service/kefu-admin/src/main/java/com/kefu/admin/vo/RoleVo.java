package com.kefu.admin.vo;

import com.kefu.admin.entity.Role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feng
 * @date 2019-05-29
 */
@Data
@NoArgsConstructor
public class RoleVo implements Serializable {
    private static final long serialVersionUID = -3673213007631248072L;

    /**
     * 角色信息
     */
    private Role roleInfo;

    /**
     * 角色拥有的所有权限编号集合
     */
    private List<Integer> allPermissionIds;

    /**
     * 角色拥有的子权限编号集合（不包括父权限编号，为了方便element-ui的tree组件处理）
     */
    private List<Integer> childrenPermissionIds;

    public RoleVo(Role roleInfo, List<Integer> allPermissionIds, List<Integer> childrenPermissionIds) {
        this.roleInfo = roleInfo;
        this.allPermissionIds = allPermissionIds;
        this.childrenPermissionIds = childrenPermissionIds;
    }
}
