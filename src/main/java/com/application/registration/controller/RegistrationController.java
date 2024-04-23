package com.application.registration.controller;

import com.application.registration.dto.RegistrationRequest;
import com.application.registration.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Operation(summary = "Registration request.",
            description = "Input firstname, lastname, email and password. Data is checked on validation. Send mail on email to confirm account email.")
    @PostMapping
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest){
        try {
            return ResponseEntity.ok(registrationService.register(registrationRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Resend request.",
            description = "Resend confirm mail if confirmation token is expired.")
    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestParam String token){
        return ResponseEntity.ok(registrationService.resendConfirmToken(token));
    }


    @Operation(summary = "Confirm request.",
            description = "Go to email service(e.g. @gmail) and push \"Activate\" button.")
    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token){
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
