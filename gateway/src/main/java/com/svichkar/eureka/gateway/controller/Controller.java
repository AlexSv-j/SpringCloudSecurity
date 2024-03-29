package com.svichkar.eureka.gateway.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class Controller {

    @GetMapping("/")
    public String getPrincipal(Principal principal) {
        return principal.getName();
    }
}
