package com.svichkar.eureka.accounts.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class Account {

    private final Logger logger = LoggerFactory.getLogger(Account.class);

    @GetMapping("/getAccount")
    public String getAccount() {
        logger.debug("getAccount service start");

        return "Account from account service";
    }
}
