package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissonService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissonService permissonService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByUserName(username);
        if(admin == null) throw new UsernameNotFoundException("用户不存在");
        List<String> codeList = permissonService.findCodeListByAdminId(admin.getId());
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String code : codeList) {
            if(StringUtils.isEmpty(code)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(code);
            authorities.add(authority);
        }
        return new User(username,admin.getPassword(), authorities);

    }
}
