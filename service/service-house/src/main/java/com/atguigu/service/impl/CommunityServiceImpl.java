package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Resource
    private CommunityDao communityDao;

    @Resource
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        if(community==null){
            return null;
        }
        String areaName = dictDao.getNameById(community.getAreaId());
        String plateName = dictDao.getNameById(community.getPlateId());
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1) ;
        int pageSize = CastUtil.castInt(filters.get("pageSize"),2) ;
        // 开始分页
        PageHelper.startPage(pageNum,pageSize);
        Page<Community> page = communityDao.findPage(filters);
        List<Community> list = page.getResult();
        for (Community community : list) {
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<Community>(page,10);

    }
}
