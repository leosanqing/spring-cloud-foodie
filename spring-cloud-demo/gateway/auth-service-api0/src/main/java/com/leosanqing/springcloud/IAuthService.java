package com.leosanqing.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午7:36
 * @Package: com.leosanqing.springcloud
 * @Description: 服务接口
 * @Version: 1.0
 */
@FeignClient("auth-service")
public interface IAuthService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    AuthResponse login(
            @RequestParam("username")
                    String username,
            @RequestParam("password")
                    String password
    );

    /**
     * 验证 token
     *
     * @param token
     * @param username
     * @return
     */
    @GetMapping("/verify")
    AuthResponse verify(
            @RequestParam("token")
                    String token,
            @RequestParam("username")
                    String username
    );

    /**
     * 刷新 token
     *
     * @param refresh
     * @return
     */
    @PostMapping("/refresh")
    @ResponseBody
    AuthResponse refresh(@RequestParam("refresh") String refresh);


}
