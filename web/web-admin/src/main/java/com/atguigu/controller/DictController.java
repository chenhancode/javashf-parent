package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;


    @RequestMapping("/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable long parentId){
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode){
        System.out.println("测试----------"+dictCode);
            List<Dict> list = dictService.findListByDictCode(dictCode);
            return Result.ok(list);
    }

    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id" ,defaultValue = "0") Long id){
        List<Map<String,Object>> list = dictService.findZnodes(id);
        return Result.ok(list);
    }

    @RequestMapping
    public String index(){
        return "dict/index";
    }
}
