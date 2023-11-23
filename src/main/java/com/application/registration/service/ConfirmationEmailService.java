package com.application.registration.service;

import com.application.registration.module.ConfirmationEmail;

public interface ConfirmationEmailService {
    void saveConfirmationEmail(ConfirmationEmail confirmationEmail);

    void deleteConfirmationEmail(String token);

    ConfirmationEmail getToken(String token);
}
