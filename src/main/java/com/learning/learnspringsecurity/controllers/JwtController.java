package com.learning.learnspringsecurity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class JwtController {
    @RequestMapping("/jwtAuthentication")
    @ResponseBody
    public String bearerTokenEndpoint(Principal principal) {
        return "Principal: " + principal;
    }
}
