package com.learning.learnspringsecurity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class BasicAuthController {
    @RequestMapping("/basicAuth")
    @ResponseBody
    public String basicAuthEndpoint(Principal principal) {
        return "authenticated as: " + principal;
    }
}
