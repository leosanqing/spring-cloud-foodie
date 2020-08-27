package com.leosanqing.order;

import com.leosanqing.item.service.ItemService;
import com.leosanqing.order.fallback.itemservice.ItemCommentsFeignClient;
import com.leosanqing.user.service.AddressService;
import com.leosanqing.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by 半仙.
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.leosanqing.order.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.leosanqing", "org.n3r.idworker"})
@EnableDiscoveryClient
// TODO feign注解
@EnableFeignClients(
//        basePackages = {
//        "com.leosanqing.user.service",
//        "com.leosanqing.item.service"
//        }

        clients = {
                ItemCommentsFeignClient.class,
                ItemService.class,
                UserService.class,
                AddressService.class
        }
)
@EnableScheduling
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
