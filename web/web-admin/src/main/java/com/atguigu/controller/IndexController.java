package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.mscapi.PRNG;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissonService permissonService;
    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap modelMap) {
        Long adminId = 1L;
        Admin admin = adminService.getById(adminId);
        List<Permission> permissionList = permissonService.findMenuPermissionByAdminId(adminId);
        modelMap.addAttribute("admin",admin);
        modelMap.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {

        return PAGE_MAIN;
    }
}
