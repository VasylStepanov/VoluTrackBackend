package com.application.user.service;

import com.application.registration.dto.RegistrationRequest;
import com.application.user.dto.UserDto;
import com.application.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    User findUserByEmail(String email);

    User findById(UUID id);

    void updateUser(UserDto userDto);

    void updateUser(User user, UserDto userDto);

    User createUser(RegistrationRequest registrationRequest);

    void deleteById(UUID id);
}
