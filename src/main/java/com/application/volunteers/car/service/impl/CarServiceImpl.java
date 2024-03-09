package com.application.volunteers.car.service.impl;

import com.application.user.service.UserService;
import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.dto.ResponseCarDto;
import com.application.volunteers.car.model.Car;
import com.application.volunteers.car.model.CarType;
import com.application.volunteers.car.repository.CarRepository;
import com.application.volunteers.car.service.CarService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
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
    UserService userService;

    @Autowired
    VolunteerService volunteerService;

    @Override
    public ResponseCarDto findCarById(UUID volunteerId) {
        return ResponseCarDto.toResponseCarDto(getCarByVolunteerId(volunteerId));
    }

    @Override
    public ResponseCarDto findCarByEmail(String email) {
        UUID volunteerId = userService.findUserByEmail(email).getId();
        return ResponseCarDto.toResponseCarDto(getCarByVolunteerId(volunteerId));
    }

    @Override
    public Set<CarType> findAllCarTypes() {
        return Set.of(CarType.values());
    }

    @Override
    public void saveCar(UUID volunteerId, RequestCarDto requestCarDto) {
         carRepository.save(Car.builder()
                .carNumber(carValidation.eitherCarNumberIsValidFull(requestCarDto.carNumber()))
                .carType(CarType.valueOfCheckedFull(requestCarDto.carType()))
                .carryingKg(carValidation.eitherCarCarryingKgIsValidFull(requestCarDto.carryingKg()))
                .volunteer(volunteerService.getVolunteer(volunteerId))
            .build());
    }

    @Override
    @Transactional
    public void updateCar(UUID volunteerId, RequestCarDto requestCarDto) {
        Car car = getCarByVolunteerId(volunteerId);
        car.isUpdated();
        if(requestCarDto.carNumber() != null)
            car.setCarNumber(carValidation.eitherCarNumberIsValid(requestCarDto.carNumber()));
        if(requestCarDto.carType() != null)
            car.setCarType(CarType.valueOfChecked(requestCarDto.carType()));
        if(requestCarDto.carryingKg() != null)
            car.setCarryingKg(carValidation.eitherCarCarryingKgIsValid(requestCarDto.carryingKg()));
    }

    @Override
    @Transactional
    public void deleteCar(UUID volunteerId) {
        Car car = getCarByVolunteerId(volunteerId);
        carRepository.deleteById(car.getId());
    }

    private Car getCarByVolunteerId(UUID volunteerId){
        return carRepository.findByVolunteerId(volunteerId).orElseThrow(() -> new RuntimeException("Car is empty"));
    }
}
