package com.leosanqing.springcloud.demo.hystrix;

import com.leosanqing.springcloud.demo.service.IService;
import com.leosanqing.springcloud.demo.service.impl.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 下午2:30
 * @Package: com.leosanqing.springcloud.demo.hystrix
 * @Description: 降级处理类
 * @Version: 1.0
 */
@Slf4j
@Component
public class FallBackImpl implements MyService {

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public String error() {
        log.info("进入降级处理函数");
        return "进入降级处理函数";
    }
}
