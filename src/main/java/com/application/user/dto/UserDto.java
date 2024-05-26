package com.application.user.dto;

import com.application.user.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID id;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String password;

    Boolean locked;

    Boolean enabled;

    RoleDto role;

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.isLocked(),
                user.isEnabled(),
                RoleDto.toRoleDto(user.getRole()));
    }

    public static User toUser(UserDto userDto) {
        if(userDto == null)
            return null;
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .locked(userDto.getLocked())
                .enabled(userDto.getEnabled())
                .role(RoleDto.toRole(userDto.getRole()))
                .build();
    }

    public static void updateUser(User user, UserDto userDto){
        if(userDto.getFirstName() != null)
            user.setFirstName(userDto.getFirstName());
        if(userDto.getLastName() != null)
            user.setLastName(userDto.getLastName());
        if(userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if(userDto.getPhoneNumber() != null)
            user.setPhoneNumber(userDto.getPhoneNumber());
        if(userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());
        if(userDto.getEnabled() != null)
            user.setEnabled(userDto.getEnabled());
        if(userDto.getLocked() != null)
            user.setLocked(userDto.getLocked());
    }
}
