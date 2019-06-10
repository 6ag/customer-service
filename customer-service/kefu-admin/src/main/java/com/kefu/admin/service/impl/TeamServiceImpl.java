package com.kefu.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.dto.TeamPageDto;
import com.kefu.admin.dto.UserPageDto;
import com.kefu.admin.entity.Team;
import com.kefu.admin.entity.User;
import com.kefu.admin.mapper.TeamMapper;
import com.kefu.admin.service.TeamService;
import com.kefu.admin.service.UserService;
import com.kefu.common.db.util.PageUtils;
import com.kefu.common.vo.PageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author feng
 * @date 2019-05-28
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private UserService userService;

    /**
     * 删除团队
     * 注意只能删除没有成员的团队
     *
     * @param teamId 团队编号
     */
    @Override
    public void deleteTeam(Integer teamId) {
        if (getById(teamId) == null) {
            throw new RuntimeException("团队信息不存在，teamId=" + teamId);
        }

        // 检查团队中是否存在成员
        UserPageDto userPageDto = new UserPageDto();
        userPageDto.setTeamId(teamId);
        PageVo<User> pageVo = userService.findUserPageList(userPageDto);
        if (pageVo.getTotalCount() > 0) {
            throw new RuntimeException("删除团队失败，需要先清空团队中的所有成员");
        }

        removeById(teamId);
    }

    /**
     * 获取带分页的团队列表
     *
     * @param teamPageDto 分页查询条件
     * @return
     */
    @Override
    public PageVo<Team> getTeamPageList(TeamPageDto teamPageDto) {
        IPage<Team> page = baseMapper.selectPageList(teamPageDto);
        return PageUtils.getPageVo(page);
    }

}
