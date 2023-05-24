package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private final static String LIST_ACTION = "redirect:/house";
    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SHOW = "house/show";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;


    @RequestMapping("/{id}")
    public String show(@PathVariable Long id,ModelMap modelMap){
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());

        List<HouseImage> houseImage1List = new ArrayList<>();
        List<HouseImage> houseImage2List = new ArrayList<>();
        List<HouseBroker> houseBrokerList = new ArrayList<>();
        List<HouseUser> houseUserList = new ArrayList<>();

        modelMap.addAttribute("house",house);
        modelMap.addAttribute("community",community);
        modelMap.addAttribute("houseImage1List",houseImageService.findList(id,1));
        modelMap.addAttribute("houseImage2List",houseImageService.findList(id,2));
        modelMap.addAttribute("houseBrokerList",houseBrokerService.findListByHouseId(id));
        modelMap.addAttribute("houseUserList",houseUserService.findListByHouseId(id));
        return PAGE_SHOW;
    }





//    /house/publish/" + id + "/" + status

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    @RequestMapping("/save")
    public String save(ModelMap modelMap,House house){
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap modelMap){
        House house = houseService.getById(id);
        List<Community> communityList = communityService.findAll();
        modelMap.addAttribute("house",house);
        modelMap.addAttribute("communityList",communityList);
        modelMap.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        modelMap.addAttribute("floorList",dictService.findListByDictCode("floor"));
        modelMap.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        modelMap.addAttribute("directionList",dictService.findListByDictCode("direction"));
        modelMap.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        modelMap.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }


    @RequestMapping("/create")
    public String create(ModelMap modelMap){
        List<Community> communityList = communityService.findAll();
        modelMap.addAttribute("communityList",communityList);
        modelMap.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        modelMap.addAttribute("floorList",dictService.findListByDictCode("floor"));
        modelMap.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        modelMap.addAttribute("directionList",dictService.findListByDictCode("direction"));
        modelMap.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        modelMap.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_CREATE;
    }

    @RequestMapping
    public String index(HttpServletRequest request, ModelMap modelMap){
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        List<Community> communityList = communityService.findAll();

        modelMap.addAttribute("filters",filters);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("communityList",communityList);
        modelMap.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        modelMap.addAttribute("floorList",dictService.findListByDictCode("floor"));
        modelMap.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        modelMap.addAttribute("directionList",dictService.findListByDictCode("direction"));
        modelMap.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        modelMap.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return PAGE_INDEX;
    }

}
