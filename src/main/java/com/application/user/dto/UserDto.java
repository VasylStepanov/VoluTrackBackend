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

    String fullName;

    String email;

    String password;

    Boolean locked;

    Boolean enabled;

    RoleDto role;

    public static UserDto toUserCache(User user) {
        return new UserDto(user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.isLocked(),
                user.isEnabled(),
                RoleDto.toRoleCache(user.getRole()));
    }

    public static User toUser(UserDto userDto) {
        if(userDto == null)
            return null;
        return User.builder()
                .id(userDto.getId())
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .locked(userDto.getLocked())
                .enabled(userDto.getEnabled())
                .role(RoleDto.toRole(userDto.getRole()))
                .build();
    }
}
