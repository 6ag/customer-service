package com.kefu.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.entity.Permission;
import com.kefu.admin.entity.enums.PermissionTypeEnum;
import com.kefu.admin.mapper.PermissionMapper;
import com.kefu.admin.service.PermissionService;
import com.kefu.common.util.tree.TreeUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    /**
     * 根据用户编号，查询所有权限。
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public List<Permission> findAllByUserId(Integer userId) {
        return baseMapper.selectListByUserId(userId);
    }

    /**
     * 根据角色编号，查找所有权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> findAllTreeByRoleId(Integer roleId) {
        return TreeUtils.findRoots(findAllByRoleId(roleId));
    }

    /**
     * 根据角色编号，查找所有权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> findAllByRoleId(Integer roleId) {
        return baseMapper.selectListByRoleId(roleId);
    }

    /**
     * 根据角色编号，查询所有权限ID
     *
     * @param roleId 角色编号
     * @return
     */
    @Override
    public List<Integer> findIdsByRoleId(Integer roleId) {
        return baseMapper.selectIdsByRoleId(roleId);
    }

    /**
     * 查询所有权限，以树节点返回。
     * children使用
     *
     * @return
     */
    @Override
    public List<Permission> findAllTree() {
        List<Permission> list = baseMapper.selectList(new QueryWrapper<Permission>().lambda().orderByAsc(Permission::getSort));
        return TreeUtils.findRoots(list);
    }

    /**
     * 查询所有权限菜单，以树节点返回。children使用
     *
     * @return
     */
    @Override
    public List<Permission> findAllMenusTree() {
        List<Permission> list = list()
                .stream()
                .filter(item -> item.getType() == PermissionTypeEnum.MENU)
                .collect(Collectors.toList());
        return TreeUtils.findRoots(list);
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限编号
     */
    @Override
    public void deletePermission(Integer permissionId) {

        // 所有权限树结构
        List<Permission> permissionList = findAllTree();
        log.info("删除权限,permissionId={},permissionList={}", permissionId, JSON.toJSONString(permissionList));
        // 查找指定权限的树结构
        Permission permission = findPermission(permissionList, permissionId);
        log.info("查找结果={}", JSON.toJSONString(permission));
        if (permission != null) {
            log.info("查找结束,id={}, name={}", permission.getId(), permission.getName());
            // 向下递归删除权限
            recursionDeletePermission(permission);
        } else {
            throw new RuntimeException("递归查找权限错误");
        }
    }

    /**
     * 新增权限
     *
     * @param permission 权限对象
     */
    @Override
    public void addPermission(Permission permission) {
        log.info("新增权限permission,{}", permission);

        Permission permission1;
        if (permission.getType() == PermissionTypeEnum.MENU) {
            permission1 = new Permission(permission.getParentId(), permission.getPath(), permission.getComponent(), permission.getResources(), permission.getName(), permission.getIcon(), permission.getType(), permission.getSort(), permission.getRemark());
        } else {
            permission1 = new Permission(permission.getParentId(), permission.getResources(), permission.getName(), permission.getType(), permission.getRemark());
        }
        log.info("新增权限permission1,{}", permission1);

        // 新增权限
        baseMapper.insert(permission1);
    }

    /**
     * 修改权限
     *
     * @param permission 权限对象
     */
    @Override
    public void updatePermission(Permission permission) {
        log.info("修改权限permission,{}", permission);

        Permission permission1;
        if (permission.getType() == PermissionTypeEnum.MENU) {
            permission1 = new Permission(permission.getId(), permission.getParentId(), permission.getPath(), permission.getComponent(), permission.getResources(), permission.getName(), permission.getIcon(), permission.getType(), permission.getSort(), permission.getRemark());
        } else {
            permission1 = new Permission(permission.getId(), permission.getParentId(), permission.getResources(), permission.getName(), permission.getType(), permission.getRemark());
        }
        log.info("修改权限permission1,{}", permission1);

        // 新增权限
        baseMapper.updateById(permission1);
    }

    /**
     * 向下递归查找
     * TODO:理清带返回值的递归调用原理
     *
     * @param permissionId 权限编号
     * @return
     */
    private Permission findPermission(List<Permission> permissionList, Integer permissionId) {
        Permission result = null;
        for (Permission item : permissionList) {
            log.info("向下递归查找id={}, name={}", item.getId(), item.getName());
            if (item.getId().equals(permissionId)) {
                log.info("找到了,id={}, name={}", item.getId(), item.getName());
                result = item;
                return result;
            } else {
                if (item.getChildren() != null) {
                    Permission temp = findPermission(item.getChildren(), permissionId);
                    if (temp != null) {
                        result = temp;
                    }
                }
            }
        }
        log.info("执行完成，返回null");
        return result;
    }

    /**
     * 递归删除权限
     *
     * @param permission 权限对象
     */
    private void recursionDeletePermission(Permission permission) {
        // 删除角色-权限关联表数据
        baseMapper.deleteRolePermissionByPermissionId(permission.getId());
        // 删除权限
        baseMapper.deleteById(permission.getId());

        // 向下递归
        if (permission.getChildren() != null) {
            permission.getChildren().forEach(this::recursionDeletePermission);
        }
    }

}
