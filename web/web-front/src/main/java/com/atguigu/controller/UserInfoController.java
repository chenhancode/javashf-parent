package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.RegisterVo;
import com.github.pagehelper.StringUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/sendCode/{moble}")
    public Result sendCode(@PathVariable String moble, HttpServletRequest request){
        int code = new Random().nextInt(9000)+1000;
        request.getSession().setAttribute("CODE",code+"");
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo,HttpServletRequest request){
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if(StringUtil.isEmpty(nickName)||StringUtil.isEmpty(phone)||StringUtil.isEmpty(password)||StringUtil.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        Object currentCode = request.getSession().getAttribute("CODE");
        if(!code.equals(currentCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(userInfo!=null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

}
