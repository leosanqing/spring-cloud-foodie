package com.leosanqing.springcloud.controller;

import com.leosanqing.springcloud.Account;
import com.leosanqing.springcloud.AuthResponse;
import com.leosanqing.springcloud.ResultCode;
import com.leosanqing.springcloud.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
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
                .username(username)
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
    public AuthResponse refresh(
            @RequestParam("refreshToken")
                    String refreshToken
    ) {

        Account account = (Account) redisTemplate.opsForValue().get(refreshToken);
        if (account == null) {
            return AuthResponse.builder().code(ResultCode.USER_NOT_FOUND).build();
        }


        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        redisTemplate.opsForValue().set(account.getRefreshToken(), account);

        redisTemplate.delete(refreshToken);

        return AuthResponse.builder()
                .account(account)
                .code(ResultCode.SUCCESS)
                .build();
    }

    @GetMapping("verify")
    @ResponseBody
    public AuthResponse verify(
            @RequestParam("token")
                    String token,
            @RequestParam("username")
                    String username
    ) {

        boolean verify = jwtService.verify(token, username);
        return AuthResponse.builder()
                .code(verify ? ResultCode.SUCCESS : ResultCode.INVALID_TOKEN)
                .build();
    }

}
