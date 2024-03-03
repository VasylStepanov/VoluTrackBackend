package com.application.test;


import com.application.registration.dto.ConfirmationEmailDto;
import com.application.registration.module.ConfirmationEmail;
import com.application.registration.repository.ConfirmationEmailRepository;
import com.application.security.dto.TokenDto;
import com.application.security.token.Token;
import com.application.security.token.TokenRepository;
import com.application.user.dto.RoleDto;
import com.application.user.dto.UserDto;
import com.application.user.model.Role;
import com.application.user.model.User;
import com.application.user.repository.RoleRepository;
import com.application.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/test/user_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDataController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ConfirmationEmailRepository confirmationEmailRepository;


    @Operation(summary = "Get all roles.",
            description = "Return JSON file.")
    @GetMapping("/all_roles")
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return ResponseEntity.ok(roleRepository.findAll().stream().map(RoleDto::toRoleCache).collect(Collectors.toList()));
    }


    @Operation(summary = "Get all users.",
            description = "Return JSON file.")
    @GetMapping("/all_users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll().stream().map(UserDto::toUserDto).collect(Collectors.toList()));
    }


    @Operation(summary = "Get all refresh tokens.",
            description = "Return JSON file.")
    @GetMapping("/all_refresh_tokens")
    public ResponseEntity<List<TokenDto>> getAllTokens(){
        return ResponseEntity.ok(tokenRepository.findAll().stream().map(TokenDto::toTokenDto).collect(Collectors.toList()));
    }


    @Operation(summary = "Get all confirmation emails.",
            description = "Return JSON file.")
    @GetMapping("/all_confirmation_emails")
    public ResponseEntity<List<ConfirmationEmailDto>> getAllConfirmationEmails(){
        return ResponseEntity.ok(confirmationEmailRepository.findAll().stream().map(ConfirmationEmailDto::toConfirmationEmailDto).collect(Collectors.toList()));
    }
}
