package com.application.authentication.service.impl;

import com.application.security.service.JwtService;
import com.application.authentication.service.AuthenticationService;
import com.application.authentication.dto.request.AuthenticationRequest;
import com.application.security.token.Token;
import com.application.security.token.TokenRepository;
import com.application.security.util.CookieUtil;
import com.application.user.model.User;
import com.application.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    CookieUtil cookieUtil;

    @Value("${security.refresh-token.expiration}")
    long refreshExpiration;

    @Override
    @Transactional
    public ResponseEntity<?> authentication(String decryptedAccessToken, AuthenticationRequest request) {
        User user = userService.findUserByEmail(request.email());

        Token token = user.getTokens().stream().filter(x -> x.getUser().getId().equals(user.getId())).findAny().orElse(null);
        boolean isRefreshTokenInvalid = token != null && !jwtService.isTokenValid(token.getRefreshToken());

        if(token == null) {
            String jwtRefresh = jwtService.generateRefreshToken(user);

            token = Token.builder()
                    .refreshToken(jwtRefresh)
                    .user(user)
                    .build();

            saveRefreshToken(token);
        }

        if(isRefreshTokenInvalid) {
            String jwtRefresh = jwtService.generateRefreshToken(user);
            token.setRefreshToken(jwtRefresh);
        }

        if(isRefreshTokenInvalid || !jwtService.isTokenValid(decryptedAccessToken)) {
            HttpHeaders httpHeaders = new HttpHeaders();
            String jwtAccess = jwtService.generateAccessToken(user, String.valueOf(token.getId()), user.getRole().getName());
            saveAccessToken(httpHeaders, jwtAccess);
            return ResponseEntity.ok().headers(httpHeaders).body("Successfully authenticated.");
        }

        return ResponseEntity.ok("User is already authenticated.");
    }

    @Override
    @Transactional
    public ResponseEntity<?> refreshAccessToken(String decryptedAccessToken) {
        boolean isAccessTokenValid = jwtService.isTokenValid(decryptedAccessToken);
        if(isAccessTokenValid)
            return ResponseEntity.ok("User is already authenticated.");

        UUID tokenId = jwtService.extractTokenId(decryptedAccessToken);

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User need to authenticate!");

        boolean isRefreshTokenValid = jwtService.isTokenValid(token.getRefreshToken());

        if(isRefreshTokenValid) {
            HttpHeaders httpHeaders = new HttpHeaders();
            String jwtAccess = jwtService.generateAccessToken(token.getUser(), String.valueOf(token.getId()), token.getUser().getRole().getName());
            saveAccessToken(httpHeaders, jwtAccess);
            return ResponseEntity.ok().headers(httpHeaders).body("Successfully refreshed.");
        } else {
            tokenRepository.removeByRefreshToken(token.getRefreshToken());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User should re-authenticate to the app!");
        }
    }

    @Override
    public void logout(HttpServletRequest request){
        String token = cookieUtil.getAccessTokenCookie(request.getCookies());
        if(token == null)
            throw new IllegalStateException("User isn't authenticated");
        UUID tokenId = jwtService.extractTokenId(token);
        tokenRepository.removeById(tokenId);
    }

    private void saveRefreshToken(Token token) {
        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void saveAccessToken(HttpHeaders httpHeaders, String token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token).toString());
    }
}
