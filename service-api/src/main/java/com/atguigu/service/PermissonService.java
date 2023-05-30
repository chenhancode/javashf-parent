package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;

import java.util.List;
import java.util.Map;

public interface PermissonService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> findAllMenu();

    List<String> findCodeListByAdminId(Long id);
}
