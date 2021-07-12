package com.learning.learnspringsecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UnauthedController {
    @RequestMapping("/unauthed")
    public ResponseEntity<String> testUnauthedEndpoint(Principal principal) {
        return ResponseEntity.ok("Unauthed request has principal: " + principal);
    }
}
