package com.atguigu.service.impl;




import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl  extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private AdminRoleDao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        // 查询所有角色
        List<Role> allRolesList  = roleDao.findAll();
        // 查询以选中角色
        List<Long> existRoleIdList = adminRoleDao.findRoleIdByAdminId(adminId);

        // 对角色进行分类
        ArrayList<Role> noAssginRoleList  = new ArrayList<>();
        ArrayList<Role> assginRoleList   = new ArrayList<>();
        for (Role role : allRolesList) {
            if(existRoleIdList.contains(role.getId())){
                assginRoleList.add(role);
            }else {
                noAssginRoleList.add(role);
            }
        }
        Map<String, Object> roleMap  = new HashMap<>();
        roleMap.put("noAssginRoleList",noAssginRoleList);
        roleMap.put("assginRoleList",assginRoleList);
        return roleMap;
    }

    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteByAdmin(adminId);
        for (Long roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)){
                continue;
            }
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(roleId);
            adminRole.setAdminId(adminId);
            adminRoleDao.insert(adminRole);
        }
    }
}
