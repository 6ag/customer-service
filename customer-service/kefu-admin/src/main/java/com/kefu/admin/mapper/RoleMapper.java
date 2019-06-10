package com.kefu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kefu.admin.dto.RolePageDto;
import com.kefu.admin.entity.Role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 删除指定角色的所有权限
     *
     * @param roleId 角色编号
     * @return
     */
    int deleteRolePermissionByRoleId(Integer roleId);

    /**
     * 给指定角色赋予指定权限
     *
     * @param roleId       角色编号
     * @param permissionId 权限编号
     * @return
     */
    int insertRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     * 查询指定用户所有角色
     *
     * @param userId 用户编号
     * @return
     */
    List<Role> selectListByUserId(Integer userId);

    /**
     * 查询角色分页列表
     *
     * @param rolePageDto
     * @return
     */
    IPage<Role> selectPageList(RolePageDto rolePageDto);

    /**
     * 查询指定角色的用户数量
     *
     * @param roleId 角色编号
     * @return
     */
    int selectUserCountByRoleId(Integer roleId);

    /**
     * 获取角色编号集合
     *
     * @param roleNameEns 角色英文名称集合
     * @return
     */
    List<Integer> getRoleIds(List<String> roleNameEns);
}
