package com.kefu.admin.controller.system;

import com.kefu.admin.dto.TeamPageDto;
import com.kefu.admin.entity.Team;
import com.kefu.admin.service.TeamService;
import com.kefu.common.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队
 *
 * @author feng
 * @date 2019-05-30
 */
@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return
     */
    @PostMapping
    public ResultVo addTeam(@RequestBody Team team) {
        teamService.save(team);
        return ResultVo.success();
    }

    /**
     * 删除团队
     *
     * @param teamId 团队编号
     * @return
     */
    @DeleteMapping("/{teamId}")
    public ResultVo deleteTeam(@PathVariable Integer teamId) {
        teamService.deleteTeam(teamId);
        return ResultVo.success();
    }

    /**
     * 更新团队信息
     *
     * @param team 团队信息
     * @return
     */
    @PutMapping
    public ResultVo updateTeam(@RequestBody Team team) {
        teamService.updateById(team);
        return ResultVo.success();
    }

    /**
     * 获取团队信息
     *
     * @param teamId 团队编号
     * @return
     */
    @GetMapping("/{teamId}")
    public ResultVo getTeam(@PathVariable Integer teamId) {
        return ResultVo.success(teamService.getById(teamId));
    }

    /**
     * 分页查询团队列表
     *
     * @param teamPageDto 分页查询条件
     * @return
     */
    @PostMapping("/list")
    public ResultVo getTeamList(@RequestBody TeamPageDto teamPageDto) {
        return ResultVo.success(teamService.getTeamPageList(teamPageDto));
    }

}
