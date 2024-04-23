package com.application.registration.service;

import com.application.registration.model.ConfirmationEmail;

public interface ConfirmationEmailService {
    void saveConfirmationEmail(ConfirmationEmail confirmationEmail);

    void deleteConfirmationEmail(String token);

    ConfirmationEmail getToken(String token);
}
