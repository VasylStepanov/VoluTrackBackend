package com.application.volunteers.item.controller;

import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Operation(summary = "Get all possible items' types possible to set.")
    @GetMapping("/getAllItemTypes")
    public ResponseEntity<Set<ItemType>> getAllItemTypes(){
        return ResponseEntity.ok(itemService.findAllItemTypes());
    }

    @Operation(summary = "Get all possible items' measurement possible to set.")
    @GetMapping("/getAllItemMeasurements")
    public ResponseEntity<Set<ItemMeasurement>> getAllItemMeasurements(){
        return ResponseEntity.ok(itemService.findAllItemMeasurements());
    }
}
