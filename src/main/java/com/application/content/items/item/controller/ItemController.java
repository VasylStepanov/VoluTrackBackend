package com.application.content.items.item.controller;

import com.application.content.items.item.model.ItemType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {


    @Operation(summary = "Get all possible items' types possible to set.")
    @GetMapping("/getAllItemTypes")
    public ResponseEntity<ItemType[]> getAllItemTypes(){
        return ResponseEntity.ok(ItemType.values());
    }

}
