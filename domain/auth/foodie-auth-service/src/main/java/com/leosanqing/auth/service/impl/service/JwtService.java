package com.leosanqing.auth.service.impl.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.leosanqing.auth.service.pojo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午7:55
 * @Package: com.leosanqing.springcloud.service
 * @Description: 鉴权服务实现
 * @Version: 1.0
 */
@Service
@Slf4j
public class JwtService {

    private static final String KEY = "just do IT";

    private static final String ISSUE = "leosanqing";

    private static final Long EXP_TIME = 60 * 1000L;

    private static final String USER_ID = "userId";

    public String token(Account account) {

        Date now = new Date();

        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                .withIssuer(ISSUE)
                .withIssuedAt(now)
                .withClaim(USER_ID, account.getUserId())
                .withExpiresAt(new Date(now.getTime() + EXP_TIME))
                .sign(algorithm);

        log.info("jwt generated, username = {}", account.getUserId());

        return token;
    }

    public boolean verify(String token, String userId) {
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUE)
                .withClaim(USER_ID, userId)
                .build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("token 验证失败");
            return false;
        }
    }

}
