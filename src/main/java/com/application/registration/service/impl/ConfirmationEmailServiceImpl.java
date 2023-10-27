package com.application.registration.service.impl;

import com.application.registration.module.ConfirmationEmail;
import com.application.registration.repository.ConfirmationEmailRepository;
import com.application.registration.service.ConfirmationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationEmailServiceImpl implements ConfirmationEmailService {

    @Autowired
    private ConfirmationEmailRepository repository;

    @Override
    public void saveConfirmationEmail(ConfirmationEmail confirmationEmail) {
        repository.save(confirmationEmail);
    }

    @Override
    public void deleteConfirmationEmail(String token){
        repository.deleteByToken(token);
    }

    @Override
    public ConfirmationEmail getToken(String token) {
        return repository.findByToken(token).orElseThrow(() -> new IllegalStateException("Confirmation entity wasn't find."));
    }

    @Override
    public void setConfirmed(String token) {
        repository.updateConfirmationToken(token, true);
    }
}
