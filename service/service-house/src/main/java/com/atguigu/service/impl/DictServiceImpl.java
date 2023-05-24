package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;

import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Resource
    private DictDao dictDao;

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Dict> list = dictDao.findZnodes(id);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        for (Dict dict:list){
            // 查看有多少count
            Integer count = dictDao.countIsParent(dict.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",count>0?true:false);
            data.add(map);
        }
        return data;
    }

    @Override
    public List<Dict> findListByParentId(long parentId) {
        return dictDao.findZnodes(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.findListByDictCode(dictCode);
        if(dict==null){
            return null;
        }
        return this.findListByParentId(dict.getId());
    }
}
