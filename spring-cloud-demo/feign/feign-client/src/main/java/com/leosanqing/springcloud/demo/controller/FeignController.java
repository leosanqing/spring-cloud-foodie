package com.leosanqing.springcloud.demo.controller;

import com.leosanqing.springcloud.demo.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 上午9:41
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: Feign 接口层
 * @Version: 1.0
 */
@RestController
@Slf4j
public class FeignController {

    @Autowired
    private IService service;


    @GetMapping("say_hi")
    public String sayHi() {
        return "this is " + service.sayHi();
    }
}
