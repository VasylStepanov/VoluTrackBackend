package com.application.security.service;

import com.application.security.token.DisabledTokenService;
import com.application.security.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${security.access-token.token-name}")
    String accessTokenName;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    DisabledTokenService disabledTokenService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = cookieUtil.getAccessTokenCookie(request.getCookies());
            if (disabledTokenService.isDisabled(jwt))
                throw new RuntimeException("Access token is disabled");
            if (jwtService.isTokenValid(jwt)) {
                Authentication authentication = jwtService.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
