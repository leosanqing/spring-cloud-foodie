package com.leosanqing;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @Author: rtliu
 * @Date: 2020/6/11 上午9:58
 * @Package: com.leosanqing
 * @Description: Turbine 启动类
 * @Version: 1.0
 */
@EnableAutoConfiguration
@EnableTurbine
@EnableDiscoveryClient
public class TurbineApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TurbineApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
