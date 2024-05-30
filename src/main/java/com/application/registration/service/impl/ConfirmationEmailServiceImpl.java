package com.application.registration.service.impl;

import com.application.registration.model.ConfirmationEmail;
import com.application.registration.repository.ConfirmationEmailRepository;
import com.application.registration.service.ConfirmationEmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationEmailServiceImpl implements ConfirmationEmailService {

    @Autowired
    ConfirmationEmailRepository repository;

    @Override
    public void saveConfirmationEmail(ConfirmationEmail confirmationEmail) {
        repository.save(confirmationEmail);
    }

    @Override
    public void deleteConfirmationEmail(String password){
        repository.deleteByPassword(password);
    }

    @Override
    public ConfirmationEmail getConfirmationEmail(String password) {
        return repository.findByPassword(password).orElseThrow(() -> new IllegalStateException("Confirmation entity wasn't find."));
    }

}
