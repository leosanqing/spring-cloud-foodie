package com.leosanqing.springcloud.demo.service.impl;

import com.leosanqing.springcloud.demo.hystrix.FallBackImpl;
import com.leosanqing.springcloud.demo.service.IService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: rtliu
 * @Date: 2020/6/9 下午2:28
 * @Package: com.leosanqing.springcloud.demo.service.impl
 * @Description:
 * @Version:
 */
@FeignClient(value = "feign-client", fallback = FallBackImpl.class)
public interface MyService extends IService {
}
