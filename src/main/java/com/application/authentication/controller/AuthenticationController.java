package com.application.authentication.controller;

import com.application.authentication.dto.request.AuthenticationRequest;
import com.application.authentication.service.AuthenticationService;
import com.application.security.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/authentication")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CookieUtil cookieUtil;

    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest request) {
        return authenticationService.authentication(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest httpServletRequest) {
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return authenticationService.refreshAccessToken(accessToken);
    }

    @PostMapping("/logout")
    public void logout(){
    }
}
