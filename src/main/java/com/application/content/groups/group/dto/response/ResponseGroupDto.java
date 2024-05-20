package com.application.content.groups.group.dto.response;

import com.application.content.groups.group.model.Group;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseGroupDto {

    UUID id;

    String name;

    String description;

    public static ResponseGroupDto toResponseGroupDto(Group group) {
        return new ResponseGroupDto(
                group.getId(),
                group.getName(),
                group.getDescription());
    }
}
