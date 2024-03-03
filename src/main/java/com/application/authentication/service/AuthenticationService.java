package com.application.authentication.service;

import com.application.authentication.dto.request.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> authentication(AuthenticationRequest request);

    ResponseEntity<?> refreshAccessToken(String decryptedAccessToken);

}
