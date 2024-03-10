package com.application.test;

import com.application.volunteers.address.dto.AddressDto;
import com.application.volunteers.address.repository.AddressRepository;
import com.application.volunteers.car.dto.CarDto;
import com.application.volunteers.car.repository.CarRepository;
import com.application.volunteers.item.dto.ItemDto;
import com.application.volunteers.item.repository.ItemRepository;
import com.application.volunteers.volunteer.dto.VolunteerDto;
import com.application.volunteers.volunteer.repository.VolunteerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/test/volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestVolunteerDataController {

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    AddressRepository addressRepository;

    @Operation(summary = "Get all volunteers' profiles data")
    @GetMapping("/getAllVolunteers")
    public ResponseEntity<List<VolunteerDto>> getAllVolunteers(){
        return ResponseEntity.ok(volunteerRepository.findAll().stream().map(VolunteerDto::toVolunteerDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Get all items")
    @GetMapping("/getAllItems")
    public ResponseEntity<List<ItemDto>> getAllItems(){
        return ResponseEntity.ok(itemRepository.findAll().stream().map(ItemDto::toItemDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Get all cars")
    @GetMapping("/getAllCars")
    public ResponseEntity<List<CarDto>> getAllCars(){
        return ResponseEntity.ok(carRepository.findAll().stream().map(CarDto::toCarDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Get all addresses")
    @GetMapping("/getAllAddresses")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        return ResponseEntity.ok(addressRepository.findAll().stream().map(AddressDto::toAddressDto).collect(Collectors.toList()));
    }

}
