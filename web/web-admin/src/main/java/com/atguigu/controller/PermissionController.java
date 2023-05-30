package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value="/permission")
@SuppressWarnings({"unchecked", "rawtypes"})
public class PermissionController {
    private final static String LIST_ACTION = "redirect:/permission";

    private final static String PAGE_INDEX = "permission/index";
    private final static String PAGE_CREATE = "permission/create";
    private final static String PAGE_EDIT = "permission/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private PermissonService permissonService;

    /**
     * 获取菜单
     * @return
     */
    @GetMapping
    public String index(ModelMap model) {
        List<Permission> list = permissonService.findAllMenu();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }


    @GetMapping("/create")
    public String create(ModelMap model, Permission permission) {
        model.addAttribute("permission",permission);
        return PAGE_CREATE;
    }


    @PostMapping("/save")
    public String save(Permission permission) {

        permissonService.insert(permission);

        return PAGE_SUCCESS;
    }


    @GetMapping("/edit/{id}")
    public String edit(ModelMap model,@PathVariable Long id) {
        Permission permission = permissonService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }


    @PostMapping(value="/update")
    public String update(Permission permission) {
        permissonService.update(permission);
        return PAGE_SUCCESS;
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissonService.delete(id);
        return LIST_ACTION;
    }


}
