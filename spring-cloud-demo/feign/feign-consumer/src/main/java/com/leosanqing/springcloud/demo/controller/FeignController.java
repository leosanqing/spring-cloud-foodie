package com.leosanqing.springcloud.demo.controller;

import com.leosanqing.springcloud.demo.api.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/5 下午2:27
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: Feign Controller
 * @Version: 1.0
 */
@RestController
public class FeignController {

    @Autowired
    private IService iService;


    @GetMapping("say_hi")
    public String sayHi() {
        return iService.sayHi();
    }

}
