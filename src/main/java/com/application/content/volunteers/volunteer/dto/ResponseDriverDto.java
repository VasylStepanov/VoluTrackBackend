package com.application.content.volunteers.volunteer.dto;

import com.application.content.volunteers.car.dto.ResponseCarDto;
import com.application.content.volunteers.car.model.Car;
import com.application.content.volunteers.volunteer.model.Volunteer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDriverDto {

    VolunteerPrivateProfileDto privateProfileDto;

    ResponseCarDto responseCarDto;

    public static ResponseDriverDto toResponseDriverDto(Volunteer volunteer, Car car) {
        return new ResponseDriverDto(
                VolunteerPrivateProfileDto.toVolunteerPrivateProfileDto(volunteer),
                ResponseCarDto.toResponseCarDto(car));
    }
}
