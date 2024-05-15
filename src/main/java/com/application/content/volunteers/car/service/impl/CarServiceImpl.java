package com.application.content.volunteers.car.service.impl;

import com.application.content.volunteers.car.dto.RequestCarDto;
import com.application.content.volunteers.car.model.Car;
import com.application.content.volunteers.car.model.CarType;
import com.application.content.volunteers.car.repository.CarRepository;
import com.application.content.volunteers.car.service.CarService;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarValidation carValidation;

    @Autowired
    VolunteerService volunteerService;

    @Override
    public Car getCar(UUID carId) {
        return carRepository.findById(carId).orElseThrow(() -> new RuntimeException("User isn't an owner of the car"));
    }

    @Override
    public Set<CarType> getAllCarTypes() {
        return Set.of(CarType.values());
    }

    @Override
    public void saveCar(UUID volunteerId, RequestCarDto requestCarDto) {
         carRepository.save(Car.builder()
                .number(carValidation.eitherNumberIsValidFull(requestCarDto.carNumber()))
                .description(carValidation.eitherDescriptionIsValidFull(requestCarDto.description()))
                .carType(CarType.valueOfCheckedFull(requestCarDto.carType()))
                .carryingKg(carValidation.eitherCarryingKgIsValidFull(requestCarDto.carryingKg()))
                .volunteer(volunteerService.getVolunteer(volunteerId))
            .build());
    }

    @Override
    @Transactional
    public void updateCar(UUID volunteerId, UUID carId, RequestCarDto requestCarDto) {
        Car car = eitherIsCarOwner(volunteerId, carId);
        car.isUpdated();
        if(requestCarDto.carNumber() != null)
            car.setNumber(carValidation.eitherNumberIsValid(requestCarDto.carNumber()));
        if(requestCarDto.description() != null)
            car.setDescription(carValidation.eitherDescriptionIsValid(requestCarDto.description()));
        if(requestCarDto.carType() != null)
            car.setCarType(CarType.valueOfChecked(requestCarDto.carType()));
        if(requestCarDto.carryingKg() != null)
            car.setCarryingKg(carValidation.eitherCarryingKgIsValid(requestCarDto.carryingKg()));
    }

    @Override
    @Transactional
    public void deleteCar(UUID volunteerId, UUID carId) {
        Car car = eitherIsCarOwner(volunteerId, carId);
        carRepository.deleteById(car.getId());
    }

    @Override
    public Car eitherIsCarOwner(UUID volunteerId, UUID carId) throws RuntimeException {
        Car car = getCar(carId);
        if(car.getVolunteer().getId().equals(volunteerId))
            return car;
        throw new RuntimeException("User isn't an owner of the car");
    }
}
