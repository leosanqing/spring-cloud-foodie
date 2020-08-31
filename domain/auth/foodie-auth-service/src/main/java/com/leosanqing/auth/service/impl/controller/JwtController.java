package com.leosanqing.auth.service.impl.controller;


import com.leosanqing.auth.service.impl.service.AuthServiceImpl;
import com.leosanqing.auth.service.impl.service.JwtService;
import com.leosanqing.auth.service.pojo.Account;
import com.leosanqing.auth.service.pojo.AuthResponse;
import com.leosanqing.auth.service.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author: rtliu
 * @Date: 2020/8/14 上午10:37
 * @Package: com.leosanqing.springcloud.controller
 * @Description: 鉴权
 * @Version: 1.0
 */
@RestController
@RequestMapping("jwt")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    AuthServiceImpl authService;

    @PostMapping("login")
    @ResponseBody
    public AuthResponse login(
            @RequestParam("username")
                    String username,
            @RequestParam("password")
                    String password
    ) {
        Account account = Account
                .builder()
                .userId(username)
                .build();

        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        redisTemplate.opsForValue().set(account.getRefreshToken(), account);


        return AuthResponse.builder()
                .account(account)
                .code(ResultCode.SUCCESS)
                .build();
    }

    @PostMapping("refresh")
    @ResponseBody
    public AuthResponse refresh(@RequestParam("refreshToken") String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @GetMapping("verify")
    @ResponseBody
    public AuthResponse verify(@RequestBody Account account) {
        return authService.verify(account);
    }

}
