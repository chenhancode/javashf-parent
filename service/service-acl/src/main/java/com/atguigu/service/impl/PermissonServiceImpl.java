package com.atguigu.service.impl;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.PermissonDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;

import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.service.PermissonService;

import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissonService.class)
public class PermissonServiceImpl extends BaseServiceImpl<Permission> implements PermissonService {

    @Resource
    private PermissonDao permissonDao;

    @Resource
    private RolePermissionDao rolePermissionDao;


    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissonDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        // 查询所有表
        List<Permission> permissonDaoAll = permissonDao.findAll();
        // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
        // 获取角色以获取权限
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Permission permission : permissonDaoAll) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if(permissionIdList.contains(permission.getId())){
                map.put("checked",true);
            }
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deleteByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionDao.insert(rolePermission);
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        if(adminId.longValue()==1){
            permissionList = permissonDao.findAll();
        }else {
            permissionList = permissonDao.findListByAdminId(adminId);
        }
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissonDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }
}
