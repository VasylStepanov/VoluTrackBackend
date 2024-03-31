package com.application.authentication.service;

import com.application.authentication.dto.AuthenticationRequest;
import com.application.authentication.dto.RequestUpdatePasswordDto;
import com.application.authentication.dto.RequestUpdateUserDataDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthenticationService {

    ResponseEntity<?> authentication(AuthenticationRequest request);

    ResponseEntity<?> refreshAccessToken(String decryptedAccessToken);

    void updateUserData(RequestUpdateUserDataDto requestUpdateUserDataDto, UUID volunteerId);

    void updateUserPassword(RequestUpdatePasswordDto requestUpdatePasswordDto, UUID volunteerId);

    void deleteUser(UUID userId, UUID volunteerId);
}
