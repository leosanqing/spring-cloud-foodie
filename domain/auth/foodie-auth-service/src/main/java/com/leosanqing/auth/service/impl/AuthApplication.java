package com.leosanqing.auth.service.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @Author: rtliu
 * @Date: 2020/4/27 下午5:31
 * @Package: com.leosanqing
 * @Description: User微服务启动类
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描 mybatis 通用 mapper 所在的包
// 扫描所有包以及相关组件包
@EnableDiscoveryClient

// TODO feign注解
public class AuthApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
