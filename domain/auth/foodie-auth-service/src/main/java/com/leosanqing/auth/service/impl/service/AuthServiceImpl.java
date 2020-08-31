package com.leosanqing.auth.service.impl.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Strings;
import com.leosanqing.auth.service.IAuthService;
import com.leosanqing.auth.service.pojo.Account;
import com.leosanqing.auth.service.pojo.AuthResponse;
import com.leosanqing.auth.service.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.UUID;

import static com.leosanqing.auth.service.pojo.AuthEnum.SUCCESS;
import static com.leosanqing.auth.service.pojo.AuthEnum.USER_NOT_FOUND;

/**
 * @Author: rtliu
 * @Date: 2020/8/31 上午11:38
 * @Package: com.leosanqing.auth.service.impl.service
 * @Description: 鉴权服务实现
 * @Version: 1.0
 */
public class AuthServiceImpl implements IAuthService {

    private static final String KEY = "just do IT";

    private static final String ISSUE = "leosanqing";

    private static final Long EXP_TIME = 60 * 1000L;

    private static final String USERNAME = "username";
    private static final String USER_TOKEN = "userToken";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse tokenize(String userId) {
        Account account = Account
                .builder()
                .userId(userId)
                .build();

        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        redisTemplate.opsForValue().set(account.getRefreshToken(), userId);
        redisTemplate.opsForValue().set(USER_TOKEN + userId, account);


        return AuthResponse.builder()
                .account(account)
                .code(ResultCode.SUCCESS)
                .build();
    }

    @Override
    public AuthResponse verify(Account account) {
        boolean success = jwtService.verify(account.getToken(), account.getUserId());

        return AuthResponse.builder()
                .code(success ? SUCCESS.getCode() : USER_NOT_FOUND.getCode())
                .build();

    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        String userId = (String) redisTemplate.opsForValue().get(refreshToken);
        if (Strings.isNullOrEmpty(userId)) {
            return AuthResponse.builder()
                    .code(USER_NOT_FOUND.getCode())
                    .build();
        }
        redisTemplate.delete(refreshToken);

        return tokenize(userId);
    }

    @Override
    public AuthResponse delete(Account account) {
        AuthResponse verify = verify(account);
        AuthResponse req = new AuthResponse();

        if (!verify.getCode().equals(SUCCESS.getCode())) {
            req.setCode(USER_NOT_FOUND.getCode());
            return req;
        }

        redisTemplate.delete(account.getRefreshToken());
        redisTemplate.delete(USER_TOKEN + account.getUserId());
        req.setCode(SUCCESS.getCode());
        return req;

    }
}
