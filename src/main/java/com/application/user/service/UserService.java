package com.application.user.service;

import com.application.user.model.User;

import java.util.UUID;

public interface UserService {

    User findUserByEmail(String email);

    User findById(UUID id);

    void createUser(User user);
}
