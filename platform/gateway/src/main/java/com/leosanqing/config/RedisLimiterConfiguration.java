package com.leosanqing.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * @Author: rtliu
 * @Date: 2020/8/10 下午4:40
 * @Package: com.leosanqing.config
 * @Description: Redis 限流配置
 * @Version: 1.0
 */
@Configuration
public class RedisLimiterConfiguration {

    @Bean
    @Primary
    public KeyResolver remoteAddressKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress()
        );
    }


    @Bean("redisLimiterUser")
    @Primary
    public RedisRateLimiter redisLimiterUser() {
        return new RedisRateLimiter(1, 20);
    }

    @Bean("redisLimiterItem")
    @Primary
    public RedisRateLimiter redisLimiterItem() {
        return new RedisRateLimiter(20, 50);
    }
}
