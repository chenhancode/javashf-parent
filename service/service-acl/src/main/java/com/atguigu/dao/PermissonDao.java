package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;

import java.util.List;

public interface PermissonDao extends BaseDao<Permission> {
    List<Permission> findListByAdminId(Long adminId);
}
