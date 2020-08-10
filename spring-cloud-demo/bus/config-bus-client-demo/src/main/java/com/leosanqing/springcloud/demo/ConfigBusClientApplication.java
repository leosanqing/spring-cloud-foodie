package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: rtliu
 * @Date: 2020/6/17 下午7:00
 * @Package: com.leosanqing.springcloud.demo
 * @Description: bus-config-client 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigBusClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
