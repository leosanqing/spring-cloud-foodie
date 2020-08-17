package com.leosanqing.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午7:50
 * @Package: com.leosanqing.springcloud
 * @Description: 鉴权服务启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
