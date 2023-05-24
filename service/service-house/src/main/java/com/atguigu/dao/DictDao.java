package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;

public interface DictDao extends BaseDao<Dict> {
    List<Dict> findZnodes(Long id);

    Integer countIsParent(Long id);


    Dict findListByDictCode(String dictCode);

    String getNameById(Long areaId);
}
