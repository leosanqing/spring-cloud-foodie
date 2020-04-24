package com.leosanqing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: rtliu
 * @Date: 2020/4/23 下午3:33
 * @Package: com.leosanqing
 * @Description: EurekaService 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
//        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
