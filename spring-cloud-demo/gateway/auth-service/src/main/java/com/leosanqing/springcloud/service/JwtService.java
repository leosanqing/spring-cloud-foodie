package com.leosanqing.springcloud.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leosanqing.springcloud.Account;
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

    private static final String USERNAME = "username";

    public String token(Account account) {

        Date now = new Date();

        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                .withIssuer(ISSUE)
                .withIssuedAt(now)
                .withClaim(USERNAME, account.getUsername())
                .withExpiresAt(new Date(now.getTime() + EXP_TIME))
                .sign(algorithm);

        log.info("jwt generated, username = {}", account.getUsername());

        return token;
    }

    public boolean verify(String token, String username) {
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUE)
                .withClaim(USERNAME, username)
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
