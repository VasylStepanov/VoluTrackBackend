package com.application.registration.service;

import com.application.registration.dto.request.RegistrationRequest;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);

    String confirmToken(String token);

    @Transactional
    String resendConfirmToken(String token);
}
