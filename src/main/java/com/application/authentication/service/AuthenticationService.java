package com.application.authentication.service;

import com.application.authentication.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> authentication(AuthenticationRequest request);

    ResponseEntity<?> refreshAccessToken(String decryptedAccessToken);

}
