package com.svichkar.eureka.gateway.config;

import static com.svichkar.eureka.gateway.config.Util.CORRELATION_ID;
import static com.svichkar.eureka.gateway.config.Util.getCorrelationId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

                if (!exchange.getResponse().getHeaders().containsKey(CORRELATION_ID)) {
                    String correlationId = getCorrelationId(requestHeaders);
                    logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                    exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
                }
            }));
        };
    }
}
