package com.application.volunteers.volunteer.dto;

import com.application.volunteers.volunteer.model.Volunteer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerDto {

    UUID id;

    int helpCounter;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    UUID userId;

    public static VolunteerDto toVolunteerDto(Volunteer volunteer){
        return new VolunteerDto(volunteer.getId(),
                volunteer.getHelpCounter(),
                volunteer.getCreatedAt(),
                volunteer.getUpdatedAt(),
                volunteer.getUser().getId());
    }
}
