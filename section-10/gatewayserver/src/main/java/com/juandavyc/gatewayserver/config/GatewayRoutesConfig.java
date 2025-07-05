package com.juandavyc.gatewayserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class GatewayRoutesConfig {


    private final RedisRateLimiter redisRateLimiter;
    private final KeyResolver userKeyResolver;

    public GatewayRoutesConfig(RedisRateLimiter redisRateLimiter,KeyResolver userKeyResolver) {
        this.redisRateLimiter = redisRateLimiter;
        this.userKeyResolver = userKeyResolver;
    }

    @Bean
    public RouteLocator bankRouteLocator(
            RouteLocatorBuilder routeLocatorBuilder
    ) {
        System.out.println("redisRateLimiter: "+redisRateLimiter);

        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/bank/accounts/**")
                        .filters(f -> f
                                .rewritePath("/bank/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config ->config
                                        .setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                        )
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/bank/loans/**")
                        .filters(f -> f
                                .rewritePath("/bank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))

                        )
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/bank/cards/**")
                        .filters(f -> f
                                .rewritePath("/bank/cards/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Request-Time", LocalDateTime.now().toString())
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter)
                                        .setKeyResolver(userKeyResolver)
                                )
                        )
                        .uri("lb://CARDS"))
                .build();
    }

}
