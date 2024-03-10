package com.application.authentication.controller;

import com.application.authentication.dto.AuthenticationRequest;
import com.application.authentication.service.AuthenticationService;
import com.application.security.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Create Access and Refresh tokens.",
            description = "Input email and password. Authenticate as a user test: email = testemail@gmail.com, password = Password_123")
    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest request) {
        return authenticationService.authentication(request);
    }


    @Operation(summary = "Refresh Access token.",
            description = "Access token is taken from http-only cookie, then replaced by the new one.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest httpServletRequest) {
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return authenticationService.refreshAccessToken(accessToken);
    }


    @Operation(summary = "Logout.",
            description = "Removes cookies, Access token is placed to set of disabled tokens, Refresh token is removed from DB.")
    @PostMapping("/logout")
    public void logout(){
    }
}
