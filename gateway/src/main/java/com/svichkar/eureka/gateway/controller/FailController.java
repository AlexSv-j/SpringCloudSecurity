package com.svichkar.eureka.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contactSupport")
public class FailController {

    @RequestMapping("/failPersonsInfo")
    Mono<String> failPersonsInfo() {
        return Mono.just("Fail persons info");
    }

    @RequestMapping("/failNotesInfo")
    Mono<String> failNotesInfo() {
        return Mono.just("Fail notes info");
    }

}
