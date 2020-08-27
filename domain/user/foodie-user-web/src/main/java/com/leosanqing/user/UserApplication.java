package com.leosanqing.user;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author: rtliu
 * @Date: 2020/4/27 下午5:31
 * @Package: com.leosanqing
 * @Description: User微服务启动类
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.leosanqing.user.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.leosanqing", "org.n3r.idworker"})
@EnableDiscoveryClient

// TODO feign注解
public class UserApplication {

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
