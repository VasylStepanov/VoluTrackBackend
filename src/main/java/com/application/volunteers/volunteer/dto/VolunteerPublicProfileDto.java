package com.application.volunteers.volunteer.dto;

import com.application.volunteers.address.dto.ResponsePublicAddressDto;
import com.application.volunteers.volunteer.model.Volunteer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerPublicProfileDto {
    String firstName;

    String lastName;

    String description;

    int helpCounter;

    LocalDateTime createdAt;

    ResponsePublicAddressDto responsePublicAddressDto;

    public static VolunteerPublicProfileDto setupVolunteerPublicProfileDto(Volunteer volunteer){
        return new VolunteerPublicProfileDto(
                volunteer.getUser().getFirstName(),
                volunteer.getUser().getLastName(),
                volunteer.getDescription(),
                volunteer.getHelpCounter(),
                volunteer.getCreatedAt(),
                ResponsePublicAddressDto.toResponsePublicAddressDto(volunteer.getAddress())
        );
    }
}
