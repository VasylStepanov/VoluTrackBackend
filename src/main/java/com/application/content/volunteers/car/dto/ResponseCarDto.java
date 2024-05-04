package com.application.content.volunteers.car.dto;

import com.application.content.volunteers.car.model.Car;
import com.application.content.volunteers.car.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResponseCarDto {

    UUID id;

    String number;

    String description;

    Integer carryingKg;

    CarType type;

    public static ResponseCarDto toResponseCarDto(Car car){
        return new ResponseCarDto(
                car.getId(),
                car.getNumber(),
                car.getDescription(),
                car.getCarryingKg(),
                car.getCarType()
        );
    }
}
