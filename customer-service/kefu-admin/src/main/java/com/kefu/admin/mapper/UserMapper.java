package com.kefu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kefu.admin.dto.UserPageDto;
import com.kefu.admin.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户分页列表
     * 结果集带用户团队信息
     *
     * @param userPageDto 用户分页查询条件
     * @return
     */
    IPage<User> selectPageList(UserPageDto userPageDto);

    /**
     * 查询用户
     * 结果集带用户团队信息
     *
     * @param id 用户编号
     * @return
     */
    User selectById(Integer id);

    /**
     * 查询用户
     * 结果集带用户团队信息
     *
     * @param username 用户名
     * @return
     */
    User selectByUsername(String username);

    /**
     * 查询用户
     * 结果集带用户团队信息
     *
     * @param openId 微信openId
     * @return
     */
    User selectByOpenId(String openId);

    /**
     * 删除指定用户的所有角色
     *
     * @param userId 用户编号
     * @return
     */
    int deleteUserRoleByUserId(Integer userId);

    /**
     * 删除指定角色与用户关联
     *
     * @param roleId 角色编号
     * @return
     */
    int deleteUserRoleByRoleId(Integer roleId);

    /**
     * 给指定用户赋予指定角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return
     */
    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
