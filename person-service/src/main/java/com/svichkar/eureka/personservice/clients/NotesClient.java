package com.svichkar.eureka.personservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notes", path = "/notes"
//        , fallback = NotesFallBack.class
)
public interface NotesClient {

    @GetMapping("/getNotes")
//    @Retry(name = "noteservice", fallbackMethod = "fallBackResponse")
    @CircuitBreaker(name = "noteservice", fallbackMethod = "circuitBreakerResponse")
    public String getNotes(@RequestHeader("CorrelationId") String correlationId);

    default String fallBackResponse(Exception e) {
        return "fallBackResponse is working";
    }

    default String circuitBreakerResponse(Exception e) {
        return "CircuitBreaker";
    }
}
