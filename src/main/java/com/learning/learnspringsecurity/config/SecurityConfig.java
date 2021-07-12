package com.learning.learnspringsecurity.config;

import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig {
    @Configuration
    @EnableWebSecurity
    @Order(0)
    public static class UnauthedSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers("/unauthed")
                    .and()
                    .authorizeRequests(authorize -> authorize.anyRequest().permitAll());
        }
    }

    @Configuration
    @EnableWebSecurity
    @Order(1)
    public static class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public UserDetailsService basicAuthUserDetailsService() {
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
            manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER", "ADMIN").build());
            return manager;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/basicAuth")
                    .authorizeRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
            .httpBasic(Customizer.withDefaults());
        }
    }

    @Configuration
    @EnableWebSecurity
    @Order(2)
    public static class FullOauth2SecurityConfig extends AADWebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/fullOauth2", "/fullOauth2/**", "/oauth2/**", "/login/**")
                    .and();
            super.configure(http);
            http.authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }
    }

    @Configuration
    @EnableWebSecurity
    @Order(3)
    public static class JwtVerificationFlow extends AADResourceServerWebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/jwtAuthentication", "/jwtAuthentication/**")
                    .and();
            super.configure(http);
            http.authorizeRequests()
                    .anyRequest().authenticated();
//                    .hasRole("testscope.read")
        }
    }

//    @Configuration
//    @EnableWebSecurity
//    public static class DefaultUnauthedConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests().anyRequest().permitAll();
//        }
//    }

}
