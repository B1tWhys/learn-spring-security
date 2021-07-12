package com.learning.learnspringsecurity.controllers;

import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AADOauth2Controller {
    @RequestMapping("/fullOauth2")
    @ResponseBody
    public String fullOauth2Endpoint(Principal principal) {
        return "oauth 2 authenticated. principal: " + principal;
    }

    @RequestMapping("/fullOauth2/getToken")
    @ResponseBody
    public String getTokenEndpoint(@RegisteredOAuth2AuthorizedClient("oauthTest") OAuth2AuthorizedClient oauthClient) {
        return "oauth token: \n" + oauthClient.getAccessToken().getTokenValue();
    }
}
