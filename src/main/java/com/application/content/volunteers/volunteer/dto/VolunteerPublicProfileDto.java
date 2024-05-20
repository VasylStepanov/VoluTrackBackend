package com.application.content.volunteers.volunteer.dto;

import com.application.content.address.dto.ResponsePublicAddressDto;
import com.application.content.volunteers.volunteer.model.Volunteer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerPublicProfileDto {
    String firstName;

    String lastName;

    LocalDateTime createdAt;

    ResponsePublicAddressDto responsePublicAddressDto;

    public static VolunteerPublicProfileDto setupVolunteerPublicProfileDto(Volunteer volunteer){
        return new VolunteerPublicProfileDto(
                volunteer.getUser().getFirstName(),
                volunteer.getUser().getLastName(),
                volunteer.getCreatedAt(),
                ResponsePublicAddressDto.toResponsePublicAddressDto(volunteer.getAddress())
        );
    }
}
