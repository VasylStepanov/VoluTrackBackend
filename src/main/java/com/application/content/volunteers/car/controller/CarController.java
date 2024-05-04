package com.application.content.volunteers.car.controller;

import com.application.content.volunteers.car.model.CarType;
import com.application.content.volunteers.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    @Autowired
    CarService carService;

    @Operation(summary = "Get all car types", description = "Get list of car types")
    @GetMapping("/getAllCarTypes")
    public ResponseEntity<Set<CarType>> getAllCarTypes(){
        return ResponseEntity.ok(carService.getAllCarTypes());
    }


}
