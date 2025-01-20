package com.example.announcementspage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure the security filter chain
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/**").permitAll() // Allow all API requests
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/LoginPage", "/LoginPage/**", "/register", "/register/**").permitAll()  // Allow access to login and register pages
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated();  // Any other request requires authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                ("/login", "/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/dashboard", true)
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true);
//    }
}
