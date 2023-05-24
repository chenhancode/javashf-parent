package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {


    private final static String LIST_ACTION = "redirect:/admin";

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private AdminService adminService;

//    opt.confirm('/admin/delete/'+id);
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

//    th:action="@{/admin/update}"
    // 更新数据
    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    //opt.openWin('/admin/edit/' + id,'修改',580,430);
    // 回显数据
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap modelMap){
        Admin admin = adminService.getById(id);
        modelMap.addAttribute("admin",admin);
        return PAGE_EDIT;
    }



//    th:action="@{/admin/save}"
   // 保存数据
    @RequestMapping("/save")
    public String save(Admin admin){
        admin.setHeadUrl("https://hbimg.huaban.com/97434b6be4581d4a7ca950ba196350798f2182e2b913-VgRxn1_fw658");
        adminService.insert(admin);
        return PAGE_SUCCESS;
    }




//    opt.openWin('/admin/create','新增',630,430) 展示页面
    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }


     // 拷贝到数据库里面，默认头像：http://139.198.127.41:9000/sph/20230505/default_handsome.jpg
    @RequestMapping
    public String index(HttpServletRequest request, ModelMap modelMap){
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("filters",filters);
        return PAGE_INDEX;

    }
}