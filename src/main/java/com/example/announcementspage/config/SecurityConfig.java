package com.example.announcementspage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure the security filter chain
        http.csrf().disable();
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user/login",
                                "/user/register",
                                "/user/**",
                                "classpath:/static/",
                                "classpath:/webapp/",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/static/**",
                                "/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                ).formLogin(from -> from
                .loginPage("/LoginPage")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll());

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