package com.leosanqing.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: rtliu
 * @Date: 2020/5/26 下午2:55
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: ribbon-demo Controller
 * @Version: 1.0
 */
@RestController
public class Controller {

    @Autowired

    private RestTemplate restTemplate;

    @GetMapping("sayHi")
    public String sayHi() {
        return restTemplate.getForObject("http://eureka-client/say_hi", String.class);
    }
}
