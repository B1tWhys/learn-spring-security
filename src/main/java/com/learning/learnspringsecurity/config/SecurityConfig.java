package com.learning.learnspringsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig {
    @Configuration
    @EnableWebSecurity
    @Slf4j
    @Order(0)
    public static class UnauthedSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers("/unauthed", "/login")
                    .and()
                    .authorizeRequests(authorize -> authorize.anyRequest().permitAll());
        }
    }

    @Configuration
    @EnableWebSecurity
    @Slf4j
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
}
