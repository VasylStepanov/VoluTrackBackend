package com.application.volunteers.car.dto;

import com.application.volunteers.car.model.Car;
import com.application.volunteers.car.model.CarType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDto {

    UUID id;

    String car_number;

    Integer carrying_kg;

    CarType car_type;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    UUID volunteerId;

    public static CarDto toCarDto(Car car){
        return new CarDto(car.getId(),
                car.getCarNumber(),
                car.getCarryingKg(),
                car.getCarType(),
                car.getCreatedAt(),
                car.getUpdatedAt(),
                car.getVolunteer().getId());
    }
}
