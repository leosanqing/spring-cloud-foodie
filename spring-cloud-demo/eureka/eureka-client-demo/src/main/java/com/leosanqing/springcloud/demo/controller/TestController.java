package com.leosanqing.springcloud.demo.controller;

import com.leosanqing.springcloud.demo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/5/15 上午10:33
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: Eureka测试Controller
 * @Version: 1.0
 */
@RestController
@Slf4j
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("say_hi")
    public String sayHi() {
        return "This is " + port;
    }


    @PostMapping("say_hi1")
    public User sayHi(@RequestBody User user) {
        log.info("我是" + user.getName());
        user.setPort(port);
        return user;
    }


}
