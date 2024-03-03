package com.application.user.service.impl;

import com.application.registration.dto.request.RegistrationRequest;
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
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public User findById(UUID id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e.getMessage());
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
        if(userDto.getFirstName() != null)
            user.setFirstName(userDto.getFirstName());
        if(userDto.getLastName() != null)
            user.setLastName(userDto.getLastName());
        if(userDto.getEmail() != null)
            user.setEmail(validator.eitherEmailIsValid(userDto.getEmail()));
        if(userDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(validator.eitherPasswordIsValid(userDto.getPassword())));
        if(userDto.getEnabled() != null)
            user.setEnabled(userDto.getEnabled());
        if(userDto.getLocked() != null)
            user.setLocked(userDto.getLocked());
    }

    @Override
    @SneakyThrows
    public void setRole(User user, String roleName) {
        if(!user.getRole().getName().equals("ROLE_USER"))
            throw new RuntimeException("Role is already set.");
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found."));
        user.setRole(role);
    }

    @Override
    @SneakyThrows
    public User createUser(RegistrationRequest registrationRequest) {
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role not found."));

        User user = User.builder()
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .email(validator.eitherEmailIsValid(registrationRequest.email()))
                .password(passwordEncoder.encode(validator.eitherPasswordIsValid(registrationRequest.password())))
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
}
