package com.leosanqing.springcloud.demo;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

/**
 * @Author: rtliu
 * @Date: 2020/6/10 下午2:13
 * @Package: com.leosanqing.springcloud.demo
 * @Description: Turbine 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableTurbine
public class TurbineApplication {

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
        new SpringApplicationBuilder(TurbineApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
