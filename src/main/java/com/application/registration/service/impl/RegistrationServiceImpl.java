package com.application.registration.service.impl;

import com.application.registration.dto.request.RegistrationRequest;
import com.application.registration.email.EmailService;
import com.application.registration.exception.ConfirmationEmailException;
import com.application.registration.exception.RegistrationException;
import com.application.registration.module.ConfirmationEmail;
import com.application.registration.service.ConfirmationEmailService;
import com.application.user.dto.UserDto;
import com.application.user.model.Role;
import com.application.user.model.User;
import com.application.registration.service.RegistrationService;
import com.application.user.repository.RoleRepository;
import com.application.user.service.UserService;
import com.application.user.validator.UserDataValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserService userService;

    @Autowired
    UserDataValidator validator;

    @Autowired
    EmailService emailService;

    @Autowired
    ConfirmationEmailService confirmationEmailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${sender.link}")
    String link;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        String token = signUpUser(registrationRequest);
        Map<String, Object> models = Map.of("name", registrationRequest.fullName(), "link", link + token);
        emailService.send(registrationRequest.email(), models);
        return "Send confirmation";
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationEmail confirmationEmail = confirmationEmailService.getToken(token);
        LocalDateTime expiresAt = confirmationEmail.getExpiresAt();

        if(LocalDateTime.now().isAfter(expiresAt))
            throw new ConfirmationEmailException("Token is expired.");

        User user = confirmationEmail.getUser();
        userService.updateUser(user, UserDto.builder().enabled(true).build());
        confirmationEmail.setConfirmed(true);

        return "Successfully signed up!";
    }

    @Override
    @Transactional
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

        Map<String, Object> models = Map.of("name", confirmationEmail.getUser().getFullName(), "link", link + token);
        emailService.send(confirmationEmail.getUser().getEmail(), models);

        return "Resent confirmation";
    }

    private String signUpUser(RegistrationRequest registrationRequest) {
        Role role = roleRepository
                .findByName(registrationRequest.role())
                .orElseThrow(() -> new IllegalStateException("Role not found."));
        User user = User.builder()
                .fullName(registrationRequest.fullName())
                .email(validator.eitherEmailIsValid(registrationRequest.email()))
                .password(passwordEncoder.encode(validator.eitherPasswordIsValid(registrationRequest.password())))
                .locked(false)
                .enabled(false)
                .role(role)
                .build();
        try {
            userService.createUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RegistrationException("Email is already taken!");
        }

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
