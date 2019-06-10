package com.kefu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kefu.admin.dto.TeamPageDto;
import com.kefu.admin.entity.Team;
import com.kefu.common.vo.PageVo;

/**
 * @author feng
 * @date 2019-05-28
 */
public interface TeamService extends IService<Team> {

    /**
     * 删除团队
     * 注意只能删除没有成员的团队
     *
     * @param teamId 团队编号
     */
    void deleteTeam(Integer teamId);

    /**
     * 获取带分页的团队列表
     *
     * @param teamPageDto 分页查询条件
     * @return
     */
    PageVo<Team> getTeamPageList(TeamPageDto teamPageDto);
}
