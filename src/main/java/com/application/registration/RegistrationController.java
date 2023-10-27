package com.application.registration;

import com.application.registration.dto.request.RegistrationRequest;
import com.application.registration.service.RegistrationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(registrationService.register(registrationRequest));
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestParam String token){
        return ResponseEntity.ok(registrationService.resendConfirmToken(token));
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token){
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
