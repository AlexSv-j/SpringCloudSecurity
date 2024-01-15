package com.svichkar.eureka.gateway.config;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class Util {

    public static String CORRELATION_ID = "CorrelationId";

    public static String getCorrelationId(final HttpHeaders requestHeaders) {
        final List<String> headers = requestHeaders.get(CORRELATION_ID);
        return Objects.nonNull(headers) ? headers.stream().findFirst().get() : null;
    }

    public static ServerWebExchange setCorellationId(ServerWebExchange exchange, String corelationIdValue) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(CORRELATION_ID, corelationIdValue).build())
                       .build();
    }
}
