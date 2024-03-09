package com.application.volunteers.volunteer.controller;


import com.application.security.service.JwtService;
import com.application.security.token.DisabledToken;
import com.application.security.token.DisabledTokenService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.volunteer.dto.VolunteerSetDto;
import com.application.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * Volunteer controller uses both volunteer and user services
 * */
@RestController
@RequestMapping("api/v1/volunteer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    DisabledTokenService disabledTokenService;

    @Operation(summary = "Get profile data", description = "Profile data is a volunteer data with user data.")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            return ResponseEntity.ok(volunteerService.getProfile(jwtService.extractUserId(accessToken)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get profile data", description = "Profile data is a volunteer data with user data.")
    @GetMapping("/profile/getByEmail")
    public ResponseEntity<?> getProfileByUserEmail(@RequestParam String email){
        try {
            return ResponseEntity.ok(volunteerService.getProfileByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update user data", description = "Here user can update his profile data: user data(firstname, lastname and password) and volunteer data.")
    @Transactional
    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(HttpServletRequest httpServletRequest,
                                           @RequestBody VolunteerSetDto volunteer){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            volunteerService.updateProfile(volunteer, jwtService.extractUserId(accessToken));
            return ResponseEntity.ok("Profile data is updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete user account.")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserProfile(HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            disabledTokenService.save(DisabledToken.builder().accessToken(accessToken).build());
            volunteerService.deleteProfile(jwtService.extractUserId(accessToken));
            return ResponseEntity.ok("User is deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
