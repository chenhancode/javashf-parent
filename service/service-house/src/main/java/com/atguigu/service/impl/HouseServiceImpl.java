package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;

import javax.annotation.Resource;
import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Resource
    private HouseDao houseDao;

    @Resource
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        System.out.println("id是-----"+id);
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
        house.setBuildStructureName(dictDao.getNameById(house.getBuildStructureId()));
        house.setDirectionName(dictDao.getNameById(house.getDirectionId()));
        house.setDecorationName(dictDao.getNameById(house.getDecorationId()));
        house.setHouseUseName(dictDao.getNameById(house.getHouseUseId()));
        house.setHouseTypeName(dictDao.getNameById(house.getHouseTypeId()));
        return house;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseDao.update(house);
    }
}
