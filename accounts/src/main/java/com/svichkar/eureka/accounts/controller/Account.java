package com.svichkar.eureka.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class Account {

    @GetMapping("/getAccount")
    public String getAccount() {
        return "Account from account service";
    }
}
