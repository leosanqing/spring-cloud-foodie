package com.leosanqing.springcloud.demo.service.impl;

import com.leosanqing.springcloud.demo.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 上午10:44
 * @Package: com.leosanqing.springcloud.demo.service.impl
 * @Description: feign-client 接口实现
 * @Version: 1.0
 */
@RestController
@Slf4j
public class FeignServiceImpl implements IService {

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHi() {
        return "This is " + port;
    }

    @Override
    public String error() {
        throw new RuntimeException("发生异常了");
    }
}
