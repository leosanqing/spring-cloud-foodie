package com.leosanqing;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: rtliu
 * @Date: 2020/6/11 上午10:23
 * @Package: com.leosanqing
 * @Description: Dashboard 启动类
 * @Version: 1.0
 */
@SpringCloudApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
