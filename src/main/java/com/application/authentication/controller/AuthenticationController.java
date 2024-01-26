package com.application.authentication.controller;

import com.application.authentication.dto.request.AuthenticationRequest;
import com.application.authentication.service.AuthenticationService;
import com.application.security.util.SecurityCipher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    SecurityCipher securityCipher;

    @PostMapping
    public ResponseEntity<?> authentication(@CookieValue(name = "accessToken", required = false) String accessToken,
                                            @RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.email(), request.password()
            ));
            authentication.getDetails();

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String decryptedAccessToken = accessToken == null ? null : securityCipher.decrypt(accessToken);

            return authenticationService.authentication(decryptedAccessToken, request);
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is banned!");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is locked!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password are wrong!");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(name = "accessToken", required = false) String accessToken) {
        if(accessToken == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token");
        String decryptedAccessToken = securityCipher.decrypt(accessToken);

        return authenticationService.refreshAccessToken(decryptedAccessToken);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody HttpServletRequest request){
        authenticationService.logout(request);
    }
}
