package com.leosanqing;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: rtliu
 * @Date: 2020/6/11 上午10:23
 * @Package: com.leosanqing
 * @Description: Dashboard 启动类
 * @Version: 1.0
 */
@SpringCloudApplication
@EnableHystrixDashboard
public class DashboardApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DashboardApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
