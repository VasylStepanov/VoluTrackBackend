package com.application.user.service;

import com.application.registration.dto.request.RegistrationRequest;
import com.application.user.dto.UserDto;
import com.application.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    User findUserByEmail(String email);

    User findById(UUID id);

    void updateUser(UserDto userDto);

    void updateUser(User user, UserDto userDto);

    void setRole(User user, String role);

    User createUser(RegistrationRequest registrationRequest);}
