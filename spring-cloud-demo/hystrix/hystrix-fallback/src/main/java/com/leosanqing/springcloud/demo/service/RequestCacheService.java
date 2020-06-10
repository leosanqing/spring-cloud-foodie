package com.leosanqing.springcloud.demo.service;

import com.leosanqing.springcloud.demo.service.impl.MyService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 下午4:31
 * @Package: com.leosanqing.springcloud.demo.service
 * @Description: 请求缓存服务
 * @Version: 1.0
 */
@Service
@Slf4j
public class RequestCacheService {
    @Autowired
    private MyService myService;


    @CacheResult
    @HystrixCommand(commandKey = "commandKey")
    public String returnName(@CacheKey String name) {
        log.info("你是 name =>" + name);
        return myService.sayHi() + name;
    }


}
