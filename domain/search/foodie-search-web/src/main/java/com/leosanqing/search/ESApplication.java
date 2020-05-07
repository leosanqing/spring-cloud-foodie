package com.leosanqing.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: leosanqing
 * @Date: 2019-12-03 23:24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.leosanqing", "org.n3r.idworker"})
@EnableDiscoveryClient
public class ESApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }
}
