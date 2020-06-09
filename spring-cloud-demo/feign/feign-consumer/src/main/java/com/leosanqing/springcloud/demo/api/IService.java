package com.leosanqing.springcloud.demo.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/5 下午2:20
 * @Package: com.leosanqing.springcloud.demo.api
 * @Description: 接口层
 * @Version: 1.0
 */
@FeignClient("eureka-client")
public interface IService {

    /**
     * 调用sayHi方法
     *
     * @return
     */
    @GetMapping("say_hi")
    String sayHi();
}
