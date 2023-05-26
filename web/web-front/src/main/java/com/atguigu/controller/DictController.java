package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping(value = "/findListByDictCode/{dictCode}")
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        System.out.println("测试是否有");
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

//    '/dict/findListByParentId/'+id
    @RequestMapping("/findListByParentId/{parenId}")
    public Result<List<Dict>> findListByParentId(@PathVariable Long parenId){
        List<Dict> list = dictService.findListByParentId(parenId);
        return Result.ok(list);
    }


}
