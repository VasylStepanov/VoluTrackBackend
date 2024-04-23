package com.application.authentication.controller;

import com.application.authentication.dto.AuthenticationRequest;
import com.application.authentication.dto.RequestUpdatePasswordDto;
import com.application.authentication.dto.RequestUpdateUserDataDto;
import com.application.authentication.service.AuthenticationService;
import com.application.security.service.JwtService;
import com.application.security.token.DisabledToken;
import com.application.security.token.DisabledTokenService;
import com.application.security.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authentication")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    DisabledTokenService disabledTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Operation(summary = "Create Access and Refresh tokens.",
            description = "Input email and password.")
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

    @Operation(summary = "Update user data", description = "Update user data: firstname, lastname.")
    @Transactional
    @PostMapping("/updateData")
    public ResponseEntity<?> updateData(HttpServletRequest httpServletRequest,
                                           @RequestBody RequestUpdateUserDataDto requestUpdateUserDataDto){
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        authenticationService.updateUserData(requestUpdateUserDataDto, jwtService.extractUserId(accessToken));
        return ResponseEntity.ok("User data is updated");
    }

    @Operation(summary = "Update user password", description = "To update user password, need to pass old password.")
    @Transactional
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(HttpServletRequest httpServletRequest,
                                           @RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto){
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        authenticationService.updateUserPassword(requestUpdatePasswordDto, jwtService.extractUserId(accessToken));
        return ResponseEntity.ok("User data is updated");
    }

    @Operation(summary = "Delete user account.")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserProfile(HttpServletRequest httpServletRequest){
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        authenticationService.deleteUser(jwtService.extractUserId(accessToken), jwtService.extractVolunteerId(accessToken));
        disabledTokenService.save(DisabledToken.builder().accessToken(accessToken).build());
        return ResponseEntity.ok("User is deleted");
    }

    @Operation(summary = "Logout.",
            description = "Removes cookies, Access token is placed to set of disabled tokens, Refresh token is removed from DB.")
    @PostMapping("/logout")
    public void logout(){
    }
}
