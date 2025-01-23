package com.example.announcementspage.config;

//import com.example.announcementspage.services.impl.UserDetailsServiceImpl;

import com.example.announcementspage.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // Configure the security filter chain
//
//        http.cors().and().authorizeRequests(auth -> auth
//                        .requestMatchers(
//                                "/user/login",
//                                "/user/register",
//                                "/user/**",
//                                "/LoginPage",
//                                "/RegisterPage",
//                                "classpath:/static/",
//                                "classpath:/webapp/",
//                                "/css/**",
//                                "/js/**",
//                                "/images/**",
//                                "/webjars/**",
//                                "/static/**",
//                                "/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                ).formLogin(from -> from
//                .loginPage("/LoginPage")
//                .defaultSuccessUrl("/dashboard", true)
//                .permitAll());
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**", "/LoginPage", "/RegisterPage", "/user/register", "/login").permitAll()
                        .requestMatchers("/*.js", "/*.css", "/static/**","/js/**", "/resources/**", "/webapp/**").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/LoginPage")
                        .loginProcessingUrl("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler)
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/LoginPage").permitAll()
                        .deleteCookies("JSESSIONID")
                );

        // Włączenie CSRF w produkcji dla zwiększenia bezpieczeństwa
        // Na prod wymagane dodanie tokena na widoki <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
        //http.csrf(csrf -> csrf.disable());

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }

    @Bean
    @Primary
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }
}