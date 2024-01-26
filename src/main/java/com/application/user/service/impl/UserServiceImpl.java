package com.application.user.service.impl;

import com.application.user.dto.UserDto;
import com.application.user.model.User;
import com.application.user.repository.UserRepository;
import com.application.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User findById(UUID id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user, UserDto userDto){
        UserDto.updateUser(user, userDto);
    }

    @Override
    public void createUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email is already taken!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findUserByEmail(username);
    }
}
