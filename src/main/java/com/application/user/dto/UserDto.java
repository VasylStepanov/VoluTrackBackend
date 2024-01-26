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

    public static UserDto toUserDto(User user) {
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

    public static void updateUser(User user, UserDto userDto){
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
    }
}
