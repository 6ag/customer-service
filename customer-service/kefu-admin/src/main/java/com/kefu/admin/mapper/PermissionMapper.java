package com.kefu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kefu.admin.entity.Permission;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户编号，查询所有权限
     *
     * @param userId 用户编号
     * @return
     */
    List<Permission> selectListByUserId(Integer userId);

    /**
     * 根据角色编号，查询所有权限
     *
     * @param roleId 角色编号
     * @return
     */
    List<Permission> selectListByRoleId(Integer roleId);

    /**
     * 根据角色编号，查询所有权限ID
     *
     * @param roleId 角色编号
     * @return
     */
    List<Integer> selectIdsByRoleId(Integer roleId);

    /**
     * 删除指定权限和角色的关联
     *
     * @param permissionId 角色编号
     * @return
     */
    int deleteRolePermissionByPermissionId(Integer permissionId);
}
