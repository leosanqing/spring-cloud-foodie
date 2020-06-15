package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: rtliu
 * @Date: 2020/6/15 上午11:21
 * @Package: com.leosanqing.springcloud.demo
 * @Description: 配置中心启动类
 * @Version: 1.0
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
