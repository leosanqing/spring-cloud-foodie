package com.leosanqing.springcloud.demo.controller;

import com.leosanqing.springcloud.demo.service.RequestCacheService;
import com.leosanqing.springcloud.demo.service.impl.MyService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 下午2:34
 * @Package: com.leosanqing.springcloud.demo.controller
 * @Description: 降级处理接口
 * @Version: 1.0
 */
@RestController
public class HystrixController {
    @Autowired
    private MyService myService;

    @Autowired
    private RequestCacheService requestCacheService;


    @GetMapping("fallback")
    public String fallback() {
        return myService.error();
    }

    @GetMapping("cache")
    public String cache(String name) {
        @Cleanup HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String name1 = requestCacheService.returnName(name);
//        name += "!";
        name1 = requestCacheService.returnName(name);
        return name1;
    }
}
