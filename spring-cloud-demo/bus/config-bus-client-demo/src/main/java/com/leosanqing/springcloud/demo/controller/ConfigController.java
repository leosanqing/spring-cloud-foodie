package com.leosanqing.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/17 下午7:04
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: Config-controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/refresh")
@RefreshScope
public class ConfigController {

    @Value("${name}")
    private String name;

    @GetMapping("name")
    public String getName() {
        return name;
    }
}
