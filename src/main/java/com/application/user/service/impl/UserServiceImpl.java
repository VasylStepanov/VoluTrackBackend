package com.application.user.service.impl;

import com.application.user.cache.UserDataCache;
import com.application.user.dto.UserDto;
import com.application.user.model.User;
import com.application.user.repository.UserRepository;
import com.application.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDataCache userDataCache;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        userDataCache.setUserToCache(user);
        return user;
    }

    @Override
    public void updateUser(User user, UserDto userDto){

        if(userDto.getFullName() != null)
            user.setFullName(userDto.getFullName());
        if(userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if(userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());
        if(userDto.getEnabled() != null)
            user.setEnabled(userDto.getEnabled());
        if(userDto.getLocked() != null)
            user.setLocked(userDto.getLocked());

        userDataCache.setUserToCache(user);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
        userDataCache.setUserToCache(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get user from cache if present, otherwise get from DB
        User user = userDataCache.getUserFromCacheByEmail(username);
        return user == null ? findUserByEmail(username) : user;
    }
}
