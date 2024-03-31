package com.application.volunteers.car.service;

import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.model.Car;
import com.application.volunteers.car.model.CarType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

public interface CarService {


    Car getCar(UUID carId);

    Set<CarType> getAllCarTypes();

    void saveCar(UUID volunteerId, RequestCarDto requestCarDto);

    @Transactional
    void updateCar(UUID volunteerId, UUID carId, RequestCarDto requestCarDto);

    @Transactional
    void deleteCar(UUID volunteerId, UUID carId);

    Car eitherIsCarOwner(UUID volunteerId, UUID carId) throws RuntimeException;
}
