package com.application.security.config;

import com.application.security.service.JwtAuthorizationFilter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    LogoutHandler logoutHandler;

    @Value("${security.access-token.token-name}")
    String accessTokenName;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**", "/swagger-ui/**", "/api/v*/test/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v*/authentication",
                                "/api/v*/registration/**"
                        ).anonymous()
                        .requestMatchers("/api/v*/authentication/**").authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v*/authentication/logout")
                        .addLogoutHandler(logoutHandler)
                        .deleteCookies(accessTokenName, "JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }
}