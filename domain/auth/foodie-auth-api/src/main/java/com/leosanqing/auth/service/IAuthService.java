package com.leosanqing.auth.service;

import com.leosanqing.auth.service.pojo.Account;
import com.leosanqing.auth.service.pojo.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午7:36
 * @Package: com.leosanqing.springcloud
 * @Description: 服务接口
 * @Version: 1.0
 */
@FeignClient("foodie-auth-service")
@RequestMapping("auth-service")
public interface IAuthService {

    /**
     * 登录
     *
     * @param userId
     * @return
     */
    @PostMapping("token")
    AuthResponse tokenize(@RequestParam("userId") String userId);

    /**
     * 验证 token
     *
     * @param account
     * @return
     */
    @GetMapping("/verify")
    AuthResponse verify(@RequestBody Account account);

    /**
     * 刷新 token
     *
     * @param refresh
     * @return
     */
    @PostMapping("/refresh")
    AuthResponse refresh(@RequestParam("refresh") String refresh);

    /**
     * 退出
     *
     * @param account
     * @return
     */
    @DeleteMapping("delete")
    AuthResponse delete(@RequestBody Account account);
}
