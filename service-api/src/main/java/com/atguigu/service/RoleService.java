package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.Map;

public interface RoleService extends BaseService<Role> {


    Map<String, Object> findRoleByAdminId(Long adminId);

    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);

}
