package com.leosanqing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: rtliu
 * @Date: 2020/8/10 下午3:54
 * @Package: com.leosanqing.config
 * @Description: 路由配置
 * @Version: 1.0
 */
@Configuration
public class RoutesConfiguration {

    @Autowired
    private KeyResolver keyResolver;

    @Autowired
    @Qualifier("redisLimiterUser")
    private RedisRateLimiter redisLimiterUser;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        r -> r
                                .path(
                                        "/address/**",
                                        "/passport/**",
                                        "/userInfo/**",
                                        "/center/**"
                                )
                                .filters(f -> f.requestRateLimiter(
                                        config -> {
                                            config.setKeyResolver(keyResolver);
                                            config.setRateLimiter(redisLimiterUser);
                                        }
                                ))
                                .uri("lb://FOODIE-USER-SERVICE")
                )

                .route(
                        r -> r
                                .path(
                                        "/items/**"
                                )
                                .uri("lb://FOODIE-ITEM-SERVICE")
                )

                .route(
                        r -> r
                                .path(
                                        "/shopcart/**"
                                )
                                .uri("lb://FOODIE-CART-SERVICE")
                )

                .route(
                        r -> r
                                .path(
                                        "/search/**",
                                        "/index/**",
                                        "/item/es/search",
                                        "item/catItems"
                                )
                                .uri("lb://FOODIE-SEARCH-SERVICE")
                )
                .build();
    }
}
