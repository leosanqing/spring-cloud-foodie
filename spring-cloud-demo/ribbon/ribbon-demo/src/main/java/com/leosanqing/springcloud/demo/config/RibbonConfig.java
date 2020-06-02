package com.leosanqing.springcloud.demo.config;

import com.leosanqing.springcloud.demo.rules.MyRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: rtliu
 * @Date: 2020/6/2 下午5:09
 * @Package: com.leosanqing.springcloud.demo.config
 * @Description: 负载均衡配置
 * @Version: 1.0
 */
@Configuration
@RibbonClient(name = "eureka-client", configuration = MyRule.class)
public class RibbonConfig {
}
