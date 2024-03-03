package com.application.user.dto;

import com.application.user.model.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {

    int id;

    String name;

    public static RoleDto toRoleCache(Role role){
        return new RoleDto(role.getId(),
                role.getName());
    }

    public static Role toRole(RoleDto role){
        if(role == null)
            return null;
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
