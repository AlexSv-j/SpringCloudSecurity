package com.svichkar.eureka.gateway.config;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator eurekaRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                                  .route(p -> p.path("/gateway/persons/**")
                                               .filters(f -> f.rewritePath("/gateway/persons/(?<remaining>.*)",
                                                                           "/${remaining}")
                                                              .addResponseHeader("My-Response-Time",
                                                                                 LocalDateTime.now().toString())
                                                              .circuitBreaker(config -> config.setName(
                                                                                                      "personCircuitBreaker")
                                                                                              .setFallbackUri(
                                                                                                      "forward:/contactSupport/failPersonsInfo"))
                                                       )
                                               .metadata(CONNECT_TIMEOUT_ATTR, 1000)
                                               .metadata(RESPONSE_TIMEOUT_ATTR, 5000)
                                               .uri("lb://PERSONS"))
                                  .route(p -> p.path("/gateway/accounts/**")
                                               .filters(f -> f.rewritePath("/gateway/accounts/(?<remaining>.*)",
                                                                           "/${remaining}")
                                                              .addResponseHeader("My-Response-Time",
                                                                                 LocalDateTime.now().toString())
                                                              .requestRateLimiter(config -> config.setRateLimiter(
                                                                      redisRateLimiter())
                                                                      .setKeyResolver(userkeyResolver())))
                                               .uri("lb://ACCOUNTS"))
                                  .route(p -> p.path("/gateway/notes/**")
                                               .filters(f -> f.rewritePath("/gateway/notes/(?<remaining>.*)",
                                                                           "/${remaining}")
                                                              .addResponseHeader("My-Response-Time",
                                                                                 LocalDateTime.now().toString())
//                                                              .circuitBreaker(config -> config.setName(
//                                                                                                      "notesCircuitBreaker")
//                                                                                              .setFallbackUri(
//                                                                                                      "forward:/contactSupport/failNotesInfo"))
//  retry in the gateway
                                                              .retry(retryConfig -> retryConfig.setRetries(3)
                                                                                               .setMethods(HttpMethod.GET)
                                                                                               .setBackoff(Duration.ofMillis(
                                                                                                                   100),
                                                                                                           Duration.ofMillis(
                                                                                                                   1000),
                                                                                                           1,
                                                                                                           true))
                                                       )
                                               .uri("lb://NOTES"))
                                  .build();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    KeyResolver userkeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                               .defaultIfEmpty("anonymous");
    }
}
