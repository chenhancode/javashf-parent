package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissonService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_CREATE = "role/create";
    private final static String PAGE_EDIT = "role/edit";
    private final static String LIST_ACTION = "redirect:/role";
    private final static String PAGE_ASSGIN_SHOW = "role/assginShow";
    private final static String PAGE_SUCCESS = "common/successPage";
    @Reference
    private RoleService roleService;

    @Reference
    private PermissonService permissonService;

    @PostMapping("/assignPermission")
    public String assignPermission(Long roleId,Long[] permissionIds){
        permissonService.saveRolePermissionRealtionShip(roleId,permissionIds);
        return PAGE_SUCCESS;
    }

    @GetMapping("/assignShow/{roleId}")
    public String assignShow(ModelMap modelMap,@PathVariable Long roleId){
        System.out.println(roleId+"roleId");
        List<Map<String,Object>> zNodes = permissonService.findPermissionByRoleId(roleId);
        modelMap.addAttribute("zNodes",zNodes);
        modelMap.addAttribute("roleId",roleId);
        return PAGE_ASSGIN_SHOW;
    }


//     opt.confirm('/role/delete/'+id);
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        roleService.delete(id);
        // 删除成功,刷新页面
        return LIST_ACTION;
    }

    /**
     * th:action="@{/role/update}"
     * 更新数据
     */
    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return "common/successPage";
    }




    /**
     * 回显数据
     */
//     opt.openWin('/role/edit/' + id,'修改',580,430);
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap modelMap){
        // 根据id，查询角色对象
        Role role = roleService.getById(id);
        modelMap.addAttribute("role",role);
        return PAGE_EDIT;


    }




//    th:action="@{/role/save}"
    /**
     * 保存数据
     * @PostMapping : 只能接收post请求 ，有一个限制
     * @RequestMapping ：可以接收所有的请求 ，没有限制
     */
    @PostMapping("/save")
    public String save(Role role){
        roleService.insert(role);
        return "common/successPage";
    }



//    opt.openWin("/role/create","新增",580,430);

    /**
     * 展示页面
     * @return
     */
    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }


    /**
     * 分页必须获取  pageNum 和 pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping
    public String index(ModelMap modelMap, HttpServletRequest request){

        Map<String,Object> filters = getFilters(request);
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        modelMap.addAttribute("filters",filters);
        modelMap.addAttribute("page",pageInfo);
        return PAGE_INDEX;
    }


}
