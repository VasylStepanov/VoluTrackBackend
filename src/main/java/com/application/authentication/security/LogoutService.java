package com.application.authentication.security;

import com.application.authentication.token.Token;
import com.application.authentication.token.TokenRepository;
import com.application.authentication.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutService implements LogoutHandler {

    @Autowired
    TokenRepository repository;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String token = cookieUtil.getAccessTokenCookie(request.getCookies());
        if(token == null)
            throw new IllegalStateException("User isn't authenticated");
        UUID tokenId = jwtService.extractTokenId(token);
        repository.removeById(tokenId);
    }
}
