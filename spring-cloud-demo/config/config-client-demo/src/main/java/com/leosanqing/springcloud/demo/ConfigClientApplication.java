package com.leosanqing.springcloud.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author: rtliu
 * @Date: 2020/6/15 上午11:47
 * @Package: com.leosanqing.springcloud.demo
 * @Description: config-client 启动类
 * @Version: 1.0
 */
@SpringBootApplication
public class ConfigClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

    }
}
