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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
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
    public ResponseEntity<?> authentication(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.email(), request.password()
            ));
            authentication.getDetails();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findUserByEmail(request.email());
            Token token = new Token();

            String jwtRefresh = jwtService.generateRefreshToken(user);
            token.setRefreshToken(jwtRefresh);
            token.setUser(user);
            saveRefreshToken(token);

            HttpHeaders httpHeaders = new HttpHeaders();
            String jwtAccess = jwtService.generateAccessToken(user, String.valueOf(user.getId()), String.valueOf(token.getId()), user.getRole().getName());
            saveAccessToken(httpHeaders, jwtAccess);
            return ResponseEntity.ok().headers(httpHeaders).body("Successfully authenticated.");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is banned!");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is locked!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password are wrong!");
        }
    }

    @Override
    public ResponseEntity<?> refreshAccessToken(String accessToken) {
        boolean isAccessTokenValid = jwtService.isTokenValid(accessToken);
        if (isAccessTokenValid)
            return ResponseEntity.ok("User is already authenticated.");

        UUID tokenId = jwtService.extractTokenId(accessToken);

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User need to authenticate!");

        boolean isRefreshTokenValid = jwtService.isTokenValid(token.getRefreshToken());

        if (isRefreshTokenValid) {
            HttpHeaders httpHeaders = new HttpHeaders();
            String jwtAccess = jwtService.generateAccessToken(token.getUser(), String.valueOf(token.getUser().getId()), String.valueOf(token.getId()), token.getUser().getRole().getName());
            saveAccessToken(httpHeaders, jwtAccess);
            return ResponseEntity.ok().headers(httpHeaders).body("Successfully refreshed.");
        } else {
            tokenRepository.removeByRefreshToken(token.getRefreshToken());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User should re-authenticate to the app!");
        }
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
