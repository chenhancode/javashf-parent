package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_UPLOED_SHOW = "house/upload";

    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, ModelMap modelMap){
        modelMap.addAttribute("houseId",houseId);
        modelMap.addAttribute("type",type);
        return PAGE_UPLOED_SHOW;
    }

//    /houseImage/upload/[[${houseId}]]/

    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId,
                         @PathVariable Integer type,
                         @RequestParam(value = "file") MultipartFile[] files) throws IOException {

        // 判断前端是否传过来图片
        if(files.length>0){

            for (MultipartFile file : files) {
                // 获取图片原始名字
                // String filename = file.getOriginalFilename();

                // uuid
                String newFileName = UUID.randomUUID().toString();
                // 一张张上传
                QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
                // 拼接图片地址
                String url = "http://rv7cilgg1.hb-bkt.clouddn.com/" + newFileName;
                // 本地数据库保存数据
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setImageName(newFileName);
                houseImage.setImageUrl(url);
                houseImage.setType(type);
                houseImageService.insert(houseImage);
            }


        }
        return Result.ok();
    }

//    opt.confirm('/houseImage/delete/[[${house.id}]]/'+id);
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Integer id){
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageUrl());
        houseImageService.delete(id);
        return LIST_ACTION+houseId;
    }



}
