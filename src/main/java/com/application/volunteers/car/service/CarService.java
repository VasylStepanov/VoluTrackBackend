package com.application.volunteers.car.service;

import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.dto.ResponseAddressDto;
import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.dto.ResponseCarDto;
import com.application.volunteers.car.model.CarType;

import java.util.Set;
import java.util.UUID;

public interface CarService {

    ResponseCarDto findCarById(UUID volunteerId);

    ResponseCarDto findCarByEmail(String email);

    Set<CarType> findAllCarTypes();

    void saveCar(UUID volunteerId, RequestCarDto requestCarDto);

    void updateCar(UUID volunteerId, RequestCarDto requestCarDto);

    void deleteCar(UUID volunteerId);
}
