package com.application.volunteers.car.dto;


import com.application.volunteers.car.model.Car;
import com.application.volunteers.car.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCarDto {

    String carNumber;

    Integer carryingKg;

    CarType carType;

    public static ResponseCarDto toResponseCarDto(Car car){
        return new ResponseCarDto(
                car.getCarNumber(),
                car.getCarryingKg(),
                car.getCarType()
        );
    }
}
