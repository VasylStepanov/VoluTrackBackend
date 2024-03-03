package com.application.authentication.service.impl;

import com.application.security.service.JwtService;
import com.application.security.token.DisabledToken;
import com.application.security.token.DisabledTokenService;
import com.application.security.token.TokenRepository;
import com.application.security.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutService implements LogoutHandler {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    DisabledTokenService disabledTokenService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        if(request.getCookies() == null)
            throw new IllegalStateException("User isn't authenticated");
        String token = cookieUtil.getAccessTokenCookie(request.getCookies());
        if(token == null)
            throw new IllegalStateException("User isn't authenticated");
        UUID tokenId = jwtService.extractTokenId(token);
        disabledTokenService.save(DisabledToken.builder().accessToken(token).build());
        tokenRepository.removeById(tokenId);
    }
}

