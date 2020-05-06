package com.leosanqing.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: rtliu
 * @Date: 2020/5/6 上午11:26
 * @Package: com.leoanqing.index.controller
 * @Description: 首页启动类
 * @Version: 1.0
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.leosanqing.index.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.leosanqing", "org.n3r.idworker"})
@EnableDiscoveryClient
public class IndexApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexApplication.class, args);
    }

}
