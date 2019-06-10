package com.kefu.admin.dto;

import com.kefu.admin.entity.Role;
import com.kefu.common.db.dto.PageDto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author feng
 * @date 2019-05-29
 */
@Data
public class RolePageDto extends PageDto<Role> implements Serializable {
    private static final long serialVersionUID = -5673061265367834313L;

    /**
     * 中文或英文名称
     */
    private String name;

}
