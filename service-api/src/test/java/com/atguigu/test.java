package com.atguigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
    @Test
    public void test1(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.encode("123456");
    }
}
