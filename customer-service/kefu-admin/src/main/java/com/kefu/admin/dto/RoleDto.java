package com.kefu.admin.dto;

import com.kefu.admin.entity.Role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author feng
 * @date 2019-05-29
 */
@Data
public class RoleDto implements Serializable {
    private static final long serialVersionUID = -8858845211780055867L;

    /**
     * 角色信息
     */
    private Role roleInfo;

    /**
     * 角色拥有的权限编号集合
     */
    private List<Integer> permissionIds;
}
