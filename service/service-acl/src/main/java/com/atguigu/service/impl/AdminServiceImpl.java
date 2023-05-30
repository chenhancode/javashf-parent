package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;


import javax.annotation.Resource;

@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public Admin getByUserName(String username) {
        return adminDao.getByUserName(username);
    }
}
