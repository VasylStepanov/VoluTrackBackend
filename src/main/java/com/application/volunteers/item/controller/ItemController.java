package com.application.volunteers.item.controller;

import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Operation(summary = "Get all user's items by volunteer id from access token.")
    @GetMapping("/getAllItems")
    public ResponseEntity<List<ResponseItemDto>> getAllItems(HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(itemService.findAllItems(volunteerId));
    }

    @Operation(summary = "Get all user's items by email.")
    @GetMapping("/getAllItemsByUserEmail")
    public ResponseEntity<?> getAllItemsByUserEmail(@RequestParam String email){
        try {
            return ResponseEntity.ok(itemService.findAllItemsByUserEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all possible items' types possible to set.")
    @GetMapping("/getAllItemTypes")
    public ResponseEntity<List<ItemType>> getAllItemTypes(){
        return ResponseEntity.ok(itemService.getAllItemTypes());
    }

    @Operation(summary = "Get all possible items' measurement possible to set.")
    @GetMapping("/getAllItemMeasurements")
    public ResponseEntity<List<ItemMeasurement>> getAllItemMeasurements(){
        return ResponseEntity.ok(itemService.getAllItemMeasurements());
    }

    @Operation(summary = "Get item by itemId")
    @GetMapping("/getItem")
    public ResponseEntity<?> getItem(@RequestParam UUID itemId){
        try {
            return ResponseEntity.ok(itemService.getItemById(itemId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Add item", description = "Amount must be more than 0 but less than 65536. ItemType and ItemMeasurement are enum, there are two endpoints what responses their content.")
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody RequestItemDto requestItemDto, HttpServletRequest httpServletRequest){
        try {
            UUID volunteerId = getVolunteerId(httpServletRequest);
            itemService.saveItem(volunteerId, requestItemDto);
            return ResponseEntity.ok("Item is saved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update item", description = "Only owner of the item can remove the item.")
    @PostMapping("/updateItem")
    public ResponseEntity<?> updateItem(@RequestParam UUID itemId, @RequestBody RequestItemDto requestItemDto, HttpServletRequest httpServletRequest){
        try {
            UUID volunteerId = getVolunteerId(httpServletRequest);
            itemService.updateItem(volunteerId, itemId, requestItemDto);
            return ResponseEntity.ok("Item is updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete item by item id", description = "Only owner of the item can remove the item.")
    @PostMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestParam UUID itemId, HttpServletRequest httpServletRequest){
        try {
            UUID volunteerId = getVolunteerId(httpServletRequest);
            itemService.deleteItemById(volunteerId, itemId);
            return ResponseEntity.ok("Item is removed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @SneakyThrows
    private UUID getVolunteerId(HttpServletRequest httpServletRequest){
        String token = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return jwtService.extractVolunteerId(token);
    }
}
