package com.leosanqing.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 上午9:56
 * @Package: com.leosanqing.cart.controller
 * @Description: 购物车服务启动类
 * @Version: 1.0
 */

@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.leosanqing.cart.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.leosanqing", "org.n3r.idworker"})
@EnableDiscoveryClient
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

}
