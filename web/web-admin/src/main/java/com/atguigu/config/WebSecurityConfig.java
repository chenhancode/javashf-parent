package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //告诉spring此类是加载配置文件
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //默认Spring Security不允许iframe嵌套显示，我们需要设置
        http.headers().frameOptions().sameOrigin();
        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll().anyRequest().authenticated();
        //配置登录成功后的地址
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        //配置登出的路径及登出成功去往的地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //关闭跨域请求伪造功能
        http.csrf().disable();
        //添加异常入口
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }

    @Bean //配置加密方式
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Override //配置用户密码及权限
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("");
//    }
}
