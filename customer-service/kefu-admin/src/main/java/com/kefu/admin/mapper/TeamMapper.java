package com.kefu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kefu.admin.dto.TeamPageDto;
import com.kefu.admin.entity.Team;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    /**
     * 通过团队编号查询团队
     * UserMapper.xml调用了此方法
     *
     * @param id 团队编号
     * @return
     */
    Team selectById(Integer id);

    /**
     * 查找带分页的团队列表
     *
     * @param teamPageDto 查询条件
     * @return
     */
    IPage<Team> selectPageList(TeamPageDto teamPageDto);
}
