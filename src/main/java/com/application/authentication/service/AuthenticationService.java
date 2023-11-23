package com.application.authentication.service;

import com.application.authentication.dto.request.AuthenticationRequest;
import com.application.authentication.dto.request.RefreshRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> authentication(String decryptedAccessToken,
                                     AuthenticationRequest request);

    ResponseEntity<?> refreshAccessToken(String decryptedAccessToken,
                                   RefreshRequest request);
}
