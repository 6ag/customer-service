package com.kefu.admin.dto;

import com.kefu.admin.entity.Team;
import com.kefu.common.db.dto.PageDto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * @author feng
 * @date 2019-05-30
 */
@Data
@ToString(callSuper = true)
public class TeamPageDto extends PageDto<Team> implements Serializable {
    private static final long serialVersionUID = 1462743915029636475L;

    /**
     * 团队/公司名称
     */
    private String name;

}
