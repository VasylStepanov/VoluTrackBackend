package com.application.content.volunteers.volunteer.dto;

import com.application.content.general.address.dto.ResponsePrivateAddressDto;
import com.application.content.volunteers.car.dto.ResponseCarDto;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VolunteerProfileDto {

    String firstName;

    String lastName;

    String email;

    LocalDateTime createdAt;

    ResponsePrivateAddressDto responsePrivateAddressDto;

    List<ResponseCarDto> responseCarDtoList;

    public static VolunteerProfileDto setupVolunteerProfileDto(Volunteer volunteer){
        return new VolunteerProfileDto(
                volunteer.getUser().getFirstName(),
                volunteer.getUser().getLastName(),
                volunteer.getUser().getEmail(),
                volunteer.getCreatedAt(),
                ResponsePrivateAddressDto.toResponseAddressDto(volunteer.getAddress()),
                volunteer.getCarList().stream().map(ResponseCarDto::toResponseCarDto).toList()
        );
    }
}
