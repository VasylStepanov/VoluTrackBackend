package com.application.registration.service;

import com.application.registration.dto.RegistrationRequest;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);

    String confirmAccount(String token);

    String resendConfirmToken(String token);
}
