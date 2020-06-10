package com.leosanqing.springcloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: rtliu
 * @Date: 2020/6/5 下午2:20
 * @Package: com.leosanqing.springcloud.demo.api
 * @Description: 接口层
 * @Version: 1.0
 */
@FeignClient(value = "feign-client")
public interface IService {

    /**
     * 调用sayHi方法
     *
     * @return
     */
    @GetMapping("say_hi")
    String sayHi();

    /**
     * 调用异常方法 抛出异常
     *
     * @return
     */
    @GetMapping("/error")
    String error();

}
