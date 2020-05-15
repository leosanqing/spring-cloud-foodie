package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: rtliu
 * @Date: 2020/5/15 上午10:17
 * @Package: com.leosanqing.springcloud.demo
 * @Description: Eureka-Client
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
