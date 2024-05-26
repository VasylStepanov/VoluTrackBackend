package com.application.user.service.impl;

import com.application.registration.dto.RegistrationRequest;
import com.application.user.dto.UserDto;
import com.application.user.model.Role;
import com.application.user.model.User;
import com.application.user.repository.RoleRepository;
import com.application.user.repository.UserRepository;
import com.application.user.service.UserService;
import com.application.user.validator.UserDataValidator;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDataValidator validator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email).get();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("User with this email doesn't exist");
        }
    }

    @Override
    @SneakyThrows
    public User findById(UUID id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("User with this id doesn't exist");
        }
    }

    @Override
    @SneakyThrows
    public void updateUser(UserDto userDto){
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        this.updateUser(user, userDto);
    }

    @Override
    @SneakyThrows
    public void updateUser(User user, UserDto userDto){
        UserDto.updateUser(user, userDto);
    }

    @Override
    @SneakyThrows
    public User createUser(RegistrationRequest registrationRequest) {
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role not found."));

        User user = User.builder()
                .firstName(validator.eitherFirstNameIsValidFull(registrationRequest.firstName()))
                .lastName(validator.eitherLastNameIsValidFull(registrationRequest.lastName()))
                .email(validator.eitherEmailIsValid(registrationRequest.email()))
                .phoneNumber(validator.eitherPhoneNumberIsValid(registrationRequest.phoneNumber()))
                .password(passwordEncoder.encode(validator.eitherPasswordIsValidFull(registrationRequest.password())))
                .locked(false)
                .enabled(false)
                .role(role)
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findUserByEmail(username);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
