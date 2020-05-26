package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: rtliu
 * @Date: 2020/5/26 下午2:49
 * @Package: com.leosanqing.springcloud.demo
 * @Description: Ribbon 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RibbonDemoApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
