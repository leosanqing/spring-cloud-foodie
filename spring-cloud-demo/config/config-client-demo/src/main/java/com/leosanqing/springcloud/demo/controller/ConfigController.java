package com.leosanqing.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/15 上午11:48
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: ConfigController
 * @Version: 1.0
 */
@RestController
public class ConfigController {
    @Value("${name}")
    private String name;

    @Value("${words}")
    private String words;

    @GetMapping("test")
    public String test() {
        return name + "=>" + words;
    }

}
