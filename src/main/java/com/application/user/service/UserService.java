package com.application.user.service;

import com.application.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    User findUserByEmail(String email);

    User findById(UUID id);

    void createUser(User user);
}
