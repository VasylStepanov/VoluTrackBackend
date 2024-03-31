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

    @Operation(summary = "Get all car types", description = "Get list of car types")
    @GetMapping("/getAllCarTypes")
    public ResponseEntity<Set<CarType>> getAllCarTypes(){
        return ResponseEntity.ok(carService.getAllCarTypes());
    }


}
