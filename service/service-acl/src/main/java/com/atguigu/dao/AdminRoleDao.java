package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleIdByAdminId(Long adminId);


    void deleteByAdmin(Long adminId);
}
