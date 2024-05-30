package com.application.registration.service.impl;

import com.application.content.volunteers.volunteer.service.VolunteerService;
import com.application.registration.dto.RegistrationRequest;
import com.application.registration.email.EmailService;
import com.application.registration.exception.ConfirmationEmailException;
import com.application.registration.model.ConfirmationEmail;
import com.application.registration.service.ConfirmationEmailService;
import com.application.user.dto.UserDto;
import com.application.user.model.User;
import com.application.registration.service.RegistrationService;
import com.application.user.service.UserService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    ConfirmationEmailService confirmationEmailService;

    @Autowired
    VolunteerService volunteerService;

    @Override
    @SneakyThrows
    public String register(RegistrationRequest registrationRequest) {
        String password = signUpUser(registrationRequest);
        Map<String, Object> models = getModels(registrationRequest.firstName(),
                registrationRequest.lastName(),
                password);

        emailService.send(registrationRequest.email(), models);
        return "Send confirmation";
    }

    @Override
    public String confirmAccount(String password) {
        ConfirmationEmail confirmationEmail = confirmationEmailService.getConfirmationEmail(password);
        LocalDateTime expiresAt = confirmationEmail.getExpiresAt();

        if(LocalDateTime.now().isAfter(expiresAt))
            throw new ConfirmationEmailException("Token is expired.");

        confirmationEmail.setConfirmed(true);
        userService.updateUser(confirmationEmail.getUser(), UserDto.builder().enabled(true).build());

        return "Successfully signed up!";
    }

    @Override
    public String resendConfirmToken(String password){
        ConfirmationEmail confirmationEmail = confirmationEmailService.getConfirmationEmail(password);
        LocalDateTime expiresAt = confirmationEmail.getExpiresAt();

        if(LocalDateTime.now().isBefore(expiresAt))
            throw new ConfirmationEmailException("Token isn't expired.");
        else if(confirmationEmail.isConfirmed())
            throw new ConfirmationEmailException("Email is already confirmed.");

        String newPassword = getPassword();

        Map<String, Object> models = getModels(confirmationEmail.getUser().getFirstName(),
                confirmationEmail.getUser().getLastName(),
                newPassword);

        confirmationEmail.setPassword(newPassword);
        confirmationEmail.setCreatedAt(LocalDateTime.now());
        confirmationEmail.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        emailService.send(confirmationEmail.getUser().getEmail(), models);

        return "Resent confirmation";
    }

    @SneakyThrows
    private String signUpUser(RegistrationRequest registrationRequest) {

        User user = userService.createUser(registrationRequest);
        volunteerService.saveVolunteerProfile(user);

        String password = getPassword();

        ConfirmationEmail confirmation = ConfirmationEmail.builder()
                .password(password)
                .confirmed(false)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        confirmationEmailService.saveConfirmationEmail(confirmation);

        return password;
    }

    private Map<String, Object> getModels(String firstName, String lastName, String password) {
        return Map.of(
                "name", String.format("%s %s", firstName, lastName),
                "password", password);
    }

    private String getPassword(){
        return String.valueOf(Math.round(Math.random() * 899999 + 100000));
    }
}
