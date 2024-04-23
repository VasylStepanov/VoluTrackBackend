package com.application.volunteers.volunteer.controller;

import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.service.AddressService;
import com.application.volunteers.car.dto.RequestCarDto;
import com.application.volunteers.car.service.CarService;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.SneakyThrows;
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
    JwtService jwtService;

    @Autowired
    CookieUtil cookieUtil;

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
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return ResponseEntity.ok(volunteerService.getPrivateProfileData(jwtService.extractVolunteerId(accessToken)));
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
        UUID volunteerId = getVolunteerId(httpServletRequest);
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
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        UUID volunteerId = jwtService.extractVolunteerId(accessToken);
        carService.saveCar(volunteerId, requestCarDto);
        return ResponseEntity.ok("Car is saved");
    }

    @Operation(summary = "Update car", description = "Car number examples: UA - АА0000АА; EU - AA000AA, AA00000. Carrying kg is a maximum weight to transport. To set car type get all possible types from /getAllCarTypes")
    @PostMapping("/car/update")
    public ResponseEntity<?> updateCar(@RequestBody RequestCarDto requestCarDto, @RequestParam UUID carId, HttpServletRequest httpServletRequest){
        String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        UUID volunteerId = jwtService.extractVolunteerId(accessToken);
        carService.updateCar(volunteerId, carId, requestCarDto);
        return ResponseEntity.ok("Car is updated");
    }

    @Operation(summary = "Delete car")
    @PostMapping("/car/delete")
    public ResponseEntity<?> deleteCar(@RequestParam UUID carId, HttpServletRequest httpServletRequest){
        try {
            String accessToken = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
            UUID volunteerId = jwtService.extractVolunteerId(accessToken);
            carService.deleteCar(volunteerId, carId);
            return ResponseEntity.ok("Car is deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
    * Item endpoints
    * */

    @Operation(summary = "Get all user's items by volunteer id.")
    @GetMapping("/inventory/getAllItems")
    public ResponseEntity<?> getAllItemsByGroupId(HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(inventoryService.findAllItemsByVolunteerId(volunteerId));
    }

    @Operation(summary = "Add item", description = "Amount must be more than 0 but less than 65536. ItemType and ItemMeasurement are enum, there are two endpoints what responses their content.")
    @PostMapping("/inventory/addItem")
    public ResponseEntity<?> addItem(@RequestBody RequestItemDto requestItemDto, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.saveItemByVolunteerId(requestItemDto, volunteerId);
        return ResponseEntity.ok("Item is saved");
    }

    @Operation(summary = "Update item", description = "Only owner of the item can update the item.")
    @PostMapping("/inventory/updateItem")
    public ResponseEntity<?> updateItem(@RequestBody RequestItemDto requestItemDto,
                                        @RequestParam UUID itemId,
                                        HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.updateItemByVolunteerId(requestItemDto, volunteerId, itemId);
        return ResponseEntity.ok("Item is updated");
    }

    @Operation(summary = "Delete item by item id", description = "Only owner of the item can remove the item.")
    @PostMapping("/inventory/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestParam UUID itemId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.deleteItemByVolunteerId(volunteerId, itemId);
        return ResponseEntity.ok("Item is removed");
    }

    @SneakyThrows
    private UUID getVolunteerId(HttpServletRequest httpServletRequest){
        String token = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return jwtService.extractVolunteerId(token);
    }

}
