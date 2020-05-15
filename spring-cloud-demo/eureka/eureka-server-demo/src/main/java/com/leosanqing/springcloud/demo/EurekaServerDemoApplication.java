package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: rtliu
 * @Date: 2020/5/14 下午5:56
 * @Package: com.leosanqing.springcloud.demo
 * @Description: eurekaServer应用
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerDemoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
