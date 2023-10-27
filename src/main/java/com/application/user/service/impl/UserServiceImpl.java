package com.application.user.service.impl;

import com.application.user.model.User;
import com.application.user.repository.UserRepository;
import com.application.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(
                String.format("User '%s' not found!", email)
        ));
    }

    @Override
    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

}
