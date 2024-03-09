package com.application.volunteers.car.controller;

import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.model.CarType;
import com.application.volunteers.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Operation(summary = "Get car by volunteerId")
    @GetMapping
    public ResponseEntity<?> getCar(HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            return ResponseEntity.ok(carService.findCarById(volunteerId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get car data by email")
    @GetMapping("/getByEmail")
    public ResponseEntity<?> getCarByEmail(@RequestParam String email){
        try {
            return ResponseEntity.ok(carService.findCarByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all car types", description = "Get list of car types")
    @GetMapping("/getAllCarTypes")
    public ResponseEntity<Set<CarType>> getAllCarTypes(){
        return ResponseEntity.ok(carService.findAllCarTypes());
    }

    @Operation(summary = "Save car", description = "Car number examples: UA - АА0000АА; EU - AA000AA, AA00000. Carrying kg is a maximum weight to transport. To set car type get all possible types from /getAllCarTypes")
    @PostMapping("/save")
    public ResponseEntity<?> saveCar(@RequestBody RequestCarDto requestCarDto, HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            carService.saveCar(volunteerId, requestCarDto);
            return ResponseEntity.ok("Car is saved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update car", description = "Car number examples: UA - АА0000АА; EU - AA000AA, AA00000. Carrying kg is a maximum weight to transport. To set car type get all possible types from /getAllCarTypes")
    @PostMapping("/update")
    public ResponseEntity<?> updateCar(@RequestBody RequestCarDto requestCarDto, HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            carService.updateCar(volunteerId, requestCarDto);
            return ResponseEntity.ok("Car is updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete car")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteCar(HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            carService.deleteCar(volunteerId);
            return ResponseEntity.ok("Car is deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
