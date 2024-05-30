package com.application.content.volunteers.volunteer.dto;

import com.application.content.volunteers.volunteer.model.Volunteer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VolunteerPrivateProfileDto {

    String firstName;

    String lastName;

    String phoneNumber;

    public static VolunteerPrivateProfileDto toVolunteerPrivateProfileDto(Volunteer volunteer) {
        if(volunteer == null)
            return null;
        return new VolunteerPrivateProfileDto(
                volunteer.getUser().getFirstName(),
                volunteer.getUser().getLastName(),
                volunteer.getUser().getPhoneNumber());
    }
}
