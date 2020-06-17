package com.leosanqing.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: rtliu
 * @Date: 2020/6/16 上午11:29
 * @Package: com.leosanqing.user
 * @Description: 用户属性
 * @Version: 1.0
 */
@Configuration
@RefreshScope
@Data
public class UserApplicationProperties {

    @Value("${userservice.registration.disable}")
    private boolean disable;
}
