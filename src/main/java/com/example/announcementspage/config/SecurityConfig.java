package com.example.announcementspage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure the security filter chain
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/**").permitAll() // Allow all API requests
                .requestMatchers("/login", "/login/**", "/register", "/register/**").permitAll()  // Allow access to login and register pages
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated();  // Any other request requires authentication

        return http.build();
    }
}
