package com.kefu.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.dto.RoleDto;
import com.kefu.admin.dto.RolePageDto;
import com.kefu.admin.entity.Permission;
import com.kefu.admin.entity.Role;
import com.kefu.admin.mapper.RoleMapper;
import com.kefu.admin.service.PermissionService;
import com.kefu.admin.service.RoleService;
import com.kefu.admin.service.UserService;
import com.kefu.admin.vo.RoleVo;
import com.kefu.common.db.util.PageUtils;
import com.kefu.common.vo.PageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author feng
 * @date 2019-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    /**
     * 更新角色和权限的对应关系（设置角色拥有的权限）
     *
     * @param roleId        角色编号
     * @param permissionIds 权限编号集合
     */
    @Override
    public void updateRolePermissionRelation(Integer roleId, List<Integer> permissionIds) {
        // 删除角色所有权限
        baseMapper.deleteRolePermissionByRoleId(roleId);
        // 编号排序
        permissionIds = permissionIds.stream().sorted().collect(Collectors.toList());
        // 遍历新增角色的权限
        permissionIds.forEach(permissionId -> baseMapper.insertRolePermission(roleId, permissionId));
    }

    /**
     * 通过用户编号查找拥有的角色集合
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public List<Role> findRoleListByUserId(Integer userId) {
        return baseMapper.selectListByUserId(userId);
    }

    /**
     * 分页查询所有角色
     *
     * @param rolePageDto 查询条件
     * @return
     */
    @Override
    public PageVo<Role> findRolePageList(RolePageDto rolePageDto) {
        IPage<Role> page = baseMapper.selectPageList(rolePageDto);
        return PageUtils.getPageVo(page);
    }

    /**
     * 获取用户默认角色，暂时最后一个角色作为默认
     *
     * @return
     */
    @Override
    public Role findUserNormalRole() {
        return getOne(new QueryWrapper<Role>().lambda().eq(Role::getNameEn, "ROLE_TEAM_ADMIN"));
    }

    /**
     * 新增角色
     *
     * @param roleDto 角色信息
     */
    @Override
    public void addRole(RoleDto roleDto) {
        log.info("新增角色,{}", JSON.toJSONString(roleDto));
        if (roleDto.getRoleInfo() == null) {
            throw new RuntimeException("新增角色失败,role=" + roleDto.getRoleInfo());
        }

        baseMapper.insert(roleDto.getRoleInfo());
        if (roleDto.getRoleInfo().getId() == null || roleDto.getRoleInfo().getId() == 0) {
            throw new RuntimeException("新增角色失败,roleId=" + roleDto.getRoleInfo().getId());
        }

        // 允许新增角色的时候，不赋予任何权限
        if (roleDto.getPermissionIds() != null && roleDto.getPermissionIds().size() > 0) {
            log.info("新增角色成功,开始更新权限{}", JSON.toJSONString(roleDto));
            updateRolePermissionRelation(roleDto.getRoleInfo().getId(), roleDto.getPermissionIds());
        }
    }

    /**
     * 删除角色
     *
     * @param roleId 角色编号
     */
    @Override
    public void deleteRole(Integer roleId) {

        // 删除用户和角色关联
        userService.deleteUserRoleRelation(roleId);

        // 删除角色和权限关联
        baseMapper.deleteRolePermissionByRoleId(roleId);

        // 删除角色
        removeById(roleId);
    }

    /**
     * 更新角色信息
     *
     * @param roleDto 角色信息
     */
    @Override
    public void updateRole(RoleDto roleDto) {
        // 新增更新角色信息
        updateById(roleDto.getRoleInfo());
        // 更新角色权限对应关系
        updateRolePermissionRelation(roleDto.getRoleInfo().getId(), roleDto.getPermissionIds());
    }

    /**
     * 获取角色信息，包括角色拥有的权限编号
     *
     * @param roleId 角色编号
     * @return
     */
    @Override
    public RoleVo getRole(Integer roleId) {
        Role role = getById(roleId);

        List<Permission> permissionTree = permissionService.findAllTreeByRoleId(roleId);
        List<Integer> allPermissionIds = new ArrayList<>();
        List<Integer> childrenPermissionIds = new ArrayList<>();

        // 递归获取权限编号
        resursionGetPermissionId(permissionTree, allPermissionIds, childrenPermissionIds);

        log.info("permissions,{}", JSON.toJSONString(permissionTree));
        log.info("allPermissionIds,{}", allPermissionIds.toString());
        log.info("childrenPermissionIds,{}", childrenPermissionIds.toString());

        return new RoleVo(role, allPermissionIds, childrenPermissionIds);
    }

    /**
     * 获取指定用户的角色级别
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public Integer getTallestRoleLevel(Integer userId) {
        List<Role> roles = baseMapper.selectListByUserId(userId);
        if (roles == null || roles.size() == 0) {
            throw new RuntimeException("没有查找到角色");
        }

        // 升序排序
        roles.sort(Comparator.comparing(Role::getLevel));

        log.info("排序完成,roles={}", JSON.toJSONString(roles));
        return roles.get(0).getLevel();
    }

    /**
     * 获取角色编号集合
     *
     * @param roleNameEns 角色英文名称集合
     * @return
     */
    @Override
    public List<Integer> getRoleIds(List<String> roleNameEns) {
        return baseMapper.getRoleIds(roleNameEns);
    }

    /**
     * 递归获取权限编号
     *
     * @param permissionTree        所有权限树结构
     * @param allPermissionIds      所有权限编号集合
     * @param childrenPermissionIds 没有下级的权限编号集合
     */
    private void resursionGetPermissionId(List<Permission> permissionTree, List<Integer> allPermissionIds, List<Integer> childrenPermissionIds) {
        for (Permission permission : permissionTree) {
            allPermissionIds.add(permission.getId());
            if (permission.getChildren() != null) {
                resursionGetPermissionId(permission.getChildren(), allPermissionIds, childrenPermissionIds);
            } else {
                childrenPermissionIds.add(permission.getId());
            }
        }
    }

}
