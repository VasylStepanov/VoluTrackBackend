package com.application.registration.service;

import com.application.registration.model.ConfirmationEmail;

public interface ConfirmationEmailService {
    void saveConfirmationEmail(ConfirmationEmail confirmationEmail);

    void deleteConfirmationEmail(String password);

    ConfirmationEmail getConfirmationEmail(String password);
}
