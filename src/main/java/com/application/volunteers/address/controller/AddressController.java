package com.application.volunteers.address.controller;


import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {


    @Autowired
    AddressService addressService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Operation(summary = "Get address by volunteerId", description = "Return address and GPS coordinates")
    @GetMapping
    public ResponseEntity<?> getAddress(HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            return ResponseEntity.ok(addressService.getAddressById(volunteerId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get address by user email", description = "Return address and GPS coordinates")
    @GetMapping("/getByEmail")
    public ResponseEntity<?> getAddressByEmail(@RequestParam String email){
        try {
            return ResponseEntity.ok(addressService.getAddressByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Save address", description = "User must input address data, GPS isn't necessary(GPS is taken from ReactFrontendApp Leaflet)")
    @PostMapping("/save")
    public ResponseEntity<?> saveAddress(@RequestBody RequestAddressDto requestAddressDto, HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            addressService.saveAddress(volunteerId, requestAddressDto);
            return ResponseEntity.ok("Address is saved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update address")
    @PostMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestBody RequestAddressDto requestAddressDto, HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            addressService.updateAddress(volunteerId, requestAddressDto);
            return ResponseEntity.ok("Address is updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
