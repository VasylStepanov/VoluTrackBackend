package com.application.volunteers.volunteer.controller;

import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.service.AddressService;
import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.service.CarService;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * Volunteer controller uses both volunteer and user services
 * */
@RestController
@RequestMapping("api/v1/volunteer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    AddressService addressService;

    @Autowired
    CarService carService;

    @Operation(summary = "Get profile data", description = "Profile data is a volunteer data with user data.")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(volunteerService.getPrivateProfileData(volunteerService.getVolunteerId(httpServletRequest)));
    }

    @Operation(summary = "Get profile public data by volunteer id", description = "Profile data is a volunteer data with user data.")
    @GetMapping("/profile/getById")
    public ResponseEntity<?> getProfileByVolunteerId(@RequestParam UUID id){
        return ResponseEntity.ok(volunteerService.getPublicProfileData(id));
    }

    /*
    * Address endpoints
    * */

    @Operation(summary = "Update user's address", description = "If it's first request, than address is saved, next requests will update the address.")
    @PostMapping("/address/updateAddress")
    public ResponseEntity<?> updateAddress(@RequestBody RequestAddressDto requestAddressDto, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        addressService.updateAddress(volunteer, requestAddressDto);
        return ResponseEntity.ok("Address is updated");
    }

    /*
    * Car endpoints
    * */

    @Operation(summary = "Save car", description = "Car number examples: UA - АА0000АА; EU - AA000AA, AA00000. Carrying kg is a maximum weight to transport. To set car type get all possible types from /getAllCarTypes")
    @PostMapping("/car/save")
    public ResponseEntity<?> saveCar(@RequestBody RequestCarDto requestCarDto, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        carService.saveCar(volunteerId, requestCarDto);
        return ResponseEntity.ok("Car is saved");
    }

    @Operation(summary = "Update car", description = "Car number examples: UA - АА0000АА; EU - AA000AA, AA00000. Carrying kg is a maximum weight to transport. To set car type get all possible types from /getAllCarTypes")
    @PostMapping("/car/update")
    public ResponseEntity<?> updateCar(@RequestBody RequestCarDto requestCarDto, @RequestParam UUID carId, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        carService.updateCar(volunteerId, carId, requestCarDto);
        return ResponseEntity.ok("Car is updated");
    }

    @Operation(summary = "Delete car")
    @PostMapping("/car/delete")
    public ResponseEntity<?> deleteCar(@RequestParam UUID carId, HttpServletRequest httpServletRequest){
        try {
            UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
            carService.deleteCar(volunteerId, carId);
            return ResponseEntity.ok("Car is deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
