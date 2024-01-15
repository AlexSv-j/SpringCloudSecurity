package com.svichkar.eureka.gateway.config;

import static com.svichkar.eureka.gateway.config.Util.getCorrelationId;
import static com.svichkar.eureka.gateway.config.Util.setCorellationId;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class RequestTraceFilter implements GlobalFilter {

    private static Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final HttpHeaders headers = exchange.getRequest().getHeaders();
        String resultHeaderValue = getCorrelationId(headers);
        if (Objects.isNull(resultHeaderValue)) {
            resultHeaderValue = UUID.randomUUID().toString();
            setCorellationId(exchange, resultHeaderValue);
            logger.debug("CorelationId is generated - {}", resultHeaderValue);
        } else {
            logger.debug("CorelationId is found in the request - {}", resultHeaderValue);
        }
        return chain.filter(exchange);
    }
}
