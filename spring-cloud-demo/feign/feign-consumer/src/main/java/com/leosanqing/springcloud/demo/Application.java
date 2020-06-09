package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: rtliu
 * @Date: 2020/6/5 下午2:18
 * @Package: com.leosanqing.springcloud.demo
 * @Description: Feign 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
