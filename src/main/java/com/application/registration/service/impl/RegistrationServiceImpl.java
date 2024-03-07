package com.application.registration.service.impl;

import com.application.registration.dto.request.RegistrationRequest;
import com.application.registration.email.EmailService;
import com.application.registration.exception.ConfirmationEmailException;
import com.application.registration.module.ConfirmationEmail;
import com.application.registration.service.ConfirmationEmailService;
import com.application.user.dto.UserDto;
import com.application.user.model.User;
import com.application.registration.service.RegistrationService;
import com.application.user.service.UserService;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sender.link}")
    String link;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        String token = signUpUser(registrationRequest);
        Map<String, Object> models = Map.of(
                "name", String.format("%s %s", registrationRequest.firstName(), registrationRequest.lastName()),
                "link", link + token);
        emailService.send(registrationRequest.email(), models);
        return "Send confirmation";
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationEmail confirmationEmail = confirmationEmailService.getToken(token);
        LocalDateTime expiresAt = confirmationEmail.getExpiresAt();

        if(LocalDateTime.now().isAfter(expiresAt))
            throw new ConfirmationEmailException("Token is expired.");

        confirmationEmail.setConfirmed(true);
        userService.updateUser(confirmationEmail.getUser(), UserDto.builder().enabled(true).build());

        return "Successfully signed up!";
    }

    @Override
    public String resendConfirmToken(String token){
        ConfirmationEmail confirmationEmail = confirmationEmailService.getToken(token);
        LocalDateTime expiresAt = confirmationEmail.getExpiresAt();

        if(LocalDateTime.now().isBefore(expiresAt))
            throw new ConfirmationEmailException("Token isn't expired.");
        else if(confirmationEmail.isConfirmed())
            throw new ConfirmationEmailException("Email is already confirmed.");

        String newToken = UUID.randomUUID().toString();

        confirmationEmail.setToken(newToken);
        confirmationEmail.setCreatedAt(LocalDateTime.now());
        confirmationEmail.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        Map<String, Object> models = Map.of(
                "name", String.format("%s %s", confirmationEmail.getUser().getFirstName(), confirmationEmail.getUser().getFirstName()),
                "link", link + newToken);
        emailService.send(confirmationEmail.getUser().getEmail(), models);

        return "Resent confirmation";
    }

    private String signUpUser(RegistrationRequest registrationRequest) {

        User user = userService.createUser(registrationRequest);
        volunteerService.saveVolunteerProfile(user);

        String token = UUID.randomUUID().toString();

        ConfirmationEmail confirmation = ConfirmationEmail.builder()
                .token(token)
                .confirmed(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        confirmationEmailService.saveConfirmationEmail(confirmation);

        return token;
    }
}
